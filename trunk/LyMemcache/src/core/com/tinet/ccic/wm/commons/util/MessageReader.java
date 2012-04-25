package com.tinet.ccic.wm.commons.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.context.i18n.LocaleContextHolder;

import com.tinet.ccic.wm.commons.Constants;
/**
 * 消息读取器。
 *<p>
 * 文件名： MessageReader.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class MessageReader {
	
	private static String messageFile = Constants.ERROR_BUNDLE_KEY;
	

	/**
	 * 错误信息的i18n ResourceBundle. 默认Properties文件名定义于{@link Constants#ERROR_BUNDLE_KEY}
	 */
	static private ResourceBundle rb = ResourceBundle.getBundle(
			messageFile, LocaleContextHolder.getLocale());

	/**
	 * 获得出错信息. 读取i18N properties文件中Error Code对应的message,并组合参数获得i18n的出错信息.
	 */
	public static String getMessage(String errorCode,Object[] errorArgs) {
		// 否则用errorCode查询Properties文件获得出错信息
		String message;
	
		try {
			message = rb.getString(errorCode);
	    //下面是通过Spring提供的方式来取得从多个文件中的出错信息或提示消息
		//	message = ContextUtil.getContext().getMessage(errorCode, null,null);
		} catch (MissingResourceException mse) {
			message = "ErrorCode is: " + errorCode
					+ ", but can't get the message of the Error Code";
		}

		// 将出错信息中的参数代入到出错信息中
		if (errorArgs != null)
			message = MessageFormat.format(message, (Object[]) errorArgs);

		return message;

	}
	public static String getMessage(String errorCode) {
		// 否则用errorCode查询Properties文件获得出错信息

		return getMessage(errorCode,null);

	}
	public static String getMessageFile() {
		return messageFile;
	}

	public static void setMessageFile(String messageFile) {
		MessageReader.messageFile = messageFile;
	}
}
