$(function () {
    var contractId = getUrlParam("contractId");
    if(contractId == null || "" == contractId){
       return;
    }
    var date = new Date();
    //初始化数据
    init(date,contractId);
    //上月、下月 单击事件绑定
    $("#last_mon").bind('click', function () {last(contractId);});
    $("#next_mon").bind('click', function () {next(date,contractId);});
    initContract(contractId);
    dialogshow();
    //hidden.bs.modal
    //批量填写数据提交
    $("#btn_commit").click(function(){
        var obj = $(this);
        check_form(obj,contractId);
    });
    
    $("#selAll").click(function(){
        reverse();
    });
    
});

var reverse = function(){
     var i=0;
     $("input[name='rep_check']").each(function () {
          i++;
          if($(this).attr("checked")){
             $(this).attr("checked", false);
             $(this).val(false);
          }else{
             $(this).attr("checked", true);
             $(this).val(true);
          }
     });
}

var check_form = function(btn,contractId){
//    btn.button('loading');
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
            if(!("ok"==data)){
               $("#upt-msg").html(data);
               $("#upt-msg").show();
            }else{
                //关闭dialog
                $("#reportModal").modal('hide');
                
                var is_visible = $("#gloal-msg").is(":visible");
                if(is_visible){
                    $("#gloal-msg").hide();
                }
            }
          
            //刷新工作日志数据
            searchReport($("#sel_year").val(),$("#sel_month").val(),contractId);
        },error:function(msg){
            $("#upt-msg").html("服务器无响应，请稍后再试...");
            $("#upt-msg").show();
        }
    });
};

var dialogshow=function(){
      $('#reportModal').on('show.bs.modal', function (event) {
            var dates = "";
            var i = 0;
            var index = null;
            $("input[name='rep_check']:checked").each(function(){ 
                var date = $(this).attr("datev");
                if(date){
                    i++;
                    dates+=$(this).attr("datev")+','; 
                    if(i == 1){
                        index = $(this).attr("id").substring(2);
                    }
                }
            }); 
            if(dates != null && dates.length > 0){
                $("#date").val(dates);
                $("#inp_address").val($("#adr"+index).val());
                $("#inp_workCon").val($("#wc"+index).val());
                $("#inp_hours").val($("#wk"+index).val());
                $("#inp_comments").val($("#wr"+index).val());
                
                var is_visible = $("#gloal-msg").is(":visible");
                if(is_visible){
                   //关闭消息框
                   $("#gloal-msg").hide();
                }
            }else{
               $("#gloal-msg").html("系统提示：请至少选择一个复选框之后，再编辑日志!");
               $("#gloal-msg").show();
               return false;
            }
      });
}

//初始化当前年月列表数据
function init(date,contractId) {
    var cur_year = date.getFullYear();
    var cur_month = date.getMonth() + 1;
    if (cur_month < 9) {
        cur_month = "0" + cur_month;
    }
    $("#sel_year").val(cur_year);
    $("#sel_month").val(cur_month);
    //查询
    searchReport(cur_year,cur_month,contractId);
}

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
}

function searchReport(cur_year,cur_month,contractId){
   //初始化日志数据
   jQuery.ajax( {
        url : '/lance/res/dailyReport/search/'+ contractId +'/'+ cur_year + "/" + cur_month, type : 'get', success : function (data) {
            $("#rep-cnt").html(template('rep-cnt-sp1', 
            {
                'list' : data
            }));
        },
        error : function (msg) {
        }
    });
}

//上月
function last(contractId) {
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
    searchReport(lastY,parseInt(sel_month),contractId);
}

//下月
function next(date,contractId) {
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
    searchReport(nextY,nextM,contractId);
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}