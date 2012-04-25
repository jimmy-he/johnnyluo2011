package com.tinet.ccic.wm.commons;

import java.util.ResourceBundle;

import org.springframework.context.i18n.LocaleContextHolder;

import com.tinet.ccic.wm.commons.util.MessageReader;
/**
 * 国际化相关异常类。
 *<p>
 * 文件名： JeawException.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class CCICException extends RuntimeException{

	/**
	 * 错误代码,默认为未知错误
	 */
	private String errorCode = "UNKNOW_ERROR";

	/**
	 * 错误信息中的参数
	 */
	protected Object[] errorArgs = null;

	/**
	 * 兼容纯错误信息，不含error code,errorArgs的情况
	 */
	private String errorMessage = null;

	/**
	 * 错误信息的i18n ResourceBundle. 默认Properties文件名定义于{@link Constants#ERROR_BUNDLE_KEY}
	 */
	static private ResourceBundle rb = ResourceBundle.getBundle(
			Constants.ERROR_BUNDLE_KEY, LocaleContextHolder.getLocale());

	public CCICException() {
		super();
	}
	
	public CCICException(String errorCode) {
		this.errorCode = errorCode;
	}

	public CCICException(String errorCode, Object[] errorArgs) {
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;
	}

	public CCICException(String errorCode, Object[] errorArgs, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;
	}
	
	public CCICException(String errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}
	
	/**
	 * 获得出错信息. 读取i18N properties文件中Error Code对应的message,并组合参数获得i18n的出错信息.
	 */
	public String getMessage() {
		// 如果errorMessage不为空,直接返回出错信息.
		if (errorMessage != null) {
			return errorMessage;
		}
		// 否则用errorCode查询Properties文件获得出错信息
		return MessageReader.getMessage(errorCode,errorArgs);
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	public void setMessage(String errorMessage){
		this.errorMessage = errorMessage;
	}
}
