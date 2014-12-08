package com.lance.view.rest;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.UUserVOImpl;
import com.lance.view.util.LUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import oracle.jbo.AttributeDef;

@Path("test")
public class TestResource {
    public TestResource() {
    }

    @GET
    public String getData() {
        LanceRestAMImpl am = LUtil.findLanceAM();
        UUserVOImpl vo = am.getUUser1();
        AttributeDef[] attrs = vo.getAttributeDefs();
        List list1=new ArrayList();
        for (AttributeDef attr : attrs) {
            String n = attr.getName();
            list1.add("\""+n+"\"");
            
            System.out.println(n +","+ attr.getJavaType() + ","+attr.getUpdateableFlag());
        }
        System.out.println(list1);
        
        for (AttributeDef attr : attrs) {
            String n = attr.getName();
            list1.add("\""+n+"\"");
            
            System.out.println(n +","+ attr.getJavaType() + ","+attr.getUpdateableFlag());
        }

        return "ok";
    }
}
