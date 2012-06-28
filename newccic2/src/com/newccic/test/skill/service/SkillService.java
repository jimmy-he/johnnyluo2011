package com.newccic.test.skill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.newccic.test.skill.dao.SkillDao;

/**
 ***********************************************
 * @Title     StudentService.java					   
 * @Pageage   com.skill.service				   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @since 1.0 创建时间 2012-6-27 下午04:59:24		   
 ***********************************************
 */
public class SkillService {

	@Autowired @Qualifier("userDao")
	protected SkillDao skillDao ;
	
	public void  getSkillId(){
		
		ApplicationContext ctx = new FileSystemXmlApplicationContext("resources/spring/applicationContext-bean.xml"); 
		skillDao=(SkillDao) ctx.getBean("userDao");
		String skillId= skillDao.getSkillId("0001");
		System.out.println(skillId);
	}
	public static void main(String[] args) {

		
		SkillService skillService=new SkillService();
		skillService.getSkillId();
	}
}
