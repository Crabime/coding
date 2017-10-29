<%@ page import="java.util.List" pageEncoding="GBK" language="java" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="myTag" uri="http://gooalgene.com/mytaglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>�������Զ����ǩʹ��</title>
</head>
<body>
<%
    List<String> list = new ArrayList<>();
    list.add("�й�");
    list.add("�ձ�");
    list.add("����");
    //�����Ƚ����countries��ֵ��collection,Ȼ�󽫵�����ֵ����item,item����i,���ֱ�����i����
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
<h1>--------����ʹ��jstl��׼���ǩ�������map����Ԫ��-------</h1>
<c:forEach var="entry" items="${catagory}">
    <p>Key:<c:out value="${entry.key}" />
    Value:<c:out value="${entry.value}" /></p>
</c:forEach>
</body>
</html>
