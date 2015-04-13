package com.zngh.platform.front.core.view.servlet;

import com.tangosol.net.CacheFactory;

import com.zngh.platform.front.core.model.cache.CacheUtil;
import com.zngh.platform.front.core.model.util.ConstantUtil;
import com.zngh.platform.front.core.view.RestUtil;

import java.io.IOException;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import oracle.adf.share.logging.ADFLogger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class FrontCoreListener implements ServletContextListener, ServletContextAttributeListener {

    public static final ADFLogger LOGGER = ADFLogger.createADFLogger(FrontCoreListener.class);

    private ServletContext context = null;
    private String name = null;
    private Object value = null;
    public static Timer timer = null;

    @SuppressWarnings("unchecked")
    public void contextInitialized(ServletContextEvent event) {
        initEnvParameters(event);
    }

    public void initEnvParameters(ServletContextEvent event) {
        LOGGER.log(LOGGER.NOTIFICATION, "init InitParameters  by params:");
        context = event.getServletContext();
        //
        initCoherence(context);

        //encure coherence
        LOGGER.log(LOGGER.NOTIFICATION, "init coherence");
        CacheFactory.ensureCluster();
        CacheUtil.CLUSTER_ENSURED = true;
        LOGGER.log(LOGGER.NOTIFICATION, "Core CoherenceListener Initialized");

    }

    @SuppressWarnings("unchecked")
    public static void initCoherence(ServletContext context) {
        Enumeration<String> em = context.getInitParameterNames();
        while (em.hasMoreElements()) {
            String n = em.nextElement();
            LOGGER.log(LOGGER.NOTIFICATION, n + ":" + context.getInitParameter(n));
            ConstantUtil.configMap.put(n, context.getInitParameter(n));
        }
    }


    public void contextDestroyed(ServletContextEvent event) {
        timer.cancel();
        context = event.getServletContext();
    }

    public void attributeAdded(ServletContextAttributeEvent event) {
        name = event.getName();
        value = event.getValue();
    }

    public void attributeRemoved(ServletContextAttributeEvent event) {
        name = event.getName();
        value = event.getValue();
    }

    public void attributeReplaced(ServletContextAttributeEvent event) {
        name = event.getName();
        value = event.getValue();
    }
}
