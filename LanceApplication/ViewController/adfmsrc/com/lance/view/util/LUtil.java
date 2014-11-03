package com.lance.view.util;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.LancerVOImpl;
import com.lance.model.vo.LancerVORowImpl;
import com.lance.view.rest.user.ClientResource;
import com.lance.view.rest.user.LancerResource;

import com.zngh.platform.front.core.view.RestUtil;

import oracle.jbo.Row;
import oracle.jbo.server.RowImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class LUtil {

    public static final String USER_TYPE_CLIENT = "CLIENT";
    public static final String USER_TYPE_LANCER = "LANCER";

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

}
