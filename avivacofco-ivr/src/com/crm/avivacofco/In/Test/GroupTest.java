/**
 * 
 */
package com.crm.avivacofco.In.Test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.crm.DBManager.DBConnection;


/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-11-18   下午01:29:43
 */
public class GroupTest {

	public static void main(String [] args){
		Connection conn=DBConnection.getConnection();
		ResultSet rs = null;
		CallableStatement statement=null;	
		String yearString="";//年
		String mothString="";//月
		String dateString="";//日
		try {
			String callExchangeMoneyStr = "{call ODSCIF.SP_IVR_POLICY_INFO(?,?)}"; 
			statement = conn.prepareCall(callExchangeMoneyStr);
			statement.setString(1,"1"); 
			statement.setString(2,"A0236388"); 
			statement.execute();
			rs=statement.getResultSet();
			if(rs.next()){
				System.out.println("-------成功调用存储过程-----------");
				String nextDay=rs.getString(8);
				String x=rs.getString(1);
				//下一应缴日
				System.out.println("下一应缴日为:"+nextDay);
				System.out.println("第一个值为："+x);
				//yearString=nextDay.substring(0,4);
				//mothString=nextDay.substring(4,6);
				//dateString =nextDay.substring(6,8);
				
				//System.out.println("年份为："+yearString);
				
			}else {
				System.out.println("存储过程中没有拿到值！");
			}					
		}catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("存储过程调用失败！");
		}
		
		
	}
}
