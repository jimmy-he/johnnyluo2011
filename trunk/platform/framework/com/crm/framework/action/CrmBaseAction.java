package com.crm.framework.action;

import org.apache.log4j.Logger;

import com.crm.framework.common.Global;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.exception.SystemException;
import com.crm.framework.common.ui.TemplateUtil;
import com.crm.framework.common.util.ExceptionUtil;
import com.crm.framework.common.util.JsonUtil;
import com.crm.framework.common.util.LogUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.dao.transaction.TransactionUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.LoggerUtils;

/**
 * 
 * @author 王永明
 * @since Jan 28, 2010 6:24:29 PM
 */
public class CrmBaseAction extends ActionSupport {
	public static final String CURRENT_METHOD = "currentMethod";
	/** 要转发页面的url */
	private String url;
	private static Logger log = Logger.getLogger(CrmBaseAction.class);
	protected Exception exception;
	private Object json;
	
	/**查看详细界面是否要执行onload方法,因为该页面会被别的页面包含,此时onload方法就不应该执行了*/
	private boolean onload=true;


	/**该页面是否是通过转发过来的*/
	private boolean redirect;

	/** 请求转发页面,此参数struts-system.xml文件里用到的,实际的入参名称为forward */
	public String getForwardPage() {
		try {
			// 如果出现异常转发到异常界面
			if (exception != null) {
				if(exception instanceof SystemException){
					return  GlobalForward.info.getPath();
				}
				return  GlobalForward.error.getPath();
			}

			String forwardParam = this.getParameter("forward");
			// 请求带参数,根据参数转
			if (forwardParam != null) {
				String forward = PageForward.getGlobalForward(forwardParam);
				//如果不是以.jsp结尾就加上.jsp并转发页面前加上模块名:例如prepareUpdae变为userPrepareUpdate.jsp
				if(forward.indexOf(".")==-1){
					if(!forward.endsWith("."+Global.getConfig().getViewPostfix())){
						forward= this.getModelName()+StringUtil.firstUp(forward)+"."+Global.getConfig().getViewPostfix();
					}
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
			log.debug("通过方法名自动匹配转发页面:" + forward);
			return forward;

		} catch (Exception ex) {
			this.exception=ex;
			log.error(LogUtil.getExceptionText(ex));
			return GlobalForward.error.getPath();
		}
	}

	protected String getParameter(String key) {
		if(StringUtil.isNotNull(this.forward)){
			return forward;
		}
		String[] forwardValue = (String[]) ActionContext.getContext().getParameters().get(key);
		if (forwardValue == null || forwardValue.length == 0) {
			return null;
		}
		return forwardValue[0];
	}


	/**将对象以转换成json对象写到页面*/
	protected void writeJson(Object obj){
		json=obj;
	}


	public void setException(Exception ex) {
		this.exception = ex;
	}

	public String getException() {
		String text="";
		Throwable tr= ExceptionUtil.getOriginException(exception);
		if(tr instanceof SystemException){
			text=tr.getMessage();
		}else{
			text=(LogUtil.getExceptionText(exception));
			log.error(text);
		}	
		return text;
	}

	public String getUrl() {
		url=url.replace("%{", "${");
		url=TemplateUtil.getText(this, url);
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Object getJson() {
		String str= JsonUtil.toString(json);
		log.debug("向页面写json对象:"+str);
		return str;
	}

	
	protected String getModelName(){
		String name=this.getClass().getSimpleName();
		return StringUtil.firstLower(name.replaceAll("Action$", ""));
	}

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
	
	//@edit 新增在action层设置转发的jsp,不到万不得已请不要设置此值
	private String forward;
	protected void setForward(String forward) {
		this.forward = forward;
	}
	
	public boolean getOnload() {
		return onload;
	}

	public void setOnload(boolean onload) {
		this.onload = onload;
	}
	

}
