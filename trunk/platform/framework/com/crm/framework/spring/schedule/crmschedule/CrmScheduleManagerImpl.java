package com.crm.framework.spring.schedule.crmschedule;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.config.PathConfig;
import com.crm.framework.common.util.JavaTypeUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.common.util.xml.XmlUtil;
import com.crm.framework.spring.schedule.crmschedule.vo.CrmSchedule;
import com.crm.framework.spring.uitl.ClassSearchUitl;

/**
 * 
 * @author 王永明
 * @since Apr 7, 2010 3:43:05 PM
 */
public class CrmScheduleManagerImpl  implements CrmScheduleManager{
	private static Logger log=Logger.getLogger(CrmScheduleManagerImpl.class);
	public String getCrmSchedules() {
		return XmlUtil.toXml(this.getCrmSchedulesList());
	}
	
	public List<CrmSchedule> getCrmSchedulesList(){
		List<CrmSchedule> list=new ArrayList();
		List<Class>	cls=ClassSearchUitl.getClass(PathConfig.scheduleClass.getPath());
		for(Class clazz:cls){
			if(JavaTypeUtil.isInstance(clazz, CrmScheduleBean.class)){
				CrmScheduleBean bean=(CrmScheduleBean) CrmBeanFactory.getBean(clazz);
				CrmSchedule crmSchedule=new CrmSchedule();
				crmSchedule.setCronExpression(bean.getCronExpression());
				crmSchedule.setTaskClass(clazz.getName());
				list.add(crmSchedule);
			}
		}
		log.debug("找到计划任务类:"+StringUtil.getLine()+list);
		return list;
	}

}
