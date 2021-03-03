<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/6
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/base-head.jsp"%>
    <%@include file="../../common/main-head.jsp" %>
    <title>错误页面</title>
    <script type="text/javascript" src="static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("button").click(function () {
                window.history.back();
            });
        });
    </script>
</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <div>
                    <a class="navbar-brand" href="index.html" style="font-size: 32px;">尚筹网-创意产品众筹平台</a>
                </div>
            </div>
        </div>
    </nav>
    <div class="container">

        <h2 class="form-signin-heading" style="text-align: center;">
            <i class="glyphicon glyphicon-log-in"></i> 尚筹网系统消息
        </h2>
        <h3 style="text-align: center;">
            ${requestScope.exception}
<%--            ${param.exception}--%>
        </h3>
        <button style="width: 150px;margin: 50px auto 0px auto;" class="btn btn-lg btn-success btn-block">点我返回上一步</button>
    </div>
</body>
</html>
