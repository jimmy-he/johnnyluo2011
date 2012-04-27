package com.crm.framework.spring.schedule;

import java.lang.reflect.Method;

import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.crm.framework.common.ui.TemplateUtil;
import com.crm.framework.common.util.FileUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.spring.schedule.crmschedule.CrmScheduleBean;

/**
 * 
 * @author 王永明
 * @since Apr 7, 2010 10:14:01 AM
 */
public class ScheduleConfig {
	public  static final String BUS_TRIGGER="busTrigger";
	public static final String BUS_JOB="busJobDetail";
	public static final String SCHEDULER_ID="scheduler";
	public static final Class SCHEDULER_BEAN=SchedulerFactoryBean.class;
	private static final Class jobDetailBean=MethodInvokingJobDetailFactoryBean.class;
	private static final Class cronTriggerBean=CronTriggerBean.class;
	private String groupName;
	
	/**任务方法*/
	private String taskMethod;
	
	private String cronExpression="0/10 * * * * ?";
	
	private String taskId;
	
	private Class taskClass;
	

	
	
	public ScheduleConfig(Method method,String cronExpression,String groupName){
		this.taskMethod=method.getName();
		this.cronExpression=cronExpression.toString();
		this.groupName=groupName;
		this.taskId=StringUtil.firstLower(method.getDeclaringClass().getSimpleName());
		this.taskClass=method.getDeclaringClass();
	}
	
	public ScheduleConfig(Method method,String cronExpression){
		this(method,cronExpression,Scheduler.DEFAULT_GROUP);
	}
	
	
	public ScheduleConfig(CrmScheduleBean crmScheduleBean,String taskId){
		this.taskMethod="run";
		this.cronExpression=crmScheduleBean.getCronExpression();
		this.taskId=taskId;
		this.groupName=Scheduler.DEFAULT_GROUP;
	}
	

	
	public String toXml(){
		String text=FileUtil.getText(ScheduleConfig.class, "scheduleConfig.ftl.xml");	
		text=TemplateUtil.getText(this, text);
		return text;
	}
	
	public static void main(String[] args) throws SecurityException, NoSuchMethodException {
		Method methd=ScheduleConfig.class.getMethods()[0];
		ScheduleConfig f=new ScheduleConfig(methd,CronExpression.PER_SECOND+"");
		System.out.println(f.toXml());
	}

	public String getTaskId(){
		return taskId;
	}


	public String getTaskMethod() {
		return taskMethod;
	}


	public String getJobName() {
		return this.getTaskId()+StringUtil.firstUp(taskMethod);
	}

	public Class getJobDetailBean() {
		return jobDetailBean;
	}

	public String getTriggerId() {
		return this.getJobName()+"Trigger";
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public Class getCronTriggerBean() {
		return cronTriggerBean;
	}


	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTaskBeanDefine(){
		if(taskClass!=null){
			return "<bean id=\""+this.getTaskId()+"\" class=\""+taskClass.getName()+"\"/>";
		}
		return "";
		
	}
}













