/**
 * 加载权限树
 * @param zNodes
 */
function getAuthTree(zNodes) {
    var setting = {
        "data": {
            "simpleData": {
                "enable": true,
                // 自定义父节点的id属性
                "pIdKey" : "categoryId"
            },
            // 自定义属性名称的取值
            "key" : {
                "name" : "title"
            }
        },
        // 添加复选框
        "check" : {
            "enable" : true
        }
    };
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
}

/**
 * 回显已有的权限
 * @param authIds
 */
function getShowAuth(authIds) {
    console.log(authIds);
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    for (var i = 0; i<authIds.length; i++) {
        var nodes = treeObj.getNodeByParam("id", authIds[i], null);
        // 勾选节点的复选框,
        treeObj.checkNode(nodes, true, false);
    }

}

/**
 * 获取选中的权限id
 */
function getCheckedAuth() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    // 获取所有选中的节点
    var nodes = treeObj.getCheckedNodes(true);
    var authIdArr = [];
    for (var i = 0; i<nodes.length; i++) {
        authIdArr.push(nodes[i].id);
    }
    return authIdArr;
}