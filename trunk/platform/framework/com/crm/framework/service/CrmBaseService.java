package com.crm.framework.service;

import java.util.List;

import com.crm.framework.common.Global;
import com.crm.framework.crmbean.interfaces.CrmUser;
import com.crm.framework.dao.CrmDao;

/**
 * 
 * @author 王永明
 * @since Apr 5, 2010 6:17:55 PM
 */
public class CrmBaseService implements UserTransactionHandle {

	private List<CrmDao> daos;
	private CrmUser user;
	
	protected CrmDao getDao(){
		return this.getDaos().get(0);
	}
	
	public List<CrmDao> getDaos() {
		return daos;
	}

	public void setDao(List<CrmDao> daos) {
		this.daos=daos;
	}

	public void setUser(CrmUser user) {
		this.user=user;
	}
	
	
	public CrmUser getUser(){
		return user;
	}
	
	
	protected Integer getDefaultPageNo(){
		return Global.getConfig().getRowsPerPageInt();
	}
}
