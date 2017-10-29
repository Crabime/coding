<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="myTag" uri="http://gooalgene.com/mytaglib" %>
<html>
<head>
    <title>基础标签</title>
    <style type="text/css">
        .tag{
            color: red;
        }
    </style>
</head>
<body>
    <myTag:pageNum num="10" />
    <c:set var="id" value="${UUID.randomUUID()}"/>
    <h1>id值为:${id}</h1>
    <c:forEach items="${catagory}" var="catagoryItem">
        <p>${catagoryItem}</p>
    </c:forEach>
    <p>下面是自定义tag标签内容:<span class="tag"><myTag:helloWorld /></span></p>
    <p>${num}</p>


</body>
</html>
