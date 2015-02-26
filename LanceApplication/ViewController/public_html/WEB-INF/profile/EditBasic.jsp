<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <script>
            var User=${user};
            var Data=${data};
        </script>
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
        <div class="row" id="waiting">
            <img src="/lance/resources/image/bloading.gif" alt="" />
            <span class="bold-text">加载中...</span>
        </div>
        <div id="t_con" class="row" style="display:none;">
           <jsp:include page="/WEB-INF/common/TopBar.jsp" />

            <div id="content" class="min-width">
                <jsp:include page="/WEB-INF/profile/ProfileMenu.jsp" />
                <div class="right-con no-border">
                    <div class="basic">
                        <h3>Edit Profile</h3>
                        <h2>Basic Information</h2>
                        <br />
                        <span class="error-color">*Required</span>
                        <br /><br />
                        <p class="mod">
                            <label>Display Name<span class="error-color">*</span></label><br />
                            <input id="inp_dname" class="inp-text" type="text" /><br />
                            <span class="error-color error"></span>
                        </p>
                        <div class="mod">
                            <label>Display Name<span class="error-color">*</span></label><br />
                            <input id="inp_dname2" class="inp-text" disabled="disabled" type="text" />
                            <p class="chk-info">
                                <input class="inp-chk" type="checkbox" id="chk_only" />
                                <label class="lbl-chk" for="chk_only">Display last initial only</label><br />
                                <i>Display name can only be customized if you have a 'Company Membership'.</i>
                            </p>
                        </div>
                        <p class="mod">
                            <label>Tagline<span class="error-color">*</span></label><br />
                            <input id="inp_tagline" class="inp-text" type="text" /><br />
                            <span class="error-color error"></span>
                            <span class="last">50</span>
                        </p>
                        <div class="mod">
                            <label>Hourly Rate (optional)</label>
                            <p>
                                <span class="hattr">My hourly rate:</span>
                                <span class="hval">$ <input id="inp_hour" type="text" /> /hr</span>
                                <br /><br />
                                <span class="hattr">What Elance will charge clients:<br />(after Elance fees)</span>
                                <span class="hval">$ <input id="inp_charge" type="text" /> /hr</span>
                            </p>
                        </div>
                        <p class="mod">
                            <label>YouTube Video URL (optional)</label><br />
                            <input id="inp_url" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                            <span class="last">50</span>
                        </p>
                        <p class="mod">
                            <label>Overview<span class="error-color">*</span></label><br />
                            <span class="sm">Add a few sentences about your background, what you offer, and why clients should hire you.</span>
                            <textarea id="inp_over" rows="" cols=""></textarea>
                            <br />
                            <span class="error-color"></span>
                            <span class="last tlast" style="margin-left:218px;">1000</span>
                        </p>
                        <p class="mod">
                            <label>Service Description</label><br />
                            <textarea id="inp_service" rows="" cols=""></textarea>
                            <br />
                            <span class="error-color"></span>
                            <span class="last tlast" style="margin-left:218px;">8000</span>
                        </p>
                        <p class="mod">
                            <label>Payment Terms</label><br />
                            <textarea id="inp_payment" rows="" cols=""></textarea>
                            <br />
                            <span class="error-color"></span>
                            <span class="last tlast" style="margin-left:218px;">4000</span>
                        </p>
                        <p class="mod">
                            <label>Keywords</label><br />
                            <textarea id="inp_keyword" rows="" cols=""></textarea>
                            <br />
                            <span class="t-info">No more than 10, each separated by a comma</span>
                            <span class="last tlast">500</span>
                        </p>
                        <p class="buttons">
                            <a id="btn_saveBasic" class="btn-green clickable">
                                <img src="/lance/resources/image/loading.gif" alt="" />
                                Save
                            </a>
                            <a href="#" class="btn-gray">Cancel</a>
                        </p>
                    </div>
                </div>
            </div>


            <jsp:include page="/WEB-INF/common/BottomBar.jsp" />

        </div>
    </div>
            


    <script src="/lance/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/core.js" type="text/javascript"></script>
    <script src="/lance/resources/js/jcheck.js" type="text/javascript"></script>
    <script src="/lance/resources/js/profile.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            Lancer.profile.getBasicInfo(setEditInfo);
        });
    </script>
    </body>
</html>