<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新版中英IVR</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script>
  function insert(){
	 var ID_NO = document.getElementById("id_no").value;
	 var Channel_No = document.getElementById("Channel_No").value;
	 var Command_No =document.getElementById("Command_No").value;

	 var URL="http://127.0.0.1:8088/avivacofco/servlet/Inservlet?ID_NO="+ID_NO+"&ChannelNo="+Channel_No+"&CommandNo="+Command_No+"&t="+new Date().getTime();
	window.open(URL)
	  }
  </script>
  <body>
    <h1>新版中英IVR测试</h1>
<h2>身份证验证 Command_No=000001
    </h2>
     <form action="servlet/Inservlet" method="get">
    <table>
    	<tr>
    	<td><input type="text" name="ID_NO"  id="id_no" value="510726197704150220"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo"  id="Channel_No" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo"  id="Command_No" value="000001"/>命令号</td>
    	</tr>
  
    	<tr>
    	<td><input type="button" onclick="insert();" value="提交"></td>
    	</tr>
    	</table>
    </form>
<h2>保单验证Command_No=000002</h2>
    <form action="servlet/Inservlet" method="get">
    	<table>
    	<tr>
    	<td><input type="text" name="ID_NO"  value="296480"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo" value="000002"/>命令号</td>
    	</tr>
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    </form>
    <h2>保单 理赔信息查询Command_No=000003</h2>
    <form action="servlet/Inservlet" method="get">
    	<table>
    	<tr>
    	<td><input type="text" name="ID_NO"  value="296480"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo" value="000003"/>命令号</td>
    	</tr>
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    </form>
    <h2>保单基本信息查询Command_No=000004</h2>
    <form action="servlet/Inservlet" method="get">
    	<table>
    	<tr>
    	<td><input type="text" name="ID_NO"  value="296480"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo" value="000004"/>命令号</td>
    	</tr>
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    </form>
<h2>代理人工号+渠道类型号进行判断Command_No=000005</h2>
     <form action="servlet/Inservlet" method="get">
    <table>
    	<tr>
    	<td><input type="text" name="ID_NO"  id="id_no" value="150200002"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo"  id="Channel_No" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo"  id="Command_No" value="000005"/>命令号</td>
    	</tr>
  
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    </form>
    <br/>
<h2>分公司号和保单号进行新契约查询----Command_No=000006</h2><br/>
<h2>修改为:-----------</h2>
<h2><font color="red">分公司号和保单号进行理赔查询----Command_No=000006</font></h2>
     <form action="servlet/Inservlet" method="get">
    <table>
    	<tr>
    	<td><input type="text" name="ID_NO"  value="00000033"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo" value="000006"/>命令号</td>
    	</tr>
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    	</form>
<h2>保单状态、保费、下次应缴日--保单资料及续期收费查询--Command_No=000008</h2>
    	<form action="servlet/Inservlet" method="get">
    	<table>
    	<tr>
    	<td><input type="text" name="ID_NO"  value="00000033"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo" value="000008"/>命令号</td>
    	</tr>
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    </form>
    <h2><font color=red>----------------------------------------新增五个接口，进行数据库操作，查询各险种利率----------------------------------------------</font></h2>
    <h2>万能险当月结算利率查询--Command_No=111111</h2>
    	<form action="servlet/Inservlet" method="get">
    	<table>
    	<tr>
    	<td><input type="text" name="ID_NO"  value="111111"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo" value="111111"/>命令号</td>
    	</tr>
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    </form>
    <h2> 金彩连连投资连结保险，金芒果投资连接保险--Command_No=222221</h2>
    	<form action="servlet/Inservlet" method="get">
    	<table>
    	<tr>
    	<td><input type="text" name="ID_NO"  value="222221"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo" value="222221"/>命令号</td>
    	</tr>
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    </form>
    <h2>金彩连连投资连结保险B，金芒果投资连接保险B---Command_No=222222</h2>
    	<form action="servlet/Inservlet" method="get">
    	<table>
    	<tr>
    	<td><input type="text" name="ID_NO"  value="111111"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo" value="222222"/>命令号</td>
    	</tr>
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    </form>
    <h2>红利储存利率查询--Command_No=333333</h2>
    	<form action="servlet/Inservlet" method="get">
    	<table>
    	<tr>
    	<td><input type="text" name="ID_NO"  value="333333"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo" value="333333"/>命令号</td>
    	</tr>
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    </form>
    <h2>万能险借款利率查询--Command_No=444444</h2>
    	<form action="servlet/Inservlet" method="get">
    	<table>
    	<tr>
    	<td><input type="text" name="ID_NO"  value="444444"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo" value="444444"/>命令号</td>
    	</tr>
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    </form>
    <h2>留存满一年生存金适用利率查询--Command_No=555555</h2>
    	<form action="servlet/Inservlet" method="get">
    	<table>
    	<tr>
    	<td><input type="text" name="ID_NO"  value="555555"/>代码</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="ChannelNo" value="1"/>通道号</td>
    	</tr>
    	<tr>
    	<td><input type="text" name="CommandNo" value="555555"/>命令号</td>
    	</tr>
    	<tr>
    	<td><input type="submit" value="提交"></td>
    	</tr>
    	</table>
    </form>
    <a href="index.jsp">个险</a>
  </body>
</html>
