package com.tinet.ssoc.wm.service;

import java.util.List;

import com.tinet.ccic.wm.commons.service.BaseService;
import com.tinet.ssoc.wm.model.NumberInuse;
/**
 * 号码使用业务逻辑接口
 * <p>
 *  文件名：NumberInuseService.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 王云龙
 * @since 1.0
 * @version 1.0
 * @see com.tinet.jboss.wm.number.service.NumberInuseService
 */
public interface NumberInuseService extends BaseService<NumberInuse> {
	/**
	 * 根据400号码查询号码使用实体
	 * @param number 400号码
	 * @return 返回list查询结果
	 */
	public List<NumberInuse> findNumberInuseByNumber(String number400);
	
}
