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
        String uri = request.getRequestURI();
        ADFContext adfctx = ADFContext.getCurrent();
        try {
            //如果用户访问的是登录后(受保护)界面
            if (uri.startsWith("/lance/pages/")) {
                if (!adfctx.getSecurityContext().isAuthenticated()) {
                    response.sendRedirect("/lance/login.htm");
                }
            }
            String user = adfctx.getSecurityContext().getUserPrincipal().getName();
            if ("/lance/pages/MyHome".equals(uri)) {
                JSONObject data = new JSONObject();
                //获取值集——公司性质
                data.put("Lookup_CompanyPorperty", new LookupsResource().getLookupsByType("CompanyProperty"));
                //获取值集——公司规模
                data.put("Lookup_CompNumGrade", new LookupsResource().getLookupsByType("CompNumGrade"));

                toPage(request, response, "/WEB-INF/search/Search.jsp", data);

            } else if ("/lance/pages/DefaultPage".equals(uri) || "/lance/pages/Search".equals(uri)) {
                JSONArray data = new JSONArray();
                data.put(new SearchResource().searchLatestPosted());
                toPage(request, response, "/WEB-INF/search/Search.jsp", data);

            } else if ("/lance/pages/profile/Overview".equals(uri)) {
                JSONObject data = new JSONObject();
                data.put("User", new UserResource().findUserById(user));
                toPage(request, response, "/WEB-INF/profile/Overview.jsp", data);

            } else if ("/lance/pages/profile/EditBasic".equals(uri)) {
                JSONObject data = new JSONObject();
                data.put("User", new UserResource().findUserById(user));
                toPage(request, response, "/WEB-INF/profile/EditBasic.jsp", data);

            } else if ("/lance/pages/profile/EditSkill".equals(uri)) {
                JSONObject data = new JSONObject();
                data.put("User", new UserResource().findUserById(user));
                toPage(request, response, "/WEB-INF/profile/EditSkill.jsp", data);

            } else if ("/lance/pages/profile/EditContact".equals(uri)) {
                JSONObject data = new JSONObject();
                data.put("User", new UserResource().findUserById(user));
                toPage(request, response, "/WEB-INF/profile/EditContact.jsp", data);

            } else if ("/lance/pages/jobs/PostNewJob".equals(uri)) {
                toPage(request, response, "/WEB-INF/jobs/PostNewJob.jsp", new JSONObject());

            } else if ("/lance/pages/UserRegSuccess1".equals(uri)){
                toPage(request, response, "/WEB-INF/profile/UserRegSuccess1.jsp", new JSONObject());
                
            } else if ("/lance/pages/UserRegSuccess2".equals(uri)){
                toPage(request, response, "/WEB-INF/profile/UserRegSuccess2.jsp", new JSONObject());
                
            }
//            else if (uri.startsWith("/lance/pages/project/Contract/")) { //uri:http://localhost:7101/lance/pages/project/Contact/157e69a513f942c7bb895e7dddd01a56
//                //读取合同
//                uri = uri.replaceFirst("/lance/pages/project/Contract/", "");
//                String contractId = uri.substring(0, 32); //32位uuid
//                System.out.println(contractId);
//                toPage(request, response, "/WEB-INF/project/Contract.jsp",
//                       new ContractResource().getContractById(contractId));
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toPage(HttpServletRequest request, HttpServletResponse response, String page,
                       JSONObject data) throws ServletException, IOException {
        //JSONObject object = new JSONObject();
        try {
            ADFContext adfctx = ADFContext.getCurrent();
            String user = adfctx.getSecurityContext().getUserPrincipal().getName();
            JSONObject userData = new UserResource().findSimpleUserByName(user);
            String[] roles = adfctx.getSecurityContext().getUserRoles();
            JSONArray roleArr = new JSONArray();
            for (String role : roles) {
                roleArr.put(role);
            }
            userData.put("roles", roleArr);

            request.setAttribute("user", userData);
            request.setAttribute("data", data);
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }
        //不改变URL的跳转，且可以携带Request参数
        System.out.println("即将跳转界面 with json：");
        System.out.println("即将跳转界面到" + page);
        request.getRequestDispatcher(page).forward(request, response);
    }

    public void toPage(HttpServletRequest request, HttpServletResponse response, String page,
                       JSONArray arr) throws ServletException, IOException {
        try {
            ADFContext adfctx = ADFContext.getCurrent();
            String user = adfctx.getSecurityContext().getUserPrincipal().getName();
            JSONObject userData = new UserResource().findSimpleUserByName(user);
            String[] roles = adfctx.getSecurityContext().getUserRoles();
            JSONArray roleArr = new JSONArray();
            for (String role : roles) {
                roleArr.put(role);
            }
            userData.put("roles", roleArr);

            request.setAttribute("user", userData);
            request.setAttribute("data", arr);
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }
        //不改变URL的跳转，且可以携带Request参数
        System.out.println("即将跳转界面 with json：");
        System.out.println("即将跳转界面到" + page);
        request.getRequestDispatcher(page).forward(request, response);
    }


}
