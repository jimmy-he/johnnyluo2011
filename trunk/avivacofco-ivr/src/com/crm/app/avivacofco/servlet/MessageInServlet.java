/**
 * 
 */
package com.crm.app.avivacofco.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.DBManager.DBConnection;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-8   下午03:24:19
 */
public class MessageInServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MessageInServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("GBK");
		
		response.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();
		String cp_no=request.getParameter("Cp_NO");
		String ba_no=request.getParameter("Ba_NO");
		System.out.println("获取分公司号："+cp_no);
		System.out.println("获取输入的保单号："+ba_no);
		Connection conn=DBConnection.getConnection();
		ResultSet rs = null;
		CallableStatement statement=null;		
		try {
			String callExchangeMoneyStr = "{call ODSCIF.SP_IVR_POLICY_INFO(?,?)}"; 
			statement = conn.prepareCall(callExchangeMoneyStr);
			statement.setString(1,cp_no); 
			statement.setString(2,ba_no); 
			statement.execute();
			rs=statement.getResultSet();
//			while (rs.next()) {
//				System.out.println(rs.getString(1) + "---"+rs.getString(2));
//			}
			if(rs.next()){
				System.out.println("-------成功拿到数据-----------");
				out.append("1");
				out.append("\n");
				out.append("-----1分公司号------:"+rs.getString(1));
				out.append("-----2支公司号------:"+rs.getString(2));
				out.append("-----3保单号(8位)------:"+rs.getString(3));
				out.append("-----4风险状态------:"+rs.getString(4));
				out.append("-----5缴别------:"+rs.getString(5));
				out.append("-----6金额------:"+rs.getString(6));
				out.append("-----7下一应缴日------:"+rs.getString(7));
				out.append("-----8逻辑编号------:"+rs.getString(8));
				
			}else {
				System.out.println("********！数据库中没有此条记录！********");
				out.append("0");
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("您输入的条件超出范围！请查证后重新输入！");
		} 
		out.flush();
		try {
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
