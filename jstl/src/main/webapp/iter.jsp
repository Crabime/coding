<%@ page import="java.util.List" pageEncoding="GBK" language="java" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="myTag" uri="http://gooalgene.com/mytaglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>迭代器自定义标签使用</title>
</head>
<body>
<%
    List<String> list = new ArrayList<>();
    list.add("中国");
    list.add("日本");
    list.add("美国");
    //这里先将这个countries赋值给collection,然后将迭代的值付给item,item赋给i,最后直接输出i即可
    pageContext.setAttribute("countries", list);

    Map<String, Object> map = new HashMap<>();
    map.put("SNP", "SNP_01");
    map.put("INDEL", "INDEL_02");
    pageContext.setAttribute("catagory", map);
%>
<myTag:myIteratorTag collection="countries" item="i">
    <tr>
        <td>${i}</td>
    </tr>
</myTag:myIteratorTag>
<h1>--------下面使用jstl标准库标签进行输出map集合元素-------</h1>
<c:forEach var="entry" items="${catagory}">
    <p>Key:<c:out value="${entry.key}" />
    Value:<c:out value="${entry.value}" /></p>
</c:forEach>
</body>
</html>
