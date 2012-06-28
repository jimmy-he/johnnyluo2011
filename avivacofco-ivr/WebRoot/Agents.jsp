<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>工号调用存储过程测试</title>
    
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
    <h1>营业员测试</h1>
     <form action="AgentsServlet" method="get">
    <table>
    	<tr>
    	<td><input type="text" name="Ag_NO" />营业员工号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="Qu_NO" />渠道类型</td>
    	</tr>
    	<tr>
    	<td><input type="submit"/ value="提交"></td>
    	</tr>
    	</table>
    </form>
    <a href="index.jsp">个险</a>
  </body>
</html>
