<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
        <script>
            var User=${user};
            var Data=${data};
        </script>
    <link type="text/css" href="/lance/resources/css/bootstrap.min.css" rel="stylesheet" />
    <link type="text/css" href="/lance/resources/css/common.css" rel="stylesheet" />
    <link type="text/css" href="/lance/resources/css/main.css" rel="stylesheet" />
    <!--[if lt IE 9]>
      <script src="/lance/resources/js/html5shiv.js"></script>
      <script src="/lance/resources/js/respond.js"></script>
    <![endif]-->
    </head>
    <body>
    <jsp:include page="/WEB-INF/common/TopBar.jsp" />
        <div class="container-fluid">
        <div class="row" id="waiting">
            <img src="/lance/resources/image/bloading.gif" alt="" />
            <span class="bold-text">加载中...</span>
        </div>
        <div id="t_con" class="row" style="display:none;">

            <div id="content" class="min-width">
                <jsp:include page="/WEB-INF/profile/ProfileMenu.jsp" />
                <div class="right-con">
                    <div class="infor">
                        <span class="name"><span class="tname">yx d.</span>
                            <a href="#dia-verify" class="btn-verify swindow"><span>Get Verified</span></a><br />
                            <span class="desc tagline">Expert PHP Developer</span>
                        </span>
                        <a href="#" class="btn-edit f-right"><span>Edit</span></a>
                        <p class="c-f">
                            <img src="/lance/resources/image/country/china.png" alt="" />
                            China
                            <span class="line">|</span>
                            Chongqing, Chongqing Shi
                            <a href="#" class="btn-edit"><span>Edit</span></a>
                            <span class="line">|</span>
                            <span class="not-set">Local Time Not Set</span>
                            <a href="#" class="btn-edit f-right"><span>Edit</span></a>
                        </p>
                    </div>
                    
                    <div class="information">
                        <div class="mod ov">
                            <a href="#" class="title">自我介绍</a>
                            <a href="EditBasic" class="btn-edit f-right"><span>编辑</span></a>
                            <p>
                                Minimum Hourly Rate <span class="num">$135</span>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp; 
                                <span class="icon-video">Add Video</span><br />
                                <span class="oview">asdasdasdas</span><br />
                                <a href="#">Read More »</a>
                            </p>
                        </div>

                        <div class="mod" style="display:none;">
                            <a href="#" class="title">Portfolio</a>
                            <a href="#" class="btn-edit f-right"><span>Edit</span></a>
                            <div class="pic">
                                <span class="no-item">No Portfolio Items</span>
                                <div style="padding-top: 75px;">
                                    <a href="#">Edit Portfolio</a> | <a href="#" target="_blank">More Info »</a>
                                </div>
                            </div>
                        </div>

                        <div class="mod">
                            <a href="#" class="title">技能</a>
                            <a href="EditSkill" class="btn-edit f-right"><span>编辑</span></a>
                            <div class="f-right bold">Tested</div>
                            <p class="no-add">
                                None
                            </p>
                            <ul class="skills">
                                <li class="zw-mod">
                                    <span class="skil">PHP5</span>
                                    <a href="#" class="f-right">Take test</a>
                                </li>
                            </ul>
                        </div>

                        <div class="mod">
                            <a href="#" class="title">Service Description</a>
                            <a href="#" class="btn-edit f-right"><span>Edit</span></a>
                            <p>
                                <span class="sdesc"></span><br />
                                <a href="#">Read More »</a>
                            </p>
                        </div>

                        <div class="mod">
                            <a href="#" class="title">Employment</a>
                            <a href="#" class="btn-edit f-right"><span>Edit</span></a>
                            <p class="payment">
                                None
                            </p>
                        </div>

                        <div class="mod">
                            <a href="#" class="title">教育信息</a>
                            <a href="#" class="btn-edit f-right"><span>添加教育信息</span></a>
                            <p class="no-add">
                                None
                            </p>
                            <div class="edus">
                                <ul>
                                    <li class="mod-edu">
                                        <a href="#" class="btn-edit f-right"><span>编辑</span></a>
                                        <span class="name bold">北京大学</span><br />
                                        <span clas="degree">学位</span><br />
                                        <span class="time">2001-2008</span>
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="links">
                            <a href="#">Overview</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="#">Job History</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="#">Portfolio</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="#">Skills</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="#">Resume/C.V.</a>&nbsp;&nbsp;|&nbsp;&nbsp;<br />
                            <a href="#">Contact Info</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="#">Privacy Settings</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="#">Public View of Profile</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                        </div>

                        <div class="mod">
                            <a href="#" class="title">关键词</a>
                            <a href="#" class="btn-edit f-right"><span>编辑</span></a>
                            <p class="no-add">
                                None
                            </p>
                            <ul class="keys">
                                <li class="mod-key">ttest</li>
                            </ul>
                        </div>

                        <div class="mod-right">
                            <div class="comp">
                                <div class="title">Your Profile Completeness</div>
                                <div class="progress">
                                    <div class="proval"></div>
                                </div>
                                <span class="val">60%</span>
                                <p>
                                    Complete your profile so that clients can find and hire you. 
                                    <a href="#">More Info</a>
                                </p>
                                <ul class="scores">
                                    <li>
                                        <img src="/lance/resources/image/profile/arrow_right.png" alt="" />
                                        <a href="#">Take The Elance Pledge</a>
                                        <span class="add">+10%</span>
                                    </li>
                                    <li>
                                        <img src="/lance/resources/image/profile/arrow_right.png" alt="" />
                                        <a href="#">Add Portfolio</a>
                                        <span class="add">+10%</span>
                                    </li>
                                    <li>
                                        <img src="/lance/resources/image/profile/arrow_right.png" alt="" />
                                        <a href="#">Add Credentials</a>
                                        <span class="add">+10%</span>
                                    </li>
                                </ul>
                            </div>

                            <div class="snap">
                                <h6>My Snapshot</h6>
                                <div tabindex="-1" class="cates">
                                    All Categories
                                    <img src="/lance/resources/image/profile/arrow_down.gif" alt="" />
                                    <div class="lists">
                                        <ul>
                                            <li class="first"><a href="#">All Categories</a></li>
                                            <li><a href="#">IT & Programming</a></li>
                                        </ul>
                                    </div>
                                    <img class="level" src="/lance/resources/image/profile/level.png" alt="" />
                                </div>
                                <div class="tab-head">
                                    <a href="#" class="sel">12 months</a>
                                    <span class="sep">|</span>
                                    <a href="#">Lifetime</a>
                                </div>
                                <table class="table-infor" border="0" cellpadding="0" cellspacing="0" width="100%">
                                    <tr>
                                        <td align="left" valign="top" rowspan="3">Jobs</td>
                                        <td align="right">0</td>
                                        <td align="right">Total</td>
                                    </tr>
                                    <tr>
                                        <td align="right">0</td>
                                        <td align="right">Milestones</td>
                                    </tr>
                                    <tr>
                                        <td align="right">0</td>
                                        <td align="right">Hours</td>
                                    </tr>
                                </table>

                                <div class="sep"></div>

                                <table class="table-infor" border="0" cellpadding="0" cellspacing="0" width="100%">
                                    <tr>
                                        <td align="left" valign="top" rowspan="2">Reviews</td>
                                        <td align="right">0</td>
                                        <td align="right">Total</td>
                                    </tr>
                                    <tr>
                                        <td align="right">0</td>
                                        <td align="right">Milestones</td>
                                    </tr>
                                </table>
                                
                                <div class="sep"></div>

                                <table class="table-infor" border="0" cellpadding="0" cellspacing="0" width="100%">
                                    <tr>
                                        <td align="left" valign="top" rowspan="2">Clients</td>
                                        <td align="right">0</td>
                                        <td align="right">Total</td>
                                    </tr>
                                    <tr>
                                        <td align="right">0</td>
                                        <td align="right">Milestones</td>
                                    </tr>
                                </table>
                                
                                <div class="sep"></div>

                                <table class="table-infor" border="0" cellpadding="0" cellspacing="0" width="100%">
                                    <tr>
                                        <td align="left" valign="top" rowspan="2">Earnings</td>
                                        <td align="right">0</td>
                                        <td align="right">Total</td>
                                    </tr>
                                    <tr>
                                        <td align="right">0</td>
                                        <td align="right">Milestones</td>
                                    </tr>
                                </table>
                            </div>

                            <div class="iden">
                                <h6>Identity</h6>
                                <div class="row">
                                    <span class="attr">Username</span>
                                    <span class="val">yxdy</span>
                                </div>
                                <div class="row">
                                    <span class="attr">Type</span>
                                    <span class="val">Individual</span>
                                </div>
                                <div class="row">
                                    <span class="attr">Member Since</span>
                                    <span class="val">September 2014</span>
                                </div>  
                                <div class="row">
                                    <span class="attr">Elance URL </span>
                                    <input type="text" value="" />
                                </div>   
                                <div class="row">
                                    <span class="attr">Verifications</span>
                                    <span class="icon-sp icon-one"></span>
                                    <span class="icon-sp icon-two"></span>
                                    <span class="icon-sp icon-thr"></span>
                                    <span class="icon-sp icon-for"></span>
                                    <span class="icon-sp icon-fiv">0</span>
                                </div> 
                                <a class="btn-gray" href="#">Download Your Profile Widget</a>
                            </div>

                            <div class="group">
                                 <h6>Groups</h6>
                                 <a href="#" class="btn-edit"><span>Join Groups</span></a>
                                 <p>
                                    You are not currently a member of any group
                                 </p>
                                 <p align="center">
                                    <a href="#">View Elance Groups</a>
                                 </p>
                            </div>

                        </div>

                    </div>


                </div>
            </div>

            <div class="overlay"></div>

            <div id="dia-verify" class="dialog">
                <img class="dclose" src="/lance/resources/image/profile/dialog_close.png" alt="" />
                <div class="dbody">
                    <span class="icon-verify"></span>
                    <span class="title">Verify Your Identity!</span>
                    <div class="text">
                        Elance uses Integrity's Global ID and Age Verification service, powered by Aristotle. You will be redirected to Aristotle's identity verification form where you will need to complete the verification form and upload a government identity document. The information you submit will be subject to <a href="#">Aristotle's privacy policy</a>.<a href="#">Learn More</a>
                        <br /><br />
                        Elance will be sending the following information to Aristotle:
                        <br /><br />
                        <img src="/lance/resources/image/pic/download.jpg" alt="" />
                        <ul>
                            <li>yx dy</li>
                            <li>123213213, 123213123123, Chongqing, Chongqing Shi, 123123213, CN</li>
                            <li>hwjwxn@163.com</li>
                        </ul>
                        <br /><br />
                        Note: Please ensure that the name on your profile matches your government ID, and your profile picture clearly shows your face.
                        <br /><br />
                        <input type="checkbox" id="chk_con" />
                        <label for="chk_con">I accept the <a href="#">terms and conditions.</a></label>
                    </div>
                    <p class="buttons">
                        <a href="#" class="btn-green">Continue</a>
                        <a href="#" class="btn-gray">Cancel</a>
                    </p>
                </div>
            </div>

        </div>
    </div>
    <script src="/lance/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/core.js" type="text/javascript"></script>
    <script src="/lance/resources/js/profile.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            $.ax("get", "user/"+User.UserName, null, function(data){
                initMainInfo(data);
            }, netWorkError);
        });
    </script>
    <jsp:include page="/WEB-INF/common/BottomBar.jsp" />
    </body>
</html>