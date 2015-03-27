<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
    <div class="lan-header-main">
        <div class="container">
            <img class="pull-left logo" src="/lance/resources/image/common/logo.png" alt="" />

            <ol class="breadcrumb pull-right">
                <li>
                    <a class="lan-font-bold" data-toggle="dropdown" aria-expanded="false" href="#">Yxdaye</a>
                    <span class="caret"></span>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">个人信息</a></li>
                        <li><a href="#">个人简历</a></li>
                        <li><a href="#">个人设置</a></li>
                        <li role="presentation" class="divider"></li>
                        <li><a href="#">退出</a></li>
                    </ul>
                </li>
                <li><a href="#">收件箱 <span class="label label-danger">1</span></a></li>
                <li class="active">
                    <a class="lan-font-bold" data-toggle="dropdown" aria-expanded="false" href="#">帮助</a>
                    <span class="caret"></span>
                </li>
            </ol>

        </div>
        
    </div>

    <div class="lan-main-menu">
        <div class="container">
            <ul class="nav navbar-nav pull-left">
                <li class="active">
                    <a href="#">首页<span class="sr-only">(current)</span></a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">找人 <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                    </ul>
                </li>
            </ul>

            <div class="pull-right search">
                <div class="input-group input-group-sm">
                    <span data-toggle="dropdown" role="button" aria-expanded="false" class="input-group-addon" id="sizing-addon3">自由人<span class="caret"></span></span>
                    <input type="text" class="form-control" placeholder="搜索" aria-describedby="sizing-addon3">

                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">自由人</a></li>
                        <li><a href="#">工作</a></li>
                        <li><a href="#">团队</a></li>
                    </ul>

                </div>
                <button type="button" class="btn btn-primary btn-small">搜索</button>

                <button type="button" class="btn btn-success btn-middle">发布工作信息</button>
            </div>

        </div>
    </div>

    <br />
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
                    <h1><small>基本联系信息</small></h1>
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
                            邮箱地址：</label>
                        <input readonly="readonly" value="" type="text" class="form-control sml-inp" id="inp_email" placeholder=""/>
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            手机号码：</label>
                        <input value="" type="text" class="form-control sml-inp" id="inp_phone" placeholder=""
                            data-container="body" data-toggle="popover" data-placement="right" 
                            data-content="请输入正确的11位手机号码" data-trigger="manual" data-animation="false"/>
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            联系方式：</label><br />
                        QQ:<input value="" type="text" class="form-control sml-inp" id="inp_contact1" placeholder=""
                            data-container="body" data-toggle="popover" data-placement="right" 
                            data-content="" data-trigger="manual" data-animation="false"/><br />
                        Email:<input value="" type="text" class="form-control sml-inp" id="inp_contact2" placeholder=""
                            data-container="body" data-toggle="popover" data-placement="right" 
                            data-content="" data-trigger="manual" data-animation="false"/><br />
                        Other:<input value="" type="text" class="form-control sml-inp" id="inp_contact3" placeholder=""
                            data-container="body" data-toggle="popover" data-placement="right" 
                            data-content="" data-trigger="manual" data-animation="false"/>
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            经常活动地点1：</label>
                        <br />
                        <select id="sel_country">
                            <option value="44">中国</option>
                        </select>
                        <select id="sel_province" data-id="sel_city">
                            <option value="-1">省份</option>
                        </select>
                        <select id="sel_city">
                            <option class="mod" value="-1">城市</option>
                        </select>
                        <br />
                        <input style="margin-top:5px;" value="" type="text" class="form-control sml-inp" id="inp_loc1" placeholder=""
                            data-container="body" data-toggle="popover" data-placement="right" 
                            data-content="" data-trigger="manual" data-animation="false"/>
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            经常活动地点2：</label>
                        <br />
                        <select id="sel_country2">
                            <option value="44">中国</option>
                        </select>
                        <select id="sel_province2" data-id="sel_city2">
                            <option value="-1">省份</option>
                        </select>
                        <select id="sel_city2">
                            <option class="mod" value="-1">城市</option>
                        </select>
                        <br />
                        <input style="margin-top:5px;" value="" type="text" class="form-control sml-inp" id="inp_loc2" placeholder=""
                            data-container="body" data-toggle="popover" data-placement="right" 
                            data-content="" data-trigger="manual" data-animation="false"/>
                    </div>
                    
                    <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            地址显示选项：</label>
                        <div class="radio">
                            <label>
                                <input type="radio" class="address_rads" name="optionloc" id="optionsRadios1" value="all" checked>
                                显示所有的地点信息
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" class="address_rads" name="optionloc" id="optionsRadios1" value="city">
                                仅仅显示城市，地区
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" class="address_rads" name="optionloc" id="optionsRadios1" value="no">
                                不显示地点信息
                            </label>
                        </div>
                    </div>
                    
                     <div class="form-group <!--has-error has-feedback-->">
                        <label for="exampleInputEmail1" class="control-label">
                            联系信息可见性：</label>
                        <div class="radio">
                            <label>
                                <input type="radio" class="contact_rads" name="optionper" id="optionsRadios1" value="all" checked>
                                显示所有个人信息
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" class="contact_rads" name="optionper" id="optionsRadios1" value="city">
                                显示一般个人信息
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" class="contact_rads" name="optionper" id="optionsRadios1" value="no">
                                不显示个人信息
                            </label>
                        </div>
                    </div>
                    
                    
                    <button autocomplete="off" data-loading-text="保存中..." id="btn_save" type="button" class="btn btn-primary">确定</button>
                 </form>
            </div>

        </div>
    </div>

    
    <div class="lan-footer">
        <div class="container">
            <div class="row">
                <div class="col-md-2">
                    <img src="/lance/resources/image/common/logo.png" alt="" />
                </div>
                <div class="col-md-10">
                    <dl class="pull-left">
                        <dt>Research</dt>
                        <dd><a href="#" class="lan-font-black lan-font-12">Trends</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Online Employment Report</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Annual Impact Report</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Global Business Survey</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Global Freelancer Survey</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Women in Technology</a></dd>
                    </dl>
                    <dl class="pull-left">
                        <dt>Research</dt>
                        <dd><a href="#" class="lan-font-black lan-font-12">Trends</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Online Employment Report</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Annual Impact Report</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Global Business Survey</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Global Freelancer Survey</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Women in Technology</a></dd>
                    </dl>
                    <dl class="pull-left">
                        <dt>Research</dt>
                        <dd><a href="#" class="lan-font-black lan-font-12">Trends</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Online Employment Report</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Annual Impact Report</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Global Business Survey</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Global Freelancer Survey</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Women in Technology</a></dd>
                    </dl>
                    <dl class="pull-left">
                        <dt>Research</dt>
                        <dd><a href="#" class="lan-font-black lan-font-12">Trends</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Online Employment Report</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Annual Impact Report</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Global Business Survey</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Global Freelancer Survey</a></dd>
                        <dd><a href="#" class="lan-font-black lan-font-12">Women in Technology</a></dd>
                    </dl>
                    <div class="copyright">© 1999 - 2014 Elance, Inc. All Rights Reserved. U.S. Patents 7,069,242, 8,073,762 and 8,380,709</div>
                </div>
            </div>
        </div>
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/lance/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/lance/resources/js/bootstrap.min.js" type="text/javascript"></script>
    
    <script src="/lance/resources/js/core.js" type="text/javascript"></script>
    <script src="/lance/resources/js/control/lan_check.js" type="text/javascript"></script>
    <script type="text/javascript" src="/lance/resources/js/page/editcontact.js"></script>
    </body>
</html>