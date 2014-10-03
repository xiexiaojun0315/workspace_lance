package com.lance.view.rest.user;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.ClientUserVOImpl;
import com.lance.model.vo.LancerVOImpl;
import com.lance.model.vo.LoginUserVOImpl;
import com.lance.view.util.LanceRestUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;

@Path("user/check")
public class RegCheckResource {
    
    
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
    @Path("userName/{userName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String existUserName(@PathParam("userName") String userName) throws JSONException {
        System.out.println("existUserName:" + userName);
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        LoginUserVOImpl logvo = am.getLoginUser1();
        logvo.setApplyViewCriteriaName("FindByUserNameVC");
        logvo.setpUserName(userName);
        logvo.executeQuery();
        System.out.println(logvo.getRowCount());
        logvo.setApplyViewCriteriaName(null);
        if (logvo.getRowCount() > 0) {
            return "true"; //用户已存在
        }
        return "false"; //用户不存在
    }

    /**
     * 检查Email是否已存在
     * 
     * GET http://localhost:7101/lance/res/user/check/email/{email}
     * 
     * @param userName
     * @return true:email已存在，不能继续。false：email不存在，可以继续
     * @throws JSONException
     */
    @GET
    @Path("email/{email}")
    @Produces(MediaType.TEXT_PLAIN)
    public String existEmail(@PathParam("email") String email) throws JSONException {
        System.out.println("existEmail:" + email);
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        LancerVOImpl vo = am.getLancer1(); //todo add table index
        vo.setApplyViewCriteriaName("FindByEmailVC");
        vo.setpEmail(email);
        vo.executeQuery();
        vo.setApplyViewCriteriaName(null);
        if (vo.getRowCount() > 0) {
            return "true"; //用户已注册
        }
        ClientUserVOImpl vo2 = am.getClientUser1();
        vo2.setApplyViewCriteriaName("FindByEmailVC");
        vo2.setpEmail(email); //todo add table index
        vo2.executeQuery();
        System.out.println(vo2.getQuery());
        vo2.setApplyViewCriteriaName(null);
        System.out.println(vo2.first()==null);
        if (vo2.getRowCount() > 0) {
            return "true"; //用户已注册
        }
        return "false"; //用户不存在
    }
}
