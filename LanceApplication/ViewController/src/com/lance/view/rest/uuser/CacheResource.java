package com.lance.view.rest.uuser;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.user.vo.UUserVOImpl;
import com.lance.model.user.vo.UUserVORowImpl;
import com.lance.model.vo.JobCategoryVOImpl;
import com.lance.model.vo.JobCategoryVORowImpl;
import com.lance.model.vo.JobSubCategoryVOImpl;
import com.lance.model.vo.JobSubCategoryVORowImpl;
import com.lance.view.util.LUtil;

import com.tangosol.net.NamedCache;

import com.zngh.platform.front.core.model.cache.AuthCache;
import com.zngh.platform.front.core.model.cache.CacheUtil;
import com.zngh.platform.front.core.view.BaseRestResource;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewRowImpl;

import org.apache.commons.lang.StringUtils;

@Path("cache")
public class CacheResource extends BaseRestResource {

    public CacheResource() {
    }

    @GET
    @Path("all")
    public String cacheAll() {
        System.out.println("开始缓存用户信息");
        LanceRestAMImpl am = LUtil.findLanceAM();
        cacheUserInfoFn(am);
        cacheCategoryInfoFn(am);
        return "success";
    }


    @GET
    @Path("userInfo")
    public String cacheUserInfo() {
        System.out.println("cacheUserInfo");
        LanceRestAMImpl am = LUtil.findLanceAM();
        cacheUserInfoFn(am);
        return "success";
    }

    public void cacheUserInfoFn(LanceRestAMImpl am) {
        UUserVOImpl vo = am.getUUser1();
        UUserVORowImpl row = null;
        RowSetIterator it = vo.createRowSetIterator(null);
        String[] attrs = AuthCache.CACHED_USER_ATTRIBUTES;
        NamedCache cache = CacheUtil.getInstance(CacheUtil.KEY_AUTH_USER);
        while (it.hasNext()) {
            row = (UUserVORowImpl) it.next();
            //System.out.println(row.getUserName() + ":" + row.getDisplayName());
            //cache.put(row.getUserName(), convertRowToMap(row, attrs));
            cacheSingleUserFn(row, cache, attrs);
        }
        it.closeRowSetIterator();
    }

    public void cacheSingleUser(UUserVORowImpl row) {
        String[] attrs = AuthCache.CACHED_USER_ATTRIBUTES;
        NamedCache cache = CacheUtil.getInstance(CacheUtil.KEY_AUTH_USER);
        cacheSingleUserFn(row, cache, attrs);
    }

    public void cacheSingleUserFn(UUserVORowImpl row, NamedCache cache, String[] attrs) {
        cache = CacheUtil.getInstance(CacheUtil.KEY_AUTH_USER);
        cache.put(row.getUserName(), convertRowToMap(row, attrs));
        System.out.println("cached user " + row.getUserName() + " with DisplayName" + row.getDisplayName());
    }

    @GET
    @Path("categoryInfo")
    public String cacheCategoryInfo() {
        LanceRestAMImpl am = LUtil.findLanceAM();
        cacheCategoryInfoFn(am);
        return null;
    }

    /**
     * 缓存工作，JobCategory & JobSubCategory
     * 如果有中文NameCn则缓存中文，否则缓存英文
     *
     * @param am
     * @return
     */
    public String cacheCategoryInfoFn(LanceRestAMImpl am) {
        NamedCache cache = CacheUtil.getInstance(CacheUtil.KEY_JOB_CATEGORY);
        JobCategoryVOImpl vo1 = am.getJobCategory1();
        RowSetIterator it1 = vo1.createRowSetIterator(null);
        JobCategoryVORowImpl row1;
        //        System.out.println("JobCategory");
        while (it1.hasNext()) {
            row1 = (JobCategoryVORowImpl) it1.next();
            row1.getUuid();
            if (StringUtils.isNotBlank(row1.getNameCn())) {
                cache.put(row1.getUuid(), row1.getNameCn());
                //                System.out.println(row1.getUuid() + ":" + row1.getNameCn());
            } else {
                cache.put(row1.getUuid(), row1.getNameEn());
                //                System.out.println(row1.getUuid() + ":" + row1.getNameEn());
            }
        }
        it1.closeRowSetIterator();

        //        System.out.println("JobSubCategory");
        NamedCache cache2 = CacheUtil.getInstance(CacheUtil.KEY_JOB_SUBCATEGORY);
        JobSubCategoryVOImpl vo2 = am.getJobSubCategory2();
        RowSetIterator it2 = vo2.createRowSetIterator(null);
        JobSubCategoryVORowImpl row2;
        while (it2.hasNext()) {
            row2 = (JobSubCategoryVORowImpl) it2.next();
            row2.getUuid();
            if (StringUtils.isNotBlank(row2.getNameCn())) {
                cache2.put(row2.getUuid(), row2.getNameCn());
                //                System.out.println(row2.getUuid() + ":" + row2.getNameCn());
            } else {
                cache2.put(row2.getUuid(), row2.getName());
                //                System.out.println(row2.getUuid() + ":" + row2.getName());
            }
        }
        it1.closeRowSetIterator();

        return null;
    }


    @GET
    @Path("user/{userId}")
    public String getUserFromCache(@PathParam("userId") String userId) {
        System.out.println(userId);
        String name;
        try {
            name = AuthCache.getUserDisplayNameByUserId(userId);
            System.out.println(name);
            //获取当前用户显示名
            System.out.println(AuthCache.getCurrentUserDisplayNameFromCache());
            //输入所有缓存的用户信息
            Map map = AuthCache.getCurrentUserFromCache();
            for (String attr : AuthCache.CACHED_USER_ATTRIBUTES) {
                System.out.println(attr + ":" + map.get(attr));
            }
            //输出所有用户（NamedCache的用法同Map）
            NamedCache cache = AuthCache.getAllUsers();
            for (Object o : cache.keySet()) {
                System.out.println(o);
            }
            return name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("jobCategory/{categoryId}")
    public String getJobCategoryNameFromCache(@PathParam("categoryId") String categoryId) {
        NamedCache cache = CacheUtil.getInstance(CacheUtil.KEY_JOB_CATEGORY);
        String res = (String) cache.get(categoryId);
        System.out.println(res);
        return res;
    }

    @GET
    @Path("jobSubCategory/{subCategoryId}")
    public String getJobSubCategoryNameFromCache(@PathParam("subCategoryId") String subCategoryId) {
        NamedCache cache = CacheUtil.getInstance(CacheUtil.KEY_JOB_SUBCATEGORY);
        String res = (String) cache.get(subCategoryId);
        System.out.println(res);
        return res;
    }

    private Map convertRowToMap(ViewRowImpl row, String[] attrs) {
        Map map = new HashMap();
        for (String attr : attrs) {
            map.put(attr, row.getAttribute(attr));
        }
        return map;
    }

}
