package com.lance.view.rest.location;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vvo.LocationCountryVVOImpl;
import com.lance.view.util.LanceRestUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

@Path("location/country")
public class CountryResource extends BaseRestResource {

    public static final String[] ATTR_GET = { "Uuid", "Name" };

    public CountryResource() {
    }

    /**
     * 获取全部国家列表
     * 已按优先级排序
     * 推荐：默认显示中国（ID：44），只在用户点击时才加载其他国家
     * 建议使用下拉选单列出全部国家，不再根据输入进行快速提示
     * 
     * http://localhost:7101/lance/res/location/country/list
     * 
     * @return
     * [
        {
            "Uuid" : "44",
            "Name" : "China"
        },
        {
            "Uuid" : "227",
            "Name" : "United States"
        },
        {
            "Uuid" : "226",
            "Name" : "United Kingdom"
        },
        ......
       ]
     *
     */
    @GET
    @Path("list")
    public JSONArray getAllCountry() throws JSONException {
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        LocationCountryVVOImpl vo = am.getLocationCountryV2();
        return this.convertVoToJsonArray(vo, this.ATTR_GET);
    }
}
