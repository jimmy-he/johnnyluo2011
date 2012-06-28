<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>个险调用webservice，并拿到报文信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <form action="InServlet" method="get">
    <table>
    	<h1>个险</h1>
    	<tr>
    	<td><input type="text" name="TradeCode" value="00030002"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="IDENTY_NO" value="510502198204200423"/>身份证号码</td>
    	</tr>
    	<%--<tr>
    		<td><input type="text" name="Messages"/>消息传递</td>
    	</tr>
    	--%><tr>
    	<td><input type="submit"/ value="提交"></td>
    	</tr>
    	</table>
    </form>
    <a href="Group.jsp">团险</a>
    <a href="Agents.jsp">工号验证</a>
    <a href="MessageIn.jsp">新契约</a>
    <a href="NewIndex.jsp">最新需求测试</a>
  </body>
</html>
