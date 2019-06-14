<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>adminPage2</title>
</head>
<body>
<h1>this is admin page</h1>
<h1>测试使用模糊匹配的方式，只有ROLE_ADMIN用户才可以访问该目录下文件adminTestPage2</h1>
<a href="${pageContext.request.contextPath}/j_spring_security_logout">退出登陆</a>
</body>
</html>
