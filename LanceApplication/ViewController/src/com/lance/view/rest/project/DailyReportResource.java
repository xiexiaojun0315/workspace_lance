package com.lance.view.rest.project;

import com.lance.model.LanceRestAMImpl;
import com.lance.view.util.LUtil;

import com.lance.view.util.RestSecurityUtil;

import com.zngh.platform.front.core.view.BaseRestResource;
import com.zngh.platform.front.core.view.RestUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Row;
import oracle.jbo.server.RowImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 每日工作报告
 *
 * 文本替换示例
 * DailyReport ：Company
 * reportId ： companyId
 * 每日工作报告 ： 公司
 *
 *
 *
 */
@Path("dailyReport")
public class DailyReportResource extends BaseRestResource {

    public DailyReportResource() {
    }

    //------------------------------implements below--------------------------

    public final ADFLogger LOGGER = ADFLogger.createADFLogger(DailyReportResource.class);

    /**
        Uuid,Precision:32,JavaType:java.lang.String
        ProjectId,Precision:32,JavaType:java.lang.String
        ContractId,Precision:32,JavaType:java.lang.String
        DateRecord,Precision:0,JavaType:java.sql.Timestamp
        WorkContent,Precision:0,JavaType:oracle.jbo.domain.ClobDomain
        WorkHours,Precision:0,JavaType:java.math.BigDecimal
        WorkRemark,Precision:2100,JavaType:java.lang.String
        Status,Precision:20,JavaType:java.lang.String
        StatusRemark,Precision:2100,JavaType:java.lang.String
        PayBillNumber,Precision:50,JavaType:java.lang.String
     */
    public static final String[] ATTR_ALL = {
        "Uuid", "ProjectId", "ContractId", "DateRecord", "WorkContent", "WorkHours", "WorkRemark", "Status",
        "StatusRemark", "PayBillNumber"
    };

    public static final String[] ATTR_GET = ATTR_ALL;

    public static final String[] ATTR_UPDATE = { };

    public static final String[] ATTR_CREATE = { };

    public static final boolean CAN_DELETE = true;


    public ViewObjectImpl getDailyReportFromAM(LanceRestAMImpl am) {
        return am.getContractReportVO1();
    }

    public Row findDailyReportById(String id, ViewObjectImpl vo, LanceRestAMImpl am) {
        vo.setApplyViewCriteriaName("FindByUuidVC");
        vo.ensureVariableManager().setVariableValue("pUuid", id);
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByUuidVC");
        return vo.first();
    }

    public String returnParamAfterCreate(Row row) {
        return (String) row.getAttribute("Uuid");
    }

    //------------------------------以下是标准代码--------------------------

    /**
     * 根据ID查询每日工作报告
     * @param reportId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("{reportId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getDailyReportById(@PathParam("reportId") String reportId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getDailyReportFromAM(am);
        Row row = findDailyReportById(reportId, vo, am);

        if (row == null) {
            String msg = "每日工作报告（" + reportId + "）对应的记录不存在或已被删除";
            JSONObject res = new JSONObject();
            res.put("msg", msg);
            return res;
        }

        return RestUtil.convertRowToJsonObject(vo, row, this.ATTR_GET);
    }


    /**
     * 更新
     * @param reportId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Path("update/{reportId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateDailyReport(@PathParam("reportId") String reportId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getDailyReportFromAM(am);
        Row row = findDailyReportById(reportId, vo, am);

        if (row == null) {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试修改每日工作报告" + reportId + "但此记录不存在");
            return "msg:can't find DailyReport by id " + reportId;
        }
        if (!RestSecurityUtil.isOwner(row)) {
            String msg = "您没有修改此记录的权限";
            return "msg:" + msg;
        }

        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_UPDATE);
        am.getDBTransaction().commit();

        LOGGER.log(LOGGER.NOTIFICATION,
                   findCurrentUserName() + "修改每日工作报告(" + reportId + ") 为：" +
                   RestUtil.convertRowToJsonObject(vo, row, this.ATTR_ALL));
        return "ok";
    }

    /**
     * 创建
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createDailyReport(JSONObject json) throws JSONException {
        LOGGER.log(LOGGER.NOTIFICATION, "create DailyReport");
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getDailyReportFromAM(am);
        RowImpl row = LUtil.createInsertRow(vo);
        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_CREATE);
        LOGGER.log(LOGGER.TRACE, "copyJsonObjectToRow :" + this.ATTR_CREATE);
        String res = returnParamAfterCreate(row);
        LOGGER.log(LOGGER.NOTIFICATION, "DailyReport created by return :" + res);
        return res;
    }

    /**
     * 删除
     * @param reportId
     * @return
     * @throws JSONException
     */
    @POST
    @Path("delete/{reportId}")
    public String deleteDailyReport(@PathParam("reportId") String reportId) throws JSONException {
        if (!CAN_DELETE) {
            return "msg:此类记录无法被删除";
        }

        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getDailyReportFromAM(am);
        Row row = findDailyReportById(reportId, vo, am);
        if (row != null) {
            //权限判断
            if (!RestSecurityUtil.isOwner(row)) {
                String msg = findCurrentUserName() + "无删除每日工作报告" + reportId + "的权限";
                LOGGER.log(LOGGER.WARNING, msg);
                return "msg:" + msg;
            }

            vo.setCurrentRow(row);
            vo.removeCurrentRow();
            am.getDBTransaction().commit();
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "删除了每日工作报告" + reportId);
        } else {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试删除每日工作报告" + reportId + "但此记录不存在");
        }
        return "ok";
    }


}
