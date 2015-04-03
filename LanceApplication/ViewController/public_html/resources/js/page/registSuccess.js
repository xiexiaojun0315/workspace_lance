$(function(){
//注册成功页面
  $("#email").html("“"+User.Email+"”");
  $("#send-email").click(function(){
        $("#sending").show();
        jQuery.ajax({
          url : '/lance/res/sendMail/resend/'+User.Email, 
          type : 'get',
          success: function(data){
            if("ok" == data){
               $("#sending").html("邮件已发送!");
            }
        },error:function(msg){
           
        }
     });
    });
    
    //激活页面
    $("#active").html(Data.msg);
    if(Data != null && "true" == Data.flag){
        var data = "expire";
        $("#active-area").show();
        $("#active-area").html(template('active-tip-sp1',{'status' : data}));
        $("#act-email").click(function(){
                $("#act-sending").show();
                jQuery.ajax({
                      url : '/lance/res/sendMail/resend2/'+Data.data.uName, 
                      type : 'get',
                      success: function(data){
                        if(data.indexOf("ok") >= 0){
                            $("#act-sending").html("激活邮件已经重新发至你的邮箱："+data.split(":")[1]);
                        }
                    },error:function(msg){
                       
                    }
                 });
        });
    }
});