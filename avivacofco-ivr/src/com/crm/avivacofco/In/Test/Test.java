/**
 * 
 */
package com.crm.avivacofco.In.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-25   下午07:54:31
 */
public class Test extends Q{
	 public static void main(String[] args) {
		 	Connection con;
			Statement stmt;
			ResultSet rs;
			try {
				Class.forName("com.ibm.db2.jcc.DB2Driver");
				con = DriverManager.getConnection("jdbc:db2://10.145.6.130:50000/PLATSYS","platformsys","platformsys");
				String sql="select title from CMS_ARCHIVE where catid = 4882 order by id desc fetch first 1 rows only with ur";
				String sql2="select subtitle from CMS_ARCHIVE where catid = 4882 order by id desc fetch first 1 rows only with ur";
				String sql3="select source from CMS_ARCHIVE where catid = 4882 order by id desc fetch first 1 rows only with ur";
				stmt=con.createStatement();
				rs=stmt.executeQuery(sql);
				while(rs.next()){
					System.out.println(rs.getObject(1));
				}
				rs=stmt.executeQuery(sql2);
				while(rs.next()){
				System.out.println(rs.getObject(1));
				}
				rs=stmt.executeQuery(sql3);
				while(rs.next()){
				System.out.println(rs.getObject(1));
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
