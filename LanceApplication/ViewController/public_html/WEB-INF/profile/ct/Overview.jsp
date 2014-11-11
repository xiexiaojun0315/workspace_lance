<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ClientOverview</title>
        <link type="text/css" href="/lance/resources/css/bootstrap.min.css" rel="stylesheet" />
        <link type="text/css" href="/lance/resources/css/common.css" rel="stylesheet" />
        <link type="text/css" href="/lance/resources/css/main.css" rel="stylesheet" />
        <script>
            var User=${user};
            var Data=${data};
        </script>
    </head>
    <body>
    <jsp:include page="/WEB-INF/common/TopBar.jsp" ></jsp:include>
    <jsp:include page="/WEB-INF/common/LancerMenu.jsp" ></jsp:include>
    </body>
</html>