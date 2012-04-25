package com.tinet.ssoc.wm.service;

import java.util.List;

import com.tinet.ccic.wm.commons.dao.BaseDao;
import com.tinet.ccic.wm.commons.service.BaseServiceImp;
import com.tinet.ssoc.inc.Const;
import com.tinet.ssoc.inc.Macro;
import com.tinet.ssoc.wm.dao.SystemSettingDao;
import com.tinet.ssoc.wm.model.SystemSetting;
/**
* 系统设置管理模块业务实现类
*<p>
* 文件名： SystemSettingServiceImp.java
*<p>
* Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
* @author 安静波
* @since 1.0
* @version 1.0
* @see  com.tinet.ccic.itf.service.SystemSettingService
*/
public class SystemSettingServiceImp extends BaseServiceImp<SystemSetting> implements SystemSettingService {

	private SystemSettingDao systemSettingDao;

	public SystemSettingDao getSystemSettingDao() {
		return systemSettingDao;
	}

	public void setSystemSettingDao(SystemSettingDao systemSettingDao) {
		this.systemSettingDao = systemSettingDao;
	}


	@Override
	protected BaseDao<SystemSetting> getBaseDao() {
		return getSystemSettingDao();
	}
	
	/**
	 * 初始化全局数据.从数据库中SystemSetting载入到内存
	 */
	public boolean initGlobal(){
		System.out.println("init Global variables for SMSC!");
		try{	
			List<SystemSetting> systemSettings;
			systemSettings = (List<SystemSetting>) systemSettingDao.getAll();
			for(int i=0; i< systemSettings.size(); i++){
				SystemSetting systemSetting = systemSettings.get(i);
				/*
				if(systemSetting.getName().equals(Const.SYSTEM_SETTING_NAME_SOAP_URL)){
					Macro.SOAP_URL = systemSetting.getValue();
				}else if(systemSetting.getName().equals(Const.SYSTEM_SETTING_NAME_SOAP_USER)){
					Macro.SOAP_USER = systemSetting.getValue();
					Macro.SOAP_PASSWORD = systemSetting.getProperty();
				}else if(systemSetting.getName().equals(Const.SYSTEM_SETTING_NAME_DEFAULT_CELL)){
					Macro.DEFAULT_CELL = systemSetting.getValue();
				}else if(systemSetting.getName().equals(Const.SYSTEM_SETTING_NAME_SERIAL_NO)){
					Macro.SERIAL_NO = systemSetting.getValue();
					Macro.SERIAL_NO_KEY = systemSetting.getProperty();
				}else if(systemSetting.getName().equals(Const.SYSTEM_SETTING_NAME_MODULE_BJLOT)){
					Macro.MODULE_BJLOT = systemSetting.getValue();
				}else if(systemSetting.getName().equals(Const.SYSTEM_SETTING_NAME_MODULE_EMAY)){
					Macro.MODULE_EMAY = systemSetting.getValue();
				}
				*/
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 重置全局数据.从数据库中SystemSetting载入到内存
	 */
	public void resetGlobal(){
		initGlobal();
	}
	
}
