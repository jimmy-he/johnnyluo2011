package com.crm.framework.spring.schedule.crmschedule;

import com.crm.framework.common.rmi.CrmRmiBean;

/**
 * 系统的计划任务标识
 * @author 王永明
 * @since Apr 7, 2010 3:01:57 PM
 */
public interface CrmScheduleBean extends CrmRmiBean{
	/**要执行的内容*/
	public void run();
	
	/**要执行的周期*/
	public String getCronExpression();
}
