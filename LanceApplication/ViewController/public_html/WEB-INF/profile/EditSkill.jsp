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
        <div class="row" id="t_con" style="display:none;">
            <jsp:include page="/WEB-INF/common/TopBar.jsp" />

            <div id="content" class="min-width">
                <jsp:include page="/WEB-INF/profile/ProfileMenu.jsp" />
                <div class="right-con no-border">
                    <div class="basic">
                        <h3>Edit Profile</h3>
                        <p class="ment">
                            <br />
                            Add skills to your profile so that clients can find and select you for jobs. Skills are also used to send you matching jobs. <a href="#">More Info</a>
                            <br /><br />
                            Note: Based on your membership plan, first 10 skills will be shown on your profile. <a href="#">More Info</a>
                        </p>
                        <br />
                        <table class="tab-skills" border="0" cellpadding="0" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Order</th>
                                    <th><a id="btn_addSkill" href="#" class="btn-green">Add Skill(s)</a></th>
                                    <th>Tested</th>
                                    <th>Display</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="gridtbody">
                                <tr class="mod-skill">
                                    <td class="xh">1.</td>
                                    <td><span class="skil">PHP5</span></td>
                                    <td><span class="no-test">not yet tested</span></td>
                                    <td><span class="chk-icon"></span></td>
                                    <td><a class="btn-delete" href="#">Delete</a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>


         <jsp:include page="/WEB-INF/common/BottomBar.jsp" />
         
         
            <div class="overlay"></div>

            <div id="dia-skill" class="dialog">
                <img class="dclose" src="/lance/resources/image/profile/dialog_close.png" alt="" />
                <div class="dbody">
                    <span class="title">Add Skill(s)</span>
                    <div class="intro">Add skills to show on your profile. Select from predefined list where possible.</div>
                    <ul class="new-add-skills">
                        <li class="mod-skill norli"><span class="skil">PHP5<a href="#">X</a></span></li>
                        <!--<li><span class="skil green">PHP5<a href="#">X</a></span></li> -->
                        <li><input id="inp_addnew" placeholder="Enter new skill" class="add-skill" type="text" value="" /></li>
                    </ul>
                    <div class="result-search">
                        <div class="mod">JAVA</div>
                        <div class="mod">JAVA</div>
                        <div class="mod">JAVA</div>
                        <div class="mod">JAVA</div>
                    </div>
                    <p class="buttons">
                        <a id="btn_confirm_add" href="#" class="btn-green">Add Skill(s)</a>
                        <a href="#" class="btn-gray">Cancel</a>
                    </p>
                </div>
            </div>

        </div>
    </div>
    <script src="/lance/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/jquery.dragsort-0.5.2.min.js" type="text/javascript"></script>
    <script src="/lance/resources/js/core.js" type="text/javascript"></script>
    <script src="/lance/resources/js/profile.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            Lancer.profile.getStart(function(data){
                startInfor(data);
                Lancer.profile.getSkillsInfo(initSkill, data);
            });
        });
    </script>
    </body>
</html>