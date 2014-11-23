$(function () {
    $("#inp_email").checkInput("邮箱", /\w@\w*\.\w/, "请输入正确的邮箱格式", function(){
        var obj = $("#inp_email");
        obj.parent().find(".load-gif").show();
        $.ax("get", "user/check/email/" + obj.val(), null, function(data){
            if(data == true){
                obj.removeClass("errBor").removeClass("passBor").addClass("errBor");
                obj.parent().find(".error").html("该邮箱已被注册，请选择其他邮箱。");
                emailOK = false;
            }else{
                obj.removeClass("errBor").removeClass("passBor").addClass("passBor");
                obj.parent().find(".error").html("");
                emailOK = true;
            }
            obj.parent().find(".load-gif").hide();
        }, function(){
            obj.parent().find(".load-gif").hide();
            obj.removeClass("errBor").removeClass("passBor").addClass("errBor");
            obj.parent().find(".error").html("服务器无响应，请稍后再试。");
            emailOK = false;
        });
    });

    $("#inp_dname").checkInput("姓名", /^[\u0391-\uFFE5A-Za-z]+$/, "姓名只能输入中文和字母", function () { 
        $("#inp_dname2").val($("#inp_dname").val()); 
        dnameOK = true;
    }, function(){
        dnameOK = false;
    });

    $("#inp_uname").checkInput("登录名", /^[0-9A-Za-z]+$/, "登录名称只能输入字母和数字", function(){
        var obj = $("#inp_uname");
        obj.parent().find(".load-gif").show();
        $.ax("get", "user/check/userName/" + obj.val(), null, function(data){
            if(data == true){
                obj.removeClass("errBor").removeClass("passBor").addClass("errBor");
                obj.parent().find(".error").html("该用户名已被注册，请选择其他用户名。");
                unameOK = false;
            }else{
                obj.removeClass("errBor").removeClass("passBor").addClass("passBor");
                obj.parent().find(".error").html("");
                unameOK = true;
            }
            obj.parent().find(".load-gif").hide();
        }, function(){
            obj.parent().find(".load-gif").hide();
            unameOK = false;
        });
    });

    $("#inp_pass").checkInput("密码", /^[0-9A-Za-z\_]{8,16}$/, "密码至少8位数字和字母", function(){passOK = true;}, function(){passOK = false;});

    $("#inp_cname").checkInput("公司名", /^[\u0391-\uFFE5A-Za-z]+$/, "公司名只能输入中文和字母", function(){comOK = true;}, function(){comOK = false;});

    $("#inp_rpass").checkInput("密码", /^[0-9A-Za-z\_]{8,16}$/, "密码至少8位数字和字母", function () {
        var err = $("#inp_rpass").parent().find(".error");
        if ($("#inp_rpass").val() != $("#inp_pass").val()) {
            err.html("密码两次输入不一致");
            pass2OK = false;
        } else {
            err.html("");
            pass2OK = true;
        }
    });

    $("#invi").click(function () {
        if ($(this)[0].checked == true) {
            $("#invip").show();
            $("#compp").hide();
        }
    });

    $("#comp").click(function () {
        if ($(this)[0].checked == true) {
            $("#compp").show();
            $("#invip").hide();
        }
    });
    
    /* regist */
    var emailOK = false, dnameOK = false, unameOK = false, passOK = false, pass2OK = false, comOK = false;
    $("#btn_regist.clickable").click(function(){
        var obj = $(this);
        if(!$("#chk-tk")[0].checked){
            $("#chk-tk").parent().find(".error").html("请阅读服务条款");
        }else {
            $("#chk-tk").parent().find(".error").html("");
            checkAddClient();
            if(emailOK && dnameOK && unameOK && passOK && pass2OK){
                var at = $("#invi")[0].checked ? 0 : 1;
                if(at == 0 || (at == 1 && comOK)){
                    //loading button
                    obj.removeClass("clickable").addClass("btn-load");
                    var param = { 
                        "UserName" : $("#inp_uname").val(),
                        "Email" :  $("#inp_email").val(),
                        "DisplayName" : $("#inp_dname").val(),
                        "Country" : $("#sel_contury").val(),
                        "TrueName" :  $("#inp_dname").val(),
                        "AccountType" : at ,
                        "CompanyName" : $("#inp_cname").val(),
                        "Password" : $("#inp_pass").val()
                    };
                    $.ax("post", "user/lancer", param, function(data){
                        obj.parent().find(".error").html("注册成功");
                        $.ae("注册成功！跳转~");
                        obj.addClass("clickable").removeClass("btn-load");
                    }, function(xhr, err, info){
                         obj.parent().find(".error").html("注册失败，请稍后再试.");
                        obj.parent().find(".error").html("注册失败，请稍后再试.");
                        obj.addClass("clickable").removeClass("btn-load");
                    });
                }
            }else{
                obj.parent().find(".error").html("请输入必要的信息.");
            }
        }
    });


});


    var checkAddClient = function(){
        if(!emailOK){
            $("#inp_email").blur();
        }
        if(!dnameOK){
            $("#inp_dname").blur();
        }
        if(!unameOK){
            $("#inp_uname").blur();
        }
        if(!passOK){
            $("#inp_pass").blur();
        }
        if(!pass2OK){
            $("#inp_rpass").blur();
        }
    };
    $("#btnAddClient.clickable").click(function(){
        var obj = $(this);
        checkAddClient();
        if(emailOK && dnameOK && unameOK && passOK && pass2OK){
            obj.removeClass("clickable").addClass("btn-load");
            var param = {
                "TrueName" : $("#inp_dname").val(),
                "DisplayName" : $("#inp_dname").val(),
                "Email" : $("#inp_email").val(),
                "UserName" : $("#inp_uname").val(),
                "Password" : $("#inp_pass").val()
            };
            $.ax("post", "user/client", param, function(data){
                $.ae("注册成功！跳转~");
                obj.addClass("clickable").removeClass("btn-load");
            }, function(){
                netWorkError();
                obj.addClass("clickable").removeClass("btn-load");
            }, "text");
        }
    });
});
