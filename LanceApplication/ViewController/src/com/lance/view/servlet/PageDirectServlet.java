package com.lance.view.servlet;

//import com.lance.view.rest.user.LancerProfileResource;
import com.lance.view.rest.job.SearchResource;
import com.lance.view.rest.project.ContractResource;
import com.lance.view.rest.uuser.LookupsResource;
import com.lance.view.rest.uuser.UserEducationResource;
import com.lance.view.rest.uuser.UserLocationListResource;
import com.lance.view.rest.uuser.UserResource;
import com.lance.view.rest.uuser.UserSkillResource;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.ADFContext;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 此页面执行页面跳转
 */
public class PageDirectServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * 页面路径映射
     * 目前只用于登陆后界面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println(request.getRequestURL());
        String uri = request.getRequestURI();
        try {
            //如果用户访问的是登录后界面
            if (uri.startsWith("/lance/pages/")) {
                System.out.println(ADFContext.getCurrent().getSecurityContext().isAuthenticated());
                if (!ADFContext.getCurrent().getSecurityContext().isAuthenticated()) {
                    response.sendRedirect("/lance/login.htm");
                }
            }

            if ("/lance/pages/MyHome".equals(uri)) {
                //改变URL的跳转，无法携带Resquest
                JSONObject data = new JSONObject();
                //获取当前用户详细信息
                ADFContext adfctx = ADFContext.getCurrent();
                String user = adfctx.getSecurityContext().getUserPrincipal().getName();
                JSONObject userData = new UserResource().findUserById(user);
                data.put("User", userData);
                //获取当前用户Education
                data.getJSONObject("User").put("Education", new UserEducationResource().findAllUserEducation(user));
                //获取当前用户Skill
                data.getJSONObject("User").put("Skill", new UserSkillResource().findAllUserSkills(user));
                //获取当前用户LocationList
                data.getJSONObject("User").put("LocationList", new UserLocationListResource().findAllLocation(user));
                //获取值集——公司性质
                data.put("Lookup_CompanyPorperty", new LookupsResource().getLookupsByType("CompanyProperty"));
                //获取值集——公司规模
                data.put("Lookup_CompNumGrade", new LookupsResource().getLookupsByType("CompNumGrade"));

                toPage(request, response, "/WEB-INF/home/UserHome.jsp", data);

            } else if ("/lance/pages/DefaultPage".equals(uri)) {
                JSONArray data = new JSONArray();
                data.put(new SearchResource().searchLatestPosted());
                toPage(request, response, "/WEB-INF/search/Search.jsp", data);

            } else if ("/lance/pages/profile/Overview".equals(uri)) {
                //                toPage(request, response, "/WEB-INF/profile/Overview.jsp",
                //                       new LancerProfileResource().findSelfProfile4CurUser());

            } else if ("/lance/pages/profile/EditBasic".equals(uri)) {
                //                toPage(request, response, "/WEB-INF/profile/EditBasic.jsp",
                //                       new LancerProfileResource().getBasicProfile4CurUser());

            } else if ("/lance/pages/jobs/PostNewJob".equals(uri)) {
                toPage(request, response, "/WEB-INF/jobs/PostNewJob.jsp", new JSONObject());

            } else if ("/lance/pages/profile/EditSkill".equals(uri)) {
                toPage(request, response, "/WEB-INF/profile/lc/EditContact.jsp",
                       new UserSkillResource().findLancerSkills4CurUser());

            } else if (uri.startsWith("/lance/pages/project/Contract/")) { //uri:http://localhost:7101/lance/pages/project/Contact/157e69a513f942c7bb895e7dddd01a56
                //读取合同
                uri = uri.replaceFirst("/lance/pages/project/Contract/", "");
                String contractId = uri.substring(0, 32); //32位uuid
                System.out.println(contractId);
                toPage(request, response, "/WEB-INF/project/Contract.jsp",
                       new ContractResource().getContractById(contractId));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toPage(HttpServletRequest request, HttpServletResponse response, String page,
                       JSONObject data) throws ServletException, IOException {
        //JSONObject object = new JSONObject();
        try {
            request.setAttribute("user", getUserData());
            request.setAttribute("data", data);
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }
        //不改变URL的跳转，且可以携带Request参数
        System.out.println("即将跳转界面 with json：");
        try {
            System.out.println(getUserData());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(data);
        System.out.println("即将跳转界面到" + page);
        request.getRequestDispatcher(page).forward(request, response);
    }

    public void toPage(HttpServletRequest request, HttpServletResponse response, String page,
                       JSONArray arr) throws ServletException, IOException {
        //JSONObject object = new JSONObject();
        try {
            request.setAttribute("user", getUserData());
            request.setAttribute("data", arr);
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }
        //不改变URL的跳转，且可以携带Request参数
        request.getRequestDispatcher(page).forward(request, response);
    }

    @SuppressWarnings("unchecked")
    public JSONObject getUserData() throws JSONException {
        JSONObject res = new JSONObject();
        ADFContext adfctx = ADFContext.getCurrent();
        String user = adfctx.getSecurityContext().getUserPrincipal().getName();
        String[] roles = adfctx.getSecurityContext().getUserRoles();
        JSONArray arr = new JSONArray();
        for (String role : roles) {
            arr.put(role);
        }
        res.put("user", user);
        //todo DisplayName
        res.put("roles", arr);
        return res;
    }

}
