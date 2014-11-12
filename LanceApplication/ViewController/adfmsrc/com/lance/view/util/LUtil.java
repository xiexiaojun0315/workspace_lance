package com.lance.view.util;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.LancerVOImpl;
import com.lance.model.vo.LancerVORowImpl;
import com.lance.model.vo.LocationCityVOImpl;
import com.lance.model.vo.LocationCityVORowImpl;
import com.lance.model.vo.LocationProvinceVOImpl;
import com.lance.model.vo.LocationProvinceVORowImpl;
import com.lance.model.vvo.LocationCountryVVOImpl;
import com.lance.model.vvo.LocationCountryVVORowImpl;
import com.lance.view.rest.user.ClientResource;
import com.lance.view.rest.user.LancerResource;

import com.zngh.platform.front.core.view.RestUtil;

import java.util.HashMap;
import java.util.Map;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.RowImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class LUtil {

    public static final String USER_TYPE_CLIENT = "CLIENT";
    public static final String USER_TYPE_LANCER = "LANCER";

    public static boolean LOCATION_INITED = false;
    public static Map LOCATION_MAP_PROVINCE = new HashMap();
    public static Map LOCATION_MAP_CITY = new HashMap();
    public static Map LOCATION_MAP_COUNTRY = new HashMap();

    public LUtil() {
        super();
    }

    public static LanceRestAMImpl findLanceAM() {
        try {
            LanceRestAMImpl am = (LanceRestAMImpl) RestUtil.findAmFromBinding("LanceRestAMDataControl");
            return am;
        } catch (Exception e) {
            System.err.println("获取AM失败，请关闭浏览器重试");
            e.printStackTrace();
        }
        return null;
    }

    public static RowImpl createInsertRow(ViewObjectImpl vo) {
        RowImpl row = (RowImpl) vo.createRow();
        vo.insertRow(row);
        return row;
    }

    public static void transJsonToRow(JSONObject json, Row row, String[] attrs) throws JSONException {
        System.out.println("transJsonToRow");
        for (String attr : attrs) {
            System.out.println(attr);
            if (json.has(attr)) {
                System.out.println("copy:" + attr + ":" + json.get(attr));
                row.setAttribute(attr, json.get(attr));
            }
        }
    }

    public static LancerVORowImpl findLancerById(String lancerId, LanceRestAMImpl am) {
        LancerVOImpl lancerVO = am.getLancer1();
        //存在性判断
        LancerVORowImpl row = (LancerVORowImpl) lancerVO.getCurrentRow();
        if (row != null && row.getUuid().equals(lancerId)) {
            return row;
        }

        lancerVO.setApplyViewCriteriaName("FindByUuidVC");
        lancerVO.setpUuid(lancerId);
        lancerVO.executeQuery();
        lancerVO.setApplyViewCriteriaName(null);
        row = (LancerVORowImpl) lancerVO.first();
        if (row != null) {
            lancerVO.setCurrentRow(row);
        }
        return row;
    }

    /**
     * 根据UserId获取UserDisplayName
     * @param userId 同uuid，userName
     * @param type 用户类型：CLIENT,LANCER
     * @param am
     * @return
     * @throws JSONException
     */
    public static String findUserDisplayNameById(String userId, String type, LanceRestAMImpl am) throws JSONException {
        if (USER_TYPE_CLIENT.equals(type)) {
            return ClientResource.findClientByIdFn(userId, am).getString("DisplayName");
        } else if (USER_TYPE_LANCER.equals(type)) {
            return LancerResource.findLancerByIdFn(userId, am).getString("DisplayName");
        }
        System.err.println("无法识别的type");
        return null;
    }

    public static boolean jsonHasNullAttrs(JSONObject json, String[] attrs) throws JSONException {
        for (String attr : attrs) {
            if ((!json.has(attr)) || (json.get(attr) == null)) {
                return true;
            }
        }
        return false;
    }

    public static String getCountryById(String id) {
        if (!LOCATION_INITED) {
            initLocation();
        }
        return (String) LOCATION_MAP_COUNTRY.get(id);
    }

    public static String getProvinceById(String id) {
        if (!LOCATION_INITED) {
            initLocation();
        }
        return (String) LOCATION_MAP_PROVINCE.get(id);
    }

    public static String getCityById(String id) {
        if (!LOCATION_INITED) {
            initLocation();
        }
        return (String) LOCATION_MAP_CITY.get(id);
    }

    public static void initLocation() {
        LanceRestAMImpl am = findLanceAM();
        LocationCountryVVOImpl vo1 = am.getLocationCountryV2();
        RowSetIterator it1 = vo1.createRowSetIterator(null);
        LocationCountryVVORowImpl row1;
        while (it1.hasNext()) {
            row1 = (LocationCountryVVORowImpl) it1.next();
            LOCATION_MAP_COUNTRY.put(row1.getUuid(), row1.getNameLoc());
        }
        it1.closeRowSetIterator();

        LocationProvinceVOImpl vo2 = am.getLocationProvince1();
        RowSetIterator it2 = vo2.createRowSetIterator(null);
        LocationProvinceVORowImpl row2;
        while (it2.hasNext()) {
            row2 = (LocationProvinceVORowImpl) it2.next();
            LOCATION_MAP_PROVINCE.put("" + row2.getUuid(), row2.getProvinceName());
        }
        it2.closeRowSetIterator();

        LocationCityVOImpl vo3 = am.getLocationCity2();
        RowSetIterator it3 = vo3.createRowSetIterator(null);
        LocationCityVORowImpl row3;
        while (it3.hasNext()) {
            row3 = (LocationCityVORowImpl) it3.next();
            LOCATION_MAP_CITY.put(row3.getUuid(), row3.getCityName());
        }
        it3.closeRowSetIterator();

        LOCATION_INITED = true;
    }

}
