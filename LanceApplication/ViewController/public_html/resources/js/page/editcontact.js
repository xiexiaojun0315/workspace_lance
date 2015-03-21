$(function(){

    

    Lancer.profile.getContactInfo(function(data){
        var user = data.User;
        
        
        $("#inp_uname").val(user.TrueName);
        $("#inp_email").val(user.Email);
        $("#inp_phone").val(user.PhoneNumber ? user.PhoneNumber : "");
        $("#inp_contact1").val(user.ImNumberA);
        $("#inp_contact2").val(user.ImNumberB);
        $("#inp_contact3").val(user.ImNumberC);
        //l
        var getCity = function(selId, proId, CurrentSelId){
            var cstr = "";
            $("#" + selId).html('<option class="tmp" selected="selected">请稍候...</option>');
            $.ax("get", "location/cityByProvince/" + proId, null, function(city_data){
                len = city_data.length;
                for(i=0;i<len;i++){
                    cstr += '<option value="' + city_data[i].Uuid + '">' + city_data[i].CityName + '</option>';
                }
                //$("#" + selId).find(".tmp").remove();
                //$("#" + selId).find("option").not(".mod").remove();
                $("#" + selId).html(cstr);
                if(CurrentSelId){
                    $("#" + selId).val(CurrentSelId)
                }
            }, function(){
                netWorkError();
                $("#" + selId).find(".tmp").remove();
            });
        };
        
        $.ax("get", "location/province", null, function(cdata){
             var len = cdata.length, i = 0, str = "", selCityId1 = null, selCityId2 = null;
            for(i=0;i<len;i++){
                str += '<option value="' + cdata[i].Uuid + '">' + cdata[i].ProvinceName + '</option>';
            }
            $("#sel_province,#sel_province2").append(str);//加载省
            
            $("#sel_province").val(user.LocationAProvince);
            $("#sel_province2").val(user.LocationBProvince);
            
            getCity("sel_city", user.LocationAProvince, user.LocationACity);
            getCity("sel_city2", user.LocationBProvince, user.LocationBCity);
            
            /*
            if(locA != null && locA.ProvinceId){
                $("#sel_province").val(user.LocationAProvince);
                selCityId1 = locA.CityId ? locA.CityId : null;
                getCity("sel_city", locA.ProvinceId, selCityId1);
            }
            if(locB != null && locB.ProvinceId){
                $("#sel_province2").val(locB.ProvinceId);
                selCityId2 = locB.CityId ? locB.CityId : null;
                getCity("sel_city2", locB.ProvinceId, selCityId2);
            }
            if(locA != null && locA.DetailLoc){
                $("#inp_detail_addr").val(locA.DetailLoc)
            }
            if(locB != null && locB.DetailLoc){
                $("#inp_detail_addr2").val(locB.DetailLoc)
            }
            */
            $("#sel_province,#sel_province2").change(function(){
                var cid = $(this).attr("data-id"), id = $(this).val(), cstr = "";
                if(id != -1){
                    getCity(cid, id, null);
                }
            });
            
        }, function(){});
        
    }, null);
    
    var basicRes = {
        "uname" : false,
        "phone" : false
    };
    
    var checkContact = function(){
        var uname = $("#inp_uname");
        uname.blur(function(){
            uname.popover("hide");
            uname.closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
            
            if(uname.lanCheck('chineseCharIntLine')){
                uname.closest(".form-group").addClass("has-success").addClass("has-feedback");
                basicRes.uname = true;
            }else{
                uname.attr("data-content", "请输入正确的用户名").popover("show");
                uname.closest(".form-group").addClass("has-error");
                basicRes.uname = false;
            }
        });
        
        var cellphone = $("#inp_phone");
        cellphone.blur(function(){
            cellphone.popover("hide");
            cellphone.closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
            
            if(cellphone.lanCheck('onlyCellphone')){
                cellphone.closest(".form-group").addClass("has-success").addClass("has-feedback");
                basicRes.phone = true;
            }else{
                cellphone.attr("data-content", "请输入正确的手机号").popover("show");
                cellphone.closest(".form-group").addClass("has-error");
                basicRes.phone = false;
            }
        });
        
    };
    
    checkContact();
    
    $("#btn_save").click(function(){
        var btn = $(this);
        $("#inp_uname,#inp_phone").blur();
        if(basicRes.uname && basicRes.phone){
            var con_param = {
                "UserName" : User.UserName,
                "DisplayName" : $("#inp_uname").val(),
                "AddressDisplay" : $(".address_rads:checked").val(),
                "ContactInfo" : $(".contact_rads:checked").val()
            };
            if($("#inp_phone").val() != ""){
                con_param.PhoneNumber = $("#inp_phone").val();
            }
            
            if($("#inp_contact1").val() != ""){
                con_param.ImNumberA = $("#inp_contact1").val();
            }
            if($("#inp_contact2").val() != ""){
                con_param.ImNumberB = $("#inp_contact2").val();
            }
            if($("#inp_contact3").val() != ""){
                con_param.ImNumberC = $("#inp_contact3").val();
            }
            
            con_param.LocationACountry = $("#sel_country").val();
            if($("#sel_province").val() != -1){
                con_param.LocationAProvince = $("#sel_province").val();
            }
            if($("#sel_city").val() != -1){
                con_param.LocationACity = $("#sel_city").val();
            }
            if($("#inp_loc1").val() != ""){
                con_param.LocationADetail = $("#inp_loc1").val();
            }
            
            con_param.LocationBCountry = $("#sel_country2").val();
            if($("#sel_province2").val() != -1){
                con_param.LocationBProvince = $("#sel_province2").val();
            }
            if($("#sel_city2").val() != -1){
                con_param.LocationBCity = $("#sel_city2").val();
            } 
            if($("#inp_loc2").val() != ""){
                con_param.LocationBDetail = $("#inp_loc2").val();
            };
            
            $.ax("post", "user/update/" + User.UserName, con_param, function(cdata){
                $.ae("保存成功!");
                btn.button("reset");
            }, function(){
                netWorkError();
                btn.button("reset");
            }, "text");
            
        }
    });
    
});