package com.lance.view.rest.uuser;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.user.vo.UUserVOImpl;
import com.lance.model.user.vo.UUserVORowImpl;
import com.lance.model.user.vo.UserRoleGrantsVOImpl;
import com.lance.model.user.vo.UserRoleGrantsVORowImpl;
import com.lance.view.util.LUtil;

import com.lance.view.util.RestSecurityUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import java.text.SimpleDateFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oracle.jbo.AttributeDef;
import oracle.jbo.LocaleContext;
import oracle.jbo.Row;
import oracle.jbo.domain.ClobDomain;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.commons.lang.StringUtils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


/**
    UserName,Precision:50,JavaType:java.lang.String
    TrueName,Precision:50,JavaType:java.lang.String
    DisplayName,Precision:50,JavaType:java.lang.String
    Email,Precision:100,JavaType:java.lang.String
    Password,Precision:100,JavaType:java.lang.String
    Img,Precision:900,JavaType:java.lang.String
    Country,Precision:32,JavaType:java.lang.String
    AccountType,Precision:100,JavaType:java.lang.String
    CompanyId,Precision:32,JavaType:java.lang.String
    PhoneNumber,Precision:20,JavaType:java.lang.String
    Attach,Precision:200,JavaType:java.lang.String
    JobTitle,Precision:20,JavaType:java.lang.String
    Video,Precision:200,JavaType:java.lang.String
    Description,Precision:0,JavaType:oracle.jbo.domain.ClobDomain
    WebsiteUrl,Precision:500,JavaType:java.lang.String
    ImNumberA,Precision:50,JavaType:java.lang.String
    ImTypeA,Precision:20,JavaType:java.lang.String
    ImNumberB,Precision:50,JavaType:java.lang.String
    ImTypeB,Precision:20,JavaType:java.lang.String
    ImNumberC,Precision:50,JavaType:java.lang.String
    ImTypeC,Precision:20,JavaType:java.lang.String
    LocationA,Precision:32,JavaType:java.lang.String
    LocationB,Precision:32,JavaType:java.lang.String
    Tagline,Precision:150,JavaType:java.lang.String
    HourlyRate,Precision:0,JavaType:java.math.BigDecimal Lancer提出的每小时价格，例如100
    ChargeRate,Precision:0,JavaType:java.math.BigDecimal 网站会向客户提出的价格，例如100.13
    Overview,Precision:3000,JavaType:java.lang.String  简历上的简短描述
    ServiceDescription,Precision:0,JavaType:oracle.jbo.domain.ClobDomain  服务描述 长度不能大于8000
    PaymentTerms,Precision:0,JavaType:oracle.jbo.domain.ClobDomain  支付方式描述 长度不能大于4000
    Keywords,Precision:1500,JavaType:java.lang.String
    AddressDisplay,Precision:10,JavaType:java.lang.String
    ContactInfo,Precision:10,JavaType:java.lang.String
    CreateBy,Precision:32,JavaType:java.lang.String
    CreateOn,Precision:0,JavaType:oracle.jbo.domain.Date
    ModifyBy,Precision:32,JavaType:java.lang.String
    ModifyOn,Precision:0,JavaType:oracle.jbo.domain.Date
    Version,Precision:0,JavaType:java.math.BigDecimal
    LastLoginTime,Precision:0,JavaType:oracle.jbo.domain.Date
    CompanyName,Precision:255,JavaType:java.lang.String
    CanBeSearch,Precision:0,JavaType:java.math.BigDecimal  是否可被搜索到（隐私）
    DefaultRole,Precision:20,JavaType:java.lang.String    默认角色（用于跳转到相应主页）  client,lancer,contract
 */
@Path("user")
public class UserResource extends BaseRestResource {
    /**
     * UserName 唯一用户名，登录名，ID
     * TrueName 真实姓名
     * DisplayName 显示名，类似网名
     * Email 邮箱，唯一
     * Password 今后在前端做加密 todo
     * Img 头像链接
     * AddressDisplay 地址显示级别
     *      all:Display all address fields
     *      city:Display only city, state, and zip
     *      no:No contact fields
     * ContactInfo 联系信息显示级别
     *      all:All contact fields
     *      noName:Only non-name fields (email, phone, fax, website)
     *      no:No contact fields
     *
     */
    public static final String[] ATTR_ALL = {
        "UserName", "TrueName", "DisplayName", "Email", "Password", "Img", "Country", "CompanyId", "PhoneNumber",
        "Attach", "JobTitle", "Video", "Description", "WebsiteUrl", "ImNumberA", "ImTypeA", "ImNumberB", "ImTypeB",
        "ImNumberC", "ImTypeC", "LocationA", "LocationB", "Tagline", "HourlyRate", "ChargeRate", "Overview",
        "ServiceDescription", "PaymentTerms", "Keywords", "AddressDisplay", "ContactInfo", "CreateBy", "CreateOn",
        "ModifyBy", "ModifyOn", "Version", "LastLoginTime", "CompanyName", "CanBeSearch", "DefaultRole"
    };


    //todo 完善字段

    /**
     * DefaultRole:默认角色，可输入：client,lancer 。仅创建时带入，不支持修改
     */
    public static final String[] ATTR_CREATE = {
        "UserName", "TrueName", "DisplayName", "Email", "Password", "Img", "Country", "CompanyId", "PhoneNumber",
        "Attach", "JobTitle", "Video", "Description", "WebsiteUrl", "ImNumberA", "ImTypeA", "ImNumberB", "ImTypeB",
        "ImNumberC", "ImTypeC", "LocationA", "LocationB", "Tagline", "HourlyRate", "ChargeRate", "Overview",
        "ServiceDescription", "PaymentTerms", "Keywords", "AddressDisplay", "ContactInfo", "CompanyName", "CanBeSearch",
        "DefaultRole"
    };

    public static final String[] ATTR_UPDATE = {
        "DisplayName", "Email", "Password", "Img", "Country", "CompanyId", "PhoneNumber", "Attach", "JobTitle", "Video",
        "Description", "WebsiteUrl", "ImNumberA", "ImTypeA", "ImNumberB", "ImTypeB", "ImNumberC", "ImTypeC",
        "LocationA", "LocationB", "Tagline", "HourlyRate", "ChargeRate", "Overview", "ServiceDescription",
        "PaymentTerms", "Keywords", "AddressDisplay", "ContactInfo", "LastLoginTime", "CompanyName", "CanBeSearch",
        "DefaultRole"
    };

    public static final String[] ATTR_GET = {
        "UserName", "TrueName", "DisplayName", "Email", "Img", "Country", "CompanyId", "PhoneNumber", "Attach",
        "JobTitle", "Video", "Description", "WebsiteUrl", "ImNumberA", "ImTypeA", "ImNumberB", "ImTypeB", "ImNumberC",
        "ImTypeC", "LocationA", "LocationB", "Tagline", "HourlyRate", "ChargeRate", "Overview", "ServiceDescription",
        "PaymentTerms", "Keywords", "AddressDisplay", "ContactInfo", "CreateBy", "CreateOn", "ModifyBy", "ModifyOn",
        "Version", "LastLoginTime", "CompanyName", "CanBeSearch", "DefaultRole"
    };

    public static final String[] ATTR_GET_A = {
        "UserName", "Email", "DisplayName", "Country", "TrueName", "CompanyId", "CompanyName", "LocationA", "LocationB",
        "PhoneNumber", "WebsiteUrl", "ImNumberA", "ImNumberB", "ImNumberC", "ImTypeA", "ImTypeB", "ImTypeC"
    };

    public UserResource() {
    }


    /**
     * 用户注册
     * todo 验证码 记录ip
     *
     *
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createNewUser(JSONObject json) throws JSONException {
        System.out.println("createNewUser");
        System.out.println(json);
        LanceRestAMImpl am = LUtil.findLanceAM();
        UUserVOImpl vo = am.getUUser1();
        UUserVORowImpl row = (UUserVORowImpl) LUtil.createInsertRow(vo);

        for (String attr : ATTR_CREATE) {
            if ("CompanyName".equals(attr) && json.has("CompanyName")) {
                //如果存在CompanyName，则执行merge操作
                String companyId = new CompanyResource().mergeCompanyByName(json.getString("CompanyName"));
                row.setAttribute("CompanyId", companyId);
                continue;
            } else if ("LocationA,LocationB".indexOf(attr) != -1) {
                continue;
            } else if ("DefaultRole".equals(attr)) {
                continue;
            } else if (json.has(attr)) {
                row.setAttribute(attr, json.get(attr));
            }
        }

        //提取地址信息
        if (json.has("create(LocationA)")) {
            String loc_id =
                new UserLocationListResource().createLocationFn(json.getString("UserName"),
                                                                json.getJSONObject("create(LocationA)"), am);
            row.setAttribute("LocationA", loc_id);
        }
        if (json.has("create(LocationB)")) {
            String loc_id =
                new UserLocationListResource().createLocationFn(json.getString("UserName"),
                                                                json.getJSONObject("create(LocationB)"), am);
            row.setAttribute("LocationB", loc_id);
        }

        //授权角色
        if ("client,lancer,contract".indexOf(json.getString("DefaultRole")) != -1) {
            UserRoleGrantsVOImpl grantsVo = am.getUserRoleGrants1();
            UserRoleGrantsVORowImpl grantsRow = (UserRoleGrantsVORowImpl) grantsVo.createRow();
            grantsRow.setUserName(json.getString("UserName"));
            //DefaultRole只允许设置基础角色
            grantsRow.setRoleName(json.getString("DefaultRole"));
            grantsVo.insertRow(grantsRow);
            row.setAttribute("DefaultRole", json.getString("DefaultRole"));
        }

        String cm = am.commit();
        if (!"ok".equals(cm)) {
            return cm;
        }
        System.out.println("返回新增记录的ID " + json.getString("UserName"));
        return json.getString("UserName"); //返回新增记录的ID
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{userName}")
    public JSONObject findUserById(@PathParam("userName") String userName) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        return findUserByNameInJsonFn(userName, am);
    }

    public static JSONObject findUserByNameInJsonFn(String userName, LanceRestAMImpl am) throws JSONException {
        UUserVORowImpl row = LUtil.getUUserByName(userName, am);
        JSONObject data = new JSONObject();

        if (row == null) {
            JSONObject res = new JSONObject();
            res.put("err", "找不到用户:" + userName);
            return res;
        }


        JSONObject json = convertRowToJsonObject(am.getUUser1(), row, ATTR_GET);
        data.put("User", json);

        //处理位置信息
        if (json.has("LocationA") || json.has("LocationB")) {
            UserLocationListResource loc = new UserLocationListResource();
            if (json.has("LocationA")) {
                data.put("LocationA", loc.findLocation(userName, json.getString("LocationA")));
            }

            if (json.has("LocationB")) {
                data.put("LocationB", loc.findLocation(userName, json.getString("LocationB")));
            }
        }

        return data;
    }

    //    public static UUserVORowImpl findUserByNameFn(String userName, LanceRestAMImpl am) {
    //        UUserVOImpl vo = am.getUUser1();
    //        vo.setApplyViewCriteriaName("FindByUserNameVC");
    //        vo.setpUserName(userName);
    //        vo.executeQuery();
    //        vo.setApplyViewCriteriaName(null);
    //        return (UUserVORowImpl) vo.first();
    //    }

    @POST
    @Path("delete/{userName}")
    public String deleteUser(@PathParam("userName") String userName) {
        LanceRestAMImpl am = LUtil.findLanceAM();
        UUserVOImpl vo = am.getUUser1();

        UUserVORowImpl row = LUtil.getUUserByName(userName, am);
        if (row == null) {
            return "error:找不到用户:" + userName;
        }
        vo.removeCurrentRow();

        am.getDBTransaction().commit();
        return "ok";
    }

    /**
     * 更新用户信息
     *
     * 关于公司
     * User更换公司：先调用CompanyResource的mergeByName接口获取公司ID，再将公司ID传入
     * 单纯修改公司信息（公司名、公司描述等）：调用CompanyResource的update/{companyId}接口
     *
     * 删除公司
     * {
     * CompanyId:""
     * }
     * 不处理公司，保持原状可以不传CompanyId
     *
     *
     * 关于地址
     * 创建用户时会默认生成两个地址ID，对应空地址（常驻地址）。
     * User修改常住地址时，先创建地址记录，然后设置常住地址ID
     *
     *
     * @param userName
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update/{userName}")
    public String updateUser(@PathParam("userName") String userName, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = am.getUUser1();
        UUserVORowImpl row = LUtil.getUUserByName(userName, am);
        if (row == null) {
            return "msg:找不到用户:" + userName;
        }

        if (!RestSecurityUtil.isOwner(row)) {
            return "msg:没有足够的权限修改此用户";
        }
        copyJsonObjectToRow(json, vo, row, ATTR_UPDATE);
        am.getDBTransaction().commit();
        return "ok";
    }

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
            if ("oracle.jbo.domain.Date".equals(type)) {
                if (obj != null) {
                    String date = null;
                    LocaleContext lc = vo.getApplicationModule().getSession().getLocaleContext();
                    if (lc != null) {
                        String format = def.getUIHelper().getFormat(lc);
                        System.out.println(format);
                        if (StringUtils.isNotBlank(format)) {
                            SimpleDateFormat sdf = new SimpleDateFormat(format);
                            date = sdf.format(((oracle.jbo.domain.Date) obj).getValue());
                            json.put(attr, date);
                        }
                    }
                    if (StringUtils.isBlank(date)) {
                        json.put(attr, DEFAULT_DATE_FORMAT.format(((oracle.jbo.domain.Date) obj).getValue()));
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

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static AttributeDef getAttributeDefInDefs(String name, AttributeDef[] attrds) {
        for (AttributeDef def : attrds) {
            if (def.getName().equals(name)) {
                return def;
            }
        }
        return null;
    }

    public static void copyJsonObjectToRow(JSONObject json, ViewObjectImpl vo, Row row,
                                           String[] attrs) throws JSONException {
        AttributeDef[] defs = vo.getAttributeDefs();
        AttributeDef def;
        String type;
        for (String attr : attrs) {
            if (json.has(attr)) {
                def = getAttributeDefInDefs(attr, defs);
                type = def.getJavaType().getName();
                if ("oracle.jbo.domain.ClobDomain".equals(type)) {
                    String c = json.getString(attr);
                    ClobDomain cd = new ClobDomain(c);
                    row.setAttribute(attr, cd);
                } else if ("oracle.jbo.domain.Date".equals(type)) {
                    row.setAttribute(attr, json.get(attr));
                } else {
                    row.setAttribute(attr, json.get(attr));
                }
            }
        }
    }

    /**
     * 检查用户UserName是否已存在
     *
     * GET http://localhost:7101/lance/res/user/check/userName/{userName}
     *
     * @param userName
     * @return true:用户已存在，不能继续。false：用户不存在，可以继续
     * @throws JSONException
     */
    @GET
    @Path("exist/userName/{userName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String existUserName(@PathParam("userName") String userName) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        Row row = LUtil.getUUserByName(userName, am);

        if (row == null) {
            return "false";
        } else {
            System.out.println(row.getAttribute("UserName"));
            return "true";
        }
    }

    /**
     * 检查Email是否已存在
     *
     * GET http://localhost:7101/lance/res/user/exist/email/{email}
     *
     * @param userName
     * @return true:email已存在，不能继续。false：email不存在，可以继续
     * @throws JSONException
     */
    @GET
    @Path("exist/email/{email}")
    @Produces(MediaType.TEXT_PLAIN)
    public String existEmail(@PathParam("email") String email) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        UUserVOImpl vo = am.getUUser1();
        vo.setApplyViewCriteriaName("FindByEmailVC");
        vo.setpEmail(email);
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByEmailVC");
        if (vo.getRowCount() > 0) {
            return "true"; //用户已注册
        }
        return "false"; //用户不存在
    }

}
