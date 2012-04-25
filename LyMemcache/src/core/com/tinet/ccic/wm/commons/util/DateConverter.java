package com.tinet.ccic.wm.commons.util;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 简易日期转换器. 供Apache BeanUtils 做转换,默认时间格式为yyyy-MM-dd,可由构造函数改变.
 *<p>
 * 文件名： DateConverter.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class DateConverter implements Converter {
	private static final Log log = LogFactory.getLog(DateConverter.class);
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public DateConverter(String formatPattern) {
		if (StringUtils.isNotBlank(formatPattern)) {
			format = new SimpleDateFormat(formatPattern);
		}
	}

	public Object convert(Class arg0, Object value) {
		try {
			String dateStr = (String) value;

			if (StringUtils.isNotBlank(dateStr)) {
				return format.parse(dateStr);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
