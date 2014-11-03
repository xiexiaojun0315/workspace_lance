package com.lance.view.rest.spread;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.SpreadHeardFromVORowImpl;
import com.lance.view.util.LUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("spread")
public class SpreadResource {
    public SpreadResource() {
    }

    /**
     *
     * 记录用户从何处找到本网站
     * GET http://localhost:7101/lance/res/spread/heardFrom
     *
     * @example
     {
        "Text" : "从xxx处听说"
     }
     *
     * @param json
     * @return ok
     * @throws JSONException
     */
    @POST
    @Path("heardFrom")
    @Consumes(MediaType.APPLICATION_JSON)
    public String postData(JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        SpreadHeardFromVORowImpl row =
            (SpreadHeardFromVORowImpl) LUtil.createInsertRow(am.getSpreadHeardFrom1());
        row.setText(json.getString("Text"));
        am.getDBTransaction().commit();
        return "ok";
    }

}
