package com.lance.view.rest.email;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * 使用Javamail实现邮件发送功能
 *
 */

@Path("sendMail")
public class SendActivateMail {
    public SendActivateMail() {
        super();
    }

    @GET
    @Path("validateEmail")
    public void validateEmail(@QueryParam("validateCode") String validateCode, @QueryParam("sid") String sid)
    {
        //通过sid找出系统用户 验证码，激活状态，激活截止日期
        //用户验证码
        String uesrValidateCode = "12345678"; 
        //用户的激活状态    0未激活1激活
        int uesrStatus = 0; 
        //激活截止日期
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);
        Date userLastActivateTime = calendar.getTime();
        if (uesrValidateCode != null) {
            //验证用户激活状态
            if (uesrStatus == 0) {
                ///没激活
                Date currentTime = new Date(); //获取当前时间
                //验证链接是否过期
                if (currentTime.before(userLastActivateTime)) {
                    //验证激活码是否正确
                    if (validateCode.equals(uesrValidateCode)) {
                        //激活成功， //并更新用户的激活状态，为已激活
                        System.out.println("激活成功");
                        //把状态改为激活
                        uesrStatus = 1;
                    } else {
                        System.out.println("激活码不正确");
                    }
                } else {
                    System.out.println("激活码已过期！");
                }
            } else {
                System.out.println("邮箱已激活，请登录！");
            }
        } else {
            System.out.println("该邮箱未注册（邮箱地址不存在）！");
        }
    }
    
    public void sendActivateEmail(String userEmail,String validateCode,String uid){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.126.com");
        props.put("mail.smtp.auth", "true");
        try {
            PopupAuthenticator auth = new PopupAuthenticator();
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            Address addressFrom = new InternetAddress(PopupAuthenticator.mailuser + "@126.com", "才才网！");
            Address addressTo = new InternetAddress(userEmail, "");   //接收邮箱和用户
            ///邮件的内容  validateCode通过MD5加密
            StringBuffer sb = new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
            sb.append("<a href=\"http://localhost:7101/lance/res/sendMail/validateEmail?uid=");
            sb.append(uid);
            sb.append("&validateCode=");
            sb.append(validateCode);
            sb.append("\">http://localhost:7101/lance/res/sendMail/validateEmail?uid=");
            sb.append(uid);
            sb.append("&validateCode=");
            sb.append(validateCode);
            sb.append("</a>");
            message.setContent(sb.toString(), "text/html;charset=utf-8");
            // message.setText(sb.toString());
            message.setSubject("欢迎您注册才才网，请激活您的才才用户名");
            message.setFrom(addressFrom);
            message.addRecipient(Message.RecipientType.TO, addressTo);
            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.126.com", PopupAuthenticator.mailuser, PopupAuthenticator.password);
            transport.send(message);
            transport.close();
            System.out.println("发送成功");
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("发送失败");
        }
    }


    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.126.com");
        props.put("mail.smtp.auth", "true");
        try {
            PopupAuthenticator auth = new PopupAuthenticator();
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            Address addressFrom = new InternetAddress(PopupAuthenticator.mailuser + "@126.com", "才才网！");
            Address addressTo = new InternetAddress("lzm1507008@126.com", "");   //接收邮箱和用户
            ///邮件的内容  validateCode通过MD5加密
            StringBuffer sb = new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
            sb.append("<a href=\"http://localhost:7101/lance/res/sendMail/validateEmail?uid=");
            sb.append("lzm1507008");
            sb.append("&validateCode=");
            sb.append("12345678");
            sb.append("\">http://http://localhost:7101/lance/res/sendMail/validateEmail?uid=");
            sb.append("lzm1507008");
            sb.append("&validateCode=");
            sb.append("12345678");
            sb.append("</a>");
            message.setContent(sb.toString(), "text/html;charset=utf-8");
            // message.setText(sb.toString());
            message.setSubject("欢迎您注册才才网，请激活您的才才用户名");
            message.setFrom(addressFrom);
            message.addRecipient(Message.RecipientType.TO, addressTo);
            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.126.com", PopupAuthenticator.mailuser, PopupAuthenticator.password);
            transport.send(message);
            transport.close();
            System.out.println("sent suc");
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("sent fail");
        }
    }
}

class PopupAuthenticator extends Authenticator {
    public static final String mailuser = "lzm1507008";   //126邮箱账号
    public static final String password = "*********";    //126邮箱密码

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(mailuser, password);
    }
}
