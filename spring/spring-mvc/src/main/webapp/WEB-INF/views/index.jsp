<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="${pageContext.servletContext.contextPath}/static/js/jquery-3.2.1.js"></script>
    <title>SpringMVC源码解读篇</title>
    <style type="text/css">
        .head-title {
            color: blue;
        }
    </style>
</head>
<body>
<h1 class="head-title">SpringMVC源码解读篇</h1>
<button id="get-random-json">使用Ajax获取任务json数据并填充到下面表格中：</button><br/>
<textarea id="random-json-text" rows="3" cols="50">初始内容</textarea>
<script type="text/javascript">
    $("#get-random-json").click(function () {
        $.ajax({
            url: "${pageContext.servletContext.contextPath}/grb.crabime",
            type: "GET",
            success: function (data) {
                var name = data.name;
                var age = data.age;
                $("#random-json-text").text("name:" + name + ", age:" + age);
            }
        })
    })
</script>
</body>
</html>
