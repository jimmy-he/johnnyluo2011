package com.crm.DBManager;


/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-8   下午03:24:19
 */
public class JdbcConfig {

	private String driverName;
	
	private String url;
	
	private String userName;
	
	private String password;

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + "{driverName:" + driverName + ", url:" + url + ", userName:" + userName + "}";
	}
	
}
