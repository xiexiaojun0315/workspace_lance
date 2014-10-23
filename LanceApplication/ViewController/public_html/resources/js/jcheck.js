$.fn.checkInput = function (param, regular, errorTxt, blurFun, errBack) {
    var obj = $(this);
    obj.focus(function () {
        obj.parent().find(".tip *").show();
    });
    obj.blur(function () {
        obj.parent().find(".tip *").hide();

        if ($.trim(obj.val()) == "") {
            obj.removeClass("errBor").removeClass("passBor").addClass("errBor").parent().find(".error").html(param + "不能为空");
            
            if(errBack != null && errBack != undefined){
                errBack();
            }
            
        } else if (regular.test(obj.val())) {
            obj.removeClass("errBor").removeClass("passBor").addClass("passBor").parent().find(".error").html("");

            if (blurFun != null && blurFun != undefined)
                blurFun();

        } else {
            obj.removeClass("errBor").removeClass("passBor").addClass("errBor").parent().find(".error").html(errorTxt);
            
            if(errBack != null && errBack != undefined){
                errBack();
            }
        }


    });

};   