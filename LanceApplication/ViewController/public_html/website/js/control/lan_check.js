/**
    rule : [];
*/
$.fn.lanCheck = function (rule) {
    //rules : emptyOk|onlyInt|onlyNumber|onlyEmail|onlyCellphone|onlyPhone|onlyChar|charIntLine|chineseCharIntLine
    // emptyOk first;
    var obj = $(this);
    var rules = new Array();
    rules["onlyInt"] = /^[0-9]*$/;
    rules["onlyNumber"] = /^\d+(\d|(\.[1-9]{1,2}))$/; //2位或者1位小数
    rules["onlyEmail"] = /w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*/;
    rules["onlyCellphone"] = /^1\d{10}$/;
    rules["onlyPhone"] = /d{3}-d{8}|d{4}-d{7}/;
    rules["onlyChar"] = /^[A-Za-z]+$/;
    rules["charIntLine"] = /^w+$/;
    rules["chineseCharIntLine"] = /^[\u0391-\uFFE5A-Za-z0-9_]+$/;

    var checkFun = function () {
        var i = 0, val = obj.val(), checkStatus = true;
        for (i = 0; i < rule.length; i++) {
            if (rule[i] == "emptyOk") {

            }
        }


    };


};