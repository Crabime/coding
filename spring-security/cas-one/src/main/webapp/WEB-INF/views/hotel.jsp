<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hotel</title>
</head>
<body>
<style type="text/css">
    .main {
        position: relative;
        height: 50%;
    }
    .main:after {
        position: absolute;
        content: "";
        display: block;
        top: 0;
        left: 0;
        background-image: url("http://images4.c-ctrip.com/target/hotel/441000/440800/76abb2bab0fa40499477450e285b874d_550_412.jpg");
        width: 100%;
        height: 100%;
        opacity: 0.2;
        z-index: -1;
    }
    .content {
        color: red;
        margin-left: 10%;
    }
</style>
<div class="main">
    <h1 class="content">here are all hotels inside WuHan!</h1>
</div>
</body>
</html>
