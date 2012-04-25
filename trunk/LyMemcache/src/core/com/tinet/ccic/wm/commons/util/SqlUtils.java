package com.tinet.ccic.wm.commons.util;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
/**
 * SQL查询相关工具类
 *<p>
 * 文件名： SqlUtils.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class SqlUtils{
	/*
	 * //////////////////// 根据表名，列名和列名值组成插入语句。插入一条记录
	 */
	public String insert(String sTable, String strRowName[],
			String[] strRowValue) {
		int iLength = strRowName.length;// 要插入列的个数

		StringBuffer sql = new StringBuffer();
		sql.append(" insert into ").append(sTable).append(" ( ");
		for (int i = 0; i < iLength - 1; i++) {
			sql = sql.append(" ").append(strRowName[i]).append(",");
		}// end of for...

		sql = sql.append(strRowName[iLength - 1]).append(") values (");
		for (int i = 0; i < iLength - 1; i++) {
			sql = sql.append(" '").append(strRowValue[i]).append("',");
		}// end of for...

		sql = sql.append("'").append(strRowValue[iLength - 1]).append("')");
		return sql.toString();
	}// end of the method insert(String)

	public String insert(String sTable, Object strRowName[],
			Object[] strRowValue) {
		int iLength = strRowName.length;// 要插入列的个数

		StringBuffer sql = new StringBuffer();
		sql.append(" insert into ").append(sTable).append(" ( ");
		for (int i = 0; i < iLength - 1; i++) {
			sql = sql.append(" ").append(strRowName[i]).append(",");
		}// end of for...

		sql = sql.append(strRowName[iLength - 1]).append(") values (");
		for (int i = 0; i < iLength - 1; i++) {
			if (strRowValue[i] instanceof Long) {
				sql = sql.append(strRowValue[i]).append(",");
			} else if (strRowValue[i] instanceof Date) {
				sql = sql.append(" to_date('").append(
						DateUtil.format((Date) strRowValue[i], DateUtil
								.getDateTimePattern())).append(
						"','yyyy-MM-dd HH24:mi:ss'),");
			} else if (strRowValue[i] != null
					&& strRowValue[i].toString().equals("")) {
				sql = sql.append(" null,");
			} else {
				sql = sql.append(" '").append(StringEscapeUtils.escapeSql(strRowValue[i].toString())).append("',");
			}
		}// end of for...
		if (strRowValue[iLength - 1] instanceof Long) {
			sql = sql.append(strRowValue[iLength - 1]).append(")");
		} else if (strRowValue[iLength - 1] instanceof Date) {
			sql = sql.append(" to_date('").append(
					DateUtil.format((Date) strRowValue[iLength - 1], DateUtil
							.getDateTimePattern())).append(
					"','yyyy-MM-dd HH24:mi:ss'))");
		} else {
			sql = sql.append(" '").append(StringEscapeUtils.escapeSql(strRowValue[iLength - 1].toString()))
					.append("')");
		}
		return sql.toString();
	}

	/*
	 * 根据表名，条件，（查找列，旧的值，新的值）
	 */
	public boolean update(String sTable, String sColumn, String sOldColumn,
			String sNewColumn) {
		String sUpdate = "update " + sTable + " set " + sColumn + " ="
				+ sNewColumn + " where " + sColumn + " =" + sOldColumn;
		return true;
	}// end of the method update(String,String,String)

	public static void main(String[] args) {
		String userName = "1' or '1'='1";
		String password = "123456";
		userName = StringEscapeUtils.escapeSql(userName);
		password = StringEscapeUtils.escapeSql(password);
		String sql = "SELECT COUNT(userId) FROM t_user WHERE userName='"
				+ userName + "' AND password ='" + password + "'";
		System.out.println(sql);
	}
}
