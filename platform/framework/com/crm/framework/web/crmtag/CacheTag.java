package com.crm.framework.web.crmtag;

import com.crm.base.permission.cache.OrganCache;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.cache.CrmCache;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.util.LogUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.crmbean.cache.TenantCache;
import com.crm.framework.crmbean.cache.UserCache;

/**
 * 显示缓存信息标签
 * @author 王永明
 * @since 2010-10-31 下午12:26:25
 */
public class CacheTag extends SimpleTag  {	
	
	/**目前可用的缓存类型*/
	enum CacheClass{
		organ(OrganCache.class),tenant(TenantCache.class),user(UserCache.class);		
		private Class clazz;
		CacheClass(Class clazz){
			this.clazz=clazz;
		}
	} 
	
	/**缓存类型*/
	private String type;
	/**缓存主键*/
	private String id;
	/**显示属性*/
	private String property;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	@Override
	protected String getPrintStr() throws Exception {
		Class clazz=CacheClass.valueOf(this.getType()).clazz;
		CrmCache cache=(CrmCache) CrmBeanFactory.getBean(clazz);
		if(StringUtil.isNull(id)){
			return "";
		}
		Object obj=cache.get(id);
		Object value=BeanUtil.getValue(obj, this.property);
		if(value!=null){
			return value+"";
		}else{
			return "";
		}
	}

	public String getProperty() {
		return property;
	}
	
	


	public void setProperty(String property) {
		this.property = property;
	}
	
	
	
	

}
