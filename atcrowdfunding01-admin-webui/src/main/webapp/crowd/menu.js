// 移入节点显示按钮
function myAddHoverDom(treeId, treeNode) {
    var spanId = treeNode.tId + "_span";
    var groupSpanId = treeNode.tId + "_show";
    if ($("#" + groupSpanId).length > 0) {
        return;
    }
    var deleteBtn = "<a id='"+ treeNode.id +"'  class='delete_menu btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var addBtn = "<a id='"+ treeNode.id +"' class='add_menu btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var putBtn = "<a id='"+ treeNode.id +"' class='put_menu btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改权限信息'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";

    var level = treeNode.level;
    var btn = "";
    if (level == 0) {
        btn = addBtn;
    }

    if (level == 1) {
        if ($("#" + treeNode.tId + "_ul").length == 0) {
            btn = deleteBtn + addBtn + putBtn;
        } else {
            btn = addBtn + putBtn;
        }
    }

    if (level == 2) {
        btn = putBtn + deleteBtn;
    }

    $("#" + spanId).after("<span id='"+ groupSpanId +"'>"+ btn +"</span>");
}
// 移出节点删除按钮
function myRemoveHoverDom(treeId, treeNode) {
    var groupSpanId = treeNode.tId + "_show";
    $("#" + groupSpanId).remove();
}

/**
 * 显示图标
 * @param treeId
 * @param treeNode
 */
function myAddDiyDom(treeId, treeNode) {
    console.log(treeId);
    console.log(treeNode);

    var spanId = treeNode.tId + "_ico";
    $("#" + spanId).attr("class",treeNode.icon);

}


function getMenuTree() {
    var setting = {
        "view" : {
            // 添加自定义的标签
            "addDiyDom": myAddDiyDom,
            // 添加鼠标移入事件的回调函数
            "addHoverDom" : myAddHoverDom,
            // 添加鼠标移出事件的回调函数
            "removeHoverDom" : myRemoveHoverDom,
        },
        "data": {
            "key" : {
                "url" : "wangwang"
            }
        }
    };

    $.ajax({
        "url": "admin/menu/list",
        "dataType": "json",
        "type": "get",
        "success": function (result) {
            if (result.code == "SUCCEED") {
                var zNodes = result.data;
                $(document).ready(function(){
                    // 将数据按照树形结构显示
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                });
            } else {
                layer.msg(result.mssage);
            }
        },
        "error": function (result) {
            layer.msg("服务器处理失败! 状态码:" + result.status + " " + ",错误信息:" + result.statusText);
        }
    });
}