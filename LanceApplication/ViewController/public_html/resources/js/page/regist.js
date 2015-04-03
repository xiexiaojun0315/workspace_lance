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
        
        uname.blur(function(){
            uname.closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
            uname.popover("hide");
            
            if(uname.lanCheck("chineseCharIntLine")){
                uname.closest(".form-group").addClass("has-success").addClass("has-feedback");
                ckresult.umr = true;
            }else{
                uname.closest(".form-group").addClass("has-error");
                uname.popover("show");
            }
        });    
        
        return {ck:mck};
    };
    
    var check_pass = function(){
        var fpass = $("#inp_pass"),
            spass = $("#inp_cpass");
        
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
                fpass.popover("show");
            }
        });
        
        spass.blur(function(){
            spass.closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
            spass.popover("hide");
        
            if(spass.lanCheck("password") && spass.val() == fpass.val()){
                spass.closest(".form-group").addClass("has-success").addClass("has-feedback");
                ckresult.psr = true;
            }else{
                spass.closest(".form-group").addClass("has-error");
                spass.popover("show");
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
        
        company.blur(function(){
            company.closest(".form-group").removeClass("has-error").removeClass("has-success").removeClass("has-feedback");
            company.popover("hide");
            
            if(company.lanCheck("chineseCharIntLine")){
                company.closest(".form-group").addClass("has-success").addClass("has-feedback");
                ckresult.cmr = true;
            }else{
                company.closest(".form-group").addClass("has-error");
                company.popover("show");
            }
        });    
        
        return {ck:mck};
    };
    
    
    var check_form = function(btn){
        if(ckresult.emr && ckresult.lgr && ckresult.umr && ckresult.psr){
            if(!$("#fwtk")[0].checked){
                $("#lbl_fwtk").popover("show");
                return;
            }else{
                $("#lbl_fwtk").popover("hide");
            }
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
                toLogin($("#inp_lgname").val(),$("#inp_pass").val(),btn);
//                location.href="/lance/registSuccess.html"
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
        var obj = $(this);
        check_form(obj);
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
//                    alert("OK");
                    toLogin($("#inp_lgname").val(),$("#inp_pass").val(),btn);
//                    location.href="/lance/login";
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

function toLogin(uname,password,btn){
  var param={
        name : uname,
        pass : password,
        optype:"regist"
    };
   param = JSON.stringify(param);
   $.post("/lance/login", param, function(data){
        if(data.indexOf("ok") >= 0){
            var url = data.split(":")[1];
            window.location.href = url;
        }else{}
    },"text");
}

$(function(){
    $("#rgr").click(function(){
        if(this.checked){
            $("#inp_comname").attr("disabled", true).val($("#inp_name").val());
        }
    });
    
    $("#rgs").click(function(){
        if(this.checked){
            $("#inp_comname").attr("disabled", false).val("");
        }
    });
    
});

$(function () {
  loadCountrys();
  function loadCountrys(){
     jQuery.ajax({
          url : '/lance/res/location/country/list', 
          type : 'get',
          success: function(data){
            if(data.length > 0){
                $("#sel_country").html(template('reg-coun-sp1',{'list' : data}));
                $("#sel_country").val('0');
            }
        },error:function(msg){
           
        }
    });
  }
});


