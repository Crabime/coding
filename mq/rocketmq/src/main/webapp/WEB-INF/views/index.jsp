<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <title>RocketMQ测试首页</title>
</head>
<body>
<textarea id="mes" placeholder="输入要发送的消息" title="消息域"></textarea>
<button id="st" title="提交"></button>
</body>
<script type="text/javascript">
    var content = $("#mes");
    $("#st").click(function () {

        $.ajax({
            url: "${pageContext.servletContext.contextPath}/message/con?mes=" + content,
            type: "GET"
        })
    });
</script>
</html>
