<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/7
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
    <head>
        <%@include file="../../common/base-head.jsp"%>

        <%@include file="../../common/main-meta.jsp"%>
            <script src="jquery/jquery-2.1.1.min.js"></script>
            <script src="bootstrap/js/bootstrap.min.js"></script>

            <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
            <link rel="stylesheet" href="css/font-awesome.min.css">
            <link rel="stylesheet" href="css/login.css">
            <style>

            </style>
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
                </div>
            </div>
        </nav>

        <div class="container">

            <form class="form-signin" role="form" action="admin/do/login.html" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 管理员登录</h2>
                <div class="form-group has-success has-feedback">
                    <p>${requestScope.exception.message}</p>
                    <p>${requestScope.loginMassage}</p>
                    <p>${SPRING_SECURITY_LAST_EXCEPTION.message }</p>
                    <input type="text" name="loginAcct" class="form-control" id="inputSuccess4" placeholder="请输入登录账号" autofocus value="${requestScope.loginAcct}">
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                </div>
                <div class="form-group has-success has-feedback">
                    <input type="text" name="userPswd" class="form-control" id="inputSuccess4" placeholder="请输入登录密码" style="margin-top:10px;">
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <input type="submit" value="登录" class="btn btn-lg btn-success btn-block"/>
            </form>
        </div>
    </body>
</html>
