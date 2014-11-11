var BASEURL = "/lance/res/";

function netWorkError(goPath){
    alert("网络错误，请稍后再试。");
    //todo
}

function getDateFromTime(strTime){
    strTime = strTime.split(" ")[0];
    return strTime;
}

$.ae = function(err){
    alert(err);
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
        timeout: 10000,
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


var Lancer = {
    profile :　{
        getStart : function(callback){
            $.ax("get", "user/lancer/muhongdi", null, function(data){
                callback(data);
            }, netWorkError);
        },
        getBasicInfo : function(callback){
            $.ax("get", "user/lancer/profile/allProfile/muhongdi", null, function(data){
                callback(data);
            }, netWorkError);
        },
        getSkillsInfo : function(callback, infor){
            $.ax("get", "user/lancer/skill/muhongdi", null, function(data){
                callback(data, infor);
            }, netWorkError);
        }
    }
};