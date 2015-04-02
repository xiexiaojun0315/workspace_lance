package com.lance.view.rest.uuser;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.user.vo.UUserVOImpl;
import com.lance.model.user.vo.UUserVORowImpl;
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

@Path("cache")
public class CacheResource extends BaseRestResource {

    public CacheResource() {
    }

    @GET
    @Path("all")
    public String cacheAll() {
        System.out.println("开始缓存用户信息");
        cacheUserInfo();
        return "success";
    }

    //    @GET
    //    @Path("sysInfo")
    //    public JSONObject sysInfo() throws JSONException {
    //        JSONObject json = new JSONObject();
    //
    //        JSONObject json1 = new JSONObject();
    //        Map map = ConstantUtil.configMap;
    //        List list = new ArrayList();
    //        Set keys = map.keySet();
    //        for (Object key : keys) {
    //            json1.put((String) key, map.get(key));
    //        }
    //
    //        JSONObject json2 = new JSONObject();
    //        map = ConstantUtil.getConfigMap();
    //        list = new ArrayList();
    //        keys = map.keySet();
    //        for (Object key : keys) {
    //            json2.put((String) key, map.get(key));
    //        }
    //
    //        json.put("InitContextMap", json1);
    //        json.put("ConfigMap", json2);
    //
    //        return json;
    //    }


    @GET
    @Path("userInfo")
    public String cacheUserInfo() {
        System.out.println("cacheUserInfo");

        LanceRestAMImpl am = LUtil.findLanceAM();
        UUserVOImpl vo = am.getUUser1();
        UUserVORowImpl row = null;
        RowSetIterator it = vo.createRowSetIterator(null);
        String[] attrs = AuthCache.CACHED_USER_ATTRIBUTES;

        NamedCache cache = CacheUtil.getInstance(CacheUtil.KEY_AUTH_USER);
        while (it.hasNext()) {
            row = (UUserVORowImpl) it.next();
            System.out.println(row.getUserName() + ":" + row.getDisplayName());
            cache.put(row.getUserName(), convertRowToMap(row, attrs));
        }
        it.closeRowSetIterator();

        return "success";
    }

    //    @GET
    //    @Path("userRole")
    //    public String cacheUserRole() {
    //        System.out.println("cacheUserRole");
    //        Map map = new HashMap();
    //        AuthAMImpl am = (AuthAMImpl) this.findAmFromBinding("AuthAMDataControl");
    //        RolesVOImpl vo = am.getRoles2();
    //        RolesVORowImpl row = null;
    //        RowSetIterator it = vo.createRowSetIterator(null);
    //        String[] attrs = AuthCache.CACHED_ROLE_ATTRIBUTES;
    //        while (it.hasNext()) {
    //            row = (RolesVORowImpl) it.next();
    //            System.out.println(row.getName());
    //            map.put(row.getName(), convertRowToMap(row, attrs));
    //        }
    //        it.closeRowSetIterator();
    //        NamedCache cache = CacheUtil.getInstance(CacheUtil.KEY_AUTH_ROLE);
    //        cache.putAll(map);
    //
    //        return "success";
    //    }

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
            NamedCache cache=AuthCache.getAllUsers();
            for(Object o:cache.keySet()){
                System.out.println(o);
            }
            return name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //    @GET
    //    @Path("outputInitParams")
    //    public String refreshInitParams() {
    //        FrontCoreListener.loadInitEnvParam();
    //
    //        //用于项目启动时获取web.xml中配置的常量信息
    //        Map map = ConstantUtil.getConfigMap();
    //        System.out.println("----ConfigMap----");
    //        Set keys = map.keySet();
    //        for (Object key : keys) {
    //            System.out.println(key + ":" + map.get(key));
    //        }
    //
    //        //用于项目启动时获取当前环境对应信息
    //        map = ConstantUtil.getInitContextMap();
    //        System.out.println("----InitConfigMap----");
    //        Set keys2 = map.keySet();
    //        for (Object key : keys2) {
    //            System.out.println(key + ":" + map.get(key));
    //        }
    //
    //        return "ok";
    //    }

    private Map convertRowToMap(ViewRowImpl row, String[] attrs) {
        Map map = new HashMap();
        for (String attr : attrs) {
            map.put(attr, row.getAttribute(attr));
        }
        return map;
    }

}
