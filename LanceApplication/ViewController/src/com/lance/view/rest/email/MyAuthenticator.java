package com.lance.view.rest.email;

import javax.mail.*;   
     
public class MyAuthenticator extends Authenticator{   
    String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }   
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
    
    public static void main(String[] args){   
             //这个类主要是设置邮件   
//          MailSenderInfo mailInfo = new MailSenderInfo();    
//          mailInfo.setMailServerHost("smtp.qq.com");    
//          mailInfo.setMailServerPort("25");    
//          mailInfo.setValidate(true);    
//          mailInfo.setUserName("806780823@qq.com");    
//          mailInfo.setPassword("******");//您的邮箱密码    
//          mailInfo.setFromAddress("806780823@qq.com");    
//          mailInfo.setToAddress("lzm1507008@126.com");    
//          mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网");    
//          mailInfo.setContent("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");    
//             //这个类主要来发送邮件   
//          SimpleMailSender sms = new SimpleMailSender();   
//              sms.sendTextMail(mailInfo);//发送文体格式    
//              sms.sendHtmlMail(mailInfo);//发送html格式   
              
              
              MailSenderInfo mailInfo = new MailSenderInfo();    
              mailInfo.setMailServerHost("smtp.126.com");    
              mailInfo.setMailServerPort("25");    
              mailInfo.setValidate(true);    
              mailInfo.setUserName("lzm1507008@126.com");    
              mailInfo.setPassword("*****");//您的邮箱密码    
              mailInfo.setFromAddress("lzm1507008@126.com");    
              mailInfo.setToAddress("muhongdi@qq.com");    
              mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网");    
              mailInfo.setContent("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");    
                 //这个类主要来发送邮件   
              SimpleMailSender sms = new SimpleMailSender();   
                  sms.sendTextMail(mailInfo);//发送文体格式    
                  sms.sendHtmlMail(mailInfo);//发送html格式   
        }  
}   