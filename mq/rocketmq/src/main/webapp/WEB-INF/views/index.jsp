<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <title>RocketMQ测试首页</title>
</head>
<body>
<textarea id="mes" placeholder="输入要发送的消息"></textarea>
<button id="st" title="提交">提交</button>
</body>
<script type="text/javascript">

    $("#st").click(function () {
        var content = $("#mes").val();
        console.log("消息为：" + content);
        $.ajax({
            type: "GET",
            url: "${pageContext.servletContext.contextPath}/message/con",
            dataType: "JSON",
            data: {
                "mes": content
            },
            success: function() {
                console.log("success")
            }
        })
    });
</script>
</html>
