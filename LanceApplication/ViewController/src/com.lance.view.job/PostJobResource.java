package com.lance.view.job;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("user/client/postJob")
public class PostJobResource {
    public PostJobResource() {
    }

    @POST
    public Response postData(String content) {

        // Provide method implementation.
        // TODO

        throw new UnsupportedOperationException();
    }

    @GET
    public String getData() {

        // Provide method implementation.
        // TODO

        throw new UnsupportedOperationException();
    }
}
