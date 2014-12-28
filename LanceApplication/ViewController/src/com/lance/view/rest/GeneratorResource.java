package com.lance.view.rest;

import com.lance.model.LanceRestAMImpl;
import com.lance.view.util.LUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import oracle.jbo.AttributeDef;
import oracle.jbo.ViewObject;

@Path("generate")
public class GeneratorResource {
    public GeneratorResource() {
    }

    @GET
    public String generateAttributes() {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObject vo = am.getUUser1();
        outputAttrTypes(vo);
        return "ok";
    }

    private void outputAttrTypes(ViewObject vo) {
        AttributeDef[] attrs = vo.getAttributeDefs();
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        String type;
        for (AttributeDef attr : attrs) {
            String n = attr.getName();
            type = attr.getJavaType().getName();
            if ("interface,oracle.jbo.RowIterator,oracle.jbo.Row".indexOf(type) != -1) {
                continue;
            }
            list1.add("\"" + n + "\"");
            list2.add(n + ",Precision:" + attr.getPrecision() + ",JavaType:" + type + "");
        }
        String str = list1.toString();
        System.out.println("public static final String[] ATTR_ALL = {" + str.substring(1, str.length() - 1) + "};");

        str = list2.toString();
        System.out.println("/**");
        for (Object o : list2) {
            System.out.println("    " + o);
        }
        System.out.println("*/");
    }

    @Path("rollback")
    @POST
    public String rollback() {
        LUtil.findLanceAM().getDBTransaction().rollback();
        return "ok";
    }

    @Path("commit")
    @POST
    public String commit() {
        LUtil.findLanceAM().getDBTransaction().commit();
        return "ok";
    }

    @GET
    @Path("test")
    public void test() {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/lanceDS");
            Connection con = ds.getConnection();
            DatabaseMetaData metadata = con.getMetaData();
            System.out.println(metadata.getURL());
            con.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (NamingException ne) {
            // TODO: Add catch code
            ne.printStackTrace();
        }
    }
    
}
