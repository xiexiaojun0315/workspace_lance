package com.lance.view.rest.project;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.ContractMilestoneVOImpl;
import com.lance.model.vo.ContractMilestoneVORowImpl;
import com.lance.model.vo.ContractVOImpl;
import com.lance.model.vo.ContractVORowImpl;
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

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.RowImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.codehaus.jettison.json.JSONArray;
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
 */
@Path("contract/milestone")
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
        "DateLatestPay"
    };

    public static final String[] ATTR_CREATE = {
        "ProjectId", "ContractId", "Title", "Location", "Remark", "DateDelivery", "Price", "DateLatestPay"
    };

    public static final boolean CAN_DELETE = true;


    public ContractMilestoneVOImpl getMilestoneFromAM(LanceRestAMImpl am) {
        return am.getContractMilestoneVO1();
    }

    public Row findMilestoneById(String contractId, String milestoneId, LanceRestAMImpl am) {
        ContractVOImpl vo1 = am.getContractVO1();
        findContractById(contractId, vo1, am);
        ContractMilestoneVOImpl vo2 = am.getContractMilestoneVO1();
        Row[] rows2 = vo2.findByKey(new Key(new Object[] { milestoneId }), 1);
        if (rows2 == null || rows2.length == 0) {
            return null;
        }
        Row row2 = vo2.first();
        vo2.setCurrentRow(row2);
        return row2;
    }

    public String returnParamAfterCreate(Row row) {
        return (String) row.getAttribute("Uuid");
    }

    public ContractVORowImpl findContractById(String id, ViewObjectImpl vo, LanceRestAMImpl am) {
        if (vo.getCurrentRow() != null && vo.getCurrentRow().getAttribute("Uuid").equals(id)) {
            return (ContractVORowImpl) vo.getCurrentRow();
        }

        vo.setApplyViewCriteriaName("FindByUuidVC");
        vo.ensureVariableManager().setVariableValue("pUuid", id);
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByUuidVC");
        vo.setCurrentRow(vo.first());
        return (ContractVORowImpl) vo.first();
    }

    //------------------------------以下是标准代码--------------------------

    @GET
    @Path("{contractId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getMilestoneByContractId(@PathParam("contractId") String contractId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ContractVOImpl vo = am.getContractVO1();
        findContractById(contractId, vo, am);
        ContractMilestoneVOImpl vo2 = am.getContractMilestoneVO1();
        vo2.executeQuery();
        return this.convertVoToJsonArray(vo2, this.ATTR_GET);
    }

    /**
     * 根据ID查询里程碑
     * @param milestoneId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("{contractId}/{milestoneId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getMilestoneById(@PathParam("contractId") String contractId,
                                       @PathParam("milestoneId") String milestoneId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        Row row = findMilestoneById(contractId, milestoneId, am);

        if (row == null) {
            String msg = "里程碑（" + milestoneId + "）对应的记录不存在或已被删除";
            return LUtil.createJsonMsg(msg);
        }

        ContractMilestoneVOImpl vo = am.getContractMilestoneVO1();
        return RestUtil.convertRowToJsonObject(vo, row, this.ATTR_GET);
    }


    /**
     * 更新(推荐用批量更新)
     *
     * @param milestoneId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Path("{contractId}/{milestoneId}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateMilestone(@PathParam("contractId") String contractId,
                                  @PathParam("milestoneId") String milestoneId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getMilestoneFromAM(am);
        Row row = findMilestoneById(contractId, milestoneId, am);

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
     * 批量更新里程碑
     *
     * @param contractId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Path("multiUpdate/{contractId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String batchUpdateMilestoneFn(@PathParam("contractId") String contractId,
                                         JSONObject json) throws JSONException {
        System.out.println("batchUpdateMilestoneFn:" + contractId);
        LanceRestAMImpl am = LUtil.findLanceAM();
        ContractVOImpl vo = am.getContractVO1();
        ContractMilestoneVOImpl vo2 = am.getContractMilestoneVO1();
        ContractMilestoneVORowImpl row2;
        this.findContractById(contractId, vo, am);

        if (json.has("add")) {
            JSONArray arr = json.getJSONArray("add");
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject data = (JSONObject) arr.get(i);
                    row2 = (ContractMilestoneVORowImpl) vo2.createRow();
                    vo2.insertRow(row2);
                    this.copyJsonObjectToRow(data, vo2, row2, this.ATTR_CREATE);
                }
            }
        }

        if (json.has("delete")) {
            JSONArray arr = json.getJSONArray("delete");
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    String milestoneId = arr.getString(i);
                    row2 = getMilestoneRowByIdFn(milestoneId, vo2);
                    row2.remove();
                }
            }
        }

        if (json.has("update")) {
            JSONArray arr = json.getJSONArray("update");
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject data = (JSONObject) arr.get(i);
                    String milestoneId = data.getString("Uuid");
                    row2 = getMilestoneRowByIdFn(milestoneId, vo2);
                    System.out.println(row2.getUuid());
                    this.copyJsonObjectToRow(data, vo2, row2, this.ATTR_UPDATE);
                }
            }
        }

        //更新里程碑执行状态
        if (json.has("process")) {
            JSONArray arr = json.getJSONArray("process");
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject data = (JSONObject) arr.get(i);
                    String milestoneId = data.getString("Uuid");
                    row2 = getMilestoneRowByIdFn(milestoneId, vo2);
                    System.out.println(row2.getUuid());
                    this.copyJsonObjectToRow(data, vo2, row2, this.ATTR_UPDATE);
                }
            }
        }

        am.getDBTransaction().commit();
        return "ok";
    }

    //更新状态
    @POST
    @Path("audit/{contractId}/{milestoneId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateProcessStatus(@PathParam("contractId") String contractId,
                                      @PathParam("milestoneId") String milestoneId,
                                      JSONObject json) throws JSONException {
        String opt = json.getString("opt");
        String desc = json.getString("desc");
        LanceRestAMImpl am = LUtil.findLanceAM();
        if (opt.equals("confirm")) {
            updateProcessStatusFn(contractId, milestoneId, am, "toPay", "已确认，待支付");
        } else if (opt.equals("reject")) {
            updateProcessStatusFn(contractId, milestoneId, am, "reject", "拒绝");
        }
        return "ok";
    }

    public void updateProcessStatusFn(String contractId, String milestoneId, LanceRestAMImpl am, String status,
                                      String statusRemark) {
        this.findContractById(contractId, am.getContractVO1(), am);
        ContractMilestoneVOImpl vo = am.getContractMilestoneVO1();
        if (milestoneId != null) {
            Row row = findMilestoneById(contractId, milestoneId, am);
            row.setAttribute("Status", status);
            row.setAttribute("StatusRemark", statusRemark);
        } else {
            vo.executeQuery();
            RowSetIterator it = vo.createRowSetIterator(null);
            Row row;
            while (it.hasNext()) {
                row = it.next();
                row.setAttribute("Status", status);
                row.setAttribute("StatusRemark", statusRemark);
            }
            it.closeRowSetIterator();
        }
    }

    private ContractMilestoneVORowImpl getMilestoneRowByIdFn(String milestoneId, ContractMilestoneVOImpl vo2) {
        Row[] rows2 = vo2.findByKey(new Key(new Object[] { milestoneId }), 1);
        if (rows2 == null || rows2.length == 0) {
            return null;
        }
        Row row2 = rows2[0];
        vo2.setCurrentRow(row2);
        return (ContractMilestoneVORowImpl) row2;
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
            row.setAttribute("Status", ReportStatus.DRAFT.status());
            RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_CREATE);
            LOGGER.log(LOGGER.TRACE, "copyJsonObjectToRow :" + this.ATTR_CREATE);
            String res = returnParamAfterCreate(row);
            LOGGER.log(LOGGER.NOTIFICATION, "Milestone created by return :" + res);
            return res;
        }
    //    /**
    //     * 删除
    //     * @param milestoneId
    //     * @return
    //     * @throws JSONException
    //     */
    //    @POST
    //    @Path("delete/{milestoneId}")
    //    public String deleteMilestone(@PathParam("milestoneId") String milestoneId) throws JSONException {
    //        if (!CAN_DELETE) {
    //            return "msg:此类记录无法被删除";
    //        }
    //
    //        LanceRestAMImpl am = LUtil.findLanceAM();
    //        ViewObjectImpl vo = getMilestoneFromAM(am);
    //        Row row = findMilestoneById(milestoneId, vo, am);
    //        if (row != null) {
    //            //权限判断
    //            if (!RestSecurityUtil.isOwner(row)) {
    //                String msg = findCurrentUserName() + "无删除里程碑" + milestoneId + "的权限";
    //                LOGGER.log(LOGGER.WARNING, msg);
    //                return "msg:" + msg;
    //            }
    //
    //            vo.setCurrentRow(row);
    //            vo.removeCurrentRow();
    //            am.getDBTransaction().commit();
    //            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "删除了里程碑" + milestoneId);
    //        } else {
    //            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试删除里程碑" + milestoneId + "但此记录不存在");
    //        }
    //        return "ok";
    //    }

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
        DRAFT("draft","草稿"), POSTED("posted", "已发送"), ARGEE("agree", "确认"), REJECT("reject","拒绝"), PAYED("payed", "已支付");
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
