$(function () {
    var contractId = getUrlParam("contractId");
    if(contractId == null || "" == contractId){
       return;
    }
    
    //判断当前用户是“甲方”还是乙方
    initCurUserData(contractId);
   
    $("#selAll").change(function(){
        reverse();
    });
    
});

var reverse = function(){
    $("input[name='rep_check']").each(function () {
          if($(this).attr("checked")){
             $(this).attr("checked", false);
          }else{
             $(this).attr("checked", true);
          }
     });
};

var commitLancerform = function(btn,contractId){
    if(!validateLancerForm()){
       return false;
    }
    
    var param = {
        "date" : $("#date").val(),
        "contractId" : contractId,
        "data" : {"Address":$("#inp_address").val(),"WorkContent":$("#inp_workCon").val(),"WorkHours":$("#inp_hours").val(),"WorkRemark":$("#inp_comments").val()}
    };
    jQuery.ajax({
          url : '/lance/res/dailyReport/mergeConRe', 
          type : 'post',
          contentType : 'application/json',
          data:jQuery.toJSON(param),
          success: function(data){
            if(!("ok"==data.toString())){
               $("#lan-msg").html(data);
               $("#lancerMsg").show();
            }else{
                
                //关闭dialog
                $("#lancerModal").modal('hide');
                
                var is_visible = $("#gloal-msg").is(":visible");
                if(is_visible){
                    $("#gloal-msg").hide();
                }
            }
          
            //刷新工作日志数据
            searchReport(parseInt($("#sel_year").val()),parseInt($("#sel_month").val()),contractId,"LANCER");
        },error:function(msg){
            $("#lan-msg").html("服务器无响应，请稍后再试...");
            $("#lancerMsg").show();
        }
    });
};

var commitClientform = function(btn,contractId,optype){
     var sta = "";
     var staName = "";
     var remark = "";
     if("reject" == optype){
         if(!validateClientForm()){
             return false;
         }
         sta = "reject";
         staName="已拒绝";
         remark = $("#inp_remark").val();
     }else{
       sta = "confirm";
       staName="待付款";
     }
    var param = {
        "ids" : $("#hide-ids").html(),
        "contractId" : contractId,
        "data" : {"StatusRemark":remark,"Status":sta,"StatusName":staName}
    };
    jQuery.ajax({
          url : '/lance/res/dailyReport/audit', 
          type : 'post',
          contentType : 'application/json',
          data:jQuery.toJSON(param),
          success: function(data){
            if(!("ok"==data.toString())){
               $("#cli-msg").html(data);
               $("#clientMsg").show();
            }else{
                
                //关闭dialog
                $("#clientModal").modal('hide');
                
                var is_visible = $("#gloal-msg").is(":visible");
                if(is_visible){
                    $("#gloal-msg").hide();
                }
            }
          
            //刷新工作日志数据
            searchReport(parseInt($("#sel_year").val()),parseInt($("#sel_month").val()),contractId,"CLIENT");
        },error:function(msg){
            $("#cli-msg").html("服务器无响应，请稍后再试...");
            $("#clientMsg").show();
        }
    });
};

function validateLancerForm(){
    var ckWc = $("#inp_workCon").lanCheck('notEmpty');
    var ckWh = $("#inp_hours").lanCheck('notEmpty');
    var paWc = $("#inp_workCon").closest('.form-group');
    var paWh = $("#inp_hours").closest('.form-group');
    if(!ckWc){
        paWc.addClass("has-error");
        $("#lan-msg").html("工作内容不能为空，请填写!");
        $("#lancerMsg").show();
        return false;
    }else{
        $("#lancerMsg").hide();
        paWc.removeClass("has-error");
    }
    if(!ckWh){
        paWh.addClass("has-error");
        $("#lan-msg").html("工时不能为空，请填写!");
        $("#lancerMsg").show();
        return false;
    }else{ 
        ckWh = isNum($("#inp_hours").val());
        if(!(ckWh && parseInt($("#inp_hours").val()) < 24)){
            paWh.addClass("has-error");
            $("#lan-msg").html("工时必须为大于0小于24的正整数!");
            $("#lancerMsg").show();
            return false;
        }else{
            $("#lancerMsg").hide();
            paWh.removeClass("has-error");
        }
    }
   return true;
}

function validateClientForm(){
    var ckRe = $("#inp_remark").lanCheck('notEmpty');
    var paRe = $("#inp_remark").closest('.form-group');
    if(!ckRe){
        paRe.addClass("has-error");
        $("#cli-msg").html("请在备注中填写拒绝原因!");
        $("#clientMsg").show();
        return false;
    }else{
        $("#clientMsg").hide();
        paRe.removeClass("has-error");
    }
   return true;
}

function isNum(str){
  var reg =/^[1-9]\d{0,2}$/;
  return reg.test(str);
}

var lancerDialogShow=function(){
    $("#btn-pltx").click(function(){
        var dates = "";
        var index = null;
        var flag = false;
        $("input[name='rep_check']:checked").each(function(){ 
            var date = $(this).attr("datev");
            if(date){
                dates+=$(this).attr("datev")+','; 
                var _index = $(this).attr("id").substring(2);
                if(!flag && !($.trim($("#wc"+_index).html()) == '')){
                    flag = true;
                    index = _index;
                }
            }
        }); 
        
        if(dates != null && dates.length > 0){
            clearLanceMsg();
            $("#date").val(dates);
            $("#inp_address").val($("#adr"+index).html());
            $("#inp_workCon").val($("#wc"+index).html());
            $("#inp_hours").val($("#wh"+index).html());
            $("#inp_comments").val($("#wr"+index).html());
            
            var is_visible = $("#gloal-msg").is(":visible");
            if(is_visible){
               //关闭消息框
               $("#gloal-msg").hide();
            }
            
            $("#lancerModal").modal('show');
        }else{
           $("#gloal-msg").html("系统提示：请至少选择一个复选框之后，再编辑日志!");
           $("#gloal-msg").show();
           return false;
       }
    });
};

//清楚lancer dialog 中的验证提示
function clearLanceMsg(){
  $("#lan-msg").html("");
  $("#lancerMsg").hide();
  $("#inp_workCon").closest('.form-group').removeClass("has-error");
  $("#inp_hours").closest('.form-group').removeClass("has-error");
}

var clientDialogShow=function(){
    $("#btn-plsp").click(function(){
        var dates = "";
        var index = null;
        var ids = "";
        $("input[name='rep_check']:checked").each(function(){ 
            var date = $(this).attr("datev");
            if(date){
                dates+=$(this).attr("datev")+','; 
                ids+=$(this).attr("uuid")+','; 
                var _index = $(this).attr("id").substring(2);
                if(!($.trim($("#sr"+_index).html()) == '')){
                    flag = true;
                    index = _index;
                }
            }
        });  
        if(dates != null && dates.length > 0){
            $("#cdate").html(dates);
            $("#inp_remark").val($("#sr"+index).html());
            $("#hide-ids").html(ids);
            var is_visible = $("#gloal-msg").is(":visible");
            if(is_visible){
               //关闭消息框
               $("#gloal-msg").hide();
            }
            
            $("#clientModal").modal('show');
        }else{
           $("#gloal-msg").html("系统提示：请至少选择一个复选框之后，再审批日志!");
           $("#gloal-msg").show();
           return false;
       }
    });
};

//初始化当前年月列表数据
function init(date,contractId,role) {
    var cur_year = date.getFullYear();
    var cur_month = date.getMonth() + 1;
    if (cur_month < 9) {
        cur_month = "0" + cur_month;
    }
    $("#sel_year").val(cur_year);
    $("#sel_month").val(cur_month);
    //查询
    searchReport(cur_year,parseInt(cur_month),contractId,role);
};

//初始化合同信息
function initContract(contractId){
   var arr = new Array("htbh","htmc","zw","cn","lanceN","gzkssj","gzjssj","zffs","zt","jg1");
   var attrs = new Array("Uuid","ContractName","Title","ClientName","LancerName","DateStart","DateEnd","Postform","ProcessStatusDesc","HourlyPay");
   jQuery.ajax( {
        url : '/lance/res/contract/'+ contractId, type : 'get', success : function (data) {
            for(var t=0;t<arr.length;t++){
               if("jg1"==arr[t]){
                  var h = data[attrs[t]];
                  if(h != null){
                     h="￥ "+h+" 元/小时"
                  }
                  $("#"+arr[t]).html(h);
               }else{
                  $("#"+arr[t]).html(data[attrs[t]]);
               }
            }
        },
        error : function (msg) {
        }
    });
};

function searchReport(cur_year,cur_month,contractId,role){
   var optype = null;
   if("LANCER" == role){
       optype = "lancer";
   }else if("CLIENT" == role){
       optype = "client";
   }
   if(optype == null){
      return;
   }
   //初始化日志数据
   jQuery.ajax( {
        url : '/lance/res/dailyReport/search/'+optype+'/'+ contractId +'/'+ cur_year + '/' + cur_month, type : 'get', success : function (data) {
            $("#rep-cnt").html(template('rep-cnt-sp1', 
            {
                'list' : data
            }));
        },
        error : function (msg) {
        }
    });
};

var initCurUserData = function(contractId){
   jQuery.ajax({
        url : '/lance/res/contract/checkUser/'+ contractId, type : 'get', success : function (data) {
            var date = new Date();
            if("LANCER" == data){
                $("#btn-plsp").empty();
                $("#btn-plsp").remove();
                $("#clientModal").empty();
                $("#clientModal").remove();
                //初始化合同信息
                initContract(contractId);
                //初始化日志数据
                init(date,contractId,"LANCER");
                //上月、下月 单击事件绑定
                $("#last_mon").bind('click', function () {last(contractId,"LANCER");});
                $("#next_mon").bind('click', function () {next(date,contractId,"LANCER");});
                //批量填写数据提交
                $("#btn_commit").click(function(){
                    var obj = $(this);
                    commitLancerform(obj,contractId);
                });
                lancerDialogShow();
                
            }else if("CLIENT" == data){
                $("#btn-pltx").empty();
                $("#btn-pltx").remove();
                $("#lancerModal").empty();
                $("#lancerModal").remove();
                //初始化合同信息
                initContract(contractId);
                //初始化日志数据
                init(date,contractId,"CLIENT");
                //上月、下月 单击事件绑定
                $("#last_mon").bind('click', function () {last(contractId,"CLIENT");});
                $("#next_mon").bind('click', function () {next(date,contractId,"CLIENT");});
                $("#btn_confirm").click(function(){
                    var obj = $(this);
                    commitClientform(obj,contractId,"comfirm");
                });
                $("#btn_reject").click(function(){
                    var obj = $(this);
                    commitClientform(obj,contractId,"reject");
                });
                clientDialogShow();
            }else{
                $("#btn-plsp").empty();
                $("#btn-plsp").remove();
                $("#clientModal").empty();
                $("#clientModal").remove();
                
                $("#btn-pltx").empty();
                $("#btn-pltx").remove();
                $("#lancerModal").empty();
                $("#lancerModal").remove();
            }
        },
        error : function (msg) {
        }
    });
};

//上月
function last(contractId,role) {
    var sel_year = parseInt($("#sel_year").val());
    var sel_month = parseInt($("#sel_month").val());
    var lastY = sel_year;
    if (sel_month == 1) {
        sel_month = "12";
        if (lastY <= 2011) {
            $("#last_mon").attr("disabled", true);
        }
        lastY = (sel_year - 1);
        $("#sel_month").val(sel_month);
    }
    else {
        sel_month = sel_month - 1;
        if (sel_month < 10) {
            sel_month = "0" + sel_month;
        }
        $("#sel_month").val(sel_month);
    }
    $("#sel_year").val(lastY.toString());
    searchReport(lastY,parseInt(sel_month),contractId,role);
};

//下月
function next(date,contractId,role) {
    var sel_year = parseInt($("#sel_year").val());
    var sel_month = parseInt($("#sel_month").val());
    var nextM = (sel_month + 1);
    var nextY = sel_year;
    if (sel_month == 12) {
        if (sel_year >= date.getFullYear()) {
            $("#next_mon").attr("disabled", true);
        }else{
           nextY = sel_year + 1;
           nextM = "01";
        }
    }
    else {
        if (nextM < 10) {
            nextM = "0" + nextM;
        }
    }
    $("#sel_month").val(nextM.toString());
    $("#sel_year").val(nextY.toString());
    searchReport(nextY,nextM,contractId,role);
};

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
};