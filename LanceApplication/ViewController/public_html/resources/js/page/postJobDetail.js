$(function () {
     
    
    
});

function initData(){
  var jobId = getUrlParam("jobId");
  if(jobId == null){
     return;
  }
  
  jQuery.ajax( {
        url : '/lance/res/postJob/'+ jobId, type : 'get', success : function (data) {
            
        },
        error : function (msg) {
        }
   });
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
};