<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>baby!</title>
<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="js/amq_jquery_adapter.js"></script>
	<script type="text/javascript" src="js/amq.js"></script>

	<!-- 初始化amq.js文件 -->	
	<script type="text/javascript">
		var amq = org.activemq.Amq;
		amq.init({uri: 'amq', loggin: true, timeout: 1, clientId: (new Date()).getDate().toString()});
		
		var msg = "<msg type='cn.crabime.common'>"
            +  "<id>msg1</id>"  
            +  "<content>This is test content</content>"  
            + "</msg>";  
        
		function send(){
			amq.sendMessage("cn.crabime.topic://Test", msg);
		};
		
		var myHandler = {
			rcvMessage : function(message){
				alert(message.getAttribute('type'));
				console.log(message.className);
			}
		};
		
		amq.addListener("testHandlerId","cn.crabime.topic://Test",myHandler.rcvMessage);
	</script>
	
</head>
<body>
<b>多人聊天室</b>
<hr>
<div style="height:300px;width:600px;border:block;overflow:auto" id="msgDiv"></div>
昵称：<input type="text" id="nickname">
内容：<input type="text" id="content">
<button onclick="send()">发送</button>
</body>
</html>