package com.lance.view.util;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.user.vo.UUserVOImpl;
import com.lance.model.user.vo.UUserVORowImpl;
import com.lance.model.vo.LocationCityVOImpl;
import com.lance.model.vo.LocationCityVORowImpl;
import com.lance.model.vo.LocationProvinceVOImpl;
import com.lance.model.vo.LocationProvinceVORowImpl;
import com.lance.model.vvo.LocationCountryVVOImpl;
import com.lance.model.vvo.LocationCountryVVORowImpl;
//import com.lance.view.rest.user.LancerResource;

import com.zngh.platform.front.core.view.RestUtil;

import java.util.HashMap;
import java.util.Map;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
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
            e.printStackTrace();
        }
        return null;
    }

    //    public static JSONObject convertVoToJsonObject(ViewObjectImpl vo){
    //
    //        }
    //
    //    public static JSONObject convertRowToJsonObject(){
    //
    //        }

    public static RowImpl createInsertRow(ViewObjectImpl vo) {
        RowImpl row = (RowImpl) vo.createRow();
        vo.insertRow(row);
        return row;
    }


    public static void transJsonToRow(JSONObject json, ViewObjectImpl vo, Row row,
                                      String[] attrs) throws JSONException {
        for (String attr : attrs) {
            System.out.println(attr);
            if (json.has(attr)) {
                row.setAttribute(attr, json.get(attr));
            }
        }
    }


    public static void transJsonToRow(JSONObject json, Row row, String[] attrs) throws JSONException {
        for (String attr : attrs) {
            System.out.println(attr);
            if (json.has(attr)) {
                row.setAttribute(attr, json.get(attr));
            }
        }
    }

    //to delete
//    public static LancerVORowImpl findLancerById(String lancerId, LanceRestAMImpl am) {
//        LancerVOImpl lancerVO = am.getLancer1();
//        //存在性判断
//        LancerVORowImpl row = (LancerVORowImpl) lancerVO.getCurrentRow();
//        if (row != null && row.getUuid().equals(lancerId)) {
//            return row;
//        }
//
//        lancerVO.setApplyViewCriteriaName("FindByUuidVC");
//        lancerVO.setpUuid(lancerId);
//        lancerVO.executeQuery();
//        lancerVO.setApplyViewCriteriaName(null);
//        row = (LancerVORowImpl) lancerVO.first();
//        if (row != null) {
//            lancerVO.setCurrentRow(row);
//        }
//        return row;
//    }

    /**
     * 确保userName对应的User为CurrentRow
     * @param userName
     * @param am
     * @return
     */
    public static UUserVORowImpl getUUserByName(String userName, LanceRestAMImpl am) {
        UUserVOImpl userVO = am.getUUser1();
        //存在性判断
        UUserVORowImpl row = (UUserVORowImpl) userVO.getCurrentRow();
        if (row != null && row.getUserName().equals(userName)) {
            return row;
        }

        userVO.setApplyViewCriteriaName("FindByUserNameVC");
        userVO.setpUserName(userName);
        userVO.executeQuery();
        userVO.removeApplyViewCriteriaName("FindByUserNameVC");
        return (UUserVORowImpl) userVO.first();
    }

    public static Row getByKey(ViewObject vo, Object key) {
        Row[] rows = vo.findByKey(new Key(new Object[] { key }), 1);
        if (rows == null||rows.length==0) {
            return null;
        }
        vo.setCurrentRow(rows[0]);
        return rows[0];
    }

    public static Row getByKey(ViewObject vo, Object key1, Object key2) {
        Row[] rows = vo.findByKey(new Key(new Object[] { key1, key2 }), 1);
        if (rows == null||rows.length==0) {
            return null;
        }
        vo.setCurrentRow(rows[0]);
        return rows[0];
    }

    /**
     * todo remove
     * 根据UserId获取UserDisplayName
     * @param userId 同uuid，userName
     * @param type 用户类型：CLIENT,LANCER
     * @param am
     * @return
     * @throws JSONException
     */
    public static String findUserDisplayNameById(String userId, String type, LanceRestAMImpl am) throws JSONException {
        if (USER_TYPE_CLIENT.equals(type)) {
//            return ClientResource.findClientByIdFn(userId, am).getString("DisplayName");
        } else if (USER_TYPE_LANCER.equals(type)) {
//            return LancerResource.findLancerByIdFn(userId, am).getString("DisplayName");
            return null;
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
    
    public static JSONObject createJsonSuccess() throws JSONException {
        JSONObject json=new JSONObject();
        json.put("status", "ok");
        return json;
    }
    
    public static JSONObject createJsonMsg(String msg) throws JSONException {
        JSONObject json=new JSONObject();
        json.put("status", "msg");
        json.put("msg", msg);
        return json;
    }
    
    public static JSONObject createJsonError(String msg) throws JSONException {
        JSONObject json=new JSONObject();
        json.put("status", "error");
        json.put("error", msg);
        return json;
    }

}
