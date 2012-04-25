package com.tinet.ccic.wm.commons;

import java.sql.Types;
/**
 * PostgreSQL数据库方言类。
 *<p>
 * 文件名： PostgreSQLDialect.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class PostgreSQLDialect extends org.hibernate.dialect.PostgreSQLDialect {
	
	public PostgreSQLDialect() {
		super();
		this.registerHibernateType(Types.ARRAY, "string");
	}
	
}
