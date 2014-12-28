package com.lance.view.servlet;

import com.lance.view.rest.job.SearchResource;
//import com.lance.view.rest.user.LancerProfileResource;
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
            //检查用户类型
            String userType = findInitUserType();
            System.out.println(userType);

            if ("client".equals(userType) || "company".equals(userType) || "lancer".equals(userType)) {
            } else {
                response.sendRedirect("/lance/login.htm");
            }

            if ("/lance/pages/ToMyHome".equals(uri)) {
                //改变URL的跳转，无法携带Resquest
                response.sendRedirect("/WEB-INF/home/MyHome");

            } else if ("/lance/pages/DefaultPage".equals(uri)) {
                JSONArray data=new JSONArray();
                data.put(new SearchResource().getLatestPosted());
                toPage(request, response, "/WEB-INF/search/Search.jsp", data);

            } else if ("/lance/pages/profile/Overview".equals(uri)) {
//                toPage(request, response, "/WEB-INF/profile/Overview.jsp",
//                       new LancerProfileResource().findSelfProfile4CurUser());

            } else if ("/lance/pages/profile/EditBasic".equals(uri)) {
//                toPage(request, response, "/WEB-INF/profile/EditBasic.jsp",
//                       new LancerProfileResource().getBasicProfile4CurUser());

            } else if ("/lance/pages/profile/EditContact".equals(uri)) {
//                toPage(request, response, "/WEB-INF/profile/EditContact.jsp",
//                       new LancerProfileResource().findContactInfo4CurUser());

            } else if ("/lance/pages/profile/EditSkill".equals(uri)) {
                toPage(request, response, "/WEB-INF/profile/lc/EditContact.jsp",
                       new UserSkillResource().findLancerSkills4CurUser());

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

    @SuppressWarnings("unchecked")
    public String findInitUserType() {
        ADFContext adfctx = ADFContext.getCurrent();
        String[] roles = adfctx.getSecurityContext().getUserRoles();
        for (String role : roles) {
            System.out.println(role);
            if ("client".equals(role)) {
                adfctx.getSessionScope().put("AccountType", "client");
                return "client";
            } else if ("company".equals(role)) {
                adfctx.getSessionScope().put("AccountType", "company");
                return "company";
            } else if ("lancer".equals(role)) {
                adfctx.getSessionScope().put("AccountType", "lancer");
                return "lancer";
            }
        }
        return null;
    }

}
