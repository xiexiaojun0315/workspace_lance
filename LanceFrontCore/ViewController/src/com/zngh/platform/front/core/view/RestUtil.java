package com.zngh.platform.front.core.view;

import com.zngh.platform.front.core.model.util.ConstantUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.SimpleDateFormat;

import java.util.Map;

import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.AttributeDef;
import oracle.jbo.LocaleContext;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.ClobDomain;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 为ADF提供REST基础工具类
 * 包含收发请求
 *  muhongdi@qq.com
 */
public class RestUtil {
    public static final ADFLogger LOGGER = ADFLogger.createADFLogger(RestUtil.class);

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final String CONTAINER_ID = "server.app.adf.container_id";

    public RestUtil() {
        super();
    }

    //不处理关系
    public static String[] findAllVOAttributes(ViewObject vo) {
        AttributeDef[] defs = vo.getAttributeDefs();
        String[] attrs = new String[defs.length];
        for (int i = 0; i < defs.length; i++) {
            attrs[i] = defs[i].getName();
        }
        return attrs;
    }


    @Deprecated
    public static JSONArray convertRowIteratorToJsonArray(RowIterator it, String[] attrs) throws JSONException {
        JSONArray array = new JSONArray();
        Row row;
        JSONObject json;
        while (it.hasNext()) {
            row = it.next();
            json = convertRowToJsonObject(row, attrs);
            array.put(json);
        }
        return array;
    }

    public static JSONArray convertRowIteratorToJsonArray(ViewObjectImpl vo, RowIterator it,
                                                          String[] attrs) throws JSONException {
        JSONArray array = new JSONArray();
        Row row;
        JSONObject json;
        while (it.hasNext()) {
            row = it.next();
            json = convertRowToJsonObject(vo, row, attrs);
            array.put(json);
        }
        return array;
    }

    /**
     *转换VO为JsonArray
     * @param vo
     * @param attrs
     * @return
     * @throws JSONException
     */
    public static JSONArray convertVoToJsonArray(ViewObjectImpl vo, String[] attrs) throws JSONException {
        JSONArray array = new JSONArray();
        RowSetIterator it = vo.createRowSetIterator(null);
        Row row;
        JSONObject json;
        while (it.hasNext()) {
            row = it.next();
            json = convertRowToJsonObject(vo, row, attrs);
            array.put(json);
        }
        it.closeRowSetIterator();
        return array;
    }

    /**
     * 转换VO为JsonArray
     * 已过期，推荐使用convertVoToJsonArray(ViewObjectImpl vo, String[] attrs)
     *
     * @param vo
     * @param attrs
     * @return
     * @throws JSONException
     */
    @Deprecated
    public static JSONArray convertVoToJsonArray(ViewObject vo, String[] attrs) throws JSONException {
        JSONArray array = new JSONArray();
        RowSetIterator it = vo.createRowSetIterator(null);
        Row row;
        JSONObject json;
        while (it.hasNext()) {
            row = it.next();
            try {
                json = convertRowToJsonObject((ViewObjectImpl) vo, row, attrs);
            } catch (JSONException jsone) {
                json = convertRowToJsonObject(row, attrs);
            }
            array.put(json);
        }
        it.closeRowSetIterator();
        return array;
    }

    /**
     * 已过期，推荐使用convertRowsToJsonArray(ViewObjectImpl vo, Row[] rows, String[] attrs)
     * @param rows
     * @param attrs
     * @return
     * @throws JSONException
     * @deprecated
     */
    @Deprecated
    public static JSONArray convertRowsToJsonArray(Row[] rows, String[] attrs) throws JSONException {
        JSONArray array = new JSONArray();
        JSONObject json;
        for (Row row : rows) {
            json = convertRowToJsonObject(row, attrs);
            array.put(json);
        }
        return array;
    }

    /**
     * 已过期，推荐使用convertRowToJsonObject(ViewObjectImpl vo, Row row, String[] attrs)
     * @param row
     * @param attrs
     * @return
     * @throws JSONException
     * @deprecated
     */
    @Deprecated
    public static JSONObject convertRowToJsonObject(Row row, String[] attrs) throws JSONException {
        JSONObject json = new JSONObject();
        Object obj;
        String clsName;
        for (String attr : attrs) {
            obj = row.getAttribute(attr);
            if (obj != null) {
                clsName = obj.getClass().getName();
                if ("oracle.jbo.domain.Date".equals(clsName)) {
                    json.put(attr, DEFAULT_DATE_FORMAT.format(((oracle.jbo.domain.Date) obj).getValue()));
                } else {
                    json.put(attr, obj);
                }
            }
        }
        return json;
    }

    /**
     * 将 Row[]转换为JsonArray
     * 可根据字段类型自动做转换
     *
     * @param vo
     * @param rows
     * @param attrs
     * @return
     * @throws JSONException
     */
    public static JSONArray convertRowsToJsonArray(ViewObjectImpl vo, Row[] rows, String[] attrs) throws JSONException {
        JSONArray array = new JSONArray();
        JSONObject json;
        for (Row row : rows) {
            json = convertRowToJsonObject(vo, row, attrs);
            array.put(json);
        }
        return array;
    }

    /**
     * 将Row转为JsonObject
     * 可根据字段类型自动做转换
     *
     * @param vo
     * @param row
     * @param attrs
     * @return
     * @throws JSONException
     */
    public static JSONObject convertRowToJsonObject(ViewObjectImpl vo, Row row, String[] attrs) throws JSONException {
        JSONObject json = new JSONObject();
        Object obj;
        String type;
        AttributeDef[] attrds = vo.getAttributeDefs();
        AttributeDef def;
        for (String attr : attrs) {
            obj = row.getAttribute(attr);
            def = getAttributeDefInDefs(attr, attrds);
            type = def.getJavaType().getName();
            if ("oracle.jbo.domain.Date".equals(type) || "java.sql.Timestamp".equals(type)) {
                if (obj != null) {
                    String date = null;
                    LocaleContext lc = vo.getApplicationModule().getSession().getLocaleContext();
                    if (lc != null) {
                        String format = def.getUIHelper().getFormat(lc);
                        if (StringUtils.isNotBlank(format)) {
                            SimpleDateFormat sdf = new SimpleDateFormat(format);
                            try {
                                date = sdf.format(obj);
                                json.put(attr, date);
                            } catch (Exception e) {
                                System.err.println("error1 attr is " + attr + " value:" + obj);
                                json.put(attr, obj);
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    if (lc == null || date == null) {
                        try {
                            json.put(attr, DEFAULT_TIME_FORMAT.format(obj));
                        } catch (Exception e) {
                            System.err.println("error2 attr is " + attr + " value:" + obj);
                            json.put(attr, obj);
                            System.out.println(e.getMessage());
                        }
                    }

                } else {
                    json.put(attr, obj);
                }
            } else if ("oracle.jbo.domain.ClobDomain".equals(type)) {
                if (obj != null) {
                    json.put(attr, ((oracle.jbo.domain.ClobDomain) obj).toString()); //示例
                } else {
                    json.put(attr, obj);
                }
            } else {
                json.put(attr, obj);
            }
        }
        return json;
    }

    public static void main(String[] args) {
        java.sql.Timestamp t = new java.sql.Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(sdf.format(t));
    }

    /**
     * 根据AttributeDef.name获取AttributeDef
     * @param name
     * @param attrds
     * @return
     */
    private static AttributeDef getAttributeDefInDefs(String name, AttributeDef[] attrds) {
        for (AttributeDef def : attrds) {
            if (def.getName().equals(name)) {
                return def;
            }
        }
        return null;
    }

    /**
     * 从json中获取值并插入row中对应的字段
     * @param json
     * @param vo
     * @param row
     * @param attrs
     * @throws JSONException
     */
    public static void copyJsonObjectToRow(JSONObject json, ViewObjectImpl vo, Row row,
                                           String[] attrs) throws JSONException {
        if (json == null || vo == null || row == null || attrs == null) {
            System.err.println("参数为空：copyJsonObjectToRow：");
            System.err.println("json:" + json + " ; " + "vo:" + vo + " ; " + "row:" + row + " ; " + "attrs:" + attrs);
        }
        AttributeDef[] defs = vo.getAttributeDefs();
        AttributeDef def;
        String type;
        for (String attr : attrs) {
            if (json.has(attr)) {
                def = getAttributeDefInDefs(attr, defs);
                type = def.getJavaType().getName();
                try {
                    if ("oracle.jbo.domain.ClobDomain".equals(type)) {
                        String c = json.getString(attr);
                        ClobDomain cd = new ClobDomain(c);
                        row.setAttribute(attr, cd);
                    } else if ("oracle.jbo.domain.Date".equals(type)) {
                        row.setAttribute(attr, json.get(attr));
                    } else {
                        row.setAttribute(attr, json.get(attr));
                    }
                } catch (Exception e) {
                    System.err.println("can't copy attribute(" + attr + ") from json to row");
                    LOGGER.log(LOGGER.ERROR, "can't copy attribute(" + attr + ") from json to row");
                    throw e;
                }
            }
        }

    }


    //    public static ApplicationModuleImpl findAmFromBinding(String dataControl) {
    //        try {
    //            @SuppressWarnings("oracle.jdeveloper.java.method-deprecated")
    //            DCDataControl dc = BindingContext.getCurrent().findDataControl(dataControl);
    //            ApplicationModuleImpl am = (ApplicationModuleImpl) dc.getDataProvider();
    //            LOGGER.log(am.toString());
    //            return am;
    //        } catch (Exception e) {
    //            LOGGER.log(LOGGER.ERROR, "BindingContext:" + BindingContext.getCurrent());
    //            LOGGER.log(LOGGER.ERROR, "ADFContext:" + ADFContext.getCurrent());
    //            LOGGER.log(LOGGER.ERROR, "FacesContext:" + FacesContext.getCurrentInstance());
    //            System.err.println("由于系统重新发布，请退出当前Session重新登录");
    //            e.printStackTrace();
    //        }
    //        LOGGER.log(LOGGER.ERROR, "findAmFromBinding can't find am:" + dataControl);
    //        return null;
    //    }

    public static ApplicationModuleImpl findAmFromBinding(String dataControl) {
        try {
            DCBindingContainer dbc =
                BindingContext.getCurrent().findBindingContainer((String) ConstantUtil.configMap.get(CONTAINER_ID));
            DCDataControl dc = dbc.findDataControl(dataControl);
            ApplicationModuleImpl am = (ApplicationModuleImpl) dc.getDataProvider();
            return am;
        } catch (Exception e) {
            LOGGER.log(LOGGER.ERROR, "BindingContext:" + BindingContext.getCurrent());
            LOGGER.log(LOGGER.ERROR, "ADFContext:" + ADFContext.getCurrent());
            LOGGER.log(LOGGER.ERROR, "FacesContext:" + FacesContext.getCurrentInstance());
            LOGGER.log(LOGGER.ERROR, "containerID:" + ConstantUtil.configMap.get(CONTAINER_ID));
            LOGGER.log(LOGGER.ERROR, "无法获取AM，请检查你当前项目对应的web.xml参数配置!");
            e.printStackTrace();
        }
        LOGGER.log(LOGGER.ERROR, "findAmFromBinding can't find am:" + dataControl);
        return null;
    }

    public static JSONObject sendHttpPostWithJson(String url, JSONObject in, Map headerParam) throws IOException,
                                                                                                     ClientProtocolException,
                                                                                                     JSONException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        StringEntity params = new StringEntity(in.toString());
        request.addHeader("content-type", "application/json");

        if (headerParam != null)
            for (Object key : headerParam.keySet()) {
                request.addHeader(key.toString(), headerParam.get(key).toString());
            }

        request.setEntity(params);
        HttpResponse responese = httpClient.execute(request);

        return getJsonFromResponse(responese);
    }

    public static HttpResponse sendHttpGet(String url) throws IOException, ClientProtocolException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse responese = httpClient.execute(request);
        //        LOGGER.log(LOGGER.NOTIFICATION,getStringFromResponse(responese));
        return responese;
    }

    public static JSONObject getJsonArrayFromResponse(HttpResponse responese) throws IOException, JSONException {
        return new JSONObject(getStringFromResponse(responese));
    }

    public static JSONObject getJsonObjectFromResponse(HttpResponse responese) throws IOException, JSONException {
        return new JSONObject(getStringFromResponse(responese));
    }

    /**
     * 已过期，用getJsonObjectFromResponse方法，功能相同
     *
     * @param responese
     * @return
     * @throws IOException
     * @throws JSONException
     * @deprecated
     */
    @Deprecated
    public static JSONObject getJsonFromResponse(HttpResponse responese) throws IOException, JSONException {
        return new JSONObject(getStringFromResponse(responese));
    }

    public static String getStringFromResponse(HttpResponse responese) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(responese.getEntity().getContent()));
        for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2.readLine()) {
            builder.append(s);
        }
        return builder.toString();
    }


}
