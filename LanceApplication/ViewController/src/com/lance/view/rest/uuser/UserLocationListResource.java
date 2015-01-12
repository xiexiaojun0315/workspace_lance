package com.lance.view.rest.uuser;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.user.vo.UUserVORowImpl;
import com.lance.model.user.vo.UserLocationListVOImpl;
import com.lance.model.vo.LancerLocationListVOImpl;
import com.lance.model.vo.LancerLocationListVORowImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;
import com.zngh.platform.front.core.view.RestUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import oracle.jbo.Key;
import oracle.jbo.Row;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * User地址列表维护
 *
 Uuid,Precision:32,JavaType:java.lang.String
 RegionId,Precision:32,JavaType:java.lang.String 州
 CountryId,Precision:32,JavaType:java.lang.String 国家
 ProvinceId,Precision:32,JavaType:java.lang.String 省
 CityId,Precision:32,JavaType:java.lang.String 
 AreaId,Precision:32,JavaType:java.lang.String
 CurrentIn,Precision:0,JavaType:oracle.jbo.domain.Number
 Telphone,Precision:20,JavaType:java.lang.String
 FaxNumber,Precision:20,JavaType:java.lang.String
 ZipCode,Precision:20,JavaType:java.lang.String
 DetailLoc,Precision:100,JavaType:java.lang.String
 CreateBy,Precision:32,JavaType:java.lang.String
 CreateOn,Precision:0,JavaType:oracle.jbo.domain.Date
 ModifyBy,Precision:32,JavaType:java.lang.String
 ModifyOn,Precision:0,JavaType:oracle.jbo.domain.Date
 Version,Precision:0,JavaType:java.math.BigDecimal
 UserName,Precision:50,JavaType:java.lang.String
 */

@Path("user/location")
public class UserLocationListResource extends BaseRestResource {

    /**
     * 字段说明
     * RegionId、CountryId、ProvinceId、CityId、AreaId、DetailLoc
     * 州，国家，省，城市，区/县，详细地址（街道、小区等）
     * ZipCode 邮编
     * Telphone 座机
     * FaxNumber 传真
     * 以上字段国家必选，其它全部可选
     */
    public static final String[] ATTR_ALL = {
        "Uuid", "RegionId", "CountryId", "ProvinceId", "CityId", "AreaId", "CurrentIn", "Telphone", "FaxNumber",
        "ZipCode", "DetailLoc", "CreateBy", "CreateOn", "ModifyBy", "ModifyOn", "Version", "UserName"
    };


    public static final String[] ATTR_CREATE = {
        "RegionId", "CountryId", "ProvinceId", "CityId", "AreaId", "CurrentIn", "Telphone", "FaxNumber", "ZipCode",
        "DetailLoc"
    };

    public static final String[] ATTR_GET = {
        "Uuid", "RegionId", "CountryId", "ProvinceId", "CityId", "AreaId", "CurrentIn", "Telphone", "FaxNumber",
        "ZipCode", "DetailLoc"
    };

    public static final String[] ATTR_GET_SIMPLE = { "CountryId", "ProvinceId", "CityId" };

    public static final String[] ATTR_UPDATE = {
        "RegionId", "CountryId", "ProvinceId", "CityId", "AreaId", "CurrentIn", "Telphone", "FaxNumber", "ZipCode",
        "DetailLoc"
    };

    public UserLocationListResource() {
    }


    /**
     * 新增地址(地址列表不能大于5条)
     * @param lancerId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Path("{userName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createLocation(@PathParam("userName") String userName, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        //check
        LUtil.getUUserByName(userName, am);
        UserLocationListVOImpl vo = am.getUserLocationList1();
        if (vo.getRowCount() >= 5) {
            return "error:常用地址不能大于5条";
        }

        //新增
        Row row = LUtil.createInsertRow(vo);
        RestUtil.copyJsonObjectToRow(json, vo, row, ATTR_CREATE);
        am.getDBTransaction().commit();
        return (String) row.getAttribute("Uuid");
    }

    /**
     * 用于内部调用，在设置好Current UUser的情况下
     * @param userName
     * @param json
     * @param am
     * @return
     * @throws JSONException
     */
    public String createLocationFn(String userName, JSONObject json, LanceRestAMImpl am) throws JSONException {
        UserLocationListVOImpl vo = am.getUserLocationList1();
        Row row = LUtil.createInsertRow(vo);
        RestUtil.copyJsonObjectToRow(json, vo, row, ATTR_CREATE);
        return (String) row.getAttribute("Uuid");
    }

    /**
     * 修改地址信息
     * @param userName
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update/{userName}/{locationId}")
    public String updateLocation(@PathParam("userName") String userName, @PathParam("locationId") String locationId,
                                 JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        updateLocationFn(userName, locationId, json, am);
        am.getDBTransaction().commit();
        return "ok";
    }


    public String updateLocationFn(String userName, String locationId, JSONObject json,
                                   LanceRestAMImpl am) throws JSONException {
        LUtil.getUUserByName(userName, am);
        UserLocationListVOImpl vo2 = am.getUserLocationList1();
        Row row = LUtil.getByKey(vo2, locationId);
        RestUtil.copyJsonObjectToRow(json, vo2, row, this.ATTR_UPDATE);
        return "ok";
    }

    /**
     * 查询单条地址
     * @param lancerId
     * @param locationId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("{userName}/{locationId}")
    public JSONObject findLocation(@PathParam("userName") String userName,
                                   @PathParam("locationId") String locationId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        LUtil.getUUserByName(userName, am);
        UserLocationListVOImpl vo2 = am.getUserLocationList1();
        Row row = LUtil.getByKey(vo2, locationId);
        return RestUtil.convertRowToJsonObject(vo2, row, this.ATTR_GET);
    }

    @GET
    @Path("all/{userName}")
    public JSONArray findAllLocation(@PathParam("userName") String userName) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        LUtil.getUUserByName(userName, am);
        UserLocationListVOImpl vo2 = am.getUserLocationList1();
        return RestUtil.convertVoToJsonArray(vo2, this.ATTR_GET);
    }

    /**
     * 删除地址
     * 如果被删除地址属于常用地址，会同时删除此常用地址
     * 
     * @param userName
     * @param locationId
     * @return
     * @throws JSONException
     */
    @POST
    @Path("delete/{userName}/{locationId}")
    public String deleteUserLocation(@PathParam("userName") String userName,
                                     @PathParam("locationId") String locationId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
       
        //被设为常用地址的情况下的处理
        UUserVORowImpl userRow = LUtil.getUUserByName(userName, am);
        if(locationId.equals( userRow.getLocationA())){
            userRow.setLocationA(null);
        }
        if(locationId.equals( userRow.getLocationB())){
            userRow.setLocationB(null);
        }
        
        //
        UserLocationListVOImpl locationVO = am.getUserLocationList1();
        Row locationRow = LUtil.getByKey(locationVO, locationId);
        locationVO.setCurrentRow(locationRow);
        locationVO.removeCurrentRow();
        
        am.getDBTransaction().commit();
        return "ok";
    }

    //to delete
//    public JSONObject findLocationFn(String locationId, LanceRestAMImpl am) throws JSONException {
//        LancerLocationListVOImpl vo = am.getLancerLocationList2();
//        LancerLocationListVORowImpl row = (LancerLocationListVORowImpl) vo.findByKey(new Key(new Object[] {
//                                                                                             locationId }), 1)[0];
//        return convertLocationName(this.convertRowToJsonObject(row, this.ATTR_GET_SIMPLE));
//    }

    //to delete
    public JSONObject convertLocationName(JSONObject locJson) throws JSONException {
        if (locJson.has("CountryId")) {
            locJson.put("CountryName", LUtil.getCountryById(locJson.getString("CountryId")));
        }
        if (locJson.has("ProvinceId")) {
            locJson.put("ProvinceName", LUtil.getProvinceById(locJson.getString("ProvinceId")));
        }
        if (locJson.has("CityId")) {
            locJson.put("CityName", LUtil.getCityById(locJson.getString("CityId")));
        }
        return locJson;
    }


}
