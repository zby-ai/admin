<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="WEB-INF/common/base-head.jsp"%>
<body>
<h2>Hello World!</h2>
<a href="admin/get/1" >go</a>

<button id="btn1">Json [1,2,3]</button> <br/>
<a href="testjson/two" >普通请求</a>

<script type="text/javascript" src="static/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#btn1").click(function () {
            var array = [1,2,3]
            var jsonString = JSON.stringify(array);
            $.ajax({
                "url":"testjson/one",
                "data":jsonString,
                "contentType":"application/json;charset=UTF-8",
                "type":"post",
                "dataType":"text",
                "success":function (result) {
                    alert(result);
                },
                "error":function (result) {
                    alert(result);
                }
            });
        });
    });
</script>
</body>
</html>
