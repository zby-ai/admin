<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/7
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <%@include file="../../common/base-head.jsp" %>
    <%@include file="../../common/main-head.jsp" %>
    <%@include file="../../common/main-style.jsp" %>

    <script type="text/javascript">
        $(function () {
            $(".list-group-item").click(function(){
                if ( $(this).find("ul") ) {
                    $(this).toggleClass("tree-closed");
                    if ( $(this).hasClass("tree-closed") ) {
                        $("ul", this).hide("fast");
                    } else {
                        $("ul", this).show("fast");
                    }
                }
            });
        });
        $("tbody .btn-success").click(function(){
            window.location.href = "assignRole.html";
        });
        $("tbody .btn-primary").click(function(){
            window.location.href = "edit.html";
        });
    </script>
    <script type="text/javascript">
        /**
         * 修改登录账号信息
         */
        $(function () {
            $("button[button_type=button_update_admin]").click(function () {
                var adminId = $(this).attr("admin_Id");
                var path = commonly_used_function("admin/get/update/massage",adminId);
                window.location = path;
            });

            /**
             * 判断查询条件
             */
            $("#query_submit_button").click(function () {
                var queryCondition = $("input[name=queryCondition]").val();
                if (queryCondition == '') {
                    alert("查询条件不能为空");
                    return false;
                }
                return true;
            });

            /**
             * 单个删除
             */
            $("button[button_type=button_delete_admin_by_id]").click(function () {
                if (confirm("确定要删除【" + $(this).parent().parent().find("td:eq(3)").text() + "】吗？")) {
                    var adminId = $(this).attr("admin_Id");
                    var path = commonly_used_function("admin/delete",adminId);
                    $("#admin_delete_by_id_form").attr("action",path).submit();
                }
            });
            /**
             * 全选全不选
             */
            $("#admin_batch_delete_checkbox").click(function () {
                $(".admin_batch_delete_checkbox").prop("checked",$(this).prop("checked"));
            });

            /**
             * 批量删除复选框
             */
            $(".admin_batch_delete_checkbox").click(function () {
                var allCheckboxLength = $(".admin_batch_delete_checkbox").length;
                var pitchOncheckboxLength = $(".admin_batch_delete_checkbox:checked").length;
                if (allCheckboxLength == pitchOncheckboxLength){
                    $("#admin_batch_delete_checkbox").prop("checked","checked");
                }else {
                    $("#admin_batch_delete_checkbox").prop("checked","");
                }
            });

            /**
             * 批量删除
             */
            $("#admin_batch_delete_button").click(function () {
                var userNames = "";
                $(".admin_batch_delete_checkbox:checked").each(function () {
                    userNames += $(this).parent().next().next().text() + ",";
                });
                userNames = userNames.substring(0,userNames.length - 1);
                if (confirm("确定要删除【" + userNames + "】吗？")) {
                    var admins = "";
                    $(".admin_batch_delete_checkbox:checked").each(function() {
                        admins += $(this).attr("admin_id") + ",";
                    });
                    admins = admins.substring(0,admins.length - 1);
                    var path = commonly_used_function("admin/delete",admins);
                    $("#admin_delete_by_id_form").attr("action",path).submit();
                }
            });



            function commonly_used_function (path,adminId) {
                var label = $(".sr-only").parent();
                var page = label.attr("page");
                var queryCondition = label.attr("queryCondition");
                if (queryCondition == '') {
                    return path + "/" + page + "?adminId=" + adminId;
                }else {
                    return path + "/" + page + "?adminId=" + adminId + "&queryCondition=" + queryCondition;
                }
            }
        });
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
<%@include file="../../common/main-list-user.jsp" %>
</body>
</html>

