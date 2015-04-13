package com.zngh.platform.front.core.view.bean;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import com.zngh.platform.front.core.view.RestUtil;

import java.util.HashMap;
import java.util.Map;

import oracle.adf.share.logging.ADFLogger;

public class FrontCoreTest {

    public static final ADFLogger LOGGER = ADFLogger.createADFLogger(FrontCoreTest.class);
    
    public FrontCoreTest() {
        super();
    }

    public String b1_action() {
        Map m = new HashMap();
        m.put("bbb", 222);
        NamedCache cache = CacheFactory.getCache("com.zngh.platform.front.core");
        cache.put("aaa", 111);
        cache.put("map", m);
        return null;
    }

    public String b2_action() {
        NamedCache cache = CacheFactory.getCache("com.zngh.platform.front.core");
        Object o1=cache.get("aaa");
        System.out.println(o1);
        Object o=((Map)cache.get("map")).get("bbb");
        System.out.println(o);
        return null;
    }
}
