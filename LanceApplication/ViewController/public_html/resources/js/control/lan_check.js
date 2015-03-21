/**
    rule : [];
*/
$.fn.lanCheck = function (rule) {
    //rules : emptyOk|onlyInt|onlyNumber|onlyEmail|onlyCellphone|
    //        onlyPhone|onlyChar|charIntLine|chineseCharIntLine|notEmpty|
    //        password
    // emptyOk first;
    var obj = $(this);
    var rules = new Array();
    rules["onlyInt"] = /^[0-9]*$/;
    rules["onlyNumber"] = /^\d+(\d|(\.[1-9]{1,2}))$/; //2位或者1位小数
    rules["onlyEmail"] = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    rules["onlyCellphone"] = /^1\d{10}$/;
    rules["onlyPhone"] = /d{3}-d{8}|d{4}-d{7}/;
    rules["onlyChar"] = /^[A-Za-z]+$/;
    rules["charIntLine"] = /^[a-zA-z]\w{3,15}$/;
    rules["chineseCharIntLine"] = /^[\u0391-\uFFE5A-Za-z0-9_]+$/;
    rules["password"] = /^.{8,}$/;

    var checkFun = function () {
        var i = 0, val = obj.val(), checkStatus = true;
        var arr_rules = rule.split("|");
        
        for (i = 0; i < arr_rules.length; i++) {
            if (arr_rules[i] == "emptyOk") {
                if($.trim(val) == ""){
                    break;
                }
            }else{
                if(arr_rules[i] == "notEmpty"){
                    if($.trim(val) == ""){
                        checkStatus = false;
                    }
                }else{
                    //check zz
                    
                    if(rules[arr_rules[i]] && rules[arr_rules[i]].test(val)){
                        checkStatus = true;
                    }else{
                        checkStatus = false;
                    }
                }
            }
            
        }
        
        return checkStatus;

    };

    return checkFun();

};


