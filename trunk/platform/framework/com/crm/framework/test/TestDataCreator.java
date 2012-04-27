package com.crm.framework.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.crm.base.config.agent.model.AgentInfo;
import com.crm.base.permission.organ.model.Organ;
import com.crm.base.permission.user.model.User;
import com.crm.base.record.info.model.Record;
import com.crm.framework.common.util.CollectionUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.service.ServiceFactory;

/**
 * 创建测试数据
 * @author 王永明
 * @since May 24, 2010 12:25:26 PM
 */
public class TestDataCreator {
	/**获得一些随机数*/
	public static long getNumber(int length){
		return (long) ((Math.random())*Math.pow(10, length));
	}

	
	/**造一票模拟用户*/
	public static  List createTestuers(){
		List list=ServiceFactory.getDefaultService().queryAll(User.class);
		if(list.size()>1){
			return list;
		}
		
		List<Organ> organs=createTestOrgans();
		list=new ArrayList();
		list.add(createUser("上海坐席1,6001",organs));
		list.add(createUser("北京坐席1,6002",organs));
		list.add(createUser("深圳坐席1,6003",organs));
		list.add(createUser("广东坐席1,6004",organs));
		list.add(createUser("福州坐席1,6005",organs));
		list.add(createUser("深圳坐席2,6006",organs));
		list.add(createUser("北京坐席2,6007",organs));
		list.add(createUser("广州坐席1,6008",organs));
		list.add(createUser("广州坐席2,6009",organs));
		list.add(createUser("广东坐席3,6010",organs));
		
		ServiceFactory.getDefaultService().insert(list);
		
		
		//给坐席工号
		List ags=new ArrayList();
		for(Object o:list){
			User user=(User)o;
			AgentInfo ag=new AgentInfo();
			ag.setUserId(user.getUserId());
			ag.setAgentId(user.getAgentId());
			ag.setUserd("Y");
			ag.setTenantId(user.getTenantId());
			ags.add(ag);
		}
		
		ServiceFactory.getDefaultService().insert(ags);
		
		return list;
	}
	
	public static User createUser(String str,List<Organ> organs){
		
		
		String[] info=str.split(",");
		User user=new User();
		user.setUserCode(TestDataCreator.getNumber(10)+"");
		user.setUserName(info[0]);
		user.setAgentId(info[1]);
		user.setVain("Y");
		user.setInit("Y");
		user.setPassword(StringUtil.toMd5(user.getUserCode()));
		for(Organ organ:organs){
			if(user.getUserName().startsWith(organ.getOrganName())){
				user.setOrganId(organ.getOrganId());
				user.setTenantId(organ.getTenantId());
				break;
			}
		}
		
		
		return user;
	}
	
	
	
	
	
	/**造一票模拟机构*/
	public static  List createTestOrgans(){
		List list=ServiceFactory.getDefaultService().queryAll(Organ.class);
		if(list.size()>1){
			return list;
		}
		list=new ArrayList();
		list.add(createOrgan("1,北京,rootOrganId"));
		list.add(createOrgan("2,天津,rootOrganId"));
		list.add(createOrgan("3,上海,rootOrganId"));
		list.add(createOrgan("4,广东,rootOrganId"));
		list.add(createOrgan("401,广州,4"));
		list.add(createOrgan("402,深圳,4"));
		list.add(createOrgan("403,汕头,4"));
		list.add(createOrgan("5,福建,rootOrganId"));
		list.add(createOrgan("501,福州,5"));
		list.add(createOrgan("502,厦门,5"));
		list.add(createOrgan("503,泉州,5"));	
		ServiceFactory.getDefaultService().insert(list);
		return list;
	}
	
	private static Organ createOrgan(String str ){
		String[] info=str.split(",");
		Organ organ=new Organ();
		organ.setOrganCode(info[0]);
		organ.setOrganName(info[1]);
		organ.setOrganId(info[0]);
		organ.setParentId(info[2]);
		organ.setTenantId("default");
		return organ;
	}
	
	/**创建录音*/
	public static List<Record> createRecord(){
		List list=ServiceFactory.getDefaultService().queryAll(Record.class);
		
		if(list.size()>0){
			return list;
		}
		
		list=new ArrayList();
		List users=TestDataCreator.createTestuers();
		for(int i=0;i<100;i++){
			User user=(User) CollectionUtil.getRandom(users);
			list.add(createRecord("",user));
		}
		ServiceFactory.getDefaultService().insert(list);
		return list;
	}
	
	private static Record createRecord(String str,User user){
		String[] info=str.split(",");
		Record r=new Record();
		r.setAni(TestDataCreator.getNumber(10)+"");
		r.setDnis(TestDataCreator.getNumber(10)+"");
		r.setTenantId(user.getTenantId());
		r.setUserCode(user.getUserCode());
		r.setUserId(user.getUserId());
		r.setRecordId(info[0]);
		r.setOrganId(user.getOrganId());
		r.setAgentId(user.getAgentId());
		r.setCreateTime(new Date());
		return r;
	}
	
	public static void main(String[] args) {
		ServiceFactory.getDefaultService().execHql(User.class,"delete from User where userId!='userAdmin'");
		ServiceFactory.getDefaultService().execHql(Organ.class,"delete from Organ where organId!='rootOrganId'");
		ServiceFactory.getDefaultService().execHql(AgentInfo.class,"delete from AgentInfo");
		ServiceFactory.getDefaultService().execHql(Record.class,"delete from Record");
		TestUtil.setMappingClass(Record.class,User.class);
		TestDataCreator.createRecord();
	}
	
}
