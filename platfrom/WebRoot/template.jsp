<%@ page contentType="text/html; charset=GB2312" %>
<heml>
 <head>
  <title><%=titlename %></title>
 </head>
 <body>
 <%--����� --%>
  <table width="100%" height="100%">
   <tr>
   <%--��ಿ�� --%>
    <td width="150" valign="top" align="left" bgcolor="#CCFFCC">
      <%@ include file="sidebar.html" %>
    </td>
    <%--�Ҳಿ�� --%>
    <td height="100%" >
     <table width="100%" height="100%">
      <tr>
      <%--ͷ�� --%>
       <td valign="top" height="15%">
           <%@ include file="header.html" %>
       </td>
      </tr>
      <tr>
      <%--���岿�� --%>
       <td valign="top">
        <jsp:include page="<%=bodyfile %>"/>
       </td>
      </tr>
      <tr>
      <%--β�� --%>
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