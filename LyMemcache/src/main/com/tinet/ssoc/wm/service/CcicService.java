package com.tinet.ssoc.wm.service;

import com.tinet.ccic.wm.commons.service.BaseService;
import com.tinet.ssoc.wm.model.Ccic;

public interface CcicService extends BaseService<Ccic>{
	/**
	 * 将数据库中cti表中数据同步到内存中
	 */
	public void cache();
}
