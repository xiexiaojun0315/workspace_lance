<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link type="text/css" href="/lance/resources/css/bootstrap.min.css" rel="stylesheet" />
    <link type="text/css" href="/lance/resources/css/common.css" rel="stylesheet" />
    <link type="text/css" href="/lance/resources/css/login.css" rel="stylesheet" />
    <link type="text/css" href="/lance/resources/css/postjob.css" rel="stylesheet" />
    <link type="text/css" href="/lance/resources/js/jqueryui/jquery-ui.css" rel="stylesheet" />
    <!--[if lt IE 9]>
      <script src="/lance/resources/js/html5shiv.js"></script>
      <script src="/lance/resources/js/respond.js"></script>
    <![endif]-->
        <script>
            var User=${user};
            var Data=${data};
        </script>
    </head>
    <body>
    <%--<jsp:include page="/WEB-INF/common/TopBar.jsp" />--%>
    <div class="container-fluid">
        <div id="t_con" class="row">
            <div id="header">
                <div class="min-width">
                    <img class="logo" src="/lance/resources/image/logo.png" alt="" />
                    <span class="f-right help">Need help? Contact <a class="alink" href="#">Customer Support.</a></span>
                </div>
            </div>
            <div id="content">
                <div id="post_step1" class="min-width postjob">
                    <br />
                    <div class="step">
                        <div class="sp1">
                            <div class="sp-left">
                            </div>
                            <span>1. Create your job</span>
                        </div>
                        <div class="sp2">
                            <span>2. Select posting type</span>
                        </div>
                        <div class="sp2" style="z-index: 6;">
                            <span>3. Preview</span>
                        </div>
                    </div>
                    <h1 class="h1-title">
                        Post Your Job</h1>
                    <div class="font16-text">
                        Describe the job or list the skills you're looking for.</div>
                    <div class="pleft chfont">
                        <div class="mod-post">
                            <span class="font16-bold">工作名称</span><br />
                            <input id="inp_jobname" class="inp-text" type="text" /><br />
                            <span class="error"></span>
                            <span class="last">25</span>
                        </div>
                        <div class="will-change">
                            <div class="original">
                                <div class="mod-post">
                                    <span class="font16-bold">工作描述</span><br />
                                    <div class="f-right undo">
                                        取消模板
                                    </div>
                                    <textarea id="inp_detail" class="inp-text job-ta" cols="" rows=""></textarea>
                                    <br />
                                    <span class="error"></span>
                                    <span class="last">500</span>
                                </div>
                                <div class="mod-post">
                                    <select class="sel-list">
                                        <option>Add files from My Computer</option>
                                        <option>Add files from Account Files</option>
                                    </select>
                                    <a href="#" class="btn-gray">Browse</a><br />
                                    <span class="tip">100 MB limit per file.</span>
                                    <p>
                                        <input type="file" class="inp-file" />
                                    </p>
                                </div>
                                <br />
                                <div class="mod-post">
                                    <span class="font16-bold">选择工作的分类</span><br />
                                    <select id="cate-lev1" class="sel-list">
                                        <option value="-1">选择分类</option>
                                    </select>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <select id="cate-lev2" class="sel-list">
                                        <option class="mod" value="-1">选择子分类</option>
                                        <option value="1">Admin Support</option>
                                        <option value="1">Design & Multimedia</option>
                                        <option value="1">Engineering & Manufacturing</option>
                                    </select>
                                </div>
                                <br />
                                <div class="mod-post">
                                    <span class="font16-bold">Request specific skills or groups</span><span class="option">(optional)</span><br />
                                    <input placeholder="输入搜索关键字" id="inp_key_cate" type="text" disabled="disabled" value="请先选择上面的工作分类" class="inp-stext" /><br />
                                    <div class="post-container f-left list-search">
                                        <ul>
                                            <!--<li><a href="#">.NET Compact Framework</a></li>-->
                                        </ul>
                                    </div>
                                    <div class="selected-list f-left">
                                        <span class="bold-text"><b>Selected skills</b>&nbsp;&nbsp;(max 5)</span>
                                        <div class="blist list-sel-skill">
                                        </div>
                                    </div>
                                </div>
                                <br />
                                <div class="mod-post">
                                    <span class="font16-bold">Set work arrangement</span><span class="option">(optional)</span><br />
                                    <select class="sel-list wid170 sel-arrange">
                                        <option value="hourly">Hourly</option>
                                        <option value="fixed">Fixed price</option>
                                    </select>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="lbl-money">时薪范围：</span>
                                    <input style="width:50px" class="sml-input sx-start" type="text" />&nbsp;元&nbsp;-
                                    <input style="width:50px" class="sml-input sx-end" type="text" />&nbsp;元
                                    <span class="error er-sx"></span>
                                    <br />
                                    <p class="smlp">
                                        工作时间：
                                        <input style="width:50px" class="sml-input work-hour" type="text" />
                                        <span class="hw">hrs / week</span>
                                        工作周期：
                                        <input style="width:50px" class="sml-input work-zq" type="text" /><span>&nbsp;周</span>&nbsp;-
                                        <input style="width:50px" class="sml-input work-zq2" type="text" /><span>&nbsp;周</span>
                                        <span class="error er-work"></span>
                                    </p>
                                </div>
                            </div>
                            
                        </div>
                        <br />
                        <div class="mod-post">
                            <span class="font16-bold">Location, Privacy and Other Options</span><br />
                            <div class="extra-info">
                                <div class="title">
                                    Job Posting Visibility</div>
                                <input checked="checked" name="jtype" type="radio" id="jpublic" />
                                <label for="jpublic">
                                    Public—Visible to everyone in the Elance community.</label><br />
                                <input checked="checked" type="checkbox" id="jpublic_chk" />
                                <label for="jpublic_chk">
                                    Get more proposals. Allow search engines like Google to view this post.</label><br />
                                <input name="jtype" type="radio" id="jprivate" />
                                <label for="jprivate">
                                    Public—Visible to everyone in the Elance community.</label>
                                <br />
                                <br />
                                <div class="title">
                                    Position to job</div>
                                <input checked="checked" type="checkbox" id="posjob_chk" />
                                <label for="posjob_chk">是否需要到达现场进行工作</label>
                                <div class="sel-location">
                                    <select id="sel_province">
                                        <option>省份</option>
                                    </select>
                                    <select id="sel_city">
                                        <option>城市</option>
                                    </select><br />
                                    <input placeholder="请输入具体地址要求" type="text" id="inp_locDesc" />
                                    <br />
                                    <span class="error er-location"></span>
                                </div>
                                
                                <br />
                                <br />
                                <div class="title">
                                    Post This Job For</div>
                                <select id="add_days" class="sel-list">
                                    <option value="15">15 days</option>
                                    <option value="30">30 days</option>
                                    <option value="45">45 days</option>
                                    <option value="60">60 days</option>
                                </select>
                                <br />
                                <br />
                                <div class="title">
                                    Proposed Start Date</div>
                                <input checked="checked" type="radio" id="jim" name="jstart" />
                                <label for="jim">
                                    Start immediately after proposal is selected.</label><br />
                                <input type="radio" id="jsel" name="jstart" />
                                <label for="jsel">
                                    Job will start on</label>
                                <input id="startTime" type="text" class="sml-input inp_date" />
                                <span class="error er-time"></span>
                                <br />
                            </div>
                        </div>
                        <br />
                        <a data-val="posted" class="btn-green btn-mid btn-post clickable">
                            <img src="/lance/resources/image/loading.gif" alt="" />
                            Continue
                        </a> 
                        <!--<a data-val="draft" class="btn-gray btn-mid btn-post clickable">
                            <img src="/lance/resources/image/loading.gif" alt="" />
                            Save & Post Later
                        </a>-->
                    </div>
                    <div class="pright">
                        <div class="tmp-box">
                            <div class="title">
                                Need help with your post?</div>
                            <p>
                                We can help you describe what you need. Choose from popular job templates.
                            </p>
                            <a href="#" class="tmp view-tmp">View Job Templates »</a>
                        </div>
                        <div class="information">
                            <span class="intro">Your access to the world's leading site for online work is only
                                moments away. Welcome to one of the greatest and fastest growing professional communities.</span>
                            <br />
                            <h3 class="f-left count">
                                118,728</h3>
                            <span class="date"><b>New Jobs Posted </b>
                                <br />
                                Past 30 days </span>
                        </div>
                    </div>
                </div>
                
                <div id="post_step2" class="min-width postjob" style="display:none;">
                    <div class="hea-back">
                        <a id="back_step1" href="#">返回编辑工作</a>
                    </div>
                    <div class="step">
                        <div class="sp11" style="z-index: 8;">
                            <div class="sp-left-gray">
                            </div>
                            <span>1. Create your job</span>
                        </div>
                        <div class="sp1" style="z-index: 7;">
                            <span>&nbsp;&nbsp;&nbsp;2. Select posting type</span>
                        </div>
                        <div class="sp2" style="z-index: 6;">
                            <span>3. Preview</span>
                        </div>
                    </div>

                    <h1 class="t-title">Want better proposals? Upgrade your job post.</h1>

                    <div class="upsell">
                        <div class="show-left"></div>

                        <div class="col">
                            <div class="col-top"></div>
                            <div class="box">
                                <h2>Featured</h2>
                                <div class="why">
                                    Why upgrade to <b>Featured</b> ?
                                </div>
                                <div class="bullets">
                                    <div class="chk-mark"></div>
                                    <div class="txt">
                                        Your job stands out with bold graphics and preferred placement
                                    </div>
                                    <div class="chk-mark"></div>
                                    <div class="txt">
                                        Get 30% more proposals
                                    </div>
                                    <div class="chk-mark"></div>
                                    <div class="txt">
                                        Invite unlimited candidates
                                    </div>
                                    <div class="chk-mark"></div>
                                    <div class="txt">
                                        Verification Seal*
                                    </div>
                                </div>
                                <div class="but">
                                    <div class="price">$30</div>
                                    <a data-type="l1" class="btn-gray btn-select">选择</a>
                                </div>
                            </div>
                            <div class="col-bot"></div>
                        </div>

                        <div class="show-left2"></div>

                        <div class="col col2">
                            <div class="col-top"></div>
                            <div class="box">
                                <div class="col-popular"></div>
                                <h2 style="color: #437025;font-size: 30px;">Verified</h2>
                                <div class="why">
                                    Why upgrade to <b>Verified</b>?
                                </div>
                                <div class="bullets">
                                    <div class="chk-mark"></div>
                                    <div class="txt">
                                        Verification Seal* will boost your job visibility
                                    </div>
                                    <div class="chk-mark"></div>
                                    <div class="txt">
                                        Show candidates you are committed
                                    </div>
                                </div>
                                <div class="but">
                                    <div class="price">$5</div>
                                    <a data-type="l2" class="btn-green btn-select">选择</a>
                                </div>
                            </div>
                            <div class="col-bot2"></div>
                        </div>

                        <div class="show-left3"></div>

                        <div class="col col3">
                            <div class="col-top"></div>
                            <div class="box">
                                <h2>Basic</h2>
                                <div class="why">
                                    Why post on <b>Lance</b>?
                                </div>
                                <div class="bullets">
                                    <div class="chk-mark"></div>
                                    <div class="txt">
                                        Find great freelancers on the world's top online workplace
                                    </div>
                                </div>
                                <div class="but">
                                    <div class="price">Free</div>
                                    <a data-type="l3" class="btn-gray btn-select">选择</a>
                                </div>
                            </div>
                        </div>

                        <div class="show-left4"></div>

                    </div>

                    <div class="upsell-footnote">
                        * You get the "Verification Seal" for this job post and all future job posts.
                    </div>

                </div>
                
                <div id="post_step3" class="min-width postjob" style="display:none;">
                    <div class="hea-back">
                        <a id="back_step2" href="#">返回编辑工作</a>
                    </div>
                    <div class="step">
                        <div class="sp11" style="z-index: 8;">
                            <div class="sp-left-gray">
                            </div>
                            <span>1. Create your job</span>
                        </div>
                        <div class="sp2" style="z-index: 7;">
                            <span>&nbsp;&nbsp;&nbsp;2. Select posting type</span>
                        </div>
                        <div class="sp1" style="z-index: 6;">
                            <span>&nbsp;&nbsp;&nbsp;3. Preview</span>
                        </div>
                    </div>
                    <h1 class="t-title">Preview and Post</h1>

                    <table class="job-preview" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td align="right" class="col1">
                                <b>Job Title:</b>
                            </td>
                            <td class="col2">
                                ???123
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="col1">
                                <b>Job Descript:</b>
                            </td>
                            <td class="col2">
                                ???123???123???123???123???123???123???123???123???123???123???123???123???123???123???123???123???123???123???123???123
                            </td>
                        </tr>
                    </table>

                    <div class="pre-button">
                        <a data-type="posted" href="#" class="btn-green btn_cadd clickable">
                            <img src="/lance/resources/image/loading.gif" alt="" />
                            发布工作
                        </a>
                        <a data-type="draft" href="#" class="btn-gray btn_cadd clickable">
                            <img src="/lance/resources/image/loading.gif" alt="" />
                            保存草稿
                        </a>
                    </div>

                </div>
                
            </div>
            <div id="job_template">
                <span class="job-tmp-close"></span>
                <div class="tmp-container">
                    <div class="sh">
                        <h5 class="chfont">
                            工作模板</h5>
                        <div class="mod-line selected-title">
                            <span class="chfont">选中的工作模板</span>
                        </div>
                        <div class="tmp-cards selected-tmp">

                        </div>
                        <div class="mod-line">
                            <span class="chfont">选择之前使用的模板</span> <a class="chfont pre-more">更多</a>
                        </div>
                        <div class="mod-line">
                            <span class="chfont">最流行的工作模板</span> <a class="chfont por-more">更多</a>
                        </div>
                        <div class="pg-list">
                            <div class="pg-con">
                                <div class="tmp-cards pg1 f-left">
                                    <div class="card">
                                        <div class="img">
                                        </div>
                                        <div class="txt chfont">
                                            <b>Logo设计</b><br />
                                            <span>项目</span>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="img">
                                        </div>
                                        <div class="txt chfont">
                                            <b>WordPress 木板开发</b><br />
                                            <span>项目</span>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="img">
                                        </div>
                                        <div class="txt chfont">
                                            <b>Logo设计</b><br />
                                            <span>项目</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="tmp-cards pg2  f-left">
                                    <div class="card">
                                        <div class="img">
                                        </div>
                                        <div class="txt chfont">
                                            <b>Logo设计</b><br />
                                            <span>项目</span>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="img">
                                        </div>
                                        <div class="txt chfont">
                                            <b>WordPress 木板开发</b><br />
                                            <span>项目</span>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="img">
                                        </div>
                                        <div class="txt chfont">
                                            <b>Logo设计</b><br />
                                            <span>项目</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="tmp-cards pg3 f-left">
                                    <div class="card">
                                        <div class="img">
                                        </div>
                                        <div class="txt chfont">
                                            <b>Logo设计</b><br />
                                            <span>项目</span>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="img">
                                        </div>
                                        <div class="txt chfont">
                                            <b>WordPress 木板开发</b><br />
                                            <span>项目</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tmp-page">
                            <div data-pg="0px" class="pg pgon">
                                1</div>
                            <div data-pg="-330px" class="pg">
                                2</div>
                            <div data-pg="-660px" class="pg">
                                3</div>
                        </div>
                    </div>
                    <div class="popular cover" >
                        <div class="top">
                        </div>
                        <h5 class="chfont">
                            所有的工作模板列表</h5>
                        <span class="chfont ntxt">使用模板快速建立自己的工作描述。</span>
                        <p class="sel-cate">
                            <label class="chfont">
                                选择一个工作分类</label><br />
                            <select class="chfont" id="tmp_cate">
                                <option value="-1">请选择</option>
                            </select>
                        </p>
                        <div class="list-title">
                            <label class="chfont tmp-count">
                                </label>
                            <a href="#" class="chfont sel-c choose-tmp">所有 </a>
                            <ul class="menu-type">
                                <li>所有</li>
                                <li>Positions</li>
                                <li>Projects</li>
                            </ul>
                        </div>
                        <div class="pg-list">
                            <div class="pg-con list-templates">
                                
                            </div>
                        </div>

                        <div class="tmp-page list-pages">
                        </div>
                    </div>
                    <div class="pre-job-tmp cover">
                        <div class="top">
                            back
                            <div class="cback">
                            </div>
                        </div>
                        <h5 class="chfont">
                            选择之前使用的模板</h5>
                        <span class="chfont ntxt">使用模板快速建立自己的工作描述。</span>
                        <div class="tmp-cards">
                            <div class="card">
                                <div class="img">
                                </div>
                                <div class="txt chfont">
                                    <b>Logo设计</b><br />
                                    <span>项目</span>
                                </div>
                            </div>
                            <div class="card">
                                <div class="img">
                                </div>
                                <div class="txt chfont">
                                    <b>WordPress 木板开发</b><br />
                                    <span>项目</span>
                                </div>
                            </div>
                            <div class="card">
                                <div class="img">
                                </div>
                                <div class="txt chfont">
                                    <b>Logo设计</b><br />
                                    <span>项目</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tmp-top">
                </div>
                <div class="tmp-bottom">
                </div>
            </div>
        </div>
    </div>
    <%--<jsp:include page="/WEB-INF/common/BottomBar.jsp" />--%>
    <script src="/lance/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/jqueryui/jquery-ui.js" type="text/javascript"></script>
    <script src="/lance/resources/js/core.js" type="text/javascript"></script>
    <script src="/lance/resources/js/jcheck.js" type="text/javascript"></script>
    <script type="text/javascript" src="/lance/resources/js/postjob.js"></script>
    </body>
</html