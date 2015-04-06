$(function(){
    //$('#inp_name').popover('show');
    //company regist
    
    var ckresult = {
        emr : false,
        lgr : false,
        umr : false,
        psr : false,
        cmr : false
    };
    
    var check_email = function(){
        var email = $("#inp_mail");
        
        var mck = function(){
            if(ckresult.emr == false)
                email.blur();
        };
        
        com_conFocus(email, "邮箱用于登录，填写之后不可修改");
        
        email.blur(function(){
            email.closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
            email.popover("hide");
            
            if(email.lanCheck('onlyEmail')){
                emailAjax(email);
            }else{
                email.attr("data-content", "请输入正确的邮箱格式。").popover("show");
                email.closest(".form-group").addClass("has-error");
            }
        });
        
        var emailAjax = function(obj){
            obj.attr("disabled", true);
            
            $.ax("get", "user/exist/email/" + obj.val(), null, function(data){
                if(data == true){
                    obj.attr("data-content", "该邮箱已被注册，请选择其他邮箱。").popover("show");
                    obj.closest(".form-group").addClass("has-error");
                }else{
                    obj.closest(".form-group").addClass("has-success").addClass("has-feedback");
                    ckresult.emr = true;
                    //fetch username
                    var tmpName = obj.val().split('@')[0];
                    $("#inp_lgname").val(tmpName);
                }
                obj.attr("disabled", false);
            }, function(){
                obj.attr("data-content", "服务器无响应，请稍后再试。").popover("show");
                obj.closest(".form-group").addClass("has-error");
                obj.attr("disabled", false);
            });
        };
        
        return {ck:mck};
    };
    
    
    var check_lgname = function(){
        var lgname = $("#inp_lgname");
        
        var mck = function(){
            if(ckresult.lgr == false)
                lgname.blur();
        };
        
        com_conFocus(lgname, "登录用户名");
        
        lgname.blur(function(){
            lgname.popover("hide");
            lgname.closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
            
            if(lgname.lanCheck('charIntLine')){
                nameAjax(lgname);
            }else{
                lgname.attr("data-content", "登录名称只能由数字、字母组成。").popover("show");
                lgname.closest(".form-group").addClass("has-error");
            }
        });
        
        var nameAjax = function(obj){
            obj.attr("disabled", true);
            
            $.ax("get", "user/exist/userName/" + obj.val(), null, function(data){
                if(data == true){
                    obj.attr("data-content", "该登录名已被注册，请选择其他用户名。").popover("show");
                    obj.closest(".form-group").addClass("has-error");
                }else{
                    obj.closest(".form-group").addClass("has-success").addClass("has-feedback");
                    ckresult.lgr = true;
                }
                obj.attr("disabled", false);
            }, function(){
                obj.attr("data-content", "服务器无响应，请稍后再试。").popover("show");
                obj.closest(".form-group").addClass("has-error");
                obj.attr("disabled", false);
            });
        };
        
        return {ck:mck};
    };
    
    
    var check_uname = function(){
        var uname = $("#inp_name");
        
        var mck = function(){
            if(ckresult.umr == false)
                uname.blur();
        };
        
        com_conFocus(uname, "用户昵称，建议使用真实姓名");
        
        uname.blur(function(){
            uname.closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
            uname.popover("hide");
            
            if(uname.lanCheck("chineseCharIntLine")){
                uname.closest(".form-group").addClass("has-success").addClass("has-feedback");
                ckresult.umr = true;
            }else{
                uname.closest(".form-group").addClass("has-error");
                uname.attr("data-content", "请输入正确的用户名").popover("show");
            }
        });    
        
        return {ck:mck};
    };
    
    var check_pass = function(){
        var fpass = $("#inp_pass"),
            spass = $("#inp_cpass");
        
        com_conFocus(fpass, "登录密码");
        
        var mck = function(){
            if(ckresult.psr == false){
                fpass.blur();
                spass.blur();
            }
        };
        
        fpass.blur(function(){
            fpass.closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
            fpass.popover("hide");
            
            if(fpass.lanCheck("password")){
                fpass.closest(".form-group").addClass("has-success").addClass("has-feedback");
            }else{
                fpass.closest(".form-group").addClass("has-error");
                fpass.attr("data-content", "请输入8位以上的密码").popover("show");
            }
        });
        
        com_conFocus(spass, "请再次输入密码");
        
        spass.blur(function(){
            spass.closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
            spass.popover("hide");
        
            if(spass.lanCheck("password") && spass.val() == fpass.val()){
                spass.closest(".form-group").addClass("has-success").addClass("has-feedback");
                ckresult.psr = true;
            }else{
                spass.closest(".form-group").addClass("has-error");
                spass.attr("data-content", "两次密码输入不一致").popover("show");
            }
        });
        return {ck:mck};
    };
    
    var check_company = function(){
        var company = $("#inp_comname");
        
        var mck = function(){
            if(ckresult.cmr == false)
                company.blur();
        };
        
        com_conFocus(company, "公司名称");
        
        company.blur(function(){
            company.closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
            company.popover("hide");
            
            if(company.lanCheck("chineseCharIntLine")){
                company.closest(".form-group").addClass("has-success").addClass("has-feedback");
                ckresult.cmr = true;
            }else{
                company.closest(".form-group").addClass("has-error");
                company.attr("data-content", "请输入合法的公司名称").popover("show");
            }
        });    
        
        return {ck:mck};
    };
    
    
    var check_form = function(btn){
        if(ckresult.emr && ckresult.lgr && ckresult.umr && ckresult.psr){
            btn.button('loading');
            
            var param = {
                "TrueName" : $("#inp_name").val(),
                "DisplayName" : $("#inp_name").val(),
                "Email" : $("#inp_mail").val(),
                "UserName" : $("#inp_lgname").val(),
                "Password" : $("#inp_pass").val(),
                "DefaultRole" : "client"
            };
            $.ax("post", "user", param, function(data){
                alert("OK");
                location.href="/lance/registSuccess.html"
            }, function(){
                btn.button('reset');
            }, "text");
            
        }else{
            c1.ck();
            c2.ck();
            c3.ck();
            c4.ck();
        }
    };
    
    $("#btn_cregist").click(function(){
        if($("#fwtk")[0].checked){
            $("#lbl_fwtk").popover("hide");
        var obj = $(this);
        check_form(obj);
            
        }else{
            
            $("#lbl_fwtk").popover("show");
        }
    });
    
    var c1 = check_pass();
    var c2 = check_uname();
    var c3 = check_email();
    var c4 = check_lgname();
    
    
    var c5 = check_company();
    
    var check_form2 = function(btn){
        if(ckresult.emr && ckresult.lgr && ckresult.umr && ckresult.psr){
            
            if(($("#rgs")[0].checked && ckresult.cmr) || $("#rgr")[0].checked ){
            
                var compName = "";
                if($("#rgs")[0].checked){
                    compName = $("#inp_comname").val();
                }
                if(!$("#fwtk")[0].checked){
                    
                    $("#lbl_fwtk").popover("show");
                    return;
                }else{
                    $("#lbl_fwtk").popover("hide");
                }
            
                btn.button('loading');
                
                var param = { 
                    "UserName" : $("#inp_lgname").val(),
                    "Email" :  $("#inp_mail").val(),
                    "DisplayName" : $("#inp_name").val(),
                    "Country" : $("#sel_contury").val(),
                    "TrueName" :  $("#inp_name").val(),
                    "CompanyName" : compName,
                    "Password" : $("#inp_pass").val(),
                    "DefaultRole" : 'lancer'
                };
                $.ax("post", "user", param, function(data){
                    alert("OK");
                    location.href="/lance/registSuccess.html";
                }, function(xhr, err, info){
                    btn.button("reset");
                }, "text");
            }
            
        }else{
            c1.ck();
            c2.ck();
            c3.ck();
            c4.ck();
            if($("#rgs")[0].checked)
                c5.ck();
        }
    };
    
    $("#btn_free_regist").click(function(){
        var obj = $(this);
        check_form2(obj);
    });
    
});

$(function(){
    $("#rgr").click(function(){
        if(this.checked){
            $("#inp_comname").attr("disabled", true).val($("#inp_name").val());
            
            $("#lbl_name").html("显示名");
            $("#inp_comname").popover("hide").closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
        }
    });
    
    $("#rgs").click(function(){
        if(this.checked){
            $("#inp_comname").attr("disabled", false).val("");
            
            $("#lbl_name").html("公司名");
        }
    });
    
});

$(function () {

var data=[{"Uuid":"0","Name":"China","NameLoc":"中国"},{"Uuid":"227","Name":"United States"},{"Uuid":"226","Name":"United Kingdom"},{"Uuid":"1","Name":"Afghanistan"},{"Uuid":"2","Name":"Albania"},{"Uuid":"3","Name":"Algeria"},{"Uuid":"4","Name":"American Samoa"},{"Uuid":"5","Name":"Andorra"},{"Uuid":"6","Name":"Angola"},{"Uuid":"7","Name":"Anguilla"},{"Uuid":"8","Name":"Antarctica"},{"Uuid":"9","Name":"Antigua and Barbuda"},{"Uuid":"10","Name":"Argentina"},{"Uuid":"11","Name":"Armenia"},{"Uuid":"12","Name":"Aruba"},{"Uuid":"13","Name":"Australia"},{"Uuid":"14","Name":"Austria"},{"Uuid":"15","Name":"Azerbaijan"},{"Uuid":"16","Name":"Bahamas"},{"Uuid":"17","Name":"Bahrain"},{"Uuid":"18","Name":"Bangladesh"},{"Uuid":"19","Name":"Barbados"},{"Uuid":"20","Name":"Belarus"},{"Uuid":"21","Name":"Belgium"},{"Uuid":"22","Name":"Belize"},{"Uuid":"23","Name":"Benin"},{"Uuid":"24","Name":"Bermuda"},{"Uuid":"25","Name":"Bhutan"},{"Uuid":"26","Name":"Bolivia"},{"Uuid":"27","Name":"Bosnia and Herzegovina"},{"Uuid":"28","Name":"Botswana"},{"Uuid":"29","Name":"Bouvet Island"},{"Uuid":"30","Name":"Brazil"},{"Uuid":"31","Name":"British Indian Ocean Territory"},{"Uuid":"32","Name":"Brunei Darussalam"},{"Uuid":"33","Name":"Bulgaria"},{"Uuid":"34","Name":"Burkina Faso"},{"Uuid":"35","Name":"Burundi"},{"Uuid":"36","Name":"Cambodia"},{"Uuid":"37","Name":"Cameroon"},{"Uuid":"38","Name":"Canada"},{"Uuid":"39","Name":"Cape Verde"},{"Uuid":"40","Name":"Cayman Islands"},{"Uuid":"41","Name":"Central African Republic"},{"Uuid":"42","Name":"Chad"},{"Uuid":"43","Name":"Chile"},{"Uuid":"45","Name":"Christmas Island"},{"Uuid":"46","Name":"Cocos (Keeling) Islands"},{"Uuid":"47","Name":"Colombia"},{"Uuid":"48","Name":"Comoros"},{"Uuid":"49","Name":"Congo"},{"Uuid":"50","Name":"Congo, The Democratic Republic Of The"},{"Uuid":"51","Name":"Cook Islands"},{"Uuid":"52","Name":"Costa Rica"},{"Uuid":"53","Name":"Cote D'Ivoire"},{"Uuid":"54","Name":"Croatia (local name: Hrvatska)"},{"Uuid":"55","Name":"Cuba"},{"Uuid":"56","Name":"Cyprus"},{"Uuid":"57","Name":"Czech Republic"},{"Uuid":"58","Name":"Denmark"},{"Uuid":"59","Name":"Djibouti"},{"Uuid":"60","Name":"Dominica"},{"Uuid":"61","Name":"Dominican Republic"},{"Uuid":"62","Name":"East Timor"},{"Uuid":"63","Name":"Ecuador"},{"Uuid":"64","Name":"Egypt"},{"Uuid":"65","Name":"El Salvador"},{"Uuid":"66","Name":"Equatorial Guinea"},{"Uuid":"67","Name":"Eritrea"},{"Uuid":"68","Name":"Estonia"},{"Uuid":"69","Name":"Ethiopia"},{"Uuid":"70","Name":"Falkland Islands (Malvinas)"},{"Uuid":"71","Name":"Faroe Islands"},{"Uuid":"72","Name":"Fiji"},{"Uuid":"73","Name":"Finland"},{"Uuid":"74","Name":"France"},{"Uuid":"75","Name":"France Metropolitan"},{"Uuid":"76","Name":"French Guiana"},{"Uuid":"77","Name":"French Polynesia"},{"Uuid":"78","Name":"French Southern Territories"},{"Uuid":"79","Name":"Gabon"},{"Uuid":"80","Name":"Gambia"},{"Uuid":"81","Name":"Georgia"},{"Uuid":"82","Name":"Germany"},{"Uuid":"83","Name":"Ghana"},{"Uuid":"84","Name":"Gibraltar"},{"Uuid":"85","Name":"Greece"},{"Uuid":"86","Name":"Greenland"},{"Uuid":"87","Name":"Grenada"},{"Uuid":"88","Name":"Guadeloupe"},{"Uuid":"89","Name":"Guam"},{"Uuid":"90","Name":"Guatemala"},{"Uuid":"91","Name":"Guinea"},{"Uuid":"92","Name":"Guinea-Bissau"},{"Uuid":"93","Name":"Guyana"},{"Uuid":"94","Name":"Haiti"},{"Uuid":"95","Name":"Heard and Mc Donald Islands"},{"Uuid":"96","Name":"Honduras"},{"Uuid":"97","Name":"Hong Kong"},{"Uuid":"98","Name":"Hungary"},{"Uuid":"99","Name":"Iceland"},{"Uuid":"100","Name":"India"},{"Uuid":"101","Name":"Indonesia"},{"Uuid":"102","Name":"Iran (Islamic Republic of)"},{"Uuid":"103","Name":"Iraq"},{"Uuid":"104","Name":"Ireland"},{"Uuid":"105","Name":"Isle of Man"},{"Uuid":"106","Name":"Israel"},{"Uuid":"107","Name":"Italy"},{"Uuid":"108","Name":"Jamaica"},{"Uuid":"109","Name":"Japan"},{"Uuid":"110","Name":"Jordan"},{"Uuid":"111","Name":"Kazakhstan"},{"Uuid":"112","Name":"Kenya"},{"Uuid":"113","Name":"Kiribati"},{"Uuid":"114","Name":"Kuwait"},{"Uuid":"115","Name":"Kyrgyzstan"},{"Uuid":"116","Name":"Lao People's Democratic Republic"},{"Uuid":"117","Name":"Latvia"},{"Uuid":"118","Name":"Lebanon"},{"Uuid":"119","Name":"Lesotho"},{"Uuid":"120","Name":"Liberia"},{"Uuid":"121","Name":"Libyan Arab Jamahiriya"},{"Uuid":"122","Name":"Liechtenstein"},{"Uuid":"123","Name":"Lithuania"},{"Uuid":"124","Name":"Luxembourg"},{"Uuid":"125","Name":"Macau"},{"Uuid":"126","Name":"Madagascar"},{"Uuid":"127","Name":"Malawi"},{"Uuid":"128","Name":"Malaysia"},{"Uuid":"129","Name":"Maldives"},{"Uuid":"130","Name":"Mali"},{"Uuid":"131","Name":"Malta"},{"Uuid":"132","Name":"Marshall Islands"},{"Uuid":"133","Name":"Martinique"},{"Uuid":"134","Name":"Mauritania"},{"Uuid":"135","Name":"Mauritius"},{"Uuid":"136","Name":"Mayotte"},{"Uuid":"137","Name":"Mexico"},{"Uuid":"138","Name":"Micronesia"},{"Uuid":"139","Name":"Moldova"},{"Uuid":"140","Name":"Monaco"},{"Uuid":"141","Name":"Mongolia"},{"Uuid":"142","Name":"Montenegro"},{"Uuid":"143","Name":"Montserrat"},{"Uuid":"144","Name":"Morocco"},{"Uuid":"145","Name":"Mozambique"},{"Uuid":"146","Name":"Myanmar"},{"Uuid":"147","Name":"Namibia"},{"Uuid":"148","Name":"Nauru"},{"Uuid":"149","Name":"Nepal"},{"Uuid":"150","Name":"Netherlands"},{"Uuid":"151","Name":"Netherlands Antilles"},{"Uuid":"152","Name":"New Caledonia"},{"Uuid":"153","Name":"New Zealand"},{"Uuid":"154","Name":"Nicaragua"},{"Uuid":"155","Name":"Niger"},{"Uuid":"156","Name":"Nigeria"},{"Uuid":"157","Name":"Niue"},{"Uuid":"158","Name":"Norfolk Island"},{"Uuid":"159","Name":"North Korea"},{"Uuid":"160","Name":"Northern Mariana Islands"},{"Uuid":"161","Name":"Norway"},{"Uuid":"162","Name":"Oman"},{"Uuid":"163","Name":"Pakistan"},{"Uuid":"164","Name":"Palau"},{"Uuid":"165","Name":"Palestine"},{"Uuid":"166","Name":"Panama"},{"Uuid":"167","Name":"Papua New Guinea"},{"Uuid":"168","Name":"Paraguay"},{"Uuid":"169","Name":"Peru"},{"Uuid":"170","Name":"Philippines"},{"Uuid":"171","Name":"Pitcairn"},{"Uuid":"172","Name":"Poland"},{"Uuid":"173","Name":"Portugal"},{"Uuid":"174","Name":"Puerto Rico"},{"Uuid":"175","Name":"Qatar"},{"Uuid":"176","Name":"Reunion"},{"Uuid":"177","Name":"Romania"},{"Uuid":"178","Name":"Russian Federation"},{"Uuid":"179","Name":"Rwanda"},{"Uuid":"180","Name":"Saint Kitts and Nevis"},{"Uuid":"181","Name":"Saint Lucia"},{"Uuid":"182","Name":"Saint Vincent and the Grenadines"},{"Uuid":"183","Name":"Samoa"},{"Uuid":"184","Name":"San Marino"},{"Uuid":"185","Name":"Sao Tome and Principe"},{"Uuid":"186","Name":"Saudi Arabia"},{"Uuid":"187","Name":"Senegal"},{"Uuid":"188","Name":"Serbia"},{"Uuid":"189","Name":"Seychelles"},{"Uuid":"190","Name":"Sierra Leone"},{"Uuid":"191","Name":"Singapore"},{"Uuid":"192","Name":"Slovakia (Slovak Republic)"},{"Uuid":"193","Name":"Slovenia"},{"Uuid":"194","Name":"Solomon Islands"},{"Uuid":"195","Name":"Somalia"},{"Uuid":"196","Name":"South Africa"},{"Uuid":"197","Name":"South Korea"},{"Uuid":"198","Name":"Spain"},{"Uuid":"199","Name":"Sri Lanka"},{"Uuid":"200","Name":"St. Helena"},{"Uuid":"201","Name":"St. Pierre and Miquelon"},{"Uuid":"202","Name":"Sudan"},{"Uuid":"203","Name":"Suriname"},{"Uuid":"204","Name":"Svalbard and Jan Mayen Islands"},{"Uuid":"205","Name":"Swaziland"},{"Uuid":"206","Name":"Sweden"},{"Uuid":"207","Name":"Switzerland"},{"Uuid":"208","Name":"Syrian Arab Republic"},{"Uuid":"209","Name":"Taiwan","NameLoc":"中国台湾"},{"Uuid":"210","Name":"Tajikistan"},{"Uuid":"211","Name":"Tanzania"},{"Uuid":"212","Name":"Thailand"},{"Uuid":"213","Name":"The former Yugoslav Republic of Macedonia"},{"Uuid":"214","Name":"Togo"},{"Uuid":"215","Name":"Tokelau"},{"Uuid":"216","Name":"Tonga"},{"Uuid":"217","Name":"Trinidad and Tobago"},{"Uuid":"218","Name":"Tunisia"},{"Uuid":"219","Name":"Turkey"},{"Uuid":"220","Name":"Turkmenistan"},{"Uuid":"221","Name":"Turks and Caicos Islands"},{"Uuid":"222","Name":"Tuvalu"},{"Uuid":"223","Name":"Uganda"},{"Uuid":"224","Name":"Ukraine"},{"Uuid":"225","Name":"United Arab Emirates"},{"Uuid":"228","Name":"United States Minor Outlying Islands"},{"Uuid":"229","Name":"Uruguay"},{"Uuid":"230","Name":"Uzbekistan"},{"Uuid":"231","Name":"Vanuatu"},{"Uuid":"232","Name":"Vatican City State (Holy See)"},{"Uuid":"233","Name":"Venezuela"},{"Uuid":"234","Name":"Vietnam"},{"Uuid":"235","Name":"Virgin Islands (British)"},{"Uuid":"236","Name":"Virgin Islands (U.S.)"},{"Uuid":"237","Name":"Wallis And Futuna Islands"},{"Uuid":"238","Name":"Western Sahara"},{"Uuid":"239","Name":"Yemen"},{"Uuid":"240","Name":"Yugoslavia"},{"Uuid":"241","Name":"Zambia"},{"Uuid":"242","Name":"Zimbabwe"}];
$("#sel_country").html(template('reg-coun-sp1',{'list' : data}));
$("#sel_country").val('0');
});
