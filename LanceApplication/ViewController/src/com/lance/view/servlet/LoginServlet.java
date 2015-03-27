package com.lance.view.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.security.auth.Subject;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import weblogic.security.URLCallbackHandler;
import weblogic.security.services.Authentication;

public class LoginServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * Ajax登陆接口
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost123");
        //
        //        Cookie[]  cs=request.getCookies();
        //        for(Cookie c:cs){
        //           System.out.println(c);
        //        }
        //
        String acceptjson = "";
        try {
            BufferedReader br =
                new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            acceptjson = sb.toString();
            JSONObject jo = new JSONObject(acceptjson);

            System.out.println("doLogin:" + acceptjson);
            //        JSONObject res = new JSONObject();
            String un = jo.getString("name");
            byte[] pw = jo.getString("pass").getBytes();
            Subject subject = Authentication.login(new URLCallbackHandler(un, pw));
            weblogic.servlet.security.ServletAuthentication.runAs(subject, request);
            System.out.println(un + " 登录成功");
            //            response.sendRedirect("/lance/pages/MyHome");
            //            new PageDirectServlet().toPage(request, response, "/lance/pages/MyHome", new JSONObject());
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            out.println("ok:/lance/pages/MyHome"); //成功，跳转页面
            out.close();
            return;
        } catch (FailedLoginException fle) {
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            out.println("error:name|pass"); //用户名或密码错误
            out.close();
            return;
        } catch (LoginException le) {
            le.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
