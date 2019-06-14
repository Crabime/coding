<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<html>
<head>
    <title>欢迎页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/index.css">
</head>
<body>
<h1>welcome to springmvc</h1>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <button id="b1" class="bt" value="管理员可见">管理员可见</button>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_USER')">
    <button id="b2" class="bt" value="普通用户可见">普通用户可见</button>
</sec:authorize>


</body>
</html>
