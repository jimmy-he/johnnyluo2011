package com.tinet.ccic.wm.commons.web;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.ParameterNameAware;
import com.tinet.ccic.wm.commons.Constants;
import com.tinet.ccic.wm.commons.CCICException;
import com.tinet.ccic.wm.commons.service.BaseService;
import com.tinet.ccic.wm.commons.util.BeanUtils;
import com.tinet.ccic.wm.commons.util.GenericsUtils;

/**
 * 负责管理单个Entity CRUD操作的Struts Action基类. 子类以以下方式声明,并实现将拥有默认的CRUD函数
 * 此类仅演示一种封装的方式，大家可按自己的项目习惯进行重新封装。 目前封装了：
 * <p/>
 * 1.list、view、edit、save、delete 五种action的流程封装；
 *<p>
 * 文件名： BaseAction.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
@SuppressWarnings("unchecked")
abstract public class BaseAction<T> 
	extends ComnAction implements ModelDriven<T>, Preparable, ParameterNameAware {
	
	protected static String ENTITY_MODEL = "entity_model";
	
	/** Action所管理的Entity类型. */
	protected Class<T> entityClass;
	
	protected T entityForm;

	/** 是否处理关联Id项 */
	protected boolean needCheckCascadeId = true; 
	
	/** 查询列表 */
	abstract public String list();
	
	/** 编辑实体时，请求某一个实体时的请求 */
	abstract public String edit();
	
	/** 查看一个实体时的请求 */
	abstract public String view();
	
	/** 保存对象的Action函数. */
	abstract public String save();
	
	/** 删除对象的Action函数.出错后转向web.xml文件中配置的统一处理页面。 */
	abstract public String delete();
	
	/** 逻辑删除 */
	abstract public String markDelete();
	
	
	/** preparable接口要实现的方法，现在写成空实现 */
	public void prepare() throws Exception {
		// donothing
	}
	
	/** Edit前要进行的动作，可以重写这个方法 */
	public void prepareEdit() throws Exception {
		prepareModel();
	}
	
	/** 在save()前执行二次绑定. */
	public void prepareSave() throws Exception {
		prepareModel();
	}
	
	public void prepareList() throws Exception {
		
	}
	
	public void prepareView() throws Exception {
		prepareModel();
	}
	
	/**
	 * 获得EntityManager类，必须在子类实现
	 */
	abstract protected BaseService<T> getBaseService();
	
	protected Serializable getKey() {
		String id = this.getParameter(Constants.DEFAULT_ID_NAME);
		if (StringUtils.isBlank(id)) {
			return null;
		}
		return Integer.parseInt(id);
	}

	public T getModel() {
		return this.entityForm;
	}
 
	protected void prepareModel() throws Exception {
		// 获得有效的参数(主键)
		Serializable id = this.getKey();
		
		if (id != null) {
			logger.debug("准备实体{}@{}", this.getEntityClass().getName(), id);
			this.entityForm = getBaseService().get(id);
		} else {
			if (entityForm == null) {
				entityForm = getNewEntity();
			}
		}

		if (needCheckCascadeId) {
			handleCascadeId();
		}
		
	}

	/**
	 * 取得entityClass的函数. JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 构造函数. 通过对T的反射获得entity class
	 */
	public BaseAction() {
		try {
			entityClass = GenericsUtils.getGenericClass(getClass());
		} catch (Exception e) {
			logger.error("初始化action时出错", e);
		}
	}

	/**
	 * url参数未定义method时的默认Action函数. 默认为分页查询List Action.
	 */
	public String execute() {
		return list();
	}

	/**
	 * 新建业务对象的函数.
	 */
	protected T getNewEntity() {
		T object = null;
		Class<T> clazz = getEntityClass();
			
		try {
			object = clazz.newInstance();
		} catch (Exception e) {
			logger.error("初始化操作实体时出错", e);
			throw new CCICException("SYS00000", e);
		}
		
		return object;
	}
	
	
	public boolean acceptableParameterName(String parameterName) {
		if ( "id".equals(parameterName) 
				&& StringUtils.isBlank(this.getParameter(parameterName)) ) {
			return false;
		}
		
		// 如果以.id结尾，则要致其对应的属性字段为空
		if (parameterName.endsWith(".id")) {
			String property = StringUtils.substringBeforeLast(parameterName, ".id");
			
			if (StringUtils.isBlank(this.getParameter(parameterName)) 
					&& property.indexOf('.') == -1 && this.getModel() != null) {	
				try {
					BeanUtils.setProperty(this.getModel(), property, null);
				} catch (Exception e) {
					// 不做任何事情，只是为了对付hibernate映射的问题
				}
			}
			return false;
		}

		return true;
	}
	
	
	private void handleCascadeId() {
		ActionContext ac = ActionContext.getContext();
		T entityForm = this.getModel();
		
        Map parameters = ac.getParameters();
        
        for (Iterator iterator = parameters.entrySet().iterator(); iterator.hasNext();) {
        	Map.Entry entry = (Map.Entry)iterator.next();
        	String key = (String)entry.getKey();
        	
        	// 如果是以id结尾，则例行检查
        	if ( key.endsWith(".id") ) {
    			String property = key.substring(0, key.length()-3);
    			String[] arrId = (String[])entry.getValue();
    			if (property.indexOf('.') != -1 || arrId.length > 1) {	
    				// 如果是多重关联，则不予处理
    				continue ;
    			}
    		
    			try {
	    			if (StringUtils.isBlank(arrId[0])) {
		    			// 如果id为空，则将此属性置为空
	    				PropertyUtils.setProperty(entityForm, property, null);
	    			} else {
	    				PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(entityForm, property);
	    				Object cascadeProperty = this.getBaseService().get(pd.getPropertyType(), arrId[0]);
	    				PropertyUtils.setProperty(entityForm, property, cascadeProperty);
	    			}
    			} catch (Exception e) {
					// 不做任何事情，只是为了对付hibernate映射的问题
				}
        	}
        }
	}

}