package com.lance.view.rest.project;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.ContractMilestoneVOImpl;
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
 * 里程碑
 *
 * 文本替换示例
 * Milestone ：Company
 * milestoneId ： companyId
 * 里程碑 ： 公司
 *
 *
 *
 */
@Path("milestone")
public class MilestoneResource extends BaseRestResource {

    public MilestoneResource() {
    }

    //------------------------------implements below--------------------------

    public final ADFLogger LOGGER = ADFLogger.createADFLogger(MilestoneResource.class);

    /**
        Uuid,Precision:32,JavaType:java.lang.String
        ProjectId,Precision:32,JavaType:java.lang.String
        ContractId,Precision:32,JavaType:java.lang.String
        Title,Precision:60,JavaType:java.lang.String
        Location,Precision:21,JavaType:java.lang.String
        Remark,Precision:2100,JavaType:java.lang.String
        DateDelivery,Precision:0,JavaType:oracle.jbo.domain.Date
        Price,Precision:0,JavaType:java.math.BigDecimal
        Process,Precision:20,JavaType:java.lang.String
        Status,Precision:10,JavaType:java.lang.String
        PayStatus,Precision:20,JavaType:java.lang.String
        DateLatestPay,Precision:0,JavaType:oracle.jbo.domain.Date
        PayBillNumber,Precision:50,JavaType:java.lang.String
        CreateBy,Precision:60,JavaType:java.lang.String
        CreateOn,Precision:0,JavaType:java.sql.Timestamp
        ModifyBy,Precision:60,JavaType:java.lang.String
        ModifyOn,Precision:0,JavaType:java.sql.Timestamp
        Version,Precision:0,JavaType:oracle.jbo.domain.Number
    */
    public static final String[] ATTR_ALL = {
        "Uuid", "ProjectId", "ContractId", "Title", "Location", "Remark", "DateDelivery", "Price", "Process", "Status",
        "PayStatus", "DateLatestPay", "PayBillNumber", "CreateBy", "CreateOn", "ModifyBy", "ModifyOn", "Version"
    };

    public static final String[] ATTR_GET = ATTR_ALL;

    public static final String[] ATTR_UPDATE = {
        "ProjectId", "ContractId", "Title", "Location", "Remark", "DateDelivery", "Price", "Process", "Status",
        "PayStatus", "DateLatestPay"
    };

    public static final String[] ATTR_CREATE = {
        "ProjectId", "ContractId", "Title", "Location", "Remark", "DateDelivery", "Price", "Process", "Status",
        "PayStatus", "DateLatestPay"
    };

    public static final boolean CAN_DELETE = true;


    public ContractMilestoneVOImpl getMilestoneFromAM(LanceRestAMImpl am) {
        return am.getContractMilestoneVO1();
    }

    public Row findMilestoneById(String id, ViewObjectImpl vo, LanceRestAMImpl am) {
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
     * 根据ID查询里程碑
     * @param milestoneId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("{milestoneId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getMilestoneById(@PathParam("milestoneId") String milestoneId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getMilestoneFromAM(am);
        Row row = findMilestoneById(milestoneId, vo, am);

        if (row == null) {
            String msg = "里程碑（" + milestoneId + "）对应的记录不存在或已被删除";
            JSONObject res = new JSONObject();
            res.put("msg", msg);
            return res;
        }

        return RestUtil.convertRowToJsonObject(vo, row, this.ATTR_GET);
    }


    /**
     * 更新
     * @param milestoneId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Path("update/{milestoneId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateMilestone(@PathParam("milestoneId") String milestoneId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getMilestoneFromAM(am);
        Row row = findMilestoneById(milestoneId, vo, am);

        if (row == null) {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试修改里程碑" + milestoneId + "但此记录不存在");
            return "msg:can't find Milestone by id " + milestoneId;
        }
        if (!RestSecurityUtil.isOwner(row)) {
            String msg = "您没有修改此记录的权限";
            return "msg:" + msg;
        }

        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_UPDATE);
        am.getDBTransaction().commit();

        LOGGER.log(LOGGER.NOTIFICATION,
                   findCurrentUserName() + "修改里程碑(" + milestoneId + ") 为：" +
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
    public String createMilestone(JSONObject json) throws JSONException {
        LOGGER.log(LOGGER.NOTIFICATION, "create Milestone");
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getMilestoneFromAM(am);
        RowImpl row = LUtil.createInsertRow(vo);
        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_CREATE);
        LOGGER.log(LOGGER.TRACE, "copyJsonObjectToRow :" + this.ATTR_CREATE);
        String res = returnParamAfterCreate(row);
        LOGGER.log(LOGGER.NOTIFICATION, "Milestone created by return :" + res);
        return res;
    }

    /**
     * 删除
     * @param milestoneId
     * @return
     * @throws JSONException
     */
    @POST
    @Path("delete/{milestoneId}")
    public String deleteMilestone(@PathParam("milestoneId") String milestoneId) throws JSONException {
        if (!CAN_DELETE) {
            return "msg:此类记录无法被删除";
        }

        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getMilestoneFromAM(am);
        Row row = findMilestoneById(milestoneId, vo, am);
        if (row != null) {
            //权限判断
            if (!RestSecurityUtil.isOwner(row)) {
                String msg = findCurrentUserName() + "无删除里程碑" + milestoneId + "的权限";
                LOGGER.log(LOGGER.WARNING, msg);
                return "msg:" + msg;
            }

            vo.setCurrentRow(row);
            vo.removeCurrentRow();
            am.getDBTransaction().commit();
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "删除了里程碑" + milestoneId);
        } else {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试删除里程碑" + milestoneId + "但此记录不存在");
        }
        return "ok";
    }


}
