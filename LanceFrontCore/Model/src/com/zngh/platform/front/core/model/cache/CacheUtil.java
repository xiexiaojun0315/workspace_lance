package com.zngh.platform.front.core.model.cache;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import oracle.adf.share.logging.ADFLogger;

public class CacheUtil {
    public static final ADFLogger LOGGER = ADFLogger.createADFLogger(CacheUtil.class);

    @SuppressWarnings("oracle.jdeveloper.java.unrestricted-field-access")
    public static boolean CLUSTER_ENSURED = false;
    
    public static final String[] ATTR_JOB_CATEGORY={"Uuid","NameEn","NameCn"};
    public static final String[] ATTR_JOB_SUBCATEGORY={"Uuid","Name","NameCn"};

    public static final String KEY_AUTH = "lance.auth";
    public static final String KEY_AUTH_USER = "lance.auth.user";
    public static final String KEY_AUTH_ROLE = "lance.auth.role";
    public static final String KEY_JOB_CATEGORY = "lance.job.jobCategory";
    public static final String KEY_JOB_SUBCATEGORY = "lance.job.jobSubCategory";

    public CacheUtil() {
        super();
    }

    public static NamedCache getInstance(String cacheName) {
        if (!CLUSTER_ENSURED) {
            LOGGER.log(LOGGER.NOTIFICATION,"CLUSTER ENSUREING");
            CacheFactory.ensureCluster();
            CLUSTER_ENSURED = true;
            LOGGER.log(LOGGER.NOTIFICATION,"CLUSTER ENSURED");
        }
        return CacheFactory.getCache(cacheName);
    }

    public static NamedCache getCacheAuth() {
        return getInstance(KEY_AUTH);
    }




}
