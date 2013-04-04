<%@ page contentType="text/html; charset=GB2312" %>
<html>
 <head>
  <title>index</title>
 </head>
 <body>
 
  <table width="100%" height="100%">
	   <tr>
		    <td width="150" valign="top" align="left" bgcolor="#CCFFCC">
		     <jsp:include page="sidebar.html"/>
		    </td>
		    
		    <td height="100%" width="*">
			     <table width="100%" height="100%">
			     
			      <tr>
			       <td valign="top" height="15%">
			        <jsp:include page="header.html"/>
			       </td>
			      </tr>
			      
			      <tr>
			       <td valign="top">
			        <jsp:include page="indexContent.jsp"/>
			       </td>
			      </tr>
			      
			      <tr>
			       <td valign="bottom" height="15%">
			         <jsp:include page="footer.html"/>
			       </td>
			      </tr>
			      
			     </table>
		    </td>
	   </tr>
  </table>
 </body>
</html>