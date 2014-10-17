package com.lance.view.template;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * JOB_TEMPLATE
 * 查询全部，根据类别查询
 * JOB_CATEGORY
 * JOB_SUB_CATEGORY
 * 
 */
@Path("template/job")
public class JobTemplateResource {
    public JobTemplateResource() {
    }

    @POST
    public Response postData(String content) {

        // Provide method implementation.
        // TODO

        throw new UnsupportedOperationException();
    }

    @GET
    public String findJobTemplate() {

        // Provide method implementation.
        // TODO

        throw new UnsupportedOperationException();
    }

    @GET
    @Path("specificSkill/{skill}")
    public String filterSkills(@PathParam("skill") String skill) {
        return null;
    }
}
