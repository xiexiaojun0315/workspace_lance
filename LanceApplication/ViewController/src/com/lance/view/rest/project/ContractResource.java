package com.lance.view.rest.project;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.user.vo.UUserVOImpl;
import com.lance.model.user.vo.UUserVORowImpl;
import com.lance.model.util.ConstantUtil;
import com.lance.model.vo.ContractLogVOImpl;
import com.lance.model.vo.ContractLogVORowImpl;
import com.lance.model.vo.ContractMilestoneVOImpl;
import com.lance.model.vo.ContractMilestoneVORowImpl;
import com.lance.model.vo.ContractVOImpl;
import com.lance.model.vo.ContractVORowImpl;
import com.lance.model.vo.PostJobDiscussVOImpl;
import com.lance.model.vo.PostJobDiscussVORowImpl;
import com.lance.model.vo.PostJobsVOImpl;
import com.lance.model.vo.PostJobsVORowImpl;
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
import oracle.jbo.server.ViewObjectImpl;

import org.apache.commons.lang.StringUtils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 合同
 *
 * 文本替换示例
 * Contract ：Company
 * contractId ： companyId
 * 合同 ： 公司
 *
 *
 *
 */
@Path("contract")
public class ContractResource extends BaseRestResource {

    public ContractResource() {
    }

    //------------------------------implements below--------------------------

    public final ADFLogger LOGGER = ADFLogger.createADFLogger(ContractResource.class);

    /**
     * 合同
     *
         Uuid,Precision:32,JavaType:java.lang.String
         AttachmentLink,Precision:600,JavaType:java.lang.String
         Title,Precision:60,JavaType:java.lang.String 合同名
         ClientCompanyId,Precision:32,JavaType:java.lang.String 甲方公司ID
         ClientCompanyName,Precision:60,JavaType:java.lang.String 甲方公司名
         ClientConfirmDelayDay,Precision:0,JavaType:java.lang.Integer 甲方确认里程碑/工作日志的可延迟时间（天）
         ClientEmail,Precision:100,JavaType:java.lang.String 甲方邮箱
         ClientIdNo,Precision:50,JavaType:java.lang.String 甲方证件号
         ClientIdType,Precision:20,JavaType:java.lang.String 甲方证件类型
         ClientName,Precision:60,JavaType:java.lang.String 甲方用户名（登录名）
         ClientSignBy,Precision:20,JavaType:java.lang.String 甲方签约方：company/self 即公司/个人
         ClientPayDelayDay,Precision:0,JavaType:java.lang.Integer 甲方支付可延迟天数
         ClientPhone,Precision:50,JavaType:java.lang.String 甲方电话
         ClientRoleTitle,Precision:120,JavaType:java.lang.String 甲方角色
         ClientTotalScore,Precision:20,JavaType:java.lang.String 甲方评分（总分）
         ClientTrueName,Precision:60,JavaType:java.lang.String 甲方真实姓名
         Content,Precision:0,JavaType:oracle.jbo.domain.ClobDomain 合同内容
         DateStart,Precision:0,JavaType:oracle.jbo.domain.Date 合同标注项目开始时间（合同开始执行时间）
         DateEnd,Precision:0,JavaType:oracle.jbo.domain.Date  合同标注的项目结束时间
         FixedPayDate,Precision:0,JavaType:java.lang.Integer  固定支付日期，每月的（1~28天）
         FixedPayDelayHoliday,Precision:10,JavaType:java.lang.String 遇到法定节假日顺延
         FixedPayPrice,Precision:0,JavaType:java.math.BigDecimal 支付的固定价格
         HourlyPay,Precision:0,JavaType:java.math.BigDecimal  每小时支付价格
         LancerCompanyId,Precision:32,JavaType:java.lang.String
         LancerCompanyName,Precision:60,JavaType:java.lang.String
         LancerCostScore,Precision:0,JavaType:java.math.BigDecimal
         LancerEmail,Precision:100,JavaType:java.lang.String
         LancerEvaluation,Precision:2100,JavaType:java.lang.String
         LancerIdNo,Precision:50,JavaType:java.lang.String
         LancerIdType,Precision:20,JavaType:java.lang.String
         LancerName,Precision:60,JavaType:java.lang.String
         LancerSignBy,Precision:20,JavaType:java.lang.String
         LancerPhone,Precision:50,JavaType:java.lang.String
         LancerPlanScore,Precision:0,JavaType:java.math.BigDecimal
         LancerProfessScore,Precision:0,JavaType:java.math.BigDecimal
         LancerQualityScore,Precision:0,JavaType:java.math.BigDecimal
         LancerResponsScore,Precision:0,JavaType:java.math.BigDecimal
         LancerRoleTitle,Precision:120,JavaType:java.lang.String
         LancerTotalScore,Precision:0,JavaType:java.math.BigDecimal
         LancerTrueName,Precision:60,JavaType:java.lang.String
         NeedDailyReport,Precision:10,JavaType:java.lang.String
         Postform,Precision:20,JavaType:java.lang.String
         ProcessStatus,Precision:20,JavaType:java.lang.String 合同进度
         ProcessStatusDesc,Precision:2100,JavaType:java.lang.String 合同进度描述
         ProjectId,Precision:32,JavaType:java.lang.String
         WeeklyHours,Precision:0,JavaType:java.lang.Integer
         Status,Precision:20,JavaType:java.lang.String
         CreateBy,Precision:60,JavaType:java.lang.String
         ModifyBy,Precision:60,JavaType:java.lang.String
         ModifyOn,Precision:0,JavaType:java.sql.Timestamp
         CreateOn,Precision:0,JavaType:java.sql.Timestamp
         Version,Precision:0,JavaType:java.math.BigDecimal
     */
    public static final String[] ATTR_ALL = {
        "Uuid", "AttachmentLink", "ClientCompanyId", "ClientCompanyName", "ClientConfirmDelayDay", "ClientEmail",
        "ClientIdNo", "ClientIdType", "ClientName", "ClientSignBy", "ClientPayDelayDay", "ClientPhone",
        "ClientRoleTitle", "ClientTotalScore", "ClientTrueName", "Content", "CreateBy", "CreateOn", "DateEnd",
        "DateStart", "FixedPayDate", "FixedPayDelayHoliday", "FixedPayPrice", "HourlyPay", "LancerCompanyId",
        "LancerCompanyName", "LancerCostScore", "LancerEmail", "LancerEvaluation", "LancerIdNo", "LancerIdType",
        "LancerName", "LancerSignBy", "LancerPhone", "LancerPlanScore", "LancerProfessScore", "LancerQualityScore",
        "LancerResponsScore", "LancerRoleTitle", "LancerTotalScore", "LancerTrueName", "ModifyBy", "ModifyOn",
        "NeedDailyReport", "Postform", "ProcessStatus", "ProcessStatusDesc", "ProjectId", "Status", "Title", "Version",
        "WeeklyHours"
    };

    public static final String[] ATTR_GET = ATTR_ALL;

    public static final String[] CONTRACT_ATTR_UPDATE_ON_CREATE = {
        "AttachmentLink", "ClientConfirmDelayDay", "ClientSignBy", "ClientPayDelayDay", "ClientRoleTitle", "Content",
        "DateEnd", "DateStart", "FixedPayDate", "FixedPayDelayHoliday", "FixedPayPrice", "HourlyPay", "NeedDailyReport",
        "Postform", "ProcessStatus", "ProjectId", "Title", "WeeklyHours"
    };


    public static final String[] CONTRACT_ATTR_UPDATE_ON_FEEDBACK = {
        "LancerCostScore", "LancerEvaluation", "LancerPlanScore", "LancerProfessScore", "LancerQualityScore",
        "LancerResponsScore", "LancerTotalScore", "ProjectId", "Status"
    };

    //    public static final String[] ATTR_CREATE = {
    //        "Title", "Content", "AttachmentLink", "DateStart", "DateEnd", "ClientRoleTitle", "LancerRoleTitle", "Postform",
    //        "HourlyPay", "WeeklyHours", "FixedPayPrice", "ClientConfirmDelayDay", "ClientPayDelayDay", "FixedPayDate",
    //        "FixedPayDelayHoliday", "NeedDailyReport"
    //    };

    public static final boolean CAN_DELETE = false;


    public ViewObjectImpl getContractFromAM(LanceRestAMImpl am) {
        return am.getContractVO1();
    }

    public ContractVORowImpl findContractById(String id, ViewObjectImpl vo, LanceRestAMImpl am) {
        if(vo.getCurrentRow()!=null && vo.getCurrentRow().getAttribute("Uuid").equals(id)){
            return (ContractVORowImpl)vo.getCurrentRow();
        }
        
        vo.setApplyViewCriteriaName("FindByUuidVC");
        vo.ensureVariableManager().setVariableValue("pUuid", id);
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByUuidVC");
        return (ContractVORowImpl)vo.first();
    }

    public String returnParamAfterCreate(Row row) {
        return (String) row.getAttribute("Uuid");
    }

    //------------------------------以下是标准代码--------------------------

    /**
     * 根据ID查询合同
     * @param contractId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("{contractId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getContractById(@PathParam("contractId") String contractId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getContractFromAM(am);
        ContractVORowImpl row = findContractById(contractId, vo, am);

        if (row == null) {
            String msg = "合同（" + contractId + "）对应的记录不存在或已被删除";
            JSONObject res = new JSONObject();
            res.put("msg", msg);
            return res;
        }

        JSONObject res=new JSONObject();
        JSONObject contract=RestUtil.convertRowToJsonObject(vo, row, this.ATTR_GET);
        if("fixed".equals(row.getPostform())){}
        return res;
    }


    /**
     * 更新
     * @param contractId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Path("update/{contractId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateContract(@PathParam("contractId") String contractId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getContractFromAM(am);
        Row row = findContractById(contractId, vo, am);

        if (row == null) {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试修改合同" + contractId + "但此记录不存在");
            return "msg:can't find Contract by id " + contractId;
        }
        if (!RestSecurityUtil.isOwner(row)) {
            String msg = "您没有修改此记录的权限";
            return "msg:" + msg;
        }

        RestUtil.copyJsonObjectToRow(json, vo, row, this.CONTRACT_ATTR_UPDATE_ON_CREATE);
        am.getDBTransaction().commit();

        LOGGER.log(LOGGER.NOTIFICATION,
                   findCurrentUserName() + "修改合同(" + contractId + ") 为：" +
                   RestUtil.convertRowToJsonObject(vo, row, this.ATTR_ALL));
        return "ok";
    }

    //    /**
    //     * 创建
    //     * @param json
    //     * @return
    //     * @throws JSONException
    //     */
    //    @POST
    //    @Consumes(MediaType.APPLICATION_JSON)
    //    public String createContract(JSONObject json) throws JSONException {
    //        LOGGER.log(LOGGER.NOTIFICATION, "create Contract");
    //        LanceRestAMImpl am = LUtil.findLanceAM();
    //        ViewObjectImpl vo = getContractFromAM(am);
    //        RowImpl row = LUtil.createInsertRow(vo);
    //        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_CREATE);
    //        LOGGER.log(LOGGER.TRACE, "copyJsonObjectToRow :" + this.ATTR_CREATE);
    //        String res = returnParamAfterCreate(row);
    //        LOGGER.log(LOGGER.NOTIFICATION, "Contract created by return :" + res);
    //        return res;
    //    }

    /**
     * 创建合同
     * @param am
     * @param lancerName
     * @param clientName
     * @param postJobId
     * @param applyDiscussId
     * @return
     */
    public String createContractFn(LanceRestAMImpl am, String lancerName, String clientName, String postJobId,
                                   String applyDiscussId) {
        //create contract
        ContractVOImpl contractVO = am.getContractVO1();
        ContractVORowImpl contractRow = (ContractVORowImpl) contractVO.createRow();
        contractVO.insertRow(contractRow);
        contractVO.setCurrentRow(contractRow);

        UUserVOImpl userVo = am.getUUser1();
        userVo.setApplyViewCriteriaName("FindByUserNameVC");
        //lancer
        userVo.setpUserName(lancerName);
        userVo.executeQuery();
        userVo.removeApplyViewCriteriaName("FindByUserNameVC");
        UUserVORowImpl lancerRow = (UUserVORowImpl) userVo.first();
        if (lancerRow == null) {
            return "无法找到用户" + lancerName;
        }
        //postJob
        PostJobsVOImpl postJobVo = am.getPostJobs1();
        postJobVo.setApplyViewCriteriaName("FindByIdVC");
        postJobVo.setpUuid(postJobId);
        postJobVo.executeQuery();
        postJobVo.removeApplyViewCriteriaName("FindByIdVC");
        PostJobsVORowImpl postJobRow = (PostJobsVORowImpl) postJobVo.first();
        //applyDiscuss
        PostJobDiscussVOImpl applyVO = am.getPostJobDiscussVO1();
        applyVO.setApplyViewCriteriaName("FindByIdVC");
        applyVO.setpId(applyDiscussId);
        applyVO.executeQuery();
        applyVO.removeApplyViewCriteriaName("FindByIdVC");
        PostJobDiscussVORowImpl applyRow = (PostJobDiscussVORowImpl) applyVO.first();
        //
        if (!applyRow.getIsApply().equals("Y")) {
            return "这个不是一个申请";
        }

        //copy lancer to contract
        contractRow.setLancerName(lancerName);
        contractRow.setLancerTrueName(lancerRow.getTrueName());
        contractRow.setLancerCompanyId(lancerRow.getCompanyId());
        contractRow.setLancerCompanyName(lancerRow.getCompanyName());
        contractRow.setLancerSignBy(applyRow.getSignBy());
        contractRow.setLancerIdNo(lancerRow.getIdentityNo());
        contractRow.setLancerIdType(lancerRow.getIdentityType());
        contractRow.setLancerEmail(lancerRow.getEmail());
        contractRow.setLancerPhone(lancerRow.getPhoneNumber());
        //client
        userVo.setApplyViewCriteriaName("FindByUserNameVC");
        userVo.setpUserName(clientName);
        userVo.executeQuery();
        userVo.removeApplyViewCriteriaName("FindByUserNameVC");
        UUserVORowImpl clientRow = (UUserVORowImpl) userVo.first();
        //copy client to contract
        contractRow.setClientName(clientName);
        contractRow.setClientTrueName(clientRow.getTrueName());
        contractRow.setClientCompanyId(clientRow.getCompanyId());
        contractRow.setClientCompanyName(clientRow.getCompanyName());
        contractRow.setClientSignBy(postJobRow.getSignBy());
        contractRow.setClientIdNo(clientRow.getIdentityNo());
        contractRow.setClientIdType(clientRow.getIdentityType());
        contractRow.setClientEmail(clientRow.getEmail());
        contractRow.setClientPhone(clientRow.getPhoneNumber());
        //other
        if (StringUtils.isBlank(applyRow.getPostform())) {
        } else if (applyRow.getPostform().equals("fixed")) {
            ContractMilestoneVOImpl cvo = am.getContractMilestoneVO1();
            ContractMilestoneVORowImpl crow = (ContractMilestoneVORowImpl) cvo.createRow();
            crow.setTitle("设计");
            cvo.insertRow(crow);
            crow = (ContractMilestoneVORowImpl) cvo.createRow();
            crow.setTitle("初稿");
            cvo.insertRow(crow);
            crow = (ContractMilestoneVORowImpl) cvo.createRow();
            crow.setTitle("最终版");
            cvo.insertRow(crow);

        } else if (applyRow.getPostform().equals("hourly")) { //稍后决定
            contractRow.setHourlyPay(applyRow.getHourlyPay());
            contractRow.setWeeklyHours(applyRow.getWeeklyHours());
            contractRow.setDateStart(applyRow.getEnteryDate());

        } else {
            am.getDBTransaction().rollback();
            return "无法识别的计费方式";
        }

        contractRow.setPostform(applyRow.getPostform());

        return contractRow.getUuid();
    }


    public String saveContractFn(LanceRestAMImpl am, String contractId, JSONObject json) throws JSONException {
        ContractVOImpl contractVO = am.getContractVO1();
        ContractVORowImpl row = findContractByIdFn(am, contractId);
        if (row == null) {
            return "无法找到对应的记录";
        }
        this.copyJsonObjectToRow(json, contractVO, row, this.CONTRACT_ATTR_UPDATE_ON_CREATE);
        return "ok";
    }

    public ContractVORowImpl findContractByIdFn(LanceRestAMImpl am, String contractId) {
        ContractVOImpl contractVO = am.getContractVO1();
        contractVO.setApplyViewCriteriaName("FindByUuidVC");
        contractVO.setpUuid(contractId);
        contractVO.executeQuery();
        contractVO.removeApplyViewCriteriaName("FindByUuidVC");
        ContractVORowImpl row = (ContractVORowImpl) contractVO.first();
        if (row == null) {
            return null;
        }
        contractVO.setCurrentRow(row);
        return (ContractVORowImpl) contractVO.first();
    }


    @POST
    @Path("update/{contractId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveContract(@PathParam("contractId") String contractId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        String res = saveContractFn(am, contractId, json);
        am.getDBTransaction().commit();
        return res;
    }


    @POST
    @Path("sendAudit/{contractId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendAuditContract(@PathParam("contractId") String contractId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        String res = saveContractFn(am, contractId, json);
        ContractVOImpl contractVO = am.getContractVO1();
        ContractVORowImpl row = (ContractVORowImpl) contractVO.first();
        contractVO.setCurrentRow(row);
        row.setProcessStatus(ConstantUtil.CONTRACT_STATUS_TO_AUDIT);
        row.setProcessStatusDesc("等待乙方确认");
        //todo 通知乙方确认
        //日志
        logContract("甲方发送合同给乙方确认");
        am.getDBTransaction().commit();
        return res;
    }

    public void logContract(String log) {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ContractLogVOImpl logVo = am.getContractLog1();
        ContractLogVORowImpl logRow = (ContractLogVORowImpl) logVo.createRow();
        logRow.setLog(log);
        logVo.insertRow(logRow);
    }

    //    @POST
    //    @Path("cancel/{contractId}")
    //    @Consumes(MediaType.APPLICATION_JSON)
    //    public String cancelContract(@PathParam("contractId") String contractId, JSONObject json) throws JSONException {
    //        LanceRestAMImpl am = LUtil.findLanceAM();
    //        ContractVOImpl contractVO = am.getContractVO1();
    //        ContractVORowImpl row =findContractByIdFn(am,contractId);
    //        if(row.getStatus().equals("anObject")){}
    //        ContractVOImpl contractVO = am.getContractVO1();
    //        ContractVORowImpl row =(ContractVORowImpl)contractVO.first();
    //        row.setStatus("toAudit");
    //        //todo 通知乙方已取消
    //        am.getDBTransaction().commit();
    //        return res;
    //    }


    /**
     * 删除
     * @param contractId
     * @return
     * @throws JSONException
     */
    @POST
    @Path("delete/{contractId}")
    public String deleteContract(@PathParam("contractId") String contractId) throws JSONException {
        if (!CAN_DELETE) {
            return "msg:此类记录无法被删除";
        }

        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getContractFromAM(am);
        Row row = findContractById(contractId, vo, am);
        if (row != null) {
            //权限判断
            if (!RestSecurityUtil.isOwner(row)) {
                String msg = findCurrentUserName() + "无删除合同" + contractId + "的权限";
                LOGGER.log(LOGGER.WARNING, msg);
                return "msg:" + msg;
            }

            vo.setCurrentRow(row);
            vo.removeCurrentRow();
            am.getDBTransaction().commit();
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "删除了合同" + contractId);
        } else {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试删除合同" + contractId + "但此记录不存在");
        }
        return "ok";
    }

    /**
     * 改变合同状态
     *
     * @param newStatus waiting,marching,end,holding
     * @return
     * @throws JSONException
     */
    @POST
    @Path("{contractId}/status/{newStatus}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String changeContractStatus(@PathParam("contractId") String contractId,
                                       @PathParam("newStatus") String newStatus, JSONObject json) throws JSONException {
        ContractVORowImpl row = findContractRowById(contractId);
        if (ConstantUtil.CONTRACT_OPT_SEND_AUDIT.equals(newStatus)) { //甲方发给乙方确认合同
            if (ConstantUtil.CONTRACT_STATUS_DRAFT.equals(row.getProcessStatus()) ||
                ConstantUtil.CONTRACT_STATUS_REJECTED.equals(row.getProcessStatus())) {
                return sendAuditContract(contractId, json);
            } else {
                return "状态不允许此操作";
            }
        } else if (ConstantUtil.CONTRACT_OPT_AUDIT.equals(newStatus)) { //乙方确认合同
            if (ConstantUtil.CONTRACT_STATUS_TO_AUDIT.equals(row.getProcessStatus())) {
                if (row.getDateStart() != null &&
                    row.getDateStart().getValue().getTime() <= System.currentTimeMillis()) {
                    //等待指定时间开始
                    row.setProcessStatus(ConstantUtil.CONTRACT_STATUS_WAITING);
                    row.setProcessStatusDesc("等待项目开始");
                } else {
                    //项目已经开始
                    row.setProcessStatus(ConstantUtil.CONTRACT_STATUS_ON_PROCESS);
                    row.setProcessStatusDesc("项目进行中");
                }
                //日志
                logContract("甲方发送合同给乙方确认");
                LUtil.findLanceAM().getDBTransaction().commit();
                return "ok";
            } else {
                return "状态不允许此操作";
            }
        } else if (ConstantUtil.CONTRACT_OPT_REJECT.equals(newStatus)) { //乙方拒绝合同
            if (ConstantUtil.CONTRACT_STATUS_TO_AUDIT.equals(row.getProcessStatus())) {
                row.setProcessStatus(ConstantUtil.CONTRACT_STATUS_CANCEL);
                logContract("乙方拒绝合同");
                LUtil.findLanceAM().getDBTransaction().commit();
                return "ok";
            } else {
                return "状态不允许此操作";
            }
        }
        //        else if (ConstantUtil.CONTRACT_OPT_CLIENT_CANCEL.equals(newStatus)){ //甲方申请取消合同
        //            if(!ConstantUtil.CONTRACT_STATUS_DONE.equals(newStatus)){
        //
        //            } else {
        //                return "状态不允许此操作";
        //            }
        //        }

        return "error";
    }

    private ContractVORowImpl findContractRowById(String contractId) {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getContractFromAM(am);
        ContractVORowImpl row = (ContractVORowImpl) findContractById(contractId, vo, am);
        return row;
    }


}
