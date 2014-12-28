//package com.lance.view.rest.user;
//
//import com.lance.model.LanceRestAMImpl;
//import com.lance.view.util.LUtil;
//
//import com.zngh.platform.front.core.view.BaseRestResource;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//import oracle.jbo.Row;
//import oracle.jbo.ViewObject;
//
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
//
///**
// * 个人设置
// *
// * AddressDisplay
// * Address Display Options
// * Page: Lancer Contact Information
// *  1 Display all address fields
// *  2 Display only city, state, and zip
// *  3 No contact fields
// *
// * ContactInfo
// * Contact Information Visibility (in your profile)
// * Page: Lancer Contact Information
// *  1 All contact fields
// *  2 Only non-name fields (email, phone, fax, website)
// *  3 No contact fields
// *
// */
//@Path("user/setting")
//public class SettingResource extends BaseRestResource {
//    public static final String[] ATTR_CREATE = { };
//    public static final String[] ATTR_UPDATE = { "AddressDisplay", "ContactInfo" };
//
//    public static final String[] ATTR_GET = { "Uuid", "AddressDisplay", "ContactInfo" };
//
//    public SettingResource() {
//    }
//
//    @POST
//    @Path("lancer/update/{lancerId}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String updateLancerSetting(@PathParam("lancerId") String lancerId, JSONObject json) throws JSONException {
//        LanceRestAMImpl am = LUtil.findLanceAM();
//        updateLancerSettingFn(lancerId, json, am);
//        return "ok";
//    }
//
//    public void updateLancerSettingFn(String lancerId, JSONObject json, LanceRestAMImpl am) throws JSONException {
//        LUtil.findLancerById(lancerId, am);
//        ViewObject vo = am.getLancerSetting1();
//        Row row = vo.first();
//        LUtil.transJsonToRow(json, row, ATTR_UPDATE);
//    }
//
//    @GET
//    @Path("lancer/{lancerId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject getLancerSetting(@PathParam("lancerId") String lancerId) throws JSONException {
//        LanceRestAMImpl am = LUtil.findLanceAM();
//        return getLancerSettingFn(lancerId, am);
//    }
//
//    public JSONObject getLancerSettingFn(String lancerId, LanceRestAMImpl am) throws JSONException {
//        LUtil.findLancerById(lancerId, am);
//        ViewObject vo = am.getLancerSetting1();
//        Row row = vo.first();
//        if (row == null) {
//            return new JSONObject();
//        }
//        return this.convertRowToJsonObject(row, this.findAllVOAttributes(vo));
//    }
//
//}
