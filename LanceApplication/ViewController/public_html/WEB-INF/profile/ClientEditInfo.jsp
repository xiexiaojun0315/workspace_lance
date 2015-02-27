<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title></title>
    <link type="text/css" href="/lance/resources/css/bootstrap.min.css" rel="stylesheet" />
    <link type="text/css" href="/lance/resources/css/common.css" rel="stylesheet" />
    <link type="text/css" href="/lance/resources/css/main.css" rel="stylesheet" />
    <!--[if lt IE 9]>
      <script src="/lance/resources/js/html5shiv.js"></script>
      <script src="/lance/resources/js/respond.js"></script>
    <![endif]-->
    </head>
    <body>
        <div class="container-fluid">
        <div class="row chfont">
            <div id="header">
                <div class="min-width">
                    <img class="logo" src="/lance/resources/image/logo.png" alt="" />
                    <div class="setting">
                        <div tabindex="-1" class="nav_sprite bold nav">yxdy
                            <div class="dialog-small">
                                <dl>
                                    <dd class="first"><a href="#">My Lance</a></dd>
                                    <dd><a href="#">Profile</a></dd>
                                    <dd><a href="#">Setting</a></dd>
                                    <dd><a href="#">Membership</a></dd>
                                </dl>
                                <div class="sep-dot"></div>
                                <dl class="dquit">
                                    <dd>
                                        <span class="nav_sprite logout"></span>
                                        <a href="#">Sign Out</a>
                                    </dd>
                                </dl>
                            </div>
                        </div>
                        <span>|</span>
                        <div href="#" class="nav">Inbox(1)</div>
                        <span>|</span>
                        <div tabindex="-1" href="#" class="nav_sprite nav">Help
                            <div class="dialog-small">
                                <dl>
                                    <dd class="first"><a href="#">Elance Code of Conduct</a></dd>
                                    <dd><a href="#">Contact Elance Support</a></dd>
                                    <dd><a href="#">Help Center</a></dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="menu">
                <div class="min-width">
                    <ul>
                        <li class="sel">
                            <span class="nav_sprite icon-home"></span>
                            <a href="#">My Elance</a>
                        </li>
                        <li>
                            <a href="#">HIRE</a>
                            <div class="sub-menu">
                                <a href="#">Search Jobs</a>
                                <a href="#">Browse</a>
                                <a href="#">Post a Job</a>
                                <a href="#">Bring Your Freelancer</a>
                                <a href="#">Client Profile</a>
                                <a href="#">Watch List</a>
                            </div>
                        </li>
                        <li>
                            <a href="#">FIND WORK</a>
                            <div class="sub-menu">
                                <a href="#">Search Jobs</a>
                                <a href="#">Browse</a>
                                <a href="#">Post a Job</a>
                                <a href="#">Bring Your Freelancer</a>
                                <a href="#">Client Profile</a>
                                <a href="#">Watch List</a>
                            </div>
                        </li>
                        <li>
                            <a href="#">MANAGE</a>
                            <div class="sub-menu">
                                <a href="#">Search Jobs</a>
                                <a href="#">Browse</a>
                                <a href="#">Post a Job</a>
                                <a href="#">Bring Your Freelancer</a>
                                <a href="#">Client Profile</a>
                                <a href="#">Watch List</a>
                            </div>
                        </li>
                        <li>
                            <a href="#">RESOURCES</a>
                            <div class="sub-menu">
                                <a href="#">Search Jobs</a>
                                <a href="#">Browse</a>
                                <a href="#">Post a Job</a>
                                <a href="#">Bring Your Freelancer</a>
                                <a href="#">Client Profile</a>
                                <a href="#">Watch List</a>
                            </div>
                        </li>
                    </ul>

                    <div class="search-bar">
                        <div class="search" tabindex="-1">
                            <span class="title">Jobs<span class="icon-sj"></span></span>
                            <div class="drop-list">
                                <span class="dlist">Jobs<span class="icon-sj"></span></span>
                                <span class="dlist">Portfolio Samples</span>
                                <span class="dlist">Freelancers</span>
                                <span class="dlist">My Jobs</span>
                            </div>
                            <input type="text" value="" />
                        </div>
                        <a href="#" class="btn-gray">Go</a>
                        <span class="sep-sea"></span>
                        <a href="#" class="btn-green">Post your job</a>
                    </div>
                </div>
            </div>

            <div id="content" class="min-width">
                <div class="left-con">
                    <div class="bt">个人信息</div>
                    <br />
                    <ul>
                        <li class="sel nav-li"><a href="#">基本信息</a></li>
                        <li><div class="sep">&nbsp;</div></li>
                    </ul>
                </div>
                <div class="right-con no-border">
                    <div class="basic">
                        <h3>编辑个人信息</h3>
                        <br />
                        <p class="mod fixh">
                            <label>用户名<span class="error-color">*</span></label><br />
                            <input id="inp_dname" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                        </p>
                        <p class="mod fixh">
                            <label>邮箱</label><br />
                            <input disabled="disabled" id="inp_mail" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                        </p>
                        <p class="mod fixh">
                            <label>手机号码</label><br />
                            <input id="inp_phone" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                        </p>
                        <p class="mod fixh addr">
                            <label>联系地址<span class="error-color">*</span></label><br />
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
                            <input id="inp_detail_addr" placeholder="请输入详细地址" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                        </p>
                        <p class="mod fixh addr">
                            <label>备用联系地址</label><br />
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
                            <input id="inp_detail_addr2" placeholder="请输入详细地址" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                        </p>
                        
                       
                        <p class="buttons">
                            <a id="btn_save" href="#" class="btn-green clickable">
                                <img src="/lance/resources/image/loading.gif" alt="" />
                                保存
                            </a>
                            <a href="#" class="btn-gray">取消</a>
                        </p>
                    </div>
                </div>
            </div>


            <div id="mfooter">
                <div class="min-width">
                    <img src="/lance/resources/image/logo.png" alt="" />
                    <span>©2014 Elance, Inc.</span>
                    <span>|</span>
                    <a href="#">Terms & Privacy</a>
                    <span>|</span>
                    <a href="#">Help</a>
                </div>
            </div>

        </div>
    </div>
            

    <script src="/lance/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/core.js" type="text/javascript"></script>
    </body>
</html>