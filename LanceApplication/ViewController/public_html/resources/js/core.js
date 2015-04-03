var BASEURL = "/lance/res/";
//var User = {"UserName" : "muhongdi"};
//var User={  "UserName":"muhongdi",
//            "Email":"muhongdi@aa.com",
//            "Country":"44",
//            "TrueName":"牟宏迪",
//            "WebsiteUrl":"www.mywebsit.com",
//            "ImNumberA":"149997704",
//            "ImNumberB":"muhongdi@xxx.com",
//            "ImTypeA":"QQ",
//            "ImTypeB":"MSN",
//            "roles":["authenticated-role","anonymous-role"]
//        };

function netWorkError(goPath){
    alert("网络错误，请稍后再试。");
    //todo
}

function getDateFromTime(strTime){
    strTime = strTime.split(" ")[0];
    return strTime;
}

$.ae = function(err, callback){
    var option = {
            title : "提示",
            content : err,
            buttons : ["确定", "取消"],
            showBut : true
        };
    var str = '<div class="dialog_alert"><div class="title chfont">'+option.title+'</div><div class="body chfont">';
    str += option.content + '<br /></div>';
    if(option.showBut == true){           
         str += '<div class="buttons chfont"><a class="btn-green btn-con">' + option.buttons[0];
         str += '</a><a class="btn-gray btn-cel">' + option.buttons[1] + '</a></div>';
    }
    str += '</div>';
    $(".container-fluid").append(str);
    $(".dialog_alert").animate({"top" : "200px"}, 500).show();
    
    if(option.showBut){
        $(".dialog_alert .btn-cel").click(function(){
            $(".dialog_alert").animate({"top" : "-200px"}, 500, function(){
                $(".dialog_alert").remove();
            });
            if(callback)
                callback(false);
        });
        $(".dialog_alert .btn-con").click(function(){
            $(".dialog_alert").animate({"top" : "-200px"}, 500, function(){
                $(".dialog_alert").remove();
            });
            if(callback)
                callback(true);
        });
    }else{
        setTimeout(function(){
            $(".dialog_alert").animate({"top" : "-200px"}, 500, function(){
                $(".dialog_alert").remove();
            });
        }, 3000);
    }
};

$.cf = function(str){
    return confirm(str);
};

$.ax = function(ptype, path, param, callback, errCallback, jtype){
    jtype = jtype || "json";
    if(param != null){
        param = JSON.stringify(param);
    }
    $.ajax({
        type: ptype,
        url: BASEURL + path,
        data: param,
        dataType: jtype,
        timeout: 60000,
        contentType: 'application/json',
        success: function(data){
            callback(data);
        },
        error: function(xhr, err, info){
            errCallback(xhr, err, info);
        }
    });
};

//limit textarea,input word count
$.fn.limitWord = function(maxCount){
    var obj = $(this), lessCount = maxCount;
    obj.keyup(function(){
        less = maxCount - obj.val().length;
        obj.parent().find(".last").html(less);
    });
};
//end

//post job input search
$.fn.postInputSearch = function(callback){
    var inter = 1000, tmpInter = null, obj = $(this), oldTex = "", curTex = "";
    obj.focus(function(){
        tmpInter = setInterval(function(){
            curTex = obj.val();
            if(curTex != oldTex && curTex != ""){
                oldTex = curTex;
                $.ax("get", "jobTemplate/specificSkill/" + curTex, null, function(cdata){
                    callback(cdata);
                }, function(){
                    callback(null);
                });
            }
        }, inter);
    }).blur(function(){
        if(tmpInter != null){
            clearInterval(tmpInter);
        }
    });
};
//end


var Lancer = {
    profile :　{
        getStart : function(callback){
            $.ax("get", "user/"+User.UserName, null, function(data){
                callback(data);
            }, netWorkError);
        },
        getBasicInfo : function(callback){
            $.ax("get", "user/"+User.UserName, null, function(data){
                callback(data);
            }, netWorkError);
        },
        getSkillsInfo : function(callback, infor){
            $.ax("get", "user/skill/all/"+User.UserName, null, function(data){
                callback(data, infor);
            }, netWorkError);
        },
        getContactInfo : function(callback, infor){
            $.ax("get", "user/"+User.UserName, null, function(data){
                callback(data, infor);
            }, netWorkError);
        }
    },
    client : {
        getHeaderInfor : function(callback){
            $.ax("get", "user/muhongdi", null, function(data){
                callback(data);
            }, netWorkError);
        }
    }
};