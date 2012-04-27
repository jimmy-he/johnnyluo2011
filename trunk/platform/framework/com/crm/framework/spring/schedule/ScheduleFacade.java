package com.crm.framework.spring.schedule;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.spring.DynamicLoadBean;
import com.crm.framework.spring.SpringBeanFactory;
import com.crm.framework.spring.schedule.crmschedule.CrmScheduleBean;

/**
 * 定时器
 * 
 * @author 王永明
 * @since Apr 6, 2010 10:12:05 PM
 */
public class ScheduleFacade {
	private static Logger log = Logger.getLogger(ScheduleFacade.class);
	private static Scheduler scheduler;

	public static Scheduler getScheduler() {
		if (scheduler == null) {
			log.debug("启动定时任务.............");
			scheduler = ScheduleFacade.startQuartz();
		}
		return scheduler;
	}

	
	/** 启动定时器 */
	public static Scheduler startQuartz() {
		if(SpringBeanFactory.containsBean(ScheduleConfig.SCHEDULER_ID)){
			log.debug("计划任务已经启动,忽略启动命令..");
			SpringBeanFactory.getBean(ScheduleConfig.SCHEDULER_ID);
		}else{
			DynamicLoadBean load = CrmBeanFactory.getBean(DynamicLoadBean.class);
			load.loadBean(ScheduleConfig.SCHEDULER_ID,ScheduleConfig.SCHEDULER_BEAN.getName());
		}	
		return SpringBeanFactory.getBean(ScheduleConfig.SCHEDULER_ID);
	}


	/**根据配置信息创建计划任务*/
	public static void addTask(ScheduleConfig scheduleConfig) throws Exception {
		DynamicLoadBean loader=CrmBeanFactory.getBean(DynamicLoadBean.class);
		loader.loadBeanFromStirng(scheduleConfig.toXml());
		
		
		String jobName = scheduleConfig.getJobName();
		String triggerId=scheduleConfig.getTriggerId();
		
		JobDetail jobDetail =SpringBeanFactory.getBean(jobName);
		jobDetail.setGroup(scheduleConfig.getGroupName());
		Trigger trigger=SpringBeanFactory.getBean(triggerId);
		trigger.setGroup(scheduleConfig.getGroupName());
		
		getScheduler().addJob(jobDetail, false);
		getScheduler().scheduleJob(trigger);
		log.debug("新增定时任务:"+scheduleConfig.getGroupName()+"."+scheduleConfig.getJobName());
	}
	

	public static void pauseJob(String jboName,String groupName) {
		try {		
			getScheduler().pauseJob(jboName, groupName);
			log.debug("暂停任务:"+groupName+"."+jboName);
		} catch (SchedulerException e) {
			throw new RuntimeException();
		}
	}
	
	public static void pauseJobGroup(String groupName) {
		try {
			getScheduler().pauseJobGroup(groupName);
			log.debug("暂停任务组:"+groupName);
		} catch (SchedulerException e) {
			throw new RuntimeException();
		}
	}
}
