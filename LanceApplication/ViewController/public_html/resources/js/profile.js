function startInfor(data){
    $("#header .lname").html(data.Uuid);
}
function initMainInfo(data){
    if(data.err){
        netWorkError();
    }else{
        //OK
        var profile = data.lancer, resume = data.lancerResume, edu = data.lancerEducations,
            skill = data.lancerSkills, i = 0, tmp;
        
        $("#header .lname").html(profile.Uuid);
        $("#content .tname").html(profile.DisplayName);
        
        $("#content .tagline").html(resume.Tagline);
        $(".ov .num").html(resume.HourlyRate);
        $(".ov .oview").html(resume.Overview);
        
        var serinfo = resume.ServiceDescriptionTxt || 'None',
            payment = resume.PaymentTermsTxt || 'None';
        $("#content .sdesc").html(serinfo);
        $("#content .payment").html(payment);
        
        for(i=0;i<skill.length;i++){
            tmp = $(".skills .zw-mod").clone().removeClass("zw-mod");
            tmp.find(".skil").html(skill[i].Name);
            $(".skills").append(tmp);
            $(".skills").parent().find(".no-add").hide();
        }
        for(i=0;i<edu.length;i++){
            tmp = $(".edus ul .mod-edu").clone().removeClass("mod-edu");
            tmp.find(".name").html(edu[i].InstitutionName);
            tmp.find(".degree").html(edu[i].DegreeType);
            tmp.find(".time").html(getDateFromTime(edu[i].StartDate) + "&nbsp;-&nbsp;" + getDateFromTime(edu[i].EndDate));
            $(".edus ul").append(tmp);
            $(".edus").parent().find(".no-add").hide();
        }
        var words = resume.Keywords.split(","), word2 = [], j = 0;
        for(i=0;i<words.length;i++){
            word2 = words[i].split("，");
            for(j=0;j<word2.length;j++){
                if(word2[j] != ""){
                    tmp = $(".keys .mod-key").clone().removeClass("mod-key");
                    tmp.html(word2[j]);
                    $(".keys").append(tmp);
                    $(".keys").parent().find(".no-add").hide();
                }
            }
        }
        
        $("#waiting").hide();
        $("#t_con").show();
    }
}

function setEditInfo(data){
    var profile = data.lancer, resume = data.lancerResume;
    $("#inp_dname").val(profile.DisplayName);
    $("#inp_dname2").val(profile.DisplayName);
    $("#inp_tagline").val(resume.Tagline);
    $("#inp_hour").val(resume.HourlyRate);
    $("#inp_charge").val(resume.ChargeRate);
    $("#inp_over").val(resume.Overview);
    $("#inp_keyword").val(resume.Keywords);
    $("#inp_service").val(resume.ServiceDescriptionTxt);
    $("#inp_payment").val(resume.PaymentTermsTxt);
    
    //init input, textarea
    $("#inp_tagline").limitWord(50);
    $("#inp_url").limitWord(50);
    $("#inp_over").limitWord(1000);
    $("#inp_service").limitWord(8000);
    $("#inp_payment").limitWord(4000);
    $("#inp_keyword").limitWord(500);
    
    //close waiting
    $("#waiting").hide();
    $("#t_con").show();
    
    //check input
    var dnameOK = true, tagOK = true;
    $("#inp_dname").checkInput("姓名", /^[\u0391-\uFFE5A-Za-z0-9\_\ ]+$/, "姓名只能输入中文和字母", function () { 
        $("#inp_dname2").val($("#inp_dname").val()); 
        dnameOK = true;
    }, function(){
        dnameOK = false;
    });
    
    $("#inp_tagline").checkInput("描述", /^[\u0391-\uFFE5A-Za-z0-9\_\ ]+$/, "描述只能输入中文和字母", function () { 
        tagOK = true;
    }, function(){
        tagOK = false;
    });
    
    $("#btn_saveBasic.clickable").click(function(){
        var obj = $(this);
        if(dnameOK && tagOK){
            var param = {
                "lancerResume" : {
                    "Keywords" : $("#inp_keyword").val(),
                    "Overview" : $("#inp_over").val(),
                    "PaymentTermsTxt" : $("#inp_payment").val(),
                    "ServiceDescriptionTxt" : $("#inp_service").val(),
                    "Tagline" : $("#inp_tagline").val(),
                    "Uuid" : resume.Uuid,
                    "LancerId" : resume.LancerId
                },
                "lancer" : {
                    "Uuid" : profile.Uuid,
                    "DisplayName" : $("#inp_dname").val(),
                    "TrueName" : $("#inp_dname2").val()
                }
            };
            if($("#inp_charge").val() != "")
                param.lancerResume.ChargeRate = $("#inp_charge").val();
            if($("#inp_hour").val() != "")
                param.lancerResume.HourlyRate = $("#inp_hour").val();
            
            //ajax update
            obj.removeClass("clickable").addClass("btn-load");
            $.ax("post", "user/lancer/profile/basicInfo/merge/" + resume.LancerId, param, function(cdata){
                window.location = "main.htm";
            }, function(){
                netWorkError();
            }, "text");
            //end
            
        }else{
            $.ae("请输入必要的信息.");
        }
    });
}

function initSkill(data, userInfo){
    var len = data.length, i = 0, tmp, alreadySkill = "";
    for(i=0;i<len;i++){
        tmp = $("#gridtbody .mod-skill").clone().removeClass("mod-skill");
        tmp.find(".xh").html(i+1);
        tmp.find(".skil").html(data[i].Name);
        tmp.find(".no-test").html(data[i].Tested ? "已测试" : "未测试");
        tmp.find(".chk-icon").addClass(data[i].Display ? "icon-checkbox" : "icon-checkbox-nor").data("display", data[i].Display);
        tmp.find(".btn-delete").data("id", data[i].Uuid);
        $("#gridtbody").append(tmp);
        alreadySkill += data[i].Name + ",";
    }
    //close waiting
    $("#waiting").hide();
    $("#t_con").show();
    
    //delete skill
    $("#gridtbody").on("click", ".btn-delete", function(){
        var obj = $(this), id = obj.data("id"), pa = obj.closest("tr");
        if($.cf("你确定要删除这个技能吗？")){
            $.ax("post", "user/lancer/skill/delete/" + userInfo.Uuid + "/" + id, null, function(data){
                pa.fadeOut(300, function(){
                    pa.remove();
                    alreadySkill = alreadySkill.replace(pa.find(".skil").html(), "");
                    $("#gridtbody .xh").each(function(index, ele){
                        $("#gridtbody .xh")[index].innerHTML = index;
                    });
                });
            }, function(){
                netWorkError();
            }, "text");
        }
    });
    
    //init show dialog
    $("#btn_addSkill").click(function(){
        $(".overlay, #dia-skill").show();
    });
    var add_post_param = [], new_add_Str = "";
    $("#inp_addnew").keydown(function(e){
        var obj = $(this);
        if(e.keyCode==13){
            if(obj.val() != "" && alreadySkill.indexOf(obj.val()) < 0){
                tmp = $(".new-add-skills .mod-skill").clone().removeClass("mod-skill");
                tmp.data("value", obj.val()).find(".skil").html(obj.val() + '<a href="#">X</a>');
                $(".new-add-skills .norli .skil").removeClass("green");
                tmp.find(".skil").addClass("green");
                $("#inp_addnew").before(tmp);
                alreadySkill += obj.val() + ",";
                new_add_Str += obj.val() + ",";
                obj.val("");
            }else{
                $.ae("“" + obj.val() + "”技能已经添加");
            }
        }
    });
    $(".new-add-skills").on("click", "a", function(){
        var pa = $(this).closest(".norli"), repstr = pa.data("value") + ",";
        pa.remove();
        alreadySkill = alreadySkill.replace(repstr, "");
        new_add_Str = new_add_Str.replace(repstr, "");
    });
    $("#btn_confirm_add").click(function(){
        var arr_names = new_add_Str.split(",");
        len = arr_names.length;
        for(i=0;i<len;i++){
            if(arr_names[i] != ""){
                add_post_param.push({"Name" : arr_names[i]});
            }
        }
        //ajax
        $.ax("post", "user/lancer/skill/muhongdi", add_post_param, function(cdata){
            $.ae("添加成功");
            location.reload();
        }, function(){
            netWorkError();
        }, "text");
    });
    
}

function initContactInfo(data, userInfor){
    var lancer = data.lancer, setting = data.setting;
    $("#inp_dname").val(lancer.DisplayName);
    $("#inp_mail").val(lancer.Email || "");
    $("#inp_phone").val(lancer.PhoneNumber || "");
    $("#inp_url").val(lancer.WebsiteUrl || "");
    $("#inp_ima").val(lancer.ImNumberA || "");
    $("#inp_imb").val(lancer.ImNumberB || "");
    $("#inp_imc").val(lancer.ImNumberC || "");
    
    var addr_index = setting.AddressDisplay, con_index = setting.ContactInfo;
    $(".address_rads input:eq(" + (addr_index - 1) + ")")[0].checked = true;
    $(".contact_rads input:eq(" + (con_index - 1) + ")")[0].checked = true;
    
    var locA = lancer.LocationA, locB = lancer.LocationB;
    
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
        
        if(locA.ProvinceId){
            $("#sel_province").val(locA.ProvinceId);
            selCityId1 = locA.CityId ? locA.CityId : null;
            getCity("sel_city", locA.ProvinceId, selCityId1);
        }
        if(locB.ProvinceId){
            $("#sel_province2").val(locB.ProvinceId);
            selCityId2 = locB.CityId ? locB.CityId : null;
            getCity("sel_city2", locB.ProvinceId, selCityId2);
        }
        if(locA.DetailLoc){
            $("#inp_detail_addr").val(locA.DetailLoc)
        }
        if(locB.DetailLoc){
            $("#inp_detail_addr2").val(locB.DetailLoc)
        }
        
        
        $("#sel_province,#sel_province2").change(function(){
            var cid = $(this).attr("data-id"), id = $(this).val(), cstr = "";
            if(id != -1){
                getCity(cid, id, null);
            }
        });
    }, function(){});
    
    //init click
    $("#btn_save.clickable").click(function(){
        var obj = $(this);
        obj.removeClass("clickable").addClass("btn-load");
        var con_param = {"lancer" :{
            "Uuid" : lancer.Uuid,
            "DisplayName" : $("#inp_dname").val()
        }, "setting":{
            "LancerId" : setting.LancerId,
            "AddressDisplay" : $(".address_rads input:checked").val(),
            "ContactInfo" : $(".contact_rads input:checked").val()
        }};
        if($("#inp_phone").val() != ""){
            con_param.lancer.PhoneNumber = $("#inp_phone").val();
        }
        if($("#inp_url").val() != ""){
            con_param.lancer.WebsiteUrl = $("#inp_url").val();
        }
        if($("#inp_ima").val() != ""){
            con_param.lancer.ImNumberA = $("#inp_ima").val();
        }
        if($("#inp_imb").val() != ""){
            con_param.lancer.ImNumberB = $("#inp_imb").val();
        }
        if($("#inp_imc").val() != ""){
            con_param.lancer.ImNumberC = $("#inp_imc").val();
        }
        con_param.lancer.LocationA = {
            "COUNTRY_ID" : $("#sel_country").val(),
            "LancerId" : lancer.Uuid
        };
        if($("#sel_province").val() != -1){
            con_param.lancer.LocationA.ProvinceId = $("#sel_province").val();
        }
        if($("#sel_city").val() != -1){
            con_param.lancer.LocationA.CityId = $("#sel_city").val();
        }
        if($("#inp_detail_addr").val() != ""){
            con_param.lancer.LocationA.DetailLoc = $("#inp_detail_addr").val();
        }
        if(lancer.LocationA.Uuid){
            con_param.lancer.LocationA.Uuid = lancer.LocationA.Uuid;
        }
        
        con_param.lancer.LocationB = {};
        if($("#sel_province2").val() != -1){
            con_param.lancer.LocationB.ProvinceId = $("#sel_province2").val();
        }
        if($("#sel_city2").val() != -1){
            con_param.lancer.LocationB.CityId = $("#sel_city2").val();
        }
        if($("#inp_detail_addr2").val() != ""){
            con_param.lancer.LocationB.DetailLoc = $("#inp_detail_addr2").val();
        }
        if(lancer.LocationB.Uuid){
            con_param.lancer.LocationB.Uuid = lancer.LocationB.Uuid;
        }
        
        $.ax("post", "user/lancer/profile/contactInfo/merge/muhongdi", con_param, function(cdata){
            $.ae("保存成功");
        }, function(){
            netWorkError();
            obj.removeClass("btn-load").addClass("clickable");
        }, "text");
        
    });
    
}

$(function () {
    $("#header .nav").click(function () {
        $(this).focus();
        $(this).find(".dialog-small").show();
    });
    $("#header .sea-input").blur(function () {
        $(this).data("isac", false);
        $(this).closest(".dialog-small").hide();
    });

    $("#header .nav").blur(function () {
        $(this).find(".dialog-small").hide();
    });

    $("#menu .title").click(function () {
        var pa = $(this).parent();
        pa.focus().find(".drop-list").show();
    });
    $("#menu .search").blur(function () {
        $(this).find(".drop-list").hide();
    });
    $("#menu .drop-list").on("click", ".dlist", function () {
        if ($(this).find(".icon-sj").length <= 0) {
            var htm = $(this).html() + '<span class="icon-sj"></span>';
            var pa = $(this).closest(".search");
            pa.find(".title").html(htm);
            pa.find(".drop-list").hide();
            pa.find(".dlist .icon-sj").remove();
            $(this).remove();
            pa.find(".drop-list").prepend('<span class="dlist">' + htm + '</span>');
        }
    });
    if ($("#gridtbody").length > 0) {
        $("#gridtbody").dragsort({ itemSelector: "tr", dragSelector: "tr", dragBetween: true, dragEnd: saveOrder1, placeHolderTemplate: "<tr></tr>" });
    }

    $(".swindow").click(function () {
        var id = $(this).attr("href").replace("#", "");
        $(".overlay,#" + id).show();
    });
    $(".dialog .dclose").click(function () {
        $(this).closest(".dialog").hide();
        $(".overlay").hide();
    });

    $(".condition").click(function () {
        $(this).focus().find(".list-sort").show();
    }).blur(function () {
        $(this).find(".list-sort").hide();
    });


    /**/
    $(".bo-search .sea-head").click(function () {
        $(".sea-cate-menu").show();
    });

    $(".sea-cate-menu").on("click", "li", function (event) {
        var oldwid = $(".bo-search .sea-head").width();
        $(".sea-cate-menu").hide();
        var str = $(this).attr("data-value");
        $(".sea-sel-txt").html(str + '<span class="sp-icons icon-arrow"></span>');

        var acwidth = $(".bo-search .sea-head").width(), wid = $(".sea-body input").width() - (acwidth - oldwid);
        $(".sea-body input").width(wid);

        var clone = $(this).clone();

        $(".sea-cate-menu li").find(".sp-icons").remove();

        clone.find("a").html(clone.attr("data-value") + '<span class="sp-icons icon-arrow"></span>');

        $(".sea-cate-menu").prepend(clone);
        $(this).remove();

        event.stopPropagation();
    });
    /**/

});

function saveOrder1() {
    //var data = $("[name='biaozhi']").map(function () { return $(this).html(); }).get();
    //$("#listhaoSortOrder").val(data.join("|"));
};  