<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<%
	String x="admin";
	//out.print(x)

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>普通输入框</title>
    
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
  <!-- from 来自于      form  表单 -->
   <form name="loginFrom" method="post" action="servlet/Servlet00">
    <table>
     <tr>
      <td><div align="right">用户名1：</div></td>
      <td><input type="text" name="username" value=<%=x%>></td>
     </tr>
     <tr>
      <td><div align="right">   密      码2:</div></td>
      <td><input type="password" name="password"></td>
     </tr>
     <tr>
      <td><input type="submit" value="登陆"></td>
      <td><input type="reset"  value="重置"></td>
     </tr>
    </table>
   </form>
  </body>
</html>
