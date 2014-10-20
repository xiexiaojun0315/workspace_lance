package com.lance.view.rest.location;

import com.lance.model.LanceRestAMImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

@Path("location")
public class LocationResource extends BaseRestResource {

    public static final String[] ATTR_GET_PROVINCE = { "Uuid", "ProvinceName" };
    public static final String[] ATTR_GET_CITY = { "Uuid", "CityName" };

    public LocationResource() {
    }

    /**
     * 获取中国所有城市
     * GET http://localhost:7101/lance/res/location/province
     * 
     *[
        {
            "Uuid" : 1,
            "ProvinceName" : "北京市"
        },
        {
            "Uuid" : 9,
            "ProvinceName" : "上海市"
        }
     ]
     *
     * @return
     * @throws JSONException
     */
    @GET
    @Path("province")
    public JSONArray getAllProvince() throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObject vo = am.getLocationProvince1();
        vo.executeQuery();
        return this.convertVoToJsonArray(vo, this.ATTR_GET_PROVINCE);
    }


    /**
     * 根据省的ID获取城市列表
     * GET http://localhost:7101/lance/res/location/cityByProvince/6
     *
     * [
        {
            "Uuid" : "39",
            "CityName" : "沈阳市"
        },
        {
            "Uuid" : "40",
            "CityName" : "大连市"
        }
        ]
     *
     * @param provinceId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("cityByProvince/{provinceId}")
    public JSONArray getAllCityByProvince(@PathParam("provinceId") String provinceId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObject vo = am.getLocationProvince1();
        Row row = vo.findByKey(new Key(new Object[] { provinceId }), 1)[0];
        vo.setCurrentRow(row);
        ViewObject vo2 = am.getLocationCity1();
        return this.convertVoToJsonArray(vo2, this.ATTR_GET_CITY);
    }
}
