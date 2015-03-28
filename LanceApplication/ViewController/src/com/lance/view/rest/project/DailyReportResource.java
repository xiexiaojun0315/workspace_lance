package com.lance.view.rest.project;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.util.ConstantUtil;
import com.lance.model.vo.ContractVOImpl;
import com.lance.model.vo.ContractVORowImpl;
import com.lance.view.util.LUtil;

import com.lance.view.util.RestSecurityUtil;

import com.zngh.platform.front.core.view.BaseRestResource;
import com.zngh.platform.front.core.view.RestUtil;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Row;
import oracle.jbo.server.RowImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 每日工作报告
 *
 * 文本替换示例
 * DailyReport ：Company
 * reportId ： companyId
 * 每日工作报告 ： 公司
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
        "StatusRemark", "PayBillNumber","Address","StatusName"
    };

    public static final String[] ATTR_GET = ATTR_ALL;

    public static final String[] ATTR_UPDATE = {"WorkContent","WorkHours","WorkRemark","Address"};
    
    public static final String[] ATTR_APPROVE = {"Status","StatusName","StatusRemark","PayBillNumber"};

    public static final String[] ATTR_CREATE = {"ProjectId","ContractId","DateRecord","WorkContent","WorkHours","WorkRemark","Address"};
    
    public static final String[] ATTR_SEARCH = {"Uuid", "ProjectId", "ContractId", "DateRecord", "WorkContent", "WorkHours", "WorkRemark", "Status",
        "StatusRemark", "PayBillNumber","Day","Address","SysDateRec","StatusName"};

    public static final boolean CAN_DELETE = true;


    public ViewObjectImpl getDailyReportFromAM(LanceRestAMImpl am) {
        return am.getContractReportVO1();
    }
    
    public ViewObjectImpl getDailyReportVVOFromAM(LanceRestAMImpl am) {
        return am.getContractReportVVO1();
    }
    
    public ViewObjectImpl getContractClientReportVVOFromAM(LanceRestAMImpl am) {
        return am.getContractClientReportV1();
    }

    public Row findDailyReportById(String id, ViewObjectImpl vo, LanceRestAMImpl am) {
        vo.setApplyViewCriteriaName("FindByUuidVC");
        vo.ensureVariableManager().setVariableValue("pUuid", id);
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByUuidVC");
        return vo.first();
    }
    
    public Row findDailyReportByConId(String date,String projid,String contractId, ViewObjectImpl vo, LanceRestAMImpl am){
        vo.setWhereClause(null);
        vo.setWhereClause("TO_CHAR(DATE_RECORD, 'yyyy-MM-dd')='"+date+"' and CONTRACT_ID='"+contractId+"' and CREATE_BY='"+findCurrentUserName()+"'");
        vo.executeQuery();
        if(vo.first() != null){
            return vo.first();
        }
        return null;
    }
    
    public ViewObjectImpl findDailyReportByContractId(String contractId,String status, ViewObjectImpl vo) {
        vo.setApplyViewCriteriaName("findByContractIdVC");
        vo.ensureVariableManager().setVariableValue("constract_Id", contractId);
        vo.ensureVariableManager().setVariableValue("pStatus", status);
        vo.setOrderByClause("CREATE_ON DESC");
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("findByContractIdVC");
        return vo;
    }
    
    public ViewObjectImpl findDailyReportByMonth(int year,int month,String contractId,ViewObjectImpl vo){
       vo.clearCache();
       vo.setWhereClause(null);
       vo.setNamedWhereClauseParam("startDate", getMonthOfDate(year, month, "first"));
       vo.setNamedWhereClauseParam("endDate", getMonthOfDate(year, month, "end"));
       vo.setNamedWhereClauseParam("conId", contractId);
       vo.setNamedWhereClauseParam("userName", findCurrentUserName());
       vo.setRangeSize(-1);
       vo.executeQuery();
       return vo;
    }
    
    public ViewObjectImpl findCliDailyReportByMonth(int year,int month,String contractId,ViewObjectImpl vo){
       vo.clearCache();
       vo.setWhereClause(null);
       vo.setNamedWhereClauseParam("startDate", getMonthOfDate(year, month, "first"));
       vo.setNamedWhereClauseParam("endDate", getMonthOfDate(year, month, "end"));
       vo.setNamedWhereClauseParam("conId", contractId);
       vo.setRangeSize(-1);
       vo.executeQuery();
       return vo;
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
    
    public boolean checkIsAllowUpt(Row row){
        return !(ReportStatus.CONFIRM.status().equals((String)row.getAttribute("Status")) || ReportStatus.PAYED.status().equals((String)row.getAttribute("Status")));
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
        
        if(!checkIsAllowUpt(row)){
            String msg = "此记录甲方已确认,请勿修改!";
            return "msg:" + msg; 
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
        row.setAttribute("Status", ReportStatus.POSTED.status());
        row.setAttribute("StatusName", ReportStatus.POSTED.statusName());
        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_CREATE);
        LOGGER.log(LOGGER.TRACE, "copyJsonObjectToRow :" + this.ATTR_CREATE);
        String res = returnParamAfterCreate(row);
        LOGGER.log(LOGGER.NOTIFICATION, "DailyReport created by return :" + res);
        am.getDBTransaction().commit();
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
            if(!checkIsAllowUpt(row)){
                String msg = "此记录甲方已确认,请勿删除!";
                return "msg:" + msg; 
            }
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
    
    /**
     *按照“年份+月份”查询工作日志
     * @param month “年份+月份”
     * @return
     * @throws JSONException
     */
    @GET
    @Path("search/lancer/{contractId}/{year}/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray findLancerDailyReportByMonth(@PathParam("year")int year,@PathParam("month")int month,@PathParam("contractId")String contractId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getDailyReportVVOFromAM(am);
        this.findDailyReportByMonth(year,month,contractId, vo);
        return this.convertVoToJsonArray(vo, this.ATTR_SEARCH);
    }
    
    @GET
    @Path("search/client/{contractId}/{year}/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray findClintDailyReportByMonth(@PathParam("year")int year,@PathParam("month")int month,@PathParam("contractId")String contractId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getContractClientReportVVOFromAM(am);
        this.findCliDailyReportByMonth(year,month,contractId, vo);
        return this.convertVoToJsonArray(vo, this.ATTR_SEARCH);
    }
    
    public String getMonthOfDate(int year,int month,String flag){
        Date d = null;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        if("end".equals(flag)){
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            d = cal.getTime();
        }else{
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            d = cal.getTime();
        }
       return formatDate(d);
    }

    public String formatDate(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(d);
        return time;
    }
   
    /**
     *审批工作日志
     * @param reportId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Path("approve/{reportId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String approveDailyReport(@PathParam("reportId") String reportId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getDailyReportFromAM(am);
        Row row = findDailyReportById(reportId, vo, am);

        if (row == null) {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试批阅每日工作报告" + reportId + "但此记录不存在");
            return "msg:can't find DailyReport by id " + reportId;
        }
        
        if(!checkIsAllowUpt(row)){
            String msg = "此记录甲方已确认,请勿修改!";
            return "msg:" + msg; 
        }
        
        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_APPROVE);
        am.getDBTransaction().commit();

        LOGGER.log(LOGGER.NOTIFICATION,
                   findCurrentUserName() + "审批每日工作报告(" + reportId + ") 为：" +
                   RestUtil.convertRowToJsonObject(vo, row, this.ATTR_APPROVE));
        return "ok";
    } 
    
    /**
     *根据合同主键UUID和工作日志状态，查询工作日志
     * @param contractId
     * @param status
     * @return
     * @throws JSONException
     */
    @GET
    @Path("search/contract")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray findDailyReportByContractId(@QueryParam("contractId") String contractId,@QueryParam("status") String status) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getDailyReportFromAM(am);
        if("all".equals(status)){
            this.findDailyReportByContractId(contractId,null,vo);
        }else{
            this.findDailyReportByContractId(contractId,status,vo);
        }
        return this.convertVoToJsonArray(vo, this.ATTR_GET);
    } 
    
    @POST
    @Path("mergeConRe")
    @Consumes(MediaType.APPLICATION_JSON)
    public String mergeContractResport(JSONObject json) throws JSONException {
        if(!isEmpty(json.getString("contractId"))){
            LanceRestAMImpl am = LUtil.findLanceAM();
            ContractVORowImpl cr = findContractByIdFn(am, json.getString("contractId"));
            if(findCurrentUserName().equals(cr.getLancerName())){
                String[] dates = json.getString("date").split(",");
                for(String d : dates){
                    Row row = findDailyReportByConId(d, cr.getProjectId(), cr.getUuid(), am.getContractReport2(), am);
                    if(row == null){
                       JSONObject data = json.getJSONObject("data");
                       data.put("DateRecord", d);
                       data.put("ProjectId", cr.getProjectId());
                       this.createDailyReport(data);   
                    }else{
                       this.updateDailyReport((String)row.getAttribute("Uuid"), json.getJSONObject("data"));
                    }
                }
            }else{
               LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "正在尝试修改他人工作日志" + json);
               return "请勿改写他人日志，谢谢!"; 
            }
        }else{
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "正在修改工作日志，但合同编号contractId获取异常" + json);
            return "系统获取合同编号异常，请重试!"; 
        }
       return "ok";
    }
    
    /**
     *工作日志审批 确认
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Path("audit")
    @Consumes(MediaType.APPLICATION_JSON)
    public String auditContractReport(JSONObject json) throws JSONException {
        if(!isEmpty(json.getString("ids"))){
            LanceRestAMImpl am = LUtil.findLanceAM();
            ContractVORowImpl cr = findContractByIdFn(am, json.getString("contractId"));
            if(findCurrentUserName().equals(cr.getClientName())){
                String[] ids = json.getString("ids").split(",");
                for(String reportId : ids){
                    approveDailyReport(reportId, json.getJSONObject("data"));
                }
            }else{
               LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "正在尝试修改审批甲方日志" + json);
               return "请勿审批甲方日志，谢谢!"; 
            }
        }else{
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "正在审批工作日志，但合同编号contractId获取异常" + json);
            return "系统获取合同编号异常，请重试!"; 
        }
       return "ok";
    }
    
    private boolean isEmpty(String str){
        if(str == null || "".equals(str)){
            return true;
        }
        return false;
    }
    
    private boolean isEmpty(String[] strs){
        if(strs == null || strs.length == 0){
            return true;
        }
        return false;
    }
   
    /**
     *工作日志状态(status)集合
     * @return
     * @throws JSONException
     */
    @GET
    @Path("statusList")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getStatusList() throws JSONException {
        JSONObject json = new JSONObject(); 
        for(ReportStatus rs : ReportStatus.values()){
            json.put(rs.status(), rs.statusName());
        }
       return json;
    }
    
    private enum ReportStatus {
        POSTED(ConstantUtil.REPORT_STATUS_POSTED,"等待确认"),
        WITHDRAW(ConstantUtil.REPORT_STATUS_WITHDRAW,"已撤回"),
        CONFIRM(ConstantUtil.REPORT_STATUS_CONFIRM, "待付款"),
        PAYED(ConstantUtil.REPORT_STATUS_PAYED, "已支付"),
        REJECT(ConstantUtil.REPORT_STATUS_REJECT,"已拒绝");
        private String _name;
        private String _status;
        private ReportStatus (String status,String name) {
            this._name = name;
            this._status = status;
        }
        
        public String status(){
          return this._status;    
        }
        
        public String statusName(){
          return this._name;    
        }
    }
}
