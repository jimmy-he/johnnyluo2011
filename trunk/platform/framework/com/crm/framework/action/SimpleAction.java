package com.crm.framework.action;

import java.util.List;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.enums.ClassType;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.vo.SystemInfo;
import com.crm.framework.crmbean.interfaces.CrmUser;
import com.crm.framework.dao.selector.PageRecord;
import com.crm.framework.model.MappingCache;
import com.crm.framework.service.ServiceFactory;
import com.crm.framework.service.SimpleService;
import com.crm.framework.ui.UISession;
import com.crm.framework.validate.NotValidate;
import com.crm.framework.validate.SimpleValidate;

/**
 * 一些简单操作的
 * @author 王永明
 * @since Apr 8, 2010 3:08:49 PM
 */
abstract public class SimpleAction extends CrmBaseAction {
	protected PageRecord pageRecord=new PageRecord();
	
	
	/**新增*/
	public void insert(){
		this.getValidate().insert(this);
		SimpleService service=ServiceFactory.getService(SimpleService.class);
		service.insert(this.getTarget());
	}
	
	/**删除*/
	public void delete(){
		this.getValidate().delete(this);
		SimpleService service=ServiceFactory.getService(SimpleService.class);
		service.delete(this.getTarget());
		this.writeJson("success");
	}
	
	/**批量删除*/
	public void deleteList(){
		this.getValidate().deleteList(this);
		SimpleService service=ServiceFactory.getService(SimpleService.class);
		service.deleteList(this.getList());
		this.writeJson("success");
	}
	
	/**更新数据,忽略为null的字段*/
	public void update(){
		this.getValidate().update(this);
		SimpleService service=ServiceFactory.getService(SimpleService.class);
		Object obj=this.getTarget();
		Class claz=this.getTarget().getClass();
		String name=MappingCache.getMapping(claz).getPkName();
		String id=(String) BeanUtil.getValue(obj, name);
		service.update(claz,id,this.getTarget());
	}
	
	/**更新数据,包括为null的字段*/
	public void updateFull(){
		this.getValidate().updateFull(this);
		SimpleService service=ServiceFactory.getService(SimpleService.class);	
		service.updateFull(this.getTarget());
	}
	
	/**查询*/
	public void query(){
		this.getValidate().query(this);
		SimpleService service=ServiceFactory.getService(SimpleService.class);
		if(pageRecord==null){
			pageRecord=service.queryPageBySample(this.getTarget());
		}else{
			pageRecord=service.queryPageBySample(this.getTarget(),pageRecord.getOrderField(),pageRecord.getOrderType());
		}
		
	}
	
	/**下一页*/
	public void queryContinue(){
		this.getValidate().queryContinue(this);
		SimpleService service=ServiceFactory.getService(SimpleService.class);
		pageRecord=service.queryPageContinue(pageRecord);
	}
	
	/**查看详细*/
	public void view(){
		this.getValidate().view(this);
		SimpleService service=ServiceFactory.getService(SimpleService.class);
		Object obj=this.getTarget();
		Class claz=this.getTarget().getClass();
		String name=MappingCache.getMapping(claz).getPkName();
		String id=(String) BeanUtil.getValue(obj, name);
		Object target=service.get(this.getTarget().getClass(), id);
		this.setTarget(target);
	}

	public PageRecord getPageRecord() {
		return pageRecord;
	}

	public void setPageRecord(PageRecord pageRecord) {
		this.pageRecord = pageRecord;
	}

	
	public Object getTarget(){
		return BeanUtil.getValue(this, this.getModelName());
	}
	
	public void setTarget(Object obj){
		BeanUtil.setField(this, this.getModelName(), obj);
	}
	
	private List getList(){
		return (List) BeanUtil.getValue(this, this.getModelName()+"s");
	}
	
	
	public SimpleValidate getValidate(){
		if("true".equals("Global.getConfig().getNeedValidate()")){
			Class clazz= ClassType.action.getOrtherType(this.getActionClsss(), ClassType.validate);
			return (SimpleValidate) CrmBeanFactory.getBean(clazz);
		}
		return new NotValidate();
	}
	
	public Class getActionClsss(){
		return this.getClass();
	}

	public CrmUser getCurrentUser() {
		return ContextHolder.getContext().getCurrentUser();
	}
	
	public CrmUser getUser() {
		return ContextHolder.getContext().getCurrentUser();
	}
	
	public SystemInfo getSystem(){
		return SystemInfo.newInstance();
	}
	
	public UISession getCrmSession(){
		return UISession.getCurrent();
	}
	
}
