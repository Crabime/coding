<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='spring' uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Formatter测试</title>
</head>
<body>
<span>下面是用户学校信息：</span>
    <c:set var="u" value="${user}" />
    <spring:eval expression="u.school" />
</body>
</html>
