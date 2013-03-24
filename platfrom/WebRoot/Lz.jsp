<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>罗拯的第一个jsp</title>
    
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
    <form name="loginFrom" method="post" action="servlet/HelloServlet">
    <table>
     <tr>
       <td><div align="right">用户名:</div></td><!--是一个文本格子  -->
       <td><input type="text" name="username"></td><!--input是 输入的意思 -->
     </tr>
     <tr>
       <td><div align="right">密码:</div></td>
       <td><input type="password" name="password"></td><!--前面的那个password是密码的意思  -->
     </tr> 
     <tr>
       <td><input type="submit" value="登陆"></td><!--submit的意思是提交  -->
       <td><input type="reset"  value="重置"></td><!--reset是重置的意思 -->
     </tr>
    </table>    
   </form>
  </body>
</html>
