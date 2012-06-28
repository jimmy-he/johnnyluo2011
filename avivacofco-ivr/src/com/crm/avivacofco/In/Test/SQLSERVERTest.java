/**
 * 
 */
package com.crm.avivacofco.In.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-25   下午07:54:31
 */
public class SQLSERVERTest{
	 public static void main(String[] args) {
		 	Connection con;
			Statement stmt;
			//PreparedStatement prtmt;
			ResultSet rs = null;
			try {
				Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
				con = DriverManager.getConnection("jdbc:microsoft:sqlserver://10.145.0.45:1433;DatabaseName=avivacofcoDW","hoivr","hoivr");
				String sql="select unit_virtual_fund,convert(varchar(8),effdate,120) as effdate,convert(varchar,unit_offer_price) as unit_offer_price,convert(varchar,unit_bid_price) as unit_bid_price  from Fact_VPRC where effdate = (select max(effdate) from Fact_VPRC) and company = '5' order by unit_virtual_fund";
				stmt=con.createStatement();
				rs=stmt.executeQuery(sql);
//				prtmt=con.prepareStatement(sql);
//				pstmt.setLong(1, 123456789);
//				pstmt.String(1, 123456789);
				
				
				while(rs.next()){
				//System.out.println(rs.getObject(1));
				String x=(String) rs.getObject(4);
				System.out.print(x);
				//System.out.println(rs.getObject(2));
				//System.out.println(rs.getObject(3));
				//System.out.println(rs.getObject(4));
				}
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }

}
