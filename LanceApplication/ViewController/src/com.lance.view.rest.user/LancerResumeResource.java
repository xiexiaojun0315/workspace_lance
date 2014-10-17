package com.lance.view.rest.user;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.LancerResumeVOImpl;
import com.lance.model.vo.LancerResumeVORowImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oracle.jbo.Row;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Lancer简历信息维护
 */
@Path("user/lancer/resume")
public class LancerResumeResource extends BaseRestResource {
    /**
     * PaymentTerms 支付方式 长度不能大于4000
     * ServiceDescription 服务描述 长度不能大于8000
     * 其它字段详见简历页面截图
     */
    public static final String[] ATTR_MERGE = {
        "ChargeRate", "HourlyRate", "Keywords", "Overview", "PaymentTermsTxt", "ServiceDescriptionTxt", "Tagline",
        "Video", "VideoUrl"
    };
    public static final String[] ATTR_GET = {
        "ChargeRate", "HourlyRate", "Keywords", "Overview", "PaymentTermsTxt", "ServiceDescriptionTxt", "Tagline",
        "Video", "VideoUrl", "Uuid", "LancerId"
    };

    public LancerResumeResource() {
    }

    @POST
    @Path("merge/{lancerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    //    @Produces(MediaType.TEXT_PLAIN)
    public String mergeLancerResume(@PathParam("lancerId") String lancerId, JSONObject json) throws JSONException {
        System.out.println("mergeLancerResume");
        LanceRestAMImpl am = LUtil.findLanceAM();
        mergeLancerResumeFn(json, lancerId, am);
        am.getDBTransaction().commit();
        return "ok";
    }

    public String mergeLancerResumeFn(JSONObject json, String lancerId, LanceRestAMImpl am) throws JSONException {
        LancerResumeVOImpl vo = am.getLancerResume1();
        vo.setApplyViewCriteriaName("FindByLancerIdVC");
        vo.setpLancerId(lancerId);
        vo.executeQuery();
        vo.setApplyViewCriteriaName(null);
        LancerResumeVORowImpl row = (LancerResumeVORowImpl) vo.first();
        if (row == null) {
            row = (LancerResumeVORowImpl) LUtil.createInsertRow(vo);
            row.setLancerId(lancerId);
        }
        for (String attr : ATTR_MERGE) {
            if (json.has(attr)) {
                //对金额类型数字进行单独处理
                if ("ChargeRate".equals(attr)) {
                    row.setChargeRate(new BigDecimal(json.getString("ChargeRate")));
                } else if ("HourlyRate".equals(attr)) {
                    row.setHourlyRate(new BigDecimal(json.getString("HourlyRate")));
                } else {
                    row.setAttribute(attr, json.get(attr));
                }
            }
        }
        return "ok";
    }

    /**
     *
     * @example
     * GET http://localhost:7101/lance/res/user/lancer/resume/muhongdi
     * 结果为空
     * {}
     * 结果不为空
     * http://localhost:7101/lance/res/user/lancer/resume/muhongdi
     {
        "ChargeRate" : 100.13,
        "HourlyRate" : 100,
        "Keywords" : "专家,Test，replace中文逗号为英文逗号",
        "Overview" : "这是overview",
        "PaymentTermsTxt" : "这是支付方式描述",
        "ServiceDescriptionTxt" : "这是服务描述",
        "Tagline" : "这是tagLine",
        "Uuid" : "1",
        "LancerId" : "muhongdi"
    }
     *
     * @param lancerId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("{lancerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getLancerResume(String lancerId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        return findLancerResumeByLancerIdFn(lancerId, am);
    }

    /**
     * 根据LancerId获得Resume简历
     *
     * @param lancerId
     * @param am
     * @return Resume记录或空json
     * @throws JSONException
     */
    public JSONObject findLancerResumeByLancerIdFn(String lancerId, LanceRestAMImpl am) throws JSONException {
        LancerResumeVOImpl vo = am.getLancerResume1();
        vo.setApplyViewCriteriaName("FindByLancerIdVC");
        vo.setpLancerId(lancerId);
        vo.executeQuery();
        vo.setApplyViewCriteriaName(null);
        Row row = vo.first();
        if (row == null) {
            return new JSONObject();
        }
        vo.setCurrentRow(row);
        return this.convertRowToJsonObject(row, this.ATTR_GET);
    }


}
