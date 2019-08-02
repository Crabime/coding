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
<textarea id="random-json-text" rows="3" cols="50">初始内容</textarea><br/>
<%--<button id="get-pdf">获取pdf文件</button><br/>--%>
<a href="${pageContext.servletContext.contextPath}/pdf.pdf">下载pdf文档</a><br>
<button id="get-random-json-yaml">传入yaml格式，获取json内容</button>
<script type="text/javascript">
    $("#get-random-json").click(function () {
        $.ajax({
            url: "${pageContext.servletContext.contextPath}/grb",
            type: "GET",
            success: function (data) {
                var name = data.name;
                var age = data.age;
                $("#random-json-text").text("name:" + name + ", age:" + age);
            }
        })
    });
    $("#get-random-json-yaml").click(function () {
        $.ajax({
            headers: {
                Accept: "application/json"
            },
            url: "${pageContext.servletContext.contextPath}/gyrb",
            type: "POST",
            contentType: "text/yaml",
            data: "name: crabime\ncharacter: programming\n",
            success: function (data) {
                var name = data.name;
                var age = data.age;
                $("#random-json-text").text("name:" + name + ", age:" + age);
            }
        })
    });

    $("#get-pdf").click(function () {
        $.ajax({
            headers: {
                Accept: "application/pdf"
            },
            contentType: "application/json; charset=utf-8",
            url: "${pageContext.servletContext.contextPath}/pdf",
            type: "POST",
            dataType: "json",
            data: "{\"name\": \"crabime\", \"character\": \"hello world pdf\"}"
        })
    })
</script>
</body>
</html>
