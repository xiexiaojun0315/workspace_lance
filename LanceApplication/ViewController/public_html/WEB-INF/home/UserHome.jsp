<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link type="text/css" href="../resources/css/bootstrap.min.css" rel="stylesheet" />
    <link type="text/css" href="../resources/css/common.css" rel="stylesheet" />
    <link type="text/css" href="../resources/css/main.css" rel="stylesheet" />
    <!--[if lt IE 9]>
      <script src="../resources/js/html5shiv.js"></script>
      <script src="../resources/js/respond.js"></script>
    <![endif]-->
        <script>
            var User=${user};
            var Data=${data};
        </script>
    </head>
    <body>
    <jsp:include page="/WEB-INF/common/TopBar.jsp" />
               <div id="search-header">
                <div class="min-width">
                    <div class="con">
                        <div>
                            <input class="inp-search" type="text" />
                            <a href="#" class="hire-icon hicon-sea btn-search-job"></a>
                        </div>
                        <div class="bot">
                            <div class="nav-text f-left">
                                <a href="#">Freelancers</a>
                                <span class="sep">|</span>
                                <a href="#" class="sel">Jobs</a>
                            </div>
                            <a href="#" class="btn-green f-right">Post Your Job</a>
                        </div>
                    </div>
                </div>
            </div>


            <div id="waiting">
                <img src="../resources/image/bloading.gif" alt="" />
                <span class="bold-text">加载中...</span>
            </div>

            <div id="content" class="min-width" style="margin-top:0px;display:none;">
                <div class="left-list">
                    <ul class="list-menu">
                        <li class="sel"><a href="#">Everyone</a></li>
                        <li><a href="#">Individuals</a></li>
                        <li><a href="#">Companies</a></li>
                    </ul>
                    <ul class="list-menu">
                        <li class="sel"><a href="#">All Categories</a></li>
                        <li><a href="#">IT & Programming</a></li>
                        <li><a href="#">Design & Multimedia</a></li>
                        <li><a href="#">Writing & Translation</a></li>
                        <li><a href="#">Sales & Marketing</a></li>
                        <li><a href="#">Admin Support</a></li>
                    </ul>
                    <ul class="list-menu">
                        <li class="sel"><a href="#">All Freelancer Locations</a></li>
                        <li><a href="#">Country</a></li>
                        <li><a href="#">City, State or State</a></li>
                        <li><a href="#">ZIP</a></li>
                        <li><a href="#">Region</a></li>
                    </ul>
                    <ul class="list-menu">
                        <li class="sel"><a href="#">All Skills</a></li>
                        <li><a href="#">Specific Skills</a></li>
                    </ul>
                    <ul class="list-menu">
                        <li class="sel"><a href="#">All Feedback</a></li>
                        <li>
                            <a href="#">At least 5</a>
                            <span class="hire-icon2 icon-star"></span>
                            <span class="hire-icon2 icon-star"></span>
                            <span class="hire-icon2 icon-star"></span>
                            <span class="hire-icon2 icon-star"></span>
                            <span class="hire-icon2 icon-star"></span>
                        </li>
                        <li><a href="#">At least 4</a>
                            
                            <span class="hire-icon2 icon-star"></span>
                            <span class="hire-icon2 icon-star"></span>
                            <span class="hire-icon2 icon-star"></span>
                            <span class="hire-icon2 icon-star"></span>
                            <span class="hire-icon2 icon-star-gray"></span>
                        </li>
                        <li><a href="#">At least 3</a>
                            <span class="hire-icon2 icon-star"></span>
                            <span class="hire-icon2 icon-star"></span>
                            <span class="hire-icon2 icon-star"></span>
                            <span class="hire-icon2 icon-star-gray"></span>
                            <span class="hire-icon2 icon-star-gray"></span>
                        </li>
                    </ul>
                    <ul class="list-menu">
                        <li class="sel"><a href="#">Any Number of Reviews</a></li>
                        <li><a href="#">At least 5</a></li>
                        <li><a href="#">At least 10</a></li>
                        <li><a href="#">At least 15</a></li>
                    </ul>
                    <ul class="list-menu">
                        <li class="sel"><a href="#">Any Hourly Rate</a></li>
                        <li><a href="#">Specific Hourly Rate</a></li>
                    </ul>
                    <ul class="list-menu">
                        <li class="sel"><a href="#">No Groups</a></li>
                        <li><a href="#">Selected Group</a></li>
                    </ul>
                </div>

                <div class="right-list">
                    <div class="all-count">
                        <span class="ntext chfont fb">All Freelances<span class="ntext sml-font">(1456778 results)</span></span>
                    </div>
                    <div class="search-key">
                        Results for 'html'
                    </div>
                    <div class="list-free">
                        <div class="mod temp_mod lancer">
                            <img src="../resources/image/pic/download.jpg" alt="" width="64" height="64" class="f-left avatar" />
                            <div class="infor">
                                <div class="title">
                                    <a href="#" class="name">MobiDev</a>
                                    
                                    <span class="wlist-con">
                                        <span class="hire-icon-watch wlist"></span>
                                        <a href="#" class="wlist">Add to Watch List</a>

                                        <a class="btn-green" href="#">Hire me</a>
                                    </span>
                                </div>
                                <div class="asso">Professional Business App Development</div>
                                <div class="jt">
                                    <img src="../resources/image/country/china.png" alt="" />
                                    <span>China</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span>Rate: $29</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span>IT & Programming<a href="#" class="hire-icon-level">30</a></span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <a href="#">49 Jobs</a>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <a href="#">$2,149,700</a>
                                    <span>Earnings</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    
                                    <span class="hire-icon2 icon-star"></span>
                                    <span class="hire-icon2 icon-star"></span>
                                    <span class="hire-icon2 icon-star"></span>
                                    <span class="hire-icon2 icon-star"></span>
                                    <span class="hire-icon2 icon-star-gray"></span>
                                </div>
                                <div class="desc normal-text">
                                    Personalized world class Mobile and Web solutions for your business. Proven expertise and dozens of successfully completed projects. Over 52,000 worked hours on Marketplaces. Agile software...
                                </div>
                                <div class="bot-info">
                                    <span class="hire-icon hicon-vedio">
                                    </span>
                                    <a href="#" class="normal-text">Portfolio</a>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="txt">Skills:</span>
                                    <a href="#" class="btn-gray">IOS 6</a>
                                    <a href="#" class="btn-gray">IOS 6</a>
                                    <a href="#" class="btn-gray">IOS 6</a>
                                    <a href="#" class="btn-gray">IOS 6</a>
                                </div>
                            </div>
                        </div>
                        
                        <div class="mod temp_mod job">
                            <div class="infor chfont">
                                <div class="title">
                                    <a href="#" class="name">MobiDev</a>
                                    
                                    <span class="wlist-con">
                                        <span class="hire-icon-watch wlist"></span>
                                        <a href="#" class="wlist">Add to Watch List</a>

                                        <a class="btn-green" href="#">Hire me</a>
                                    </span>
                                </div>
                                <div class="jt jthour">
                                    <span class="s1 fb">Hourly rate: Not true</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="s2">Duration: $29</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="s3">Posted: 12 minute ago</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="s4">Ended:14d,23hour</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <a class="s5">0 proposle</a>
                                </div>
                                <div class="jt jtfix">
                                    <span class="s1 fb">Hourly rate: Not true</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="s2">Duration: $29</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="s3">Posted: 12 minute ago</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="s4">Ended:14d,23hour</span>
                                </div>
                                <div class="desc normal-text">
                                    Personalized world class Mobile and Web solutions for your business. Proven expertise and dozens of successfully completed projects. Over 52,000 worked hours on Marketplaces. Agile software...
                                </div>
                                <div class="bot-info">
                                    <span class="normal-text s21"><span>Category: </span></span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="normal-text s22"><span>SubCategory: </span></span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="hire-icon2 icon-star"></span>
                                    <span class="hire-icon2 icon-star"></span>
                                    <span class="hire-icon2 icon-star"></span>
                                    <span class="hire-icon2 icon-star"></span>
                                    <span class="hire-icon2 icon-star-gray"></span>
                                </div>
                                <div class="btm">
                                    <table border="0" class="normal-text chfont">
                                        <tr>
                                            <td class="fb">
                                                类型：
                                            </td>
                                            <td class="job-lx" style="width:150px;">
                                                XXX
                                            </td>
                                            <td class="fb">
                                                技能：
                                            </td>
                                            <td class="job-skill">
                                                <a href="#" class="btn-gray">IOS 6</a>
                                                <a href="#" class="btn-gray">IOS 6</a>
                                                <a href="#" class="btn-gray">IOS 6</a>
                                                <a href="#" class="btn-gray">IOS 6</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="fb">
                                                评级：
                                            </td>
                                            <td class="job-pj">
                                                XXX
                                            </td>
                                            <td class="fb">
                                                地点：
                                            </td>
                                            <td class="job-loc">
                                                XXX
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="pages-list">
                        <ul>
                            <li><a href="#"><span>«</span>&nbsp;First</a></li>
                            <li><a href="#"><span>«</span>&nbsp;Back</a></li>
                            <li><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li class="sel"><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li><a href="#">Next&nbsp;<span>»</span></a></li>
                            <li><a href="#">Last&nbsp;<span>»</span></a></li>
                        </ul>
                    </div>

                </div>



                <div class="oppor" style="display:none;">
                    <h1>We found some jobs you may like ... <br />
                        <span>Submit your proposal today!</span>
                    </h1>
                    
                    <div class="nav-text">
                        <a href="#">My Jobs</a>
                        <span class="sep">&nbsp;</span>
                        <a href="#" class="sel">Opportunities</a>
                    </div>
                    <br />
                    <div class="title-bar">
                        <div class="dv-chk">
                            <span class="icon-checkbox-nor"></span>
                        </div>
                        <div class="nav">
                            <span class="title">View:</span>
                            <a href="#" class="sel">All</a>
                            <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                            <a href="#">Invited</a>
                            <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                            <a href="#">Recommended</a>
                        </div>
                        <div class="choose">
                            Job Status:&nbsp;&nbsp;&nbsp;&nbsp;
                            <span>Hiring Open</span>
                            <div class="lists">
                                <ul>
                                    <li><a href="#">All</a></li>
                                    <li><a href="#">Hiring Open</a></li>
                                    <li><a href="#">Selecting Candidate</a></li>
                                    <li><a href="#">Working</a></li>
                                    <li><a href="#">Other</a></li>
                                </ul>
                            </div>
                        </div>

                    </div>

                    <div class="job-list">
                        <div class="mod">
                            <span class="icon-checkbox-nor f-left"></span>
                            <div class="mod-content">
                                <div>
                                    <span class="title">Facebook API/Open Graph protocol integration for existing website</span>
                                    <span class="modclose f-right"></span>
                                </div>
                                <div class="infor">
                                    <span class="bold-text">Fixed Price: Not Sure</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="normal-text">Web Programming </span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="normal-text">Posted: Sep 11, 2014 </span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <a href="#" class="font12">9 Proposals</a>
                                    <div class="recomm">
                                        <div class="content">
                                            <div class="arrow"><div class="arrow-after"></div></div>
                                            <div class="txt">
                                                <img src="../resources/image/lance/pricing.png" alt="" />
                                                <div class="preview">
                                                    <p><span class="bt">Low</span>$<span class="squrt">&nbsp;</span></p>
                                                    <p><span class="bt">Avg</span>$<span class="squrt">&nbsp;</span></p>
                                                    <p><span class="bt">High</span>$<span class="squrt">&nbsp;</span></p>
                                                </div>
                                                <a href="#" class="btn-green">Go Premium</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="detail">
                                    <span class="rating rating-3"></span>
                                    <a href="#" class="font12">M****est</a>
                                    ,&nbsp;&nbsp;&nbsp;
                                    <img src="../resources/image/country/china.png" alt="" />
                                    <br />
                                    <p class="txt">
                                        We provide an online brain health tool that collects data using an online form, and then scores the user responses to create a customized report. We are currently trying a new *prototype* version of the test that provid..
                                        <span class="icons-control icon-down"></span>
                                    </p>
                                    <div class="skills">
                                        Skills:
                                        <a href="#">Facebook Development</a>, <a href="#">PHP</a>, <a href="">Facebook Marketing</a>, <a href="#">Facebook Javascript (FBJS)</a>
                                    </div>
                                </div>
                                <div class="buttons">
                                    <span class="tag">RECOMMENDED</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="normal-text">Hiring Open</span>
                                    <p>
                                        <a href="#" class="btn-gray">Apply</a>
                                        <a href="#" class="btn-gray">Delete</a>
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="mod">
                            <span class="icon-checkbox-nor f-left"></span>
                            <div class="mod-content">
                                <div>
                                    <span class="title">Facebook API/Open Graph protocol integration for existing website</span>
                                    <span class="modclose f-right"></span>
                                </div>
                                <div class="infor">
                                    <span class="bold-text">Fixed Price: Not Sure</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="normal-text">Web Programming </span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="normal-text">Posted: Sep 11, 2014 </span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <a href="#" class="font12">9 Proposals</a>
                                    <div class="recomm">
                                        <div class="content">
                                            <div class="arrow"><div class="arrow-after"></div></div>
                                            <div class="txt">
                                                <img src="../resources/image/lance/pricing.png" alt="" />
                                                <div class="preview">
                                                    <p><span class="bt">Low</span>$<span class="squrt">&nbsp;</span></p>
                                                    <p><span class="bt">Avg</span>$<span class="squrt">&nbsp;</span></p>
                                                    <p><span class="bt">High</span>$<span class="squrt">&nbsp;</span></p>
                                                </div>
                                                <a href="#" class="btn-green">Go Premium</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="detail">
                                    <span class="rating rating-3"></span>
                                    <a href="#" class="font12">M****est</a>
                                    ,&nbsp;&nbsp;&nbsp;
                                    <img src="../resources/image/country/china.png" alt="" />
                                    <br />
                                    <p class="txt">
                                        We provide an online brain health tool that collects data using an online form, and then scores the user responses to create a customized report. We are currently trying a new *prototype* version of the test that provid..
                                        <span class="icons-control icon-down"></span>
                                    </p>
                                    <div class="skills">
                                        Skills:
                                        <a href="#">Facebook Development</a>, <a href="#">PHP</a>, <a href="">Facebook Marketing</a>, <a href="#">Facebook Javascript (FBJS)</a>
                                    </div>
                                </div>
                                <div class="buttons">
                                    <span class="tag">RECOMMENDED</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="normal-text">Hiring Open</span>
                                    <p>
                                        <a href="#" class="btn-gray">Apply</a>
                                        <a href="#" class="btn-gray">Delete</a>
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="mod">
                            <span class="icon-checkbox-nor f-left"></span>
                            <div class="mod-content">
                                <div>
                                    <span class="title">Facebook API/Open Graph protocol integration for existing website</span>
                                    <span class="modclose f-right"></span>
                                </div>
                                <div class="infor">
                                    <span class="bold-text">Fixed Price: Not Sure</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="normal-text">Web Programming </span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="normal-text">Posted: Sep 11, 2014 </span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <a href="#" class="font12">9 Proposals</a>
                                    <div class="recomm">
                                        <div class="content">
                                            <div class="arrow"><div class="arrow-after"></div></div>
                                            <div class="txt">
                                                <img src="../resources/image/lance/pricing.png" alt="" />
                                                <div class="preview">
                                                    <p><span class="bt">Low</span>$<span class="squrt">&nbsp;</span></p>
                                                    <p><span class="bt">Avg</span>$<span class="squrt">&nbsp;</span></p>
                                                    <p><span class="bt">High</span>$<span class="squrt">&nbsp;</span></p>
                                                </div>
                                                <a href="#" class="btn-green">Go Premium</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="detail">
                                    <span class="rating rating-3"></span>
                                    <a href="#" class="font12">M****est</a>
                                    ,&nbsp;&nbsp;&nbsp;
                                    <img src="../resources/image/country/china.png" alt="" />
                                    <br />
                                    <p class="txt">
                                        We provide an online brain health tool that collects data using an online form, and then scores the user responses to create a customized report. We are currently trying a new *prototype* version of the test that provid..
                                        <span class="icons-control icon-down"></span>
                                    </p>
                                    <div class="skills">
                                        Skills:
                                        <a href="#">Facebook Development</a>, <a href="#">PHP</a>, <a href="">Facebook Marketing</a>, <a href="#">Facebook Javascript (FBJS)</a>
                                    </div>
                                </div>
                                <div class="buttons">
                                    <span class="tag">RECOMMENDED</span>
                                    <span class="sep">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                                    <span class="normal-text">Hiring Open</span>
                                    <p>
                                        <a href="#" class="btn-gray">Apply</a>
                                        <a href="#" class="btn-gray">Delete</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="right-infor f-right">
                        <div class="widget">
                            <div class="title">Your Work Category</div>
                            <p class="normal-text">
                                We'll send you jobs and clients in the following category:
                            </p>
                            <span class="bold-text">IT & Programming <a href="#">Change</a></span>
                        </div>

                        <div class="box-infor">
                            <div class="title">Getting Started Tips</div>
                            <ul>
                                <li><img src="../resources/image/lance/lightbulb.png" alt="" /><a href="#">How to Create a Great Profile</a></li>
                                <li><img src="../resources/image/lance/lightbulb.png" alt="" /><a href="#">Making Every Proposal Count</a></li>
                                <li><img src="../resources/image/lance/lightbulb.png" alt="" /><a href="#">7 Tips to Win More Jobs on Elance</a></li>
                                <li><img src="../resources/image/lance/lightbulb.png" alt="" /><a href="#">Learn More From the official Freelancer Guide</a></li>
                                <li><img src="../resources/image/lance/lightbulb.png" alt="" /><a href="#">Attend a Free Webinar & Learn More From the Pros</a></li>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>
    <script src="../resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../resources/js/core.js" type="text/javascript"></script>
    <script src="../resources/js/profile.js" type="text/javascript"></script>
    <script src="../resources/js/searchjob.js" type="text/javascript"></script>
            
            
    <jsp:include page="/WEB-INF/common/BottomBar.jsp" />
    </body>
</html>