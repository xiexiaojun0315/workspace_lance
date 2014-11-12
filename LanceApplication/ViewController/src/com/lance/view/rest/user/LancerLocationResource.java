package com.lance.view.rest.user;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.LancerLocationListVOImpl;
import com.lance.model.vo.LancerLocationListVORowImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import oracle.jbo.Key;
import oracle.jbo.Row;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("user/lancer/location")
public class LancerLocationResource extends BaseRestResource {

    /**
     * 字段说明
     * RegionId、CountryId、ProvinceId、CityId、AreaId、DetailLoc
     * 州，国家，省，城市，区/县，详细地址（街道、小区等）
     * ZipCode 邮编
     * Telphone 座机
     * FaxNumber 传真
     * 以上字段国家必选，其它全部可选
     */
    public static final String[] ATTR_CREATE = { "LancerId" };
    public static final String[] ATTR_GET = {
        "Uuid", "AreaId", "CityId", "CountryId", "CurrentIn", "FaxNumber", "LancerId", "ProvinceId", "RegionId",
        "Telphone", "ZipCode", "DetailLoc"
    };
    public static final String[] ATTR_GET_SIMPLE = { "CountryId", "ProvinceId", "CityId" };
    public static final String[] ATTR_UPDATE = {
        "AreaId", "CityId", "CountryId", "CurrentIn", "FaxNumber", "ProvinceId", "RegionId", "Telphone", "ZipCode",
        "DetailLoc"
    };

    public LancerLocationResource() {
    }

    /**
     * 用于内部调用
     * @param lancerId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Path("{lancerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createLocation(@PathParam("lancerId") String lancerId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        String id = createLocationFn(lancerId, json, am);
        am.getDBTransaction().commit();
        return id;
    }

    public String createLocationFn(String lancerId, JSONObject json, LanceRestAMImpl am) throws JSONException {
        LancerLocationListVOImpl vo = am.getLancerLocationList1();
        Row row = LUtil.createInsertRow(vo);
        row.setAttribute("LancerId", lancerId);
        LUtil.transJsonToRow(json, row, ATTR_CREATE);
        return (String) row.getAttribute("Uuid");
    }

    /**
     * 用于内部调用
     * @param lancerId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update/{userId}")
    public String updateLocation(@PathParam("lancerId") String lancerId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        updateLocationFn(lancerId, json, am);
        am.getDBTransaction().commit();
        return "ok";
    }


    public String updateLocationFn(String lancerId, JSONObject json, LanceRestAMImpl am) throws JSONException {
        LUtil.findLancerById(lancerId, am);
        LancerLocationListVOImpl vo2 = am.getLancerLocationList1();
        String locId = json.getString("Uuid");
        Row row2 = vo2.findByKey(new Key(new Object[] { locId }), 1)[0];
        LUtil.transJsonToRow(json, row2, this.ATTR_UPDATE);
        return "ok";
    }

    /**
     * 用于内部调用
     * @param lancerId
     * @param locationId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("{lancerId}/{locationId}")
    public JSONObject findLocation(@PathParam("lancerId") String lancerId,
                                   @PathParam("locationId") String locationId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        LUtil.findLancerById(lancerId, am);
        LancerLocationListVOImpl vo = am.getLancerLocationList1();
        Row row = vo.findByKey(new Key(new Object[] { locationId }), 1)[0];
        return this.convertRowToJsonObject(row, this.ATTR_GET);
    }

    public JSONObject findLocationByIdFn(String locationId, LanceRestAMImpl am) throws JSONException {
        System.out.println("findLocationByIdFn:" + locationId);
        LancerLocationListVOImpl vo = am.getLancerLocationList2();
        LancerLocationListVORowImpl row = (LancerLocationListVORowImpl) vo.findByKey(new Key(new Object[] {
                                                                                             locationId }), 1)[0];
        return convertLocationName(this.convertRowToJsonObject(row, this.ATTR_GET_SIMPLE));
    }

    public JSONObject convertLocationName(JSONObject locJson) throws JSONException {
        System.out.println("convertLocationName:" + locJson);
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
