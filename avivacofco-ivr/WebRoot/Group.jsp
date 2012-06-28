<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>团险调用webservice，并拿到报文信息</title>
    
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
  <h1>团险</h1>
     <form action="GroupServlet" method="get">
    <table>
    	<tr>
    	<td><input type="text" name="IdNo"  value="370102198501010022"/>身份证号码</td>
    	</tr>
    	<tr>
    	<td><input type="submit"/ value="提交"></td>
    	</tr>
    	</table>
    </form>
    <a href="index.jsp">个险</a><br/>
    <a href="Agents.jsp">保单函数</a>
  </body>
</html>
