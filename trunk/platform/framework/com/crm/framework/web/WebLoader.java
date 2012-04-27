package com.crm.framework.web;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.crm.framework.action.CrmBaseAction;
import com.crm.framework.common.Global;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.config.PathConvertor;
import com.crm.framework.common.config.PathInfoCache;
import com.crm.framework.common.config.vo.FrameworkConfig;
import com.crm.framework.common.config.vo.FrameworkConstanct;
import com.crm.framework.common.i18n.DefaultTextProvider;
import com.crm.framework.common.rmi.RmiFacadeImpl;
import com.crm.framework.common.util.JavaTypeUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.common.util.file.FileMonitor;
import com.crm.framework.common.vo.MemoryInfo;
import com.crm.framework.common.vo.SystemInfo;
import com.crm.framework.common.web.WebConfig;
import com.crm.framework.crmbean.defaultbean.DefaultBeanCache;
import com.crm.framework.crmbean.defaultbean.DefaultBeanCache.BeanType;
import com.crm.framework.dao.hibernate.DbCreator;
import com.crm.framework.service.ServiceFactory;
import com.crm.framework.spring.DynamicLoadBean;
import com.crm.framework.spring.SpringBeanFactory;
import com.crm.framework.spring.enums.BeanScope;
import com.crm.framework.spring.uitl.ClassSearchUitl;
import com.crm.framework.web.fileListener.FileListenerCache;
import com.crm.framework.web.fileListener.FileListenerConfig;

/**
 * web工程启动加载器
 * @author 王永明
 * @since Mar 21, 2010 2:03:42 PM
 */
public class WebLoader {
	
	private static Logger log=Logger.getLogger(WebLoader.class);
	
	public ServletContextEvent event;
	
	public static FileMonitor fileMonitor;
	
	public void setServletContextEvent(ServletContextEvent event){
		this.event=new MyServletContextEvent(event.getServletContext(), event);
	}
	
	public ServletContextEvent getEvent() {
		return event;
	}

	/**保存spring信息*/
	public void saveSpring(){
		if(event==null){
			log.error("ServletContextEvent为空spring将直接读取本地文件");
			SpringBeanFactory.getApplicationContext();
			return ;
		}
		ServletContext context = event.getServletContext();
		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		SpringBeanFactory.setApplicationContext(applicationContext);
	}
	

	
	public void setServerInfo(){
		if(event==null){
			log.debug("ServletContextEvent为空,无法读取服务器信息");
			return;
		}
		// 设置web服务器信息
		String serverInfo = event.getServletContext().getServerInfo();
		WebConfig wc=CrmBeanFactory.getBean(WebConfig.class) ;
		wc.setWebServiceFullName(serverInfo);
		
		String webRoot=wc.getWebServer().getWebServerInfo().getWebRoot(event.getServletContext());
		PathInfoCache cache=CrmBeanFactory.getBean(PathInfoCache.class);
		cache.setWebRoot(webRoot);		
		log.info("使用的服务器为:"+wc.getWebServer());
	}
	
	public void validateMemory() {
		//开始验证内存
		MemoryInfo memoryInfo = new MemoryInfo();
		log.info(memoryInfo.getFullInfo());
		if(!Global.getConfig().getValidateMemory().equals("true")){
			return;
		}
		if (memoryInfo.getMaxMeomory() < 256 * 1024 * 1024) {
			log.error("分配给java虚拟机的内存太小,请在启动参数中将虚拟机最大可用内存调大(例:-Xmx512m -XX:PermSize=256M  -XX:MaxPermSize=512m),参数值要在512以上.");
			throw new java.lang.OutOfMemoryError();
		}
	}
	
	
	/**
	 * 因为spring读取配置文件路径是调用event.getServletContext().getInitParameter("contextConfigLocation"),
	 * 我们系统的spring配置文件路径是通过PathConvertor.getSpringConfigFile()获得的,但ServletContext没有getInitParameter方法,
	 * 所以通过ServletContext和ServletContextHandle实现修改InitParameter的功能
	 */
	private class MyServletContextEvent extends ServletContextEvent {
		private ServletContextEvent event;

		public MyServletContextEvent(ServletContext source, ServletContextEvent event) {
			super(source);
			this.event = event;
		}

		public ServletContext getServletContext() {
			ServletContext context = super.getServletContext();
			InvocationHandler handler = new ServletContextHandle(context);
			ServletContext proxy = (ServletContext) Proxy.newProxyInstance(context.getClass().getClassLoader(), context
					.getClass().getInterfaces(), handler);
			return proxy;

		}
	}

	public void resetServerInfo(String serverName,String ip,String port,String contenxtPath){
		FrameworkConfig config=Global.getConfig();
		config.setWebPort(port);
		config.setWebRoot(contenxtPath);
		if(serverName.indexOf('.')!=-1){
			config.setLocalIp(serverName);
		}
		this.event.getServletContext().setAttribute("webRoot", contenxtPath);
	}
	
	
	private class ServletContextHandle implements InvocationHandler {
		private ServletContext target;

		public ServletContextHandle(ServletContext target) {
			this.target = target;
		}

		/**
		 * 如果方法名是getInitParameter,参数是contextConfigLocation则调用PathConvertor.getSpringConfigLocation();
		 * 否则使用原方法
		 */
		public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
			if (method.getName().equals("getInitParameter")) {
				if (args.length == 1 && args[0].equals("contextConfigLocation")) {
					String[] paths = PathConvertor.getSpringConfigLocation();
					StringBuilder sb = new StringBuilder();
					for (String path : paths) {
						log.debug("找到spring配置文件:"+path);
						sb.append("classpath:" + path + ";");
					}
					return sb.toString();
				}
			}
			return method.invoke(target, args);
		}

	}

	/**将所有action加入到spring容器内*/
	public void addActions() {
		DynamicLoadBean loader=CrmBeanFactory.getBean(DynamicLoadBean.class);
		List actions=new ArrayList();
		
		List<Class> list=ClassSearchUitl.getClass("com.crm.**.action");
		for(Class cla:list){
			if(cla==CrmBaseAction.class){
				continue;
			}
			if(JavaTypeUtil.isInstance(cla, CrmBaseAction.class)){
				String id=StringUtil.firstLower(cla.getSimpleName());
				loader.loadBean(id,cla.getName(), BeanScope.prototype);		
				actions.add(cla.getName());
			}
		}
		log.debug("spirng内新增action"+StringUtil.getLine()+actions);
	}

	
	/**验证外围系统是否启动*/
	public void validateNet() {
		//如果需要打开本机rmi服务
		if(Global.getConfig().getHasRmi().equals("true")){
			RmiFacadeImpl.startRmi(RmiFacadeImpl.class,SystemInfo.newInstance().getRmiPort());
		}
		
		
	}

	public void validateDateBase() {
		
		if(Global.getConfig().getValidateDataBase().equals("true")){
			log.debug("开始验证数据库");
			DbCreator.updaeAll();		
		}
		Class clazz=DefaultBeanCache.getBean(BeanType.server).getClass();
		ServiceFactory.getDefaultService().get(clazz, FrameworkConstanct.defaultServerId.getValue());	
	}
	
	
	public void startup(){
		//启动文件监听器
		if(Global.getConfig().getStartFileListener().equals("true")){
			log.debug("启动文件监听器");
			this.startFileMonitor();
		}else{
			log.debug("不启动文件监听器");
		}
		//创建国际化信息
		CrmBeanFactory.getBean(DefaultTextProvider.class);
		//保存服务器信息
		this.setServerInfo();
		//验证内存大小
		this.validateMemory();
		//验证网络情况,包括其他服务器是否启动
		this.validateNet();
		//验证数据库
		this.validateDateBase();
		
	}
	
	
	public void startFileMonitor(){
		fileMonitor=new FileMonitor();
		
		List<FileListenerConfig> list= FileListenerCache.getListenrs();
		for(FileListenerConfig config:list){
			for(File f:config.getFile()){
				fileMonitor.addFile(f);
			}	
			fileMonitor.addListener(config.getListener());
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		WebLoader f=new WebLoader();
		f.startFileMonitor();
		Thread.sleep(Long.MAX_VALUE);
	}
}
