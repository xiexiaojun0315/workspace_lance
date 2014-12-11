package com.lance.view.rest;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.UUserVOImpl;
import com.lance.view.util.LUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import oracle.jbo.AttributeDef;

@Path("generate")
public class GeneratorResource {
    public GeneratorResource() {
    }

    @GET
    public String generateAttributes() {
        LanceRestAMImpl am = LUtil.findLanceAM();
        UUserVOImpl vo = am.getUUser1();
        AttributeDef[] attrs = vo.getAttributeDefs();
        List list1 = new ArrayList();
        for (AttributeDef attr : attrs) {
            if (attr.getJavaType().equals("oracle.jbo.domain.ClobDomain")) {
                //去掉clob类型字段，改用Txt
                continue;
            }
            String n = attr.getName();
            list1.add("\"" + n + "\"");
        }
        String str = list1.toString();
        System.out.println("public static final String[] ATTR_ALL = {" + str.substring(1, str.length() - 1) + "};");

        //        for (AttributeDef attr : attrs) {
        //            String n = attr.getName();
        //            list1.add("\""+n+"\"");
        //
        //            System.out.println(n +","+ attr.getJavaType() + ","+attr.getUpdateableFlag());
        //        }

        return "ok";
    }
}
