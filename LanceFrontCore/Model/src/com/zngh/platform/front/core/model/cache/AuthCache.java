package com.zngh.platform.front.core.model.cache;

import com.tangosol.net.NamedCache;

import java.io.IOException;

import java.net.MalformedURLException;

import java.util.Map;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;

import org.apache.commons.lang.StringUtils;

public class AuthCache {
    public static final ADFLogger LOGGER = ADFLogger.createADFLogger(AuthCache.class);

    public AuthCache() {
        super();
    }

    
    public static final String[] CACHED_USER_ATTRIBUTES = {
        "UserName", "TrueName", "DisplayName", "Email", "Img", "Country", "CompanyId", "PhoneNumber",
        "Attach", "JobTitle", "Video", "Description", "WebsiteUrl", "ImNumberA", "ImTypeA", "ImNumberB", "ImTypeB",
        "ImNumberC", "ImTypeC", "LocationARegion", "LocationACountry", "LocationAProvince", "LocationACity",
        "LocationADetail", "LocationBRegion", "LocationBCountry", "LocationBProvince", "LocationBCity",
        "LocationBDetail", "Tagline", "HourlyRate", "ChargeRate", "Overview", "ServiceDescription", "PaymentTerms",
        "Keywords", "AddressDisplay", "ContactInfo", "CreateBy", "CreateOn", "ModifyBy", "ModifyOn", "Version",
        "LastLoginTime", "CompanyName", "CanBeSearch", "DefaultRole"
    };

    public static final String[] CACHED_ROLE_ATTRIBUTES = {  "Name", "DisplayName", "Type", "Description" };

    public static String getUserDisplayNameByUserId(String userId) {
        if(StringUtils.isBlank(userId)){
            return null;
        }
        Map user = getUserById(userId);
        if (user == null) {
            LOGGER.log(LOGGER.ERROR, "user " + userId + " not exists");

            return null;
        }
        return (String) user.get("DisplayName");
    }

    public static Map getUserById(String userId) {
        NamedCache cache = CacheUtil.getInstance(CacheUtil.KEY_AUTH_USER);
        return (Map) cache.get(userId);
    }

    public static NamedCache getAllUsers() {
        NamedCache cache = CacheUtil.getInstance(CacheUtil.KEY_AUTH_USER);
        return cache;
    }

    public static Map getRoleById(String id) {
        NamedCache cache = CacheUtil.getInstance(CacheUtil.KEY_AUTH_ROLE);
        return (Map) cache.get(id);
    }

    public static NamedCache getAllRoles() {
        NamedCache cache = CacheUtil.getInstance(CacheUtil.KEY_AUTH_ROLE);
        return cache;
    }

    public static Map getCurrentUserFromCache() throws MalformedURLException, IOException {
        String userName = ADFContext.getCurrent().getSecurityContext().getUserName();
        return getUserById(userName);
    }

    public static String getCurrentUserDisplayNameFromCache() throws MalformedURLException, IOException {
        String userName = ADFContext.getCurrent().getSecurityContext().getUserName();
        return getUserDisplayNameByUserId(userName);
    }


    //    public static Map getUserByUserId(String userId) {
    //        NamedCache nc = CacheUtil.getInstance(CacheUtil.KEY_AUTH_USER);
    //        return (Map) nc.get(userId);
    //    }

}
