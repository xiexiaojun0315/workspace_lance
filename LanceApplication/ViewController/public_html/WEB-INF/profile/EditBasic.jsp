<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>编辑基本信息</title>
    <script>
            var User=${user};
            var Data=${data};
    </script>
    <!-- Bootstrap -->
    <link href="/lance/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="/lance/resources/css/common/common.css" rel="stylesheet" type="text/css" />
    <link href="/lance/resources/css/control/main.css" rel="stylesheet" type="text/css" />
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="/lance/resources/js/html5shiv.js" type="text/javascript"></script>
      <script src="/lance/resources/js/respond.js" type="text/javascript"></script>
    <![endif]-->
    </head>
    <body>
    <jsp:include page="/WEB-INF/common/TopBar.jsp" />
    <div class="container">
        <div class="row">
            <div class="col-md-3 lan-main-left">
                <img width="200px" src="/lance/resources/image/jpg/avatar5.png" alt="" class="img-thumbnail">

                <br /><br />
                <ul class="nav nav-stacked">
                    <li role="presentation" class="active"><a href="#">Home</a></li>
                    <li role="presentation"><a href="#">Profile</a></li>
                    <li role="presentation"><a href="#">Messages</a></li>
                </ul>
            </div>
            <div class="col-md-9 lan-main-right">
                <div class="page-header">
                    <h1><small>个人基本信息</small></h1>
                </div>
                 <form class="basic-form" role="form" method="post" action="#">
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            用户名：</label>
                        <input value="" type="text" class="form-control sml-inp" id="inp_uname" placeholder=""
                            data-container="body" data-toggle="popover" data-placement="right" 
                            data-content="请输入合法的用户名" data-trigger="manual" data-animation="false"/>
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            我的座右铭：</label>
                        <input value="" type="text" class="form-control sml-inp" id="inp_tagline" placeholder=""
                            data-container="body" data-toggle="popover" data-placement="right" 
                            data-content="请输入座右铭" data-trigger="manual" data-animation="false"/>
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            我的时薪价格（元/小时）：</label>
                        <input value="" type="text" class="form-control sml-inp" id="inp_hour" placeholder=""
                            data-container="body" data-toggle="popover" data-placement="right" 
                            data-content="请输入正确的数字" data-trigger="manual" data-animation="false"/>
                    </div>
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            驻才网向客户提出的时薪价格（元/小时）：</label>
                        <input readonly value="" type="text" class="form-control sml-inp" id="inp_hour2" placeholder="" />
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            我的个人网站：</label>
                        <input value="" type="text" class="form-control sml-inp" id="inp_website" placeholder=""
                            data-container="body" data-toggle="popover" data-placement="right" 
                            data-content="请输入我的个人网址" data-trigger="manual" data-animation="false"/>
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            我的个人描述：</label>
                        <textarea class="form-control" rows="3" id="inp_over"
                            data-container="body" data-toggle="popover" data-placement="right" 
                            data-content="请输入我的个人描述" data-trigger="manual" data-animation="false"></textarea>
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            我可以提供的服务描述：</label>
                        <textarea class="form-control" id="inp_service" rows="3"></textarea>
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            我可以接受的支付条件描述：</label>
                        <textarea class="form-control" id="inp_payment" rows="3"></textarea>
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            我的关键词描述（逗号分隔）：</label>
                        <textarea class="form-control" id="inp_keywords" rows="3"></textarea>
                    </div>
                    
                    <button autocomplete="off" data-loading-text="保存中..." id="btn_save" type="button" class="btn btn-primary">确定</button>
                 </form>
            </div>

        </div>
    </div>

    
    <jsp:include page="/WEB-INF/common/BottomBar.jsp" />
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/lance/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/lance/resources/js/bootstrap.min.js" type="text/javascript"></script>
    
    <script src="/lance/resources/js/core.js" type="text/javascript"></script>
    <script src="/lance/resources/js/control/lan_check.js" type="text/javascript"></script>
    <script type="text/javascript" src="/lance/resources/js/page/editbasic.js"></script>
    </body>
</html>