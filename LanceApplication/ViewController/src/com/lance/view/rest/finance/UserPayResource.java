package com.lance.view.rest.finance;

import com.lance.model.util.PriceConstant;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 资源代码模版
 *
 * 文本替换示例
 * Template ：Company
 * templateId ： companyId
 * 模版 ： 公司
 *
 *
 *
 */
@Path("pay/user")
public class UserPayResource extends BaseRestResource {

    public UserPayResource() {
    }

    /**
     * 根据用户级别（vip）获取用户价格变量
     * 
     * @return
     * @throws JSONException
     */
    @GET
    @Path("priceMap")
    public JSONObject getPriceMap() throws JSONException {
        JSONObject json = new JSONObject();
        if (this.isUserInRole("vip")) {
            //lancer提出价格，网站像
            json.put("lancer_argue_rate", PriceConstant.VIP_PRICE_RATE_YEAR_1);
        } else {
            json.put("lancer_argue_rate", PriceConstant.NORMAL_CLIENT_PRICE_RATE);
        }
        return json;
    }

    /**
     * Lancer填写合同价格后
     * 网站像
     * @return
     */
    public String getContractArguePrice() {
        return null;
    }


}
