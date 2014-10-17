package com.lance.view.rest.user;

import com.lance.model.LanceRestAMImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

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
 * 对Lancer的教育信息提供
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
@Path("user/lancer/education")
public class LancerEducationResource extends BaseRestResource {

    public static final String[] ATTR_CREATE = {
        "InstitutionName", "DegreeType", "StartDate", "EndDate", "Description", "Attach1Link", "Attach2Link",
        "Attach3Link"
    };
    public static final String[] ATTR_GET = {
        "InstitutionName", "DegreeType", "StartDate", "EndDate", "Description", "Attach1Link", "Attach2Link",
        "Attach3Link"
    };


    public LancerEducationResource() {
    }

    /**
     * 为指定Lancer新增单条Edu.教育信息
     * POST http://localhost:7101/lance/res/user/lancer/education/{lancerId}
     *
     * Example
     * POST http://localhost:7101/lance/res/user/lancer/education/muhongdi
      
            {
                "InstitutionName" : "sy ooxx citi",
                "DegreeType" : "学位",
                "StartDate" : "2012-12-12 00:00:00",
                "EndDate" : "2014-12-12 00:00:00"
            }
      
       Return 20162e4addbd43adb73273f658d52063
     *
     * @param lancerId
     * @param json
     * @return 32位Edu. ID
     * @throws JSONException
     */
    @POST
    @Path("{lancerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createLancerEducation(@PathParam("lancerId") String lancerId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        String uuid = createLancerEducationFn(lancerId, json, am);
        am.getDBTransaction().commit();
        return uuid;
    }

    public String createLancerEducationFn(String lancerId, JSONObject json, LanceRestAMImpl am) throws JSONException {
        new LancerResumeResource().findLancerResumeByLancerIdFn(lancerId, am);
        ViewObjectImpl vo2 = am.getLancerEducation1();
        Row row2 = LUtil.createInsertRow(vo2);
        LUtil.transJsonToRow(json, row2, this.ATTR_CREATE);
        return (String) row2.getAttribute("Uuid");
    }

    /**
     * 修改单条教育信息
     * POST http://localhost:7101/lance/res/user/lancer/education/update/{lancerId}/{educationId}
     *
     * Example
     * POST http://localhost:7101/lance/res/user/lancer/education/update/muhongdi/20162e4addbd43adb73273f658d52063
     *  {
            "InstitutionName" : "sy ooxx citi3",
            "DegreeType" : "学位3",
            "StartDate" : "2012-12-14 00:00:00",
            "EndDate" : "2014-12-15 00:00:00"
        }
        Return ok
     *
     *
     * @param lancerId
     * @param educationId
     * @param json
     * @return ok
     * @throws JSONException
     */
    @POST
    @Path("update/{lancerId}/{educationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateLancerEducation(@PathParam("lancerId") String lancerId,
                                        @PathParam("educationId") String educationId,
                                        JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        new LancerResumeResource().findLancerResumeByLancerIdFn(lancerId, am);

        ViewObjectImpl vo2 = am.getLancerEducation1();
        Row row2 = vo2.findByKey(new Key(new Object[] { educationId }), 1)[0];
        vo2.setCurrentRow(row2);

        LUtil.transJsonToRow(json, row2, this.ATTR_CREATE);
        am.getDBTransaction().commit();
        return "ok";
    }

    /**
     * 获取指定Lancer的Edu.教育信息
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
    @Path("{lancerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONArray findLancerEducationByLancerId(@PathParam("lancerId") String lancerId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        return findLancerEducationByLancerIdFn(lancerId, am);
    }

    public JSONArray findLancerEducationByLancerIdFn(String lancerId, LanceRestAMImpl am) throws JSONException {
        new LancerResumeResource().findLancerResumeByLancerIdFn(lancerId, am);
        ViewObjectImpl vo2 = am.getLancerEducation1();
        if (vo2.getRowCount() == 0) {
            return new JSONArray();
        }
        return this.convertVoToJsonArray(vo2, this.ATTR_GET);
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
    @Path("delete/{lancerId}/{educationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteLancerEducation(@PathParam("lancerId") String lancerId,
                                        @PathParam("educationId") String educationId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        new LancerResumeResource().findLancerResumeByLancerIdFn(lancerId, am);

        ViewObjectImpl vo2 = am.getLancerEducation1();
        Row row2 = vo2.findByKey(new Key(new Object[] { educationId }), 1)[0];
        vo2.setCurrentRow(row2);

        vo2.removeCurrentRow();
        am.getDBTransaction().commit();
        return "ok";
    }
}
