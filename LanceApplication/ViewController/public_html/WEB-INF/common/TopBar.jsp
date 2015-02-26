<%@ page contentType="text/html;charset=UTF-8"%>
<div class="container-fluid">
    <div class="row">
        <div id="header">
            <div class="min-width">
                <img class="logo" src="/lance/resources/image/logo.png" alt=""/>
                <div class="setting">
                    <div tabindex="-1" class="nav_sprite bold nav">
                        <span id="gbuname"></span>
                        <div class="dialog-small">
                            <dl>
                                <dd class="first">
                                    <a href="#">我的驻才网</a>
                                </dd>
                                <dd>
                                    <a href="#">个人信息</a>
                                </dd>
                                <dd>
                                    <a href="#">设置</a>
                                </dd>
                                <dd>
                                    <a href="#">Membership</a>
                                </dd>
                            </dl>
                            <div class="sep-dot"></div>
                            <dl class="dquit">
                                <dd>
                                    <span class="nav_sprite logout"></span>
                                    <a href="/lance/adfAuthentication?logout=true&amp;end_url=/lance/login.htm">退出登录</a>
                                </dd>
                            </dl>
                        </div>
                    </div>
                    <span>|</span>
                    <div href="#" class="nav">消息(1)</div>
                    <span>|</span>
                    <div tabindex="-1" href="#" class="nav_sprite nav">
                        帮助
                        <div class="dialog-small">
                            <dl>
                                <dd>
                                    <a href="#">联系驻才网客服</a>
                                </dd>
                                <dd>
                                    <a href="#">帮助中心</a>
                                </dd>
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
                         
                        <a href="/lance/pages/MyHome">我的驻才网</a>
                    </li>
                    <li>
                        <a href="#">找自由工作者</a>
                        <div class="sub-menu">
                            <a href="/lance/pages/Search">搜索</a>
                             
                            <a href="#">浏览</a>
                             
                            <a href="/lance/pages/jobs/PostNewJob">发布招聘信息</a>
                             
                            <a href="#">引荐自由工作者</a>
                             
                            <a href="/lance/pages/profile/Overview">个人信息</a>
                             
                            <a href="#">关注列表</a>
                        </div>
                    </li>
                    <li>
                        <a href="#">找项目</a>
                        <div class="sub-menu">
                            <a href="/lance/pages/Search">搜索</a>
                             
                            <a href="#">浏览</a>
                             
                            <a href="/lance/pages/Search">推荐给我的机会</a>
                             
                            <a href="/lance/pages/Search">推荐给团队的机会</a>
                             
                            <a href="/lance/pages/profile/Overview">我的信息</a>
                             
                            <a href="#">关注列表</a>
                        </div>
                    </li>
                    <li>
                        <a href="#">管理</a>
                        <div class="sub-menu">
                            <a href="#">状态</a>
                             
                            <a href="/lance/project/Contract">合同</a>
                             
                            <a href="#">里程碑</a>
                             
                            <a href="#">账户</a>
                             
                            <a href="#">团队信息</a>
                        </div>
                    </li>
                    <li>
                        <a href="#">帮助</a>
                        <div class="sub-menu">
                            <a href="#">使用手册</a>
                             
                            <a href="#">关于驻才网</a>
                             
                            <a href="#">开放API</a>
                             
                            <a href="#">联系客服</a>
                        </div>
                    </li>
                </ul>
                <div class="search-bar">
                    <div class="search" tabindex="-1">
                        <span class="title">项目
                            <span class="icon-sj"></span></span>
                        <div class="drop-list">
                            <span class="dlist">项目
                                <span class="icon-sj"></span></span>
                             
                            <span class="dlist">作品展示</span>
                             
                            <span class="dlist">自由工作者</span>
                             
                            <span class="dlist">My Jobs</span>
                        </div>
                        <input type="text" value=""/>
                    </div>
                    <a href="#" class="btn-gray">Go</a>
                     
                    <span class="sep-sea"></span>
                     
                    <a href="/lance/pages/jobs/PostNewJob" class="btn-green">发布招聘信息</a>
                </div>
            </div>
        </div>
        <script>
          document.getElementById("gbuname").innerHTML = User.UserName;
        </script>
    </div>
</div>