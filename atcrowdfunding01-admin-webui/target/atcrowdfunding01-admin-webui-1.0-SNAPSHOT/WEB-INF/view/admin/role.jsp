<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/7
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <%@include file="../../common/base-head.jsp" %>
    <%@include file="../../common/main-head.jsp" %>
    <%@include file="../../common/main-style.jsp" %>
    <link rel="stylesheet" href="ztree/zTreeStyle.css"/>
    <script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="crowd/auth.js"></script>
    <script type="text/javascript">
        <%@include file="../../common/main-foot-javascript.jsp"%>
        $("tbody .btn-success").click(function(){
            window.location.href = "assignPermission.html";
        });
    </script>
    <script type="text/javascript">
        window.pageNum = 1;
        window.queryCondition = "";
        var tr = "<tr></tr>";
        var td = "<td></td>";
        var tfoot = "<tfoot></tfoot>";
        var th = "<th></th>";
        var max_page = 2147483647;
        $(function () {
            ajax_get_role_list(pageNum,queryCondition);

            /**
             * 翻页
             */
            $("body").on("click",".page",function () {
                $("table").empty();
                var href =  $(this).attr("href");
                var index = href.lastIndexOf("/");
                pageNum = href.substring(index + 1,href.length);
                ajax_get_role_list(pageNum,"");
                return false;
            });
            /**
             * 条件查询
             */
            $("body").on("click","#query_submit_button",function () {
                var queryCondition = $("input[name=queryCondition]").val();
                if (queryCondition == '') {
                    layer.msg("查询条件不能为空！");
                    return false;
                }
                $("table").empty();
                ajax_get_role_list(1,queryCondition);
                return false;
            });
            /**
             * 单个删除
             */
            $("body").on("click","button[button_type=button_delete_role_by_id]",function () {
                if (confirm("确认要删除【 " + $(this).parent().parent().find("td:eq(2)").text() + "】吗？")) {
                    var roleId = $(this).attr("role_id");
                    // var pageNum = $("a[page]").attr("page");

                    if (window.queryCondition != "") {
                        $("table").empty();
                        ajax_delete_role_by_id(roleId,window.pageNum,window.queryCondition);
                        return false;
                    }
                    $("table").empty();
                    ajax_delete_role_by_id(roleId,window.pageNum,"");
                }
            });

            /**
             * 弹出修改模态框
             */
            $("body").on("click","button[button_type=button_update_role]",function () {
                $("#menuEditModal").modal("show");
                var roleName = $(this).parent().parent().find("td:eq(2)").text();
                $("#model_edit_role").val(roleName);
                var roleId = $(this).attr("role_id");
                $("#menuEditBtn").attr("role_id",roleId);
            });

            /**
             * 修改数据
             */
            $("#menuEditBtn").click(function () {
                var name = $("#model_edit_role").val();

                var role_id = $("#menuEditBtn").attr("role_id");

                ajax_update_role_by_id(name,window.pageNum,window.queryCondition,role_id);
                $("#menuEditModal").modal("hide");
            });

            /**
             * 打开添加模态框
             */
            $("body").on("click","#admin_batch_add_button",function () {
                $("#menuAddModal").modal("show");
            });

            /**
             * 添加数据
             */
            $("#menuSaveBtn").click(function () {
                var name = $("#model_add_role").val();
                if (name == "") {
                    layer.msg("职位名称不能为空！");
                }

                ajax_add_role(name);
                $("#model_add_role").val("");
                $("#menuAddModal").modal("hide");
            });

            /**
             * 全选，全不选
             */
            $("body").on("click","#admin_batch_delete_checkbox",function () {
                $(".admin_batch_delete_checkbox").prop("checked",$(this).prop("checked"));
            });

            /**
             * 批量删除复选框
             */
            $("body").on("click",".admin_batch_delete_checkbox",function () {
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
            $("body").on("click","#admin_batch_delete_button",function () {
                var roleNames = "";
                $(".admin_batch_delete_checkbox:checked").each(function () {
                    roleNames += $(this).parent().next().text() + ",";
                });
                // if (roleNames == "") {
                //     layer.msg("请选择要删除的角色！");
                //     return false;
                // }
                roleNames = roleNames.substring(0,roleNames.length - 1);
                if (confirm("确定要删除【" + roleNames + "】吗？")) {
                    var roles = "";
                    $(".admin_batch_delete_checkbox:checked").each(function() {
                        roles += $(this).attr("role_id") + ",";
                    });
                    roles = roles.substring(0,roles.length - 1);
                    ajax_delete_role_by_id(roles,window.pageNum,window.queryCondition);
                }
            });

            // 打开权限分配模态框
            $("body").on("click","button[button_type=button_auth_role]",function () {

                // 获取树形权限数据
                var result = ajax_get_auth_tree();

                if (result.status == 200) {
                    getAuthTree(result.responseJSON.data);
                } else {
                    layer.msg("服务器查询错误:" + result.statusText +  ",状态码：" + result.status);
                    return;
                }

                // 回显数据
                var roleId = $(this).attr("role_id");
                result = ajax_get_role_auth(roleId);
                if (result.status == 200) {
                    getShowAuth(result.responseJSON.data);
                } else {
                    layer.msg("服务器查询错误:" + result.statusText +  ",状态码：" + result.status);
                    return;
                }
                window.roleId = $(this).attr("role_id");
                // 打开模态框
                $("#menuAuthModal").modal("show");
            });

            $("#authSaveBtn").click(function () {
                var authIdArr = getCheckedAuth();
                var json = {
                    "authIdArr" : authIdArr
                };
                var jsonString =  JSON.stringify(json);
                ajax_update_role_auth(jsonString,window.roleId);
                $("#menuAuthModal").modal("hide");
            });

        });

        /**
         * 更新角色的权限
         */
        function ajax_update_role_auth(jsonString,roleId) {
            $.ajax({
                "url" : "admin/auth/put/roleAuth/" + roleId,
                "type" : "put",
                "data" : jsonString,
                "contentType" : "application/json;charset=UTF-8",
                "dataType" : "json",
                "success" : function (result) {
                    if (result.code == "SUCCEED") {
                        layer.msg("修改成功");
                    } else {
                        layer.msg(result.mssage);
                    }
                },
                "error" : function (result) {
                    layer.msg("服务器处理失败! 状态码:" + result.status + " " + ",错误信息:" + result.statusText);
                }

            });

        }

        /**
         * 获取权限树
         */
        function ajax_get_auth_tree() {
            var result = $.ajax({
                "url" : "admin/auth/get/list",
                "type" : "get",
                "dataType" : "json",
                "async" : false
            });
           return result;
        }

        /**
         * 获取已有的权限信息
         */
        function ajax_get_role_auth(roleId) {
            var result = $.ajax({
                "url" : "admin/auth/get/roleAuth/" + roleId,
                "type" : "get",
                "dataType" : "json",
                "async" : false
            });
            return result;
        }

        /**
         * 查询数据
         */
        function ajax_get_role_list(pageNum,queryCondition) {
            var ajaxResult = $.ajax({
                "url": "admin/roleList/" + pageNum,
                "data": (queryCondition != "" ? "queryCondition=" + queryCondition:""),
                "dataType": "json",
                "type": "get",
                "success" :function (result) {
                    if (result.code == "200") {
                        data_show(result);

                        window.queryCondition = $("a[page]").attr("querycondition");
                        window.pageNum = $("a[page]").attr("page");

                        if (result.data.queryCondition !=null) {
                            judge_whether_has_query_condition(result.data.queryCondition);
                        }
                    }else {
                        if (result.code == "404") {
                            information(result.mssage);
                        }
                        if (result.code == "FAILURE") {
                            layer.msg(result.mssage);
                        }
                    }
                },
                "error": function (result) {
                    layer.msg("服务器处理失败! 状态码:" + result.status + " " + ",错误信息:" + result.statusText);
                }
            });
        }
        /**
         * 删除
         */
        function ajax_delete_role_by_id(roleId,pageNum,queryCondition) {
            $.ajax({
                "url": "admin/delete/role",
                "type": "post",
                "data": "_method=DELETE&roleId=" + roleId,
                "dataType": "json",
                "success" :function (result) {
                    if (result.code == "200") {
                        $("table").empty();
                        ajax_get_role_list(pageNum,queryCondition);
                        layer.msg("删除成功！");
                    }else {
                        if (result.code == "500") {
                            layer.msg(result.mssage);
                            // information(result.mssage);
                        }
                    }
                },
                "error": function (result) {
                    layer.msg("服务器处理失败! 状态码:" + result.status + " " + ",错误信息:" + result.statusText);
                }
            });
        }
        /**
         * 修改数据
         */
        function ajax_update_role_by_id(name,pageNum,queryCondition,roleId) {
            $.ajax({
                "url": "admin/update/role",
                "data": "name=" + name  + "&id=" +  roleId + "&_method=PUT",
                "type": "post",
                "dataType": "json",
                "success": function (result) {
                    if (result.code == "200") {
                        $("table").empty();
                        ajax_get_role_list(pageNum,queryCondition);
                        layer.msg("修改成功！");
                    } else {
                        layer.msg(result.mssage);
                    }
                },
                "error": function (result) {
                    layer.msg("服务器处理失败! 状态码:" + result.status + " " + ",错误信息:" + result.statusText);
                }
            });
        }
        /**
         * 添加数据
         */
        function ajax_add_role(name) {
            $.ajax({
                "url": "admin/add/role",
                "data": "name=" + name,
                "type": "post",
                "dataType": "json",
                "success": function (result) {
                    if (result.code == "200") {
                        $("table").empty();
                        ajax_get_role_list(max_page,"");
                        $("input[name=queryCondition]").val("");
                        layer.msg("添加成功！");
                    } else {
                        layer.msg(result.mssage);
                    }
                },
                "error": function (result) {
                    layer.msg("服务器处理失败! 状态码:" + result.status + " " + ",错误信息:" + result.statusText);
                }
            });
        }
        /**
         * 查询条件信息回显
         */
        function judge_whether_has_query_condition(queryCondition) {
            if (queryCondition != null) {
                $("input[name=queryCondition]").var(queryCondition);
            }
        }
        /**
         * 错误信息提示
         */
        function information(mssage) {
            /**
             * <tr>
             <td colspan="6" align="center">
                <ul class="pagination">
                    <h3> ${requestScope.pageMassage} </h3>
                </ul>
                </td>
             </tr>
             */
            var ul = "<ul class='pagination'></ul>";
            $("table").empty();
            $("table").append($(tr).append($(td).append($(ul).append(($("<h3></h3>").text(mssage)))).attr("colspan","6").attr("align","center")));
        }

        /**
         * 数据显示
         * @param pageData
         */
        function data_show(pageData) {
            add_th_head();
            var dataList = pageData.data.pageInfo.list;
            $("table").append("<tbody></tbody>");
            for (var i = 0; i<dataList.length; i++) {
                var checkbox = "<input type='checkbox' class='admin_batch_delete_checkbox' role_id='"+ dataList[i].id + "'>";
                $(tr).append($(td).text("" + (i + 1)))
                     .append($(td).append(checkbox))
                     .append($(td).text("" + dataList[i].name))
                     .append($(td).append($("<button type='button' button_type='button_auth_role' role_id='"+ dataList[i].id +"' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>"))
                                  .append($("<button type='button' button_type='button_update_role' role_id='"+ dataList[i].id +"'   class='btn btn-primary btn-xs'><i class=' glyphicon glyphicon-pencil'></i></button>"))
                                  .append($("<button type='button' button_type='button_delete_role_by_id' role_id='"+ dataList[i].id +"' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>"))
                            ).appendTo($("tbody"));
            }
            page_foot_show(pageData)
        }

        /**
         * 分页条
         * @param pageData
         */
        function page_foot_show(pageData) {
            var ur = "<ul class='pagination'></ul>";
            var pageFoot = pageData.data.pageFoot;
            var pageInfo = pageData.data.pageInfo;
            var totalStr = "总页数 " + pageInfo.total;
            var pageNumStr = "第 " + pageInfo.pageNum + " 页";
            var pagesStr = "总共 " + pageInfo.pages + " 页";
            var td = "<td colspan='6' align='center'></td>";
            var url = "<ul class='pagination'></ul>";
            $(tfoot).append($(tr).append($(td).append($(url).append(pageFoot))))
                .append($(tr).append($(td).append($(url).text(totalStr + "," + pageNumStr + "," + pagesStr))))
                .appendTo("table");
        }

        /**
         * 拼接表头
         */
        function add_th_head() {
            var checkbox = $("<input type='checkbox'/>").attr("id","admin_batch_delete_checkbox");
            $("table").append($("<thead></thead>")
                                    .append($(tr)
                                            .append($(th).attr("width","30").text("#"))
                                            .append($(th).attr("width","30").append(checkbox))
                                            .append($(th).text("名称"))
                                            .append($(th).attr("width","100").text("操作"))
                                            ));

        }
    </script>
</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 角色维护</a></div>
            </div>
            <%@include file="../../common/main-head-menubar.jsp" %>
        </div>
    </nav>
    <%@include file="../../common/main-list-role.jsp" %>
    <%@include file="../../common/modal-role-add.jsp"%>
    <%@include file="../../common/modal-role-edit.jsp"%>
    <%@include file="../../common/modal-role-auth.jsp"%>
</body>
</html>