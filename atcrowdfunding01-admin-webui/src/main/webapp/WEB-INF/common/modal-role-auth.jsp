<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/23
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="menuAuthModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">尚筹网系统弹窗</h4>
            </div>
            <div class="panel-body">
                <ul id="treeDemo" class="ztree">
                    
                </ul>
                <div class="modal-footer">
                    <button id="authSaveBtn" type="button" class="btn btn-default"><i class="glyphicon glyphicon-plus"></i> 保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
