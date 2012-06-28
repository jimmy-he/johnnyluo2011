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
public class DBTest{
	 public static void main(String[] args) {
		 	Connection con;
			Statement stmt;
			//PreparedStatement prtmt;
			ResultSet rs = null;
			
			Object xObject1=null;
			Object xObject2=null;
			Object xObject3=null;
			Object xObject4=null;
			Object xObject5=null;
			Object xObject6=null;
			try {
				Class.forName("com.ibm.db2.jcc.DB2Driver");
				con = DriverManager.getConnection("jdbc:db2://10.145.6.80:50000/odsdev","db2call","db2call");
				String sql="select  TYPE_ID,UNIT_OFFER_PRICE,UNIT_BID_PRICE,UNIT_VIRTUAL_FUND,(select CODE_Desc from ODSCIF.CD_CODE_T B where A.COMPANY_CODE =B.COMPANY_CODE and A.UNIT_VIRTUAL_FUND =B.DESC_ITEM and B.code_scheme ='T5515' ),EFF_DTE, UPDTE_DTE from ODSCIF.CD_IDX_VALUE_T A where company_code='1' and UNIT_VIRTUAL_FUND = 'FUN2' and VALID_FLG ='1'and eff_dte between (current date-  10  DAYS) and current date order by eff_dte desc fetch first rows only  with ur";
				stmt=con.createStatement();
				rs=stmt.executeQuery(sql);
//				prtmt=con.prepareStatement(sql);
//				pstmt.setLong(1, 123456789);
//				pstmt.String(1, 123456789);
				
				
				while(rs.next()){
					xObject1=rs.getObject(1);
					xObject2=rs.getObject(2);
					xObject3=rs.getObject(3);
					xObject4=rs.getObject(4);
					xObject5=rs.getObject(5);
					xObject6=rs.getObject(6);
					
				}
				
				System.out.println(xObject1.toString());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }

}
