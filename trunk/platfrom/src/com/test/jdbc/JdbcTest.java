package com.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcTest {


	//连接数据库的方法
	public void getJDBCCon(){
		 String driver="com.mysql.jdbc.Driver";		 
		 String url="jdbc:mysql://127.0.0.1:3306/test";
		 String userName="root";
		 String passWd="123456";
		 Connection con = null;
			 try {	
				Class.forName(driver);	
				con = DriverManager.getConnection(url, userName, passWd);
				if(con!=null){
					 System.out.println("数据库连接成功");
				 }else {
					System.out.println("数据库连接失败");
				}
				Statement  statement=con.createStatement();		//创建一个查询的对象
				ResultSet resultSet=statement.executeQuery("select * from users");
				while(resultSet.next()){
					System.out.println(resultSet.getString("name"));
				}
			} catch (ClassNotFoundException e) {			
				e.printStackTrace();
			}catch (SQLException e) {
				e.printStackTrace();
			}
			 
		 
		
		 
	}
	
	public static void main(String[] args) {
		JdbcTest jdbcTest=new JdbcTest();
		jdbcTest.getJDBCCon();

	}

}
