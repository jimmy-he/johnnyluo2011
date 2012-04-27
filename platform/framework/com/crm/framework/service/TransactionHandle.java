package com.crm.framework.service;

import java.util.List;

import com.crm.framework.dao.CrmDao;

/**
 * 
 * @author 王永明
 * @since Apr 5, 2010 9:24:07 PM
 */
public interface TransactionHandle  {
	public void setDao(List<CrmDao> daos);
	
	public List<CrmDao> getDaos();
}
