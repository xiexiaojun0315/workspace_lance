package com.lance.view.rest.uuser;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.user.vo.UserEducationVOImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import com.zngh.platform.front.core.view.RestUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 对User的教育信息提供
 * 1.单条新增
 * 2.单条修改
 * 3.获取全部Edu.信息
 * 4.单条删除Edu.
 * 字段说明
 * Institution Name 机构名称
 * Attach_link提供3个链接，可以用于证书查看
 * Display 0不显示，1显示
 * todo 教育结果验证功能
 *
 */
/**
    Uuid,Precision:32,JavaType:java.lang.String
    UserName,Precision:32,JavaType:java.lang.String
    InstitutionName,Precision:45,JavaType:java.lang.String
    DegreeType,Precision:45,JavaType:java.lang.String
    StartDate,Precision:0,JavaType:oracle.jbo.domain.Date
    EndDate,Precision:0,JavaType:oracle.jbo.domain.Date
    Description,Precision:0,JavaType:oracle.jbo.domain.ClobDomain
    Attach1Link,Precision:500,JavaType:java.lang.String
    Attach2Link,Precision:500,JavaType:java.lang.String
    Attach3Link,Precision:500,JavaType:java.lang.String
    TobeVerified,Precision:0,JavaType:java.math.BigDecimal
    DoneVerified,Precision:0,JavaType:java.math.BigDecimal
    Display,Precision:0,JavaType:java.math.BigDecimal
    CreateBy,Precision:32,JavaType:java.lang.String
    CreateOn,Precision:0,JavaType:oracle.jbo.domain.Date
    ModifyBy,Precision:32,JavaType:java.lang.String
    ModifyOn,Precision:0,JavaType:oracle.jbo.domain.Date
    Version,Precision:0,JavaType:java.math.BigDecimal
 */

@Path("user/education")
@SuppressWarnings("oracle.jdeveloper.java.array-field-access")
public class UserEducationResource extends BaseRestResource {

    public static final String[] ATTR_ALL = {
        "Uuid", "UserName", "InstitutionName", "DegreeType", "StartDate", "EndDate", "Description", "Attach1Link",
        "Attach2Link", "Attach3Link", "TobeVerified", "DoneVerified", "Display", "CreateBy", "CreateOn", "ModifyBy",
        "ModifyOn", "Version"
    };

    public static final String[] ATTR_CREATE = {
        "InstitutionName", "DegreeType", "StartDate", "EndDate", "Description", "Attach1Link", "Attach2Link",
        "Attach3Link", "TobeVerified", "DoneVerified", "Display"
    };

    public static final String[] ATTR_GET = {
        "Uuid", "UserName", "InstitutionName", "DegreeType", "StartDate", "EndDate", "Description", "Attach1Link",
        "Attach2Link", "Attach3Link", "TobeVerified", "DoneVerified", "Display"
    };

    public static final String[] ATTR_UPDATE = {
        "InstitutionName", "DegreeType", "StartDate", "EndDate", "Description", "Attach1Link", "Attach2Link",
        "Attach3Link", "TobeVerified", "DoneVerified", "Display"
    };


    public UserEducationResource() {
    }

    /**
     * 为指定User新增单条Edu.教育信息
     *
     * @param lancerId
     * @param json
     * @return 32位Edu. ID
     * @throws JSONException
     */
    @POST
    @Path("{userName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUserEducation(@PathParam("userName") String userName, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        String res = createUserEducationFn(userName, json, am);
        am.getDBTransaction().commit();
        return res;
    }

    public String createUserEducationFn(String userName, JSONObject json, LanceRestAMImpl am) throws JSONException {
        LUtil.getUUserByName(userName, am);
        ViewObjectImpl vo = am.getUserEducation1();
        if (vo.getRowCount() > 20) {
            return "msg:学历信息不能大于20条";
        }
        Row row = LUtil.createInsertRow(vo);
        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_CREATE);
        return (String) row.getAttribute("Uuid");
    }

    /**
     * 修改单条教育信息
     */
    @POST
    @Path("update/{userName}/{educationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUserEducation(@PathParam("userName") String userName,
                                      @PathParam("educationId") String educationId,
                                      JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        LUtil.getUUserByName(userName, am);

        ViewObjectImpl vo = am.getUserEducation1();
        Row row = LUtil.getByKey(vo, educationId);
        if (row == null) {
            return "msg:记录不存在或已删除";
        }
        vo.setCurrentRow(row);
        System.out.println(row);

        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_UPDATE);
        am.getDBTransaction().commit();
        return "ok";
    }

    /**
     * 获取指定User的Edu.教育信息
     * GET http://localhost:7101/lance/res/user/lancer/education/{lancerId}
     *
     * Example
     * GET http://localhost:7101/lance/res/user/lancer/education/muhongdi
     * Return
         [
            {
                "InstitutionName" : "sy ooxx citi",
                "DegreeType" : "学位",
                "StartDate" : "2012-12-12 00:00:00",
                "EndDate" : "2014-12-12 00:00:00"
            },
            {
                "InstitutionName" : "sy ooxx citi2",
                "DegreeType" : "学位2",
                "StartDate" : "2012-12-14 00:00:00",
                "EndDate" : "2014-12-15 00:00:00"
            }
        ]

     *
     * @param lancerId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("{userName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONArray findAllUserEducation(@PathParam("userName") String userName) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        return findAllUserEducationFn(userName, am);
    }

    public JSONArray findAllUserEducationFn(String userName, LanceRestAMImpl am) throws JSONException {
        LUtil.getUUserByName(userName, am);
        UserEducationVOImpl vo2 = am.getUserEducation1();
        if (vo2.getRowCount() == 0) {
            return new JSONArray();
        }
        return RestUtil.convertVoToJsonArray(vo2, this.ATTR_GET);
    }


    /**
     *
     * 删除单条教育信息
     * POST http://localhost:7101/lance/res/user/lancer/education/delete/{lancerId}/{educationId}
     *
     * Example
     * POST http://localhost:7101/lance/res/user/lancer/education/delete/muhongdi/20162e4addbd43adb73273f658d52063
     * Return ok
     *
     * @param lancerId
     * @param educationId
     * @return
     * @throws JSONException
     */
    @POST
    @Path("delete/{userName}/{educationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteUserEducation(@PathParam("userName") String userName,
                                      @PathParam("educationId") String educationId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        LUtil.getUUserByName(userName, am);

        ViewObjectImpl vo = am.getUserEducation1();
        LUtil.getByKey(vo, educationId);
        vo.removeCurrentRow();
        am.getDBTransaction().commit();
        return "ok";
    }

}
