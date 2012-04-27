package com.crm.framework.spring.schedule.crmschedule.vo;
/**
 * 系统计划任务信息保存类
 * @author 王永明
 * @since Apr 7, 2010 3:45:09 PM
 */
public class CrmSchedule{
	/**任务所对应的类,该类必须实现CrmScheduleBean接口*/
	private String taskClass;
	/**执行任务周期*/
	private String cronExpression;

	public String getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	
}
