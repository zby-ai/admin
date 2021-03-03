<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/23
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../../common/base-head.jsp" %>
    <%@include file="../../common/main-head.jsp" %>
    <link rel="stylesheet" href="css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>
    <script type="text/javascript">
        <%@include file="../../common/main-foot-javascript.jsp" %>
        $(function () {
            // 添加到已分配
            $(".glyphicon-chevron-right").click(function () {
                $("select:eq(0)>option:checked").appendTo($("select:eq(1)"));
            });
            // 添加到未分配
            $(".glyphicon-chevron-left").click(function () {
                $("select:eq(1)>option:checked").appendTo($("select:eq(0)"));
            });
            // 执行分配
            $(".btn-primary").click(function () {
                var roles = $("select:eq(0)>option[id]");
                // 删除移出的角色
                if (roles.length > 0) {

                    var roleIds = [];
                    var adminId = $("input:hidden").val();
                    var adminIdArr = [];
                    adminIdArr.push(adminId);
                    roles.each(function () {
                        roleIds.push($(this).attr("id"));
                    });
                    var json = {
                        "roleIds" : roleIds,
                        "adminId" : adminIdArr
                    };
                    var jsonString = JSON.stringify(json);
                    ajax_delete_admin_role(jsonString);
                }
                // 添加新添加的角色
                $("select:eq(1)>option:not([id])").prop("selected",true);
                $("select:eq(1)>option[id]").prop("selected",false);
                return true;
            });

            $("#back_data_list").click(function () {
                window.history.back();
                return false;
            });
        });
        // 删除角色
        function ajax_delete_admin_role(jsonString) {
            $.ajax({
                "url" : "admin/authority/delete/role",
                "data" : jsonString,
                "contentType" : "application/json;charset=UTF-8",
                "type" : "post",
                "dataType" : "json",
                "async" : false,
                "success" : function (result) {
                    if (result.code != "SUCCEED") {
                        layer.msg(result.mssage);

                    }
                },
                "error" : function (result) {
                    layer.msg("服务器处理失败! 状态码:" + result.status + " " + ",错误信息:" + result.statusText);
                }
            });
        }
    </script>
</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 用户维护</a></div>
            </div>
            <%@include file="../../common/main-head-menubar.jsp" %>
        </div>
    </nav>
    <div class="container-fluid">
        <div class="row">
            <%@include file="../../common/main-function-list.jsp" %>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <ol class="breadcrumb">
                    <li><a href="#">首页</a></li>
                    <li id="back_data_list" ><a href="#">数据列表</a></li>
                    <li class="active">分配角色</li>
                </ol>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <form role="form" class="form-inline" action="admin/authority/add/role" method="post">
                            <input type="hidden" name="adminId" value="${requestScope.adminId}">
                            <input type="hidden" name="pageNum" value="${requestScope.pageNum}">
                            <input type="hidden" name="querycondition" value="${requestScope.querycondition}">
                            <div class="form-group">
                                <label for="exampleInputPassword1">未分配角色列表</label><br>
                                <select class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
                                    <c:forEach items="${requestScope.noOwnRoleList}" var="role">
                                        <option value="${role.id}">${role.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <ul>
                                    <li class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                    <br>
                                    <li class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                                </ul>
                            </div>
                            <div class="form-group" style="margin-left:40px;">
                                <label for="exampleInputPassword1">已分配角色列表</label><br>
                                <select class="form-control" multiple size="10" style="width:100px;overflow-y:auto;" name="roleIds">
                                        <c:forEach items="${requestScope.ownRoleList}" var="role">
                                            <option value="${role.id}" id="${role.id}">${role.name}</option>
                                        </c:forEach>
                                </select>
                                <button type="submit" class="btn btn-primary">执行分配</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
