package com.tinet.ssoc.wm.service;



import java.util.List;

import com.tinet.ccic.wm.commons.dao.BaseDao;
import com.tinet.ccic.wm.commons.service.BaseServiceImp;
import com.tinet.ssoc.inc.Macro;
import com.tinet.ssoc.wm.dao.CcicDao;
import com.tinet.ssoc.wm.model.Ccic;


public class CcicServiceImp extends BaseServiceImp<Ccic> 
implements CcicService {
	
	private CcicDao ccicDao;
	

	public CcicDao getCcicDao() {
		return ccicDao;
	}

	public void setCcicDao(CcicDao ccicDao) {
		this.ccicDao = ccicDao;
	}

	@Override
	protected BaseDao<Ccic> getBaseDao() {
		return this.getCcicDao();
	}
	
	/**
	 * 将数据库中cti表中数据同步到内存中
	 */
	public void cache(){
		List<Ccic> ccics = (List<Ccic>)ccicDao.getAll();
		Macro.CCICS = ccics;
	}
}
