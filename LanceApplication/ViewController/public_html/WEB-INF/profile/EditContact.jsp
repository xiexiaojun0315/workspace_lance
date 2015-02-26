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
        <div class="row">
            <jsp:include page="/WEB-INF/common/TopBar.jsp" />

            <div id="content" class="min-width">
                <jsp:include page="/WEB-INF/profile/ProfileMenu.jsp" />
                <div class="right-con no-border">
                    <div class="basic">
                        <h3>Edit Profile</h3>
                        <h2>Contact Information</h2>
                        <div class="information-box">
                            <div class="title">As a free member, your contact information is not publicly displayed.</div>
                            <a href="#">Upgrade your Membership.</a>
                        </div>
                        <br />
                        <p class="mod fixh">
                            <label>Display Name<span class="error-color">*</span></label><br />
                            <input id="inp_dname" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                        </p>
                        <p class="mod fixh" style="display:none;">
                            <label>Title</label><br />
                            <input id="inp_title" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                        </p>
                        <p class="mod fixh">
                            <label>Email Address</label><br />
                            <input disabled="disabled" id="inp_mail" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                        </p>
                        <p class="mod fixh">
                            <label>Phone Number</label><br />
                            <input id="inp_phone" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                        </p>
                        <p class="mod fixh">
                            <label>Website URL</label><br />
                            <input id="inp_url" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                        </p>
                        <div class="mod">
                            <label>Instant Messaging</label><br />
                            <p>
                                <input id="inp_ima" class="inp-text" type="text" value="" />
                            </p>
                            <p>
                                <input id="inp_imb" class="inp-text" type="text" value="" />
                            </p>
                            <p>
                                <input id="inp_imc" class="inp-text" type="text" value="" />
                            </p>
                            <div>
                                <div class="tip-infor">
                                    Show when you are online and allow chatting from your profile. Leave these fields blank if you don't want chat enabled or your chat IDs displayed to other Elance users.
                                </div>
                                <span class="icon-ar f-right"></span>
                            </div>
                        </div>
                        <p class="mod fixh addr">
                            <label>Address Line 1<span class="error-color">*</span></label><br />
                            <select id="sel_country">
                                <option value="44">中国</option>
                            </select>
                            <select id="sel_province" data-id="sel_city">
                                <option value="-1">省份</option>
                            </select>
                            <select id="sel_city">
                                <option class="mod" value="-1">城市</option>
                            </select>
                            <input id="inp_detail_addr" placeholder="请输入详细地址" class="inp-text" type="text" /><br />
                            <span class="error-color"></span>
                        </p>
                        <p class="mod fixh addr">
                            <label>Address Line 2</label><br />
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
                        
                        <p class="mod address_rads">
                            <label>Address Display Options</label><br />
                            <input value="all" type="radio" name="ar" checked="checked" id="ar1" /><label class="ratxt" for="ar1">Display all address fields</label><br />
                            <input value="city" type="radio" name="ar" id="ar2" /><label class="ratxt" for="ar2">Display only city, state, and zip</label><br />
                            <input value="no" type="radio" name="ar" id="ar3" /><label class="ratxt" for="ar3">No contact fields</label><br />
                        </p>
                        
                        <p class="mod contact_rads">
                            <label>Contact Information Visibility (in your profile)</label><br />
                            <input value="all" type="radio" name="co" checked="checked" id="cr1" /><label class="ratxt" for="cr1">Display all address fields</label><br />
                            <input value="city" type="radio" name="co" id="cr2" /><label class="ratxt" for="cr2">Display only city, state, and zip</label><br />
                            <input value="no" type="radio" name="co" id="cr3" /><label class="ratxt" for="cr3">No contact fields</label><br />
                        </p>
                        <p class="buttons">
                            <a id="btn_save" href="#" class="btn-green clickable">
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
    <script src="/lance/resources/js/profile.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            Lancer.profile.getStart(function(data){
                startInfor(data);
                Lancer.profile.getContactInfo(initContactInfo, data);
            });
        });
    </script>
    </body>
</html>