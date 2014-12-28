package com.lance.view.rest.uuser;

import com.lance.model.LanceRestAMImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;
import com.zngh.platform.front.core.view.RestUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


 /**
     ItemKey,Precision:500,JavaType:java.lang.String
     ItemDisplay,Precision:500,JavaType:java.lang.String
     ItemDesc,Precision:900,JavaType:java.lang.String
     ItemType,Precision:50,JavaType:java.lang.String
     CreateBy,Precision:32,JavaType:java.lang.String
     UpdateBy,Precision:32,JavaType:java.lang.String
     CreateOn,Precision:0,JavaType:java.sql.Timestamp
     UdpateOn,Precision:0,JavaType:java.sql.Timestamp
     Version,Precision:0,JavaType:java.math.BigDecimal
 */

@Path("lookups")
public class LookupsResource extends BaseRestResource {

    public LookupsResource() {
    }

    //------------------------------implements below--------------------------

    public final ADFLogger LOGGER = ADFLogger.createADFLogger(LookupsResource.class);

    public static final String[] ATTR_ALL = {"ItemKey", "ItemDisplay", "ItemDesc", "ItemType", "CreateBy", "UpdateBy", "CreateOn", "UdpateOn", "Version"};

    public static final String[] ATTR_GET = { "ItemKey", "ItemDisplay", "ItemDesc", "ItemType", "CreateBy", "UpdateBy", "CreateOn", "UdpateOn", "Version"};

    public static final String[] ATTR_UPDATE = { };

    public static final String[] ATTR_CREATE = { };

    public static final boolean CAN_DELETE = true;


    public ViewObjectImpl getLookupsFromAM(LanceRestAMImpl am) {
        return am.getLookupsV1();
    }

    public Row findLookupsByType(String type, ViewObjectImpl vo, LanceRestAMImpl am) {
        vo.setApplyViewCriteriaName("FindByTypeVC");
        vo.ensureVariableManager().setVariableValue("pType", type);
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByTypeVC");
        return null;
    }

    public String returnParamAfterCreate(Row row) {
        return (String) row.getAttribute("Uuid");
    }

    //------------------------------以下是标准代码--------------------------


    @GET
    @Path("type/{lookupsType}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getLookupsByType(@PathParam("lookupsType") String lookupsType) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getLookupsFromAM(am);
        findLookupsByType(lookupsType, vo, am);

        if (vo.getRowCount() == 0) {
            return new JSONArray();
        }

        return RestUtil.convertVoToJsonArray(vo, this.ATTR_GET);
    }


}
