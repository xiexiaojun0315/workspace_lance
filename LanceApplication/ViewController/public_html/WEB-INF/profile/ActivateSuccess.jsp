<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>驻才网</title>
        <link type="text/css" href="../../resources/css/bootstrap.min.css" rel="stylesheet" />
        <link type="text/css" href="../../resources/css/common.css" rel="stylesheet" />
        <link type="text/css" href="../../resources/css/main.css" rel="stylesheet" />
        <!--[if lt IE 9]>
          <script src="resources/js/html5shiv.js"></script>
          <script src="resources/js/respond.js"></script>
        <![endif]-->
        <script>
            var User=${user};
            var Data=${data};
        </script>
    </head>
    <body>
     <div class="container-fluid chfont">
        <div class="row" id="waiting" style="display:none;">
            <img src="resources/image/bloading.gif" alt="" />
            <span class="bold-text">欢迎使用驻才网，正在为您创建账户，请稍候...</span>
        </div>
        
        <div id="t_con" class="row">
            <jsp:include page="/WEB-INF/common/TobBar2.jsp"/>
            <div id="content" class="min-width">
                <div class="reg-success">
                    <div class="title">
                        <span id="active">激活成功！</span>
                        <span>
                           欢迎来到驻才网！
                        </span>
                    </div>
                    <div class="resend" id="active-area" style="display: none;">
                        
                    </div>
                    <script id="active-tip-sp1" type="text/html">
                            {{if status=="expire"}} 
                                <span>激活邮件已过期？</span>
                                <a href="#" id="act-email">重新发送激活邮件</a>
                                <span id="act-sending" style="display: none;color:Red;font-size:12px;">
                                   <img src="../../resources/image/loading.gif" alt="" height="16" width="16"/>
                                    邮件发送中...
                                </span>
                            {{/if}}
                         </script>
                    <div class="share" id="share-area">
                        将Lance分享给你的好友<br /><br />
                        <!-- JiaThis Button BEGIN -->
                        <div class="jiathis_style_32x32">
                            <a class="jiathis_button_qzone"></a>
                            <a class="jiathis_button_tsina"></a>
                            <a class="jiathis_button_tqq"></a>
                            <a class="jiathis_button_weixin"></a>
                            <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
                        </div>
                        <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1406171982693217" charset="utf-8"></script>
                        <!-- JiaThis Button END -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="/lance/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/core.js" type="text/javascript"></script>
    <script src="/lance/resources/js/template.js" type="text/javascript"></script>
    <script src="/lance/resources/js/page/registSuccess.js" type="text/javascript"></script>
    <jsp:include page="/WEB-INF/common/BottomBar2.jsp"/>
</body>
</html>