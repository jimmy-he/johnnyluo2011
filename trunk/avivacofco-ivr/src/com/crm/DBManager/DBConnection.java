/**
 * 
 */
package com.crm.DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *@author 罗尧 Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-8 下午03:24:19
 */
public class DBConnection {

	public static Connection getConnection() {
		Connection conn = null;
		JdbcConfig jdbcConfig = XmlConfigReader.getInstance().getJdbcConfig();
		try {
			Class.forName(jdbcConfig.getDriverName());
			conn = DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig
					.getUserName(), jdbcConfig.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
//		ResultSet rs = null;
//		CallableStatement statement;
//		String x = "90200087";
//		String y = "3";
//		
//		try {
//			String callExchangeMoneyStr = "{call ODSCIF.SP_IVR_AGENTS_INFO(?,?)}"; 
//			statement = DBConnection.getConnection().prepareCall(callExchangeMoneyStr);
//			statement.setString(1, x);
//			statement.setString(2, y);
//			//statement.registerOutParameter(1, Types.OTHER);// 为输出			
//			statement.execute();
//			rs=statement.getResultSet();
//			//rs=statement.executeQuery();
//			//System.out.println(rs);
////			
////			String meString=statement.getObject(4).toString();
////			System.out.println(meString);
//			//rs = statement.executeQuery();
//			// System.out.println(statement);
//			while (rs.next()) {
//				System.out.println(rs.getString(1) + "---"+rs.getString(2));
//			}
//			//String strReturn = statement.getString(0);
//
//			//String strOutput = statement.getString(1);
//
//			//System.out.println("OutPut:" + strOutput + "---Return:" );
//			//rs.close();
//			//statement.close();
//
//			//DBConnection.getConnection().close();
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
}
