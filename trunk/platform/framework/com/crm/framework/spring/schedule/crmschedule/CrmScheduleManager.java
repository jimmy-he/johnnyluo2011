package com.crm.framework.spring.schedule.crmschedule;

import com.crm.framework.common.rmi.CrmRmiBean;

/**
 * 
 * @author 王永明
 * @since Apr 7, 2010 3:05:33 PM
 */
public interface CrmScheduleManager extends CrmRmiBean {
	
	/**获得所有要执行任务的类,返回值为xml,通过xmlUtil.xmlToBean可以转换为List<CrmQuartzBean>*/
	public String getCrmSchedules();
}
