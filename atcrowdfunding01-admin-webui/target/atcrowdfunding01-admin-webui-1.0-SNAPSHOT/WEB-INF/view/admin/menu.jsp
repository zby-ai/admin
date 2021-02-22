<%--
  Created by IntelliJ IDEA.
  User: 17547
  Date: 2021/2/22
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/base-head.jsp" %>
    <%@include file="../../common/main-head.jsp"%>
    <link rel="stylesheet" href="css/doc.min.css">
    <link rel="stylesheet" href="ztree/zTreeStyle.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>

    <script src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script src="crowd/menu.js"></script>
    <script type="text/javascript">
        <%@include file="../../common/main-foot-javascript.jsp"%>

        $(function () {
            // 获取属性结构的数组
            getMenuTree();
            // 添加子节点
            add_menu_node();

            // 删除节点
            delete_menu_node();

            // 更新节点
            put_menu_node();

        });

        /**
         * 添加子节点
         */
        function add_menu_node() {
            $("#treeDemo").on("click",".add_menu",function () {
                $("#menuAddModal").modal("show");
                window.pId = $(this).attr("id");
                return false;
            });
            $("#menuSaveBtn").click(function () {
                var name = $("#menuAddModal [name=name]").val();
                var url = $("#menuAddModal [name=url]").val();
                var icon = $("#menuAddModal [name=icon]:checked").val();
                $.ajax({
                    "url": "admin/menu/add",
                    "type":"post",
                    "data": {
                        "name" : name,
                        "url" : url,
                        "icon" : icon,
                        "pid" : window.pId
                    },
                    "dataType": "json",
                    "success": function (result) {
                        if (result.code == "SUCCEED") {
                            $("#treeDemo").empty();
                            getMenuTree();
                            layer.msg("添加成功！！！");
                        } else {
                            layer.msg(result.mssage);
                        }
                    },
                    "error" : function (result) {
                        layer.msg("服务器处理失败! 状态码:" + result.status + " " + ",错误信息:" + result.statusText);
                    }
                });
                // 清空模态框的数据，并关闭模态框
                $("#menuResetBtn").click();
                $("#menuAddModal").modal("hide");
            });
        }

        /**
         * 删除节点
         */
        function delete_menu_node() {
            $("#treeDemo").on("click",".delete_menu",function () {
                var name = $(this).parent().prev().text();
                var icon = $(this).parent().prev().prev().attr("class");
                $("#removeNodeSpan").html("【" +  "<span class='"+ icon+"'></span>" + " " + name + "】");
                $("#menuConfirmModal").modal("show");
                window.id = $(this).attr("id");
                return false;
            });
            $("#confirmBtn").click(function () {
                $.ajax({
                    "url": "admin/menu/delete/" + window.id,
                    "type":"post",
                    "data": {
                        "_method" : "DELETE",
                    },
                    "dataType": "json",
                    "success": function (result) {
                        if (result.code == "SUCCEED") {
                            $("#treeDemo").empty();
                            getMenuTree();
                            layer.msg("删除成功！！！");
                        } else {
                            layer.msg(result.mssage);
                        }
                    },
                    "error" : function (result) {
                        layer.msg("服务器处理失败! 状态码:" + result.status + " " + ",错误信息:" + result.statusText);
                    }
                });
                // 关闭模态框
                $("#menuConfirmModal").modal("hide");
            });
        }

        /**
         * 更新节点
         */
        function put_menu_node() {
            $("#treeDemo").on("click",".put_menu",function () {
                $("#menuEditModal").modal("show");
                window.id = $(this).attr("id");
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                var node = treeObj.getNodeByParam("id", window.id, null);
                window.pId = node.pid;
                $("#menuEditModal [name=name]").val(node.name);
                $("#menuEditModal [name=url]").val(node.url);
                $("#menuEditModal [name=icon]").val([node.icon]);
                return false;
            });
            $("#menuEditBtn").click(function () {
                var name = $("#menuEditModal [name=name]").val();
                var url = $("#menuEditModal [name=url]").val();
                var icon = $("#menuEditModal [name=icon]:checked").val();
                $.ajax({
                    "url" : "admin/menu/put",
                    "type" : "post",
                    "data" : {
                        "name" : name,
                        "url" : url,
                        "icon" : icon,
                        "id" : window.id,
                        "pid" : window.pId,
                        "_method" : "PUT"
                    },
                    "dataType" : "json",
                    "success" : function (result) {
                        if (result.code == "SUCCEED") {
                            $("#treeDemo").empty();
                            getMenuTree();
                            layer.msg("修改成功！！！");
                        } else {
                            layer.msg(result.mssage);
                        }
                    },
                    "error" : function (result) {
                        layer.msg("服务器处理失败! 状态码:" + result.status + " " + ",错误信息:" + result.statusText);
                    }
                });
                $("#menuEditModal").modal("hide");
            });

        }
    </script>
</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 菜单维护</a></div>
            </div>
            <%@include file="../../common/main-head-menubar.jsp"%>
        </div>
    </nav>
    <div class="container-fluid">
        <div class="row">
            <%@include file="../../common/main-function-list.jsp"%>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

                <div class="panel panel-default">
                    <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                    <div class="panel-body">
                        <ul id="treeDemo" class="ztree">

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="../../common/modal-menu-edit.jsp"%>
    <%@include file="../../common/modal-menu-add.jsp"%>
    <%@include file="../../common/modal-menu-confirm.jsp"%>
</body>
</html>
