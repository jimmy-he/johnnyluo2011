package com.crm.framework.action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.crm.extend.business.module.model.Module;
import com.crm.framework.common.Constant;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.exception.SystemException;
import com.crm.framework.common.ui.TemplateUtil;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.util.LogUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.crmbean.cache.DatasourceInfoCache;
import com.crm.framework.crmbean.interfaces.CrmDatasource;
import com.crm.framework.dao.enums.DatasourceType;
import com.crm.framework.service.ServiceFactory;
import com.crm.framework.service.SimpleService;
import com.crm.framework.test.TestUtil;

/**
 * 
 * @author 王永明
 * @since 2010-12-29 下午04:32:34
 */
public class FreemarkAction extends SimpleAction {
	private static Logger log = Logger.getLogger(FreemarkAction.class);
	/**  所有freemar全都转发到json界面 */
	public String getForwardPage() {
		if (exception != null) {
			if(exception instanceof SystemException){
				return  GlobalForward.info.getPath();
			}
			return  GlobalForward.error.getPath();
		}
		return GlobalForward.json.toString();
	}
	
	/**要向页面写的内容*/
	public Object getJson() {
		return this.getResult();
	}
	
	public String getResult(){
		try{
			String text=TemplateUtil.getTemplateText(this.getMap(), this.getTemplate());
			return text;
		}catch(Exception ex){
			exception=ex;
			return null;
		}
	}
	
	public Map getMap(){
		Map map=new HashMap();
		map.put("webRoot", Constant.WEBROOT);
		map.put("user", this.getUser());
		map.put("system", this.getSystem());
		Set<String> fields = new HashSet();
		for (Method method : this.getClass().getDeclaredMethods()) {
			String methodName = method.getName();
			if (method.getParameterTypes().length != 0) {
				continue;
			}
			if (methodName.startsWith("get") && !methodName.equals("get")) {
				String name = StringUtil.getFieldName(method.getName());
				fields.add(name);
			}
		}
		for(String field:fields){
			if(field.equals("result")){
				continue;
			}
			if(field.equals("josn")){
				continue;
			}
			if(field.equals("template")){
				continue;
			}
			if(field.equals("map")){
				continue;
			}
			
			map.put(field, BeanUtil.getValue(this, field));
		}
		return map;
	}
	


	public String getTemplate() {
		try {
			// 如果出现异常转发到异常界面
			String forwardParam = this.getParameter("forward");
			// 请求带参数,根据参数转
			if (forwardParam != null) {
				String forward = PageForward.getGlobalForward(forwardParam);
				//如果不是以.ftl结尾就加上.jsp并转发页面前加上模块名:例如prepareUpdae变为userPrepareUpdate.jsp
				if(!forward.endsWith("."+"ftl")){
					forward= this.getModelName()+StringUtil.firstUp(forward)+"."+"ftl";
				}	
				// 如果不是以'/'开头,则转到action对应目录路径
				if (!forward.startsWith("/")) {
					forward = PageForward.getDir(this.getClass()) +"/"+ forward;
				}
			
				log.debug("请求参数带forward,直接返回界面:" + forward);
				return forward;
			}
			// 通过方法名正常转
			String method = CrmBeanFactory.getBean(ContextHolder.class).getActionMethod();
			String forward = PageForward.getForward(this.getClass(), method);
			//将结尾的jsp替换成ftl
			forward=forward.replaceAll("jsp$", "ftl");
			log.debug("通过方法名自动匹配转发页面:" + forward);
			return forward;

		} catch (Exception ex) {
			this.exception=ex;
			log.error(LogUtil.getExceptionText(ex));
			return null;
		}
	}
	

}
