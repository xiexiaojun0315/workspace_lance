package com.zngh.platform.front.core.view;

import com.zngh.platform.front.core.view.servlet.FrontCoreListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import oracle.adf.share.ADFContext;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 本段代码实现《燃气电厂一体化平台ADF程序开发命名及接口规范》1.5 REST接口规范部分
 */
public class BaseRestResource {
    public final ADFLogger LOGGER = ADFLogger.createADFLogger(BaseRestResource.class);

    public static final String CODE_SUCCESS = "y";
    public static int DEFAULT_LIMIT = 25;

    @Context
    public UriInfo uriInfo;

    @Context
    public HttpServletRequest request;

    @Context
    public HttpServletResponse response;

    /**
     * 最大返回结果数量，默认25条
     */
    @DefaultValue("25") //DEFAULT_LIMIT
    @QueryParam("limit")
    public int limit;

    /**
     * 本次查询起始位置，默认第1条
     */
    @DefaultValue("1")
    @QueryParam("start")
    public int start;

    /**
     * y获取记录总数,n不获取，出于性能考虑默认为n
     */
    @DefaultValue("n")
    @QueryParam("estimated")
    public String estimated;

    /**
     * 将一个VO中的记录包装为JSONObject
     * 根据url参数start,limit,estimated执行查询
     * 1. 此方法内不执行executeQuery()，只返回指定范围内的记录
     * 2. 请提前设置好过滤条件,并执行executeQuery()
     * 3. 适用于基于非ID字段进行的查询，处理返回多笔的记录
     *
     * @param vo
     * @return JSONObject
     *  {
            code:业务执行状态码，s为成功
            msg:业务执行说明
            estimated:符合条件的记录总数
            count:当前返回条数
            data:[返回的多条记录]
        }
     */
    public JSONObject packViewObject(ViewObjectImpl vo, String code, String msg) throws JSONException {
        Row[] rows = getAllRowsInUrlRange(vo);
        JSONArray arr = RestUtil.convertRowsToJsonArray(vo,rows, findAllVOAttributes(vo));
        if ("y".equalsIgnoreCase(estimated)) {
            long rowCount = 0;
            rowCount = vo.getEstimatedRowCount();
            return this.packQueryResults(arr, code, msg, "y", rowCount);
        } else {
            return this.packQueryResults(arr, code, msg, "n", 0);
        }
    }

    public JSONObject packViewObject(ViewObjectImpl vo, String code, String msg, String[] attrs) throws JSONException {
        Row[] rows = getAllRowsInUrlRange(vo);
        JSONArray arr = RestUtil.convertRowsToJsonArray(vo,rows, attrs);
        if ("y".equalsIgnoreCase(estimated)) {
            long rowCount = 0;
            rowCount = vo.getEstimatedRowCount();
            return this.packQueryResults(arr, code, msg, "y", rowCount);
        } else {
            return this.packQueryResults(arr, code, msg, "n", 0);
        }
    }


    /**
     * 根据url参数start,limit,estimated执行查询
     * 1. 此方法内不执行executeQuery()，只返回指定范围内的记录
     * 2. 请提前设置好过滤条件,并执行executeQuery()
     * 3. 适用于基于非ID字段进行的查询，处理返回多笔的记录
     *
     * @param vo
     * @return Row[]
     */
    public Row[] getAllRowsInUrlRange(ViewObjectImpl vo) {
        if (start <= 0) {
            start = 1;
        }

        if (!checkQueryRange(vo)) {
            return new Row[] { };
        }

        if (vo.getRangeSize() != limit) {
            vo.setRangeSize(limit);
        }
        if (vo.getRangeStart() != start - 1) {
            vo.setRangeStart(start - 1);
        }
        return vo.getAllRowsInRange();
    }

    /**
     * 判断start是否大于记录总数
     * @param vo
     * @return false：start超过记录总数
     */
    public Boolean checkQueryRange(ViewObjectImpl vo) {
        long rowCount = vo.getEstimatedRowCount();
        if (start > rowCount) {
            return false;
        } else if (start == rowCount) {
            limit = 1;
        }
        if (start + limit > rowCount) {
            limit = (int) (rowCount - start) + 1;
        }
        return true;
    }

    /**
     * 将单笔查询结果（jsonObject）按照REST规范进行包装（不负责计算）
     * @param json 需要包装的数据
     * @param code s:成功，其它为失败
     * @param msg 需要发送给客户端的文字消息
     *
     * @return
     * @throws JSONException
     */
    public JSONObject packQueryResults(JSONObject json, String code, String msg) throws JSONException {
        JSONArray array = new JSONArray();
        array.put(json);
        return this.packQueryResults(array, code, msg, "n", 0);
    }

    /**
     * 将多笔记录的查询结果（JsonArray）按照REST规范进行包装（不负责计算）
     * @param array 需要包装的数据
     * @param code s:成功，其它为失败
     * @param msg 需要发送给客户端的文字消息
     * @param estimated y:输出 总记录数量，需要传入estimatedRowCount,本方法不自动计算
     * @param estimatedRowCount 总记录数量，需要在执行executeQuery后单独再执行getEstimatedRowCount获得
     * @return
     *  {
            code:业务执行状态码，s为成功
            msg:业务执行说明
            estimated:符合条件的记录总数
            count:当前返回条数
            data:[返回的多条记录]
        }
     * @throws JSONException
     */
    public JSONObject packQueryResults(JSONArray array, String code, String msg, String estimated,
                                       long estimatedRowCount) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if ("y".equals(estimated)) {
            json.put("estimated", estimatedRowCount);
        }
        if (array != null) {
            json.put("count", array.length());
        } else {
            System.err.println("packQueryResult:input param 'array' is null");
            json.put("count", 0);
        }
        json.put("data", array);
        return json;
    }


    public static String[] findAllVOAttributes(ViewObject vo) {
        return RestUtil.findAllVOAttributes(vo);
    }

    public static JSONArray convertRowIteratorToJsonArray(ViewObjectImpl vo, RowIterator it,
                                                          String[] attrs) throws JSONException {
        return RestUtil.convertRowIteratorToJsonArray(vo, it, attrs);
    }

    public static JSONArray convertVoToJsonArray(ViewObjectImpl vo, String[] attrs) throws JSONException {
        return RestUtil.convertVoToJsonArray(vo, attrs);
    }

    public static JSONArray convertRowsToJsonArray(ViewObjectImpl vo, Row[] rows, String[] attrs) throws JSONException {
        return RestUtil.convertRowsToJsonArray(vo, rows, attrs);
    }

    public static JSONObject convertRowToJsonObject(ViewObjectImpl vo, Row row, String[] attrs) throws JSONException {
        return RestUtil.convertRowToJsonObject(vo, row, attrs);
    }


    @Deprecated
    public static JSONArray convertVoToJsonArray(ViewObject vo, String[] attrs) throws JSONException {
        return RestUtil.convertVoToJsonArray(vo, attrs);
    }

    @Deprecated
    public static JSONArray convertRowsToJsonArray(Row[] rows, String[] attrs) throws JSONException {
        return RestUtil.convertRowsToJsonArray(rows, attrs);
    }

    @Deprecated
    public static JSONObject convertRowToJsonObject(Row row, String[] attrs) throws JSONException {
        return RestUtil.convertRowToJsonObject(row, attrs);
    }
    
    public static void copyJsonObjectToRow(JSONObject json, ViewObjectImpl vo, Row row,
                                           String[] attrs) throws JSONException {
            RestUtil.copyJsonObjectToRow(json, vo, row, attrs);
        }

    public static ApplicationModuleImpl findAmFromBinding(String dataControl) {
        return RestUtil.findAmFromBinding(dataControl);
    }

    @Deprecated
    public String findCurrentUserId() {
        return ADFContext.getCurrent().getSecurityContext().getUserName();
    }
    
    public String findCurrentUserName() {
        return ADFContext.getCurrent().getSecurityContext().getUserName();
    }

    public String[] findCurrentUserRoles() {
        return ADFContext.getCurrent().getSecurityContext().getUserRoles();
    }


    public boolean isUserInRole(String role) {
        return ADFContext.getCurrent().getSecurityContext().isUserInRole(role);
    }


}
