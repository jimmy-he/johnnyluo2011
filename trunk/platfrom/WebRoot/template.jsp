<%@ page contentType="text/html; charset=GB2312" %>
<heml>
 <head>
  <title><%=titlename %></title>
 </head>
 <body>
 <%--最外层 --%>
  <table width="100%" height="100%">
   <tr>
   <%--左侧部分 --%>
    <td width="150" valign="top" align="left" bgcolor="#CCFFCC">
      <%@ include file="sidebar.html" %>
    </td>
    <%--右侧部分 --%>
    <td height="100%" >
     <table width="100%" height="100%">
      <tr>
      <%--头部 --%>
       <td valign="top" height="15%">
           <%@ include file="header.html" %>
       </td>
      </tr>
      <tr>
      <%--主体部分 --%>
       <td valign="top">
        <jsp:include page="<%=bodyfile %>"/>
       </td>
      </tr>
      <tr>
      <%--尾部 --%>
       <td valign="bottom" height="15%">
         <%@ include file="footer.html" %>
       </td>
      </tr>
     </table>
    </td>
   </tr>
  </table>
 </body>
</heml>