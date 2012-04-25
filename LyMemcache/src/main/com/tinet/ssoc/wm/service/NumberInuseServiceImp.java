package com.tinet.ssoc.wm.service;

import java.util.ArrayList;
import java.util.List;

import com.tinet.ccic.wm.commons.dao.BaseDao;
import com.tinet.ccic.wm.commons.service.BaseServiceImp;
import com.tinet.ssoc.wm.dao.NumberInuseDao;
import com.tinet.ssoc.wm.model.NumberInuse;
/**
 * 号码使用业务逻辑接口实现类
 * <p>
 *  文件名：NumberInuseServiceImp.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 王云龙
 * @since 1.0
 * @version 1.0
 * @see com.tinet.jboss.wm.number.service.NumberInuseService
 */
public class NumberInuseServiceImp extends BaseServiceImp<NumberInuse> implements
		NumberInuseService {
	private NumberInuseDao numberInuseDao;
	public NumberInuseDao getNumberInuseDao() {
		return numberInuseDao;
	}

	public void setNumberInuseDao(NumberInuseDao numberInuseDao) {
		this.numberInuseDao = numberInuseDao;
	}

	@Override
	protected BaseDao<NumberInuse> getBaseDao() {
		// TODO Auto-generated method stub
		return this.getNumberInuseDao();
	}

	/**
	 * 根据400号码查询号码信息及其所在平台信息
	 * @param number 400号码
	 * @return 返回list查询结果
	 */
	public List<NumberInuse> findNumberInuseByNumber(String number400) {
		List<NumberInuse> numberList = new ArrayList<NumberInuse>();
		numberList = numberInuseDao.findBy("number", number400);
		return numberList;
	}

}
