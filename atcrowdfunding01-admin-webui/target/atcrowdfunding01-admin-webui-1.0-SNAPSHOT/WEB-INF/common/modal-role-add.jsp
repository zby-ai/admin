<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="menuAddModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">尚筹网系统弹窗</h4>
			</div>
			<form>
				<div class="modal-body">
					请输入职位名称：<input type="text" id="model_add_role" name="name" /><br />
				</div>
				<div class="modal-footer">
					<button id="menuSaveBtn" type="button" class="btn btn-default"><i class="glyphicon glyphicon-plus"></i> 保存</button>
					<button id="menuResetBtn" type="reset" class="btn btn-primary"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
				</div>
			</form>
		</div>
	</div>
</div>