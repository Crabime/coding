<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>自定义登陆页面</title>
    <style>
        .error{
            color: red;
        }
        .hide{
            display: none;
        }
    </style>
</head>
<body>

<div class="error ${param.error == true ? '' : 'hide'}">
    登陆失败<br>
    ${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
</div>
<form method="post" action="${pageContext.request.contextPath}/j_spring_security_check" style="width:260px; text-align: center">
    <fieldset>
        <legend>登陆</legend>
        用户： <input type="text" name="username" style="width: 150px;"
                   value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" /><br />
        密码： <input type="password" name="password" style="width: 150px;" /><br />
        <input type="checkbox" name="remember-me" />两周之内不必登陆<br />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="submit" value="登陆" /> <input type="reset" value="重置" />
    </fieldset>
</form>

</body>
</html>
