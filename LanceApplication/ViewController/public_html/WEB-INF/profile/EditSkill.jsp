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
     <jsp:include page="/WEB-INF/common/TopBar.jsp" />
    <div class="container">
        <div class="row" id="t_con">
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
                    <h1><small>个人基本技能编辑</small></h1>
                </div>
                <div class="alert alert-info" role="alert">
                    添加个人技能为了驻才网给您推荐合适的工作机会，同时也可以让公司很容易的看上你。<br />
                    <br />
                    <b>注意：根据每个用户的级别，技能最先只能显示10条。</b>
                </div>
                
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>顺序</th>
                            <th>技能名称 <a class="btn-delete btn btn-xs btn-success" href="#" data-toggle="modal" data-target="#addSkill">添加技能</a></th>
                            <th>是否显示</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody id="gridtbody">
                        <tr class="mod-skill" style="display:none">
                            <td class="xh">1.</td>
                            <td><span class="skil">PHP5</span></td>
                            <td>
                                <input class="chk-icon" type="checkbox" value="" />
                            </td>
                            <td><a class="btn-delete btn btn-xs btn-danger" href="#">删除</a></td>
                        </tr>
                    </tbody>
                </table>
                
            </div>
            
            <div class="modal fade" id="addSkill" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">添加技能</h4>
                        </div>
                        <div class="modal-body">
                            <span>输入个人技能，按回车键添加。</span>
                            <div class="well well-sm pskills">
                                <button type="button" class="btn btn-primary btn-xs mod-skill" style="display:none;">
                                    
                                </button>
                                <input type="text" id="inp_addnew" class="inp_add_skill" placeholder="输入技能" />
                            </div>
                            
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" id="btn_confirm_add" class="btn btn-primary">保存</button>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="overlay"></div>

            <div id="dia-skill" class="dialog" style="display:none;">
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
            
    <jsp:include page="/WEB-INF/common/BottomBar.jsp" />

     <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/lance/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/lance/resources/js/bootstrap.min.js" type="text/javascript"></script>
    
    <script src="/lance/resources/js/core.js" type="text/javascript"></script>
    <script src="/lance/resources/js/control/lan_check.js" type="text/javascript"></script>
    <script type="text/javascript" src="/lance/resources/js/page/editskill.js"></script>

    </body>
</html>