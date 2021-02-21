<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/7
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="../common/main-function-list.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
        </div>
        <div class="panel-body">
            <form class="form-inline" role="form" style="float:left;" action="admin/userList/1">
                <div class="form-group has-feedback">
                    <div class="input-group">
                        <div class="input-group-addon">查询条件</div>
                        <input class="form-control has-success" name="queryCondition" type="text" placeholder="请输入查询条件" value="${requestScope.queryCondition}">
                    </div>
                </div>
                <button type="submit" id="query_submit_button"  class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
            </form>
            <button type="button" id="admin_batch_delete_button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<%--            <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='admin/to/add/page?pages=${requestScope.pageInfo.pages}'"><i class="glyphicon glyphicon-plus"></i> 新增</button>--%>
            <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='admin/to/add/page'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
            <br>
            <hr style="clear:both;">
            <div class="table-responsive">
                <table class="table  table-bordered">
                    <thead>
                    <tr >
                        <th width="30">#</th>
                        <th width="30"><input id="admin_batch_delete_checkbox" type="checkbox"></th>
                        <th>账号</th>
                        <th>名称</th>
                        <th>邮箱地址</th>
                        <th width="100">操作</th>
                    </tr>
                    </thead>
                    <c:if test="${not empty requestScope.pageMassage}">
                        <tr>
                            <td colspan="6" align="center">
                                <ul class="pagination">
                                    <h3> ${requestScope.pageMassage} </h3>
                                </ul>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${empty requestScope.pageMassage}">
                        <tbody>
                        <c:forEach items="${requestScope.admins}" var="admin" varStatus="num">
                        <tr>
                            <td>${num.count}</td>
                            <td><input type="checkbox" class="admin_batch_delete_checkbox" admin_id="${admin.id}"></td>
                            <td>${admin.loginAcct}</td>
                            <td>${admin.userName}</td>
                            <td>${admin.email}</td>
                            <td>
                                <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
                            <button type="button" button_type="button_update_admin" admin_id="${admin.id}"   class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>
                             <button type="button" button_type="button_delete_admin_by_id" admin_id="${admin.id}" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    </c:if>
                    <tfoot>
                    <tr>
                        <td colspan="6" align="center">
                            <ul class="pagination">
                               ${requestScope.pageFoot}
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6" align="center">
                            <ul class="pagination">
                                总页数 ${requestScope.pageInfo.pages},第 ${requestScope.pageInfo.pageNum} 页,总记录数 ${requestScope.pageInfo.total}
                            </ul>
                        </td>

                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
    </div>
    <a id="update_admin_a" href=""></a>
    <form method="post" action="" id="admin_delete_by_id_form">
        <input type="hidden" name="_method" value="DELETE">
    </form>
</div>

