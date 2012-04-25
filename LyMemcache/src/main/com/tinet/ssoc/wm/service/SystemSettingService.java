package com.tinet.ssoc.wm.service;

import com.tinet.ccic.wm.commons.service.BaseService;
import com.tinet.ssoc.wm.model.SystemSetting;

/**
 * 系统设置管理模块业务逻辑接口
 *<p>
 * 文件名： SystemSettingService.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 安静波
 * @since 1.0
 * @version 1.0
 * @see  com.tinet.ccic.itf.service.imp.SystemSettingServiceImp
 */
public interface SystemSettingService extends BaseService<SystemSetting> {
	public boolean initGlobal();
	
	
}
