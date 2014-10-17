package com.lance.view.rest.user;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.LancerSkillVOImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 对Skill的技能提供
 * 1.批量增加
 * 2.单个删除
 * 3.重排序 todo 实现方案待确定，基于前台实现？
 * 4.查询
 * 技能重复输入时不提示错误，后台将忽略重复的添加 todo
 * 无法删除验证过的Skill，只能隐藏
 * 不提供修改功能
 *
 *
 * 字段说明：
 * Name技能名
 * Tested技能是否经过系统认证（忽略）todo（认证系统）
 * Display是否显示，0不显示，1显示
 * ShowOrder排序，执行新增操作时，后台自动按JsonArray的顺序生成排序 todo
 * MasterLevel 同Tested，忽略
 */
@Path("user/lancer/skill")
public class LancerSkillResource extends BaseRestResource {
    public static final String[] ATTR_CREATE = { "Name", "ShowOrder", "Display"};
    public static final String[] ATTR_UPDATE = { "Name", "ShowOrder" , "Display"};
    public static final String[] ATTR_GET = {
        "Name", "Tested", "Display", "ShowOrder", "MasterLevel", "Uuid" };

    public LancerSkillResource() {
    }

    /**
     *
     * 单个删除
     * POST http://localhost:7101/lance/res/user/lancer/skill/delete/{lancerId}/{lancerSkillId}
     *
     * example
     * POST http://localhost:7101/lance/res/user/lancer/skill/delete/muhongdi/594f2c55bd5749eabaf93f74b6c5a364
     * return ok
     *
     * @param lancerId
     * @param skillId
     * @return ok
     * @throws JSONException
     */
    @POST
    @Path("delete/{lancerId}/{skillId}")
    public String deleteLancerSkill(@PathParam("lancerId") String lancerId,
                                    @PathParam("skillId") String skillId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        //find and set current parent row
        new LancerResumeResource().findLancerResumeByLancerIdFn(lancerId, am);

        LancerSkillVOImpl vo2 = am.getLancerSkill1();
        Row row2 = vo2.findByKey(new Key(new Object[] { skillId }), 1)[0];
        vo2.setCurrentRow(row2);

        vo2.removeCurrentRow();
        am.getDBTransaction().commit();
        return "ok";
    }

    /**
     *
     * 批量为Lancer添加Skill
     * todo 忽略重复名称（基于前台实现，需先确认此接口可用）
     * POST http://localhost:7101/lance/res/user/lancer/skill/{lancerId}
     * 
     * example
     * POST http://localhost:7101/lance/res/user/lancer/skill/muhongdi
     * [
            {
                "Name" : "XML"
            },
            {
                "Name" : "CSS"
            }
        ]           
     *
     *
     * @param lancerId
     * @param JSONArray
     * @return  ok
     * @throws JSONException
     *
     */
    @POST
    @Path("{lancerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createLancerSkills(@PathParam("lancerId") String lancerId, JSONArray jarray) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        createLancerSkillsFn(lancerId, jarray, am);
        am.getDBTransaction().commit();
        return "ok";
    }

    public void createLancerSkillsFn(String lancerId, JSONArray jarray, LanceRestAMImpl am) throws JSONException {
        //find and set current parent row
        new LancerResumeResource().findLancerResumeByLancerIdFn(lancerId, am);

        ViewObjectImpl vo2 = am.getLancerSkill1();
        JSONObject json;
        Row row2;
        for (int i = 0; i < jarray.length(); i++) {
            json = jarray.getJSONObject(i);
            row2 = LUtil.createInsertRow(vo2);
            LUtil.transJsonToRow(json, row2, this.ATTR_CREATE);
        }
    }

    /**
     * 根据LancerId获取属于该Lancer的Skills
     *
     * GET http://localhost:7101/lance/res/user/lancer/skill/{lancerId}
     *
     * example
     * GET http://localhost:7101/lance/res/user/lancer/skill/muhongdi
     *
     * [
        {
            "Name" : "html",
            "Tested" : 0,
            "Display" : 1,
            "ShowOrder" : 1,
            "MasterLevel" : 0,
            "Uuid" : "1",
            "ResumeId" : "1"
        }
       ]
     *
     * @param lancerId
     * @return JSONArray
     * @throws JSONException
     *
     */
    @GET
    @Path("{lancerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray findLancerSkills(@PathParam("lancerId") String lancerId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        return findLancerSkillsFn(lancerId, am);
    }

    public JSONArray findLancerSkillsFn(String lancerId, LanceRestAMImpl am) throws JSONException {
        new LancerResumeResource().findLancerResumeByLancerIdFn(lancerId, am);
        ViewObjectImpl vo2 = am.getLancerSkill1();
        if (vo2.getRowCount() == 0) {
            return new JSONArray();
        }
        return this.convertVoToJsonArray(vo2, this.ATTR_GET);
    }

}
