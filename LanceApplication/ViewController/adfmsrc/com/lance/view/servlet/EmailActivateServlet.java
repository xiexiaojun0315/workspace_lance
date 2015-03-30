package com.lance.view.servlet;

import com.lance.model.LanceRestAMImpl;

import com.lance.model.vo.RegEmailChkVOImpl;
import com.lance.model.vo.RegEmailChkVORowImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.RestUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Calendar;
import java.util.Date;

import java.util.GregorianCalendar;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import oracle.jbo.Key;
import oracle.jbo.Row;


public class EmailActivateServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String validateCode = request.getParameter("validateCode");
        try {
            LanceRestAMImpl am = (LanceRestAMImpl) RestUtil.findAmFromBinding("LanceRestAMDataControl");
            RegEmailChkVOImpl regEmailChkVO = am.getRegEmailChk1();
            Row[] regEmailChkRows = regEmailChkVO.findByKey(new Key(new Object[] { validateCode }), 1);
            if (regEmailChkRows != null && regEmailChkRows.length > 0) {
                //通过验证码,用户找出系统用户 激活状态，激活截止日期
                RegEmailChkVORowImpl regEmailChkRow = (RegEmailChkVORowImpl) regEmailChkRows[0];
                //用户的激活状态    0未激活1激活
                int uesrStatus = 0;
                //激活截止日期
                Date date = new Date();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                //激活2天内有效
                calendar.add(calendar.DATE, 2);
                Date userLastActivateTime = calendar.getTime();
                //验证用户激活状态
                if (uesrStatus == 0) {
                    ///没激活
                    Date userCreateTime = regEmailChkRow.getCreateOn(); //获取用户注册的时间
                    //验证链接是否过期
                    if (userCreateTime.before(userLastActivateTime)) {
                        //激活成功， //并更新用户的激活状态，为已激活
                        System.out.println("激活成功");
                        //把状态改为激活
                        uesrStatus = 1;
                        response.sendRedirect("/lance/ActivateSuccess.html");
                    } else {
                        System.out.println("激活码已过期！");
                    }
                } else {
                    System.out.println("邮箱已激活，请登录！");
                    response.sendRedirect("/lance/login.htm");
                }
            } else {
                System.out.println("该用户未注册（邮用户不存在）！");
            }
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
    }
}
