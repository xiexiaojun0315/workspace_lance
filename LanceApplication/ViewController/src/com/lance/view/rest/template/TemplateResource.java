package com.lance.view.rest.template;

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
@Path("template") 
public class TemplateResource extends BaseRestResource {

    public TemplateResource() {
    }

    //------------------------------implements below--------------------------

    public final ADFLogger LOGGER = ADFLogger.createADFLogger(TemplateResource.class);

    public static final String[] ATTR_ALL = { }; 

    public static final String[] ATTR_GET = { }; 

    public static final String[] ATTR_UPDATE = { }; 

    public static final String[] ATTR_CREATE = { }; 
    
    public static final boolean CAN_DELETE=true;


    public ViewObjectImpl getTemplateFromAM(LanceRestAMImpl am) {
        return am.getCompany1();
    }

    public Row findTemplateById(String id, ViewObjectImpl vo, LanceRestAMImpl am) {
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
     * 根据ID查询模版
     * @param templateId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("{templateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getTemplateById(@PathParam("templateId") String templateId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getTemplateFromAM(am);
        Row row = findTemplateById(templateId, vo, am);

        if (row == null) {
            String msg = "模版（" + templateId + "）对应的记录不存在或已被删除";
            JSONObject res = new JSONObject();
            res.put("msg", msg);
            return res;
        }

        return RestUtil.convertRowToJsonObject(vo, row, this.ATTR_GET);
    }


    /**
     * 更新
     * @param templateId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Path("update/{templateId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateTemplate(@PathParam("templateId") String templateId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getTemplateFromAM(am);
        Row row = findTemplateById(templateId, vo, am);

        if (row == null) {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试修改模版" + templateId + "但此记录不存在");
            return "msg:can't find Template by id " + templateId;
        }
        if(!RestSecurityUtil.isOwner(row)){
            String msg="您没有修改此记录的权限";
            return "msg:"+msg;
        }

        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_UPDATE);
        am.getDBTransaction().commit();

        LOGGER.log(LOGGER.NOTIFICATION,
                   findCurrentUserName() + "修改模版(" + templateId + ") 为：" +
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
    public String createTemplate(JSONObject json) throws JSONException {
        LOGGER.log(LOGGER.NOTIFICATION, "create Template");
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getTemplateFromAM(am);
        RowImpl row = LUtil.createInsertRow(vo);
        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_CREATE);
        LOGGER.log(LOGGER.TRACE, "copyJsonObjectToRow :" + this.ATTR_CREATE);
        String res = returnParamAfterCreate(row);
        LOGGER.log(LOGGER.NOTIFICATION, "Template created by return :" + res);
        return res;
    }

    /**
     * 删除
     * @param templateId
     * @return
     * @throws JSONException
     */
    @POST
    @Path("delete/{templateId}")
    public String deleteTemplate(@PathParam("templateId") String templateId) throws JSONException {
        if(!CAN_DELETE){
            return "msg:此类记录无法被删除";
        }
        
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getTemplateFromAM(am);
        Row row = findTemplateById(templateId, vo, am);
        if (row != null) {
            //权限判断
            if (!RestSecurityUtil.isOwner(row)) {
                String msg = findCurrentUserName() + "无删除模版" + templateId + "的权限";
                LOGGER.log(LOGGER.WARNING, msg);
                return "msg:" + msg;
            }

            vo.setCurrentRow(row);
            vo.removeCurrentRow();
            am.getDBTransaction().commit();
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "删除了模版" + templateId);
        } else {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试删除模版" + templateId + "但此记录不存在");
        }
        return "ok";
    }
    
    

}
