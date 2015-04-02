package com.lance.view.servlet;

import com.zngh.platform.front.core.model.util.ConstantUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class AuthListener implements ServletContextListener {

    private ServletContext context = null;
    private int failedCount = 0;
    private Timer timer = null;
    private Timer timer2 = null;
    //    private Timer timer3 = null;
    //    private String host3 = null;

    public void contextInitialized(ServletContextEvent event) {
        context=event.getServletContext();
        initUserInfoCache(event);
    }

    /**
     * 启动时调用刷新用户到缓存接口
     * 每隔10秒调用一次，直到成功
     *
     * @param event
     */
    public void initUserInfoCache(ServletContextEvent event) {
        System.out.println("Coherence开始缓存用户信息");
        timer = new Timer();
        TimerTask tt = new TimerTask() {
            private boolean inited = false;

            @Override
            public void run() {
                System.out.println("timmer1 run");
                //如果刷新成功，则改为10分钟刷新一次
                if (refreshUserInfoCache()) {
//                if (refreshUserInfoCache() && refreshInitConfigMap()) {
                    System.out.println("Coherence初始化成功，改为10分钟刷新一次");
                    timer.cancel();
                } else {
                    System.out.println("将在10秒钟内重试...");
                    failedCount++;
                    if (failedCount > 20) { //禁用
                        timer.cancel();
                        timer2.cancel();
                        System.out.println("连续失败20次，停止从缓存刷新");
                    }
                }
            }
        };
        timer.schedule(tt, 10000, 10000); //从启动开始，10秒钟刷新一次


        timer2 = new Timer();
        TimerTask tt2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Coherence正在刷新用户，每10分钟执行一次");
                //如果刷新成功，则改为5分钟刷新一次
                refreshUserInfoCache();
            }
        };
        timer2.scheduleAtFixedRate(tt2, 600000, 600000); //10分钟刷新一次用户

        //        host3 = event.getServletContext().getInitParameter("server.app.host_name");
        //        timer3 = new Timer();
        //        TimerTask tt3 = new TimerTask() {
        //            @Override
        //            public void run() {
        //                System.out.println("测试"+host3);
        //                //如果刷新成功，则改为5分钟刷新一次
        //            }
        //        };
        //        timer3.scheduleAtFixedRate(tt3, 5000, 5000); //10分钟刷新一次用户

    }

    public boolean refreshUserInfoCache() {
        try {
            String hostName = context.getInitParameter("lance.host.hostName");
            System.out.println("hostName is "+hostName);
            String refreshCacheUrl = hostName + "/lance/res/cache/all";
            System.out.println("refreshCacheUrl:" + refreshCacheUrl);
            HttpResponse responese = (HttpResponse) sendHttpGet(refreshCacheUrl);
            String res = getStringFromResponse(responese);
            System.out.println("尝试缓存......" + hostName);
            if (res.indexOf("success") != -1) {
                System.out.println("缓存成功");
                return true;
            }
        } catch (Exception e) {
            //url无法联通等问题，无需处理
            System.out.println(e.getMessage());
        }
        System.out.println("缓存失败");

        return false;
    }

//    public boolean refreshInitConfigMap(){
//        try {
//            String hostName = getLocalHostName();
//            String refreshUrl = hostName + "/auth/res/cache/outputInitParams";
//            HttpResponse responese = (HttpResponse) sendHttpGet(refreshUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    public static Object sendHttpGet(String url) throws IOException, ClientProtocolException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse responese = httpClient.execute(request);
        return responese;
    }

    public static JSONObject getJsonFromResponse(org.apache.http.HttpResponse responese) throws IOException,
                                                                                                JSONException {
        return new JSONObject(getStringFromResponse(responese));
    }

    public static String getStringFromResponse(org.apache.http.HttpResponse responese) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(responese.getEntity().getContent()));
        for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2.readLine()) {
            builder.append(s);
        }
        return builder.toString();
    }

    public void contextDestroyed(ServletContextEvent event) {
        timer.cancel();
        timer2.cancel();
        context = event.getServletContext();
    }

   



}
