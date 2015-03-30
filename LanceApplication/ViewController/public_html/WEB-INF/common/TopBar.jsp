<%@ page contentType="text/html;charset=UTF-8"%>
<div class="lan-header-main">
    <div class="container">
        <img class="pull-left logo" src="\lance\resources\image\common\logo.png" alt=""/>
        <ol class="breadcrumb pull-right">
            <li>
                <a class="lan-font-bold" data-toggle="dropdown" aria-expanded="false" href="#">
                    <span id="gbuname"></span></a>
                 
                <span class="caret"></span>
                <ul class="dropdown-menu" role="menu">
                    <li>
                        <a href="#">个人信息</a>
                    </li>
                    <li>
                        <a href="#">个人简历</a>
                    </li>
                    <li>
                        <a href="#">个人设置</a>
                    </li>
                    <li role="presentation" class="divider"></li>
                    <li>
                        <a href="/lance/adfAuthentication?logout=true&amp;end_url=/lance/login.htm">退出</a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">收件箱
                    <span class="label label-danger">1</span></a>
            </li>
            <li class="active">
                <a class="lan-font-bold" data-toggle="dropdown" aria-expanded="false" href="#">帮助</a>
                 
                <span class="caret"></span>
            </li>
        </ol>
    </div>
    <script>
      document.getElementById("gbuname").innerHTML = User.UserName;
    </script>
</div>
<div class="lan-main-menu">
    <div class="container">
        <ul class="nav navbar-nav pull-left">
            <li class="active">
                <a href="/lance/pages/MyHome">我的驻才网
                    <span class="sr-only">(current)</span></a>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">找人
                    <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li>
                        <a href="/lance/pages/Search">搜索</a>
                    </li>
                    <li>
                        <a href="#">浏览</a>
                    </li>
                    <li>
                        <a href="/lance/pages/jobs/PostNewJob">发布招聘信息</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">引荐自由工作者</a>
                    </li>
                    <li>
                        <a href="/lance/pages/profile/Overview">个人信息</a>
                    </li>
                    <li>
                        <a href="#">关注列表</a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">找项目
                    <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li>
                        <a href="/lance/pages/Search">搜索</a>
                    </li>
                    <li>
                        <a href="#">浏览</a>
                    </li>
                    <li>
                        <a href="/lance/pages/Search">推荐给我的机会</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="/lance/pages/Search">推荐给团队的机会</a>
                    </li>
                    <li>
                        <a href="/lance/pages/profile/Overview">我的信息</a>
                    </li>
                    <li>
                        <a href="#">关注列表</a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">管理
                    <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li>
                        <a href="#">状态</a>
                    </li>
                    <li>
                        <a href="/lance/project/Contract">合同</a>
                    </li>
                    <li>
                        <a href="#">里程碑</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">账户</a>
                    </li>
                    <li>
                        <a href="#">团队信息</a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">帮助
                    <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li>
                        <a href="#">使用手册</a>
                    </li>
                    <li>
                        <a href="#">关于驻才网</a>
                    </li>
                    <li>
                        <a href="#">开放API</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">联系客服</a>
                    </li>
                </ul>
            </li>
        </ul>
        <div class="pull-right search">
            <div class="input-group input-group-sm">
                <span data-toggle="dropdown" role="button" aria-expanded="false" class="input-group-addon"
                      id="sizing-addon3">自由人 
                    <span class="caret"></span></span>
                 
                <input type="text" class="form-control" placeholder="搜索" aria-describedby="sizing-addon3"/>
                <ul class="dropdown-menu" role="menu">
                    <li>
                        <a href="#">自由人</a>
                    </li>
                    <li>
                        <a href="#">工作</a>
                    </li>
                    <li>
                        <a href="#">团队</a>
                    </li>
                </ul>
            </div>
            <button type="button" class="btn btn-primary btn-small">搜索</button>
             
            <button type="button" class="btn btn-success btn-middle">发布工作信息</button>
        </div>
    </div>
</div>
<br/>