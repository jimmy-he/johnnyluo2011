<%@ page contentType="text/html;charset=GB2312" %>
<html>
  <head>
    <title>index1</title>
  </head>
  <body>
  <%--最外层Table --%>
    <table width="100%" height="100%">
      <tr>
      <%--左侧菜单 --%>
        <td width="150" valign="top" align="left" bgcolor="#CCFFCC">
          <table>
            <tr> 
            <%--菜单上方 --%>
              <td width="150" height="65" valign="top" align="left">
                <a href="">
                    <img src="images/00.jpg" border="0" width="40" height="40"/></a>
                <a>
                    <img src="images/01.jpg" border="0" width="40" height="40"/></a>
              </td>
            </tr>
            <tr>
            <%--菜单部分下方 --%>
              <td>
                <font size="5">Links</font><p>
                <a href="index.jsp">Home</a><br>
                <a href="">孙悟空</a><br>
                <a herf="">猪八戒</a><br>
                <a herf="">沙僧</a><br>
                <a herf="">唐三藏</a><br>
              </td>
            </tr>
          </table>
        </td>
        <%--网页右边部分 --%>>
        <td valign="top" height="100%" width="*">
          <table width="100%" height="100%">
            <tr>
            <%--头部 --%>
              <td valign="top" height="15%">
                <font size="6">Welcome to XiYouJi .</font>
                <hr>
              </td>
            </tr>
            <tr>
            <%--主体部分 --%>
              <td valign="top">
                <font size="4">Page-specific content goes here</font>
              </td>
            </tr>
            <tr>
            <%--尾部 --%>
               <td valign="bottom" height="15%">
               <hr>
               Thanks for stoppong by!
               </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>