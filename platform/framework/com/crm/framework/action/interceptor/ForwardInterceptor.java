package com.crm.framework.action.interceptor;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.crm.framework.action.CrmBaseAction;
import com.crm.framework.common.Global;
import com.crm.framework.common.config.PathConvertor;
import com.crm.framework.common.enums.TemplateType;
import com.crm.framework.common.util.ExceptionUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.dao.hibernate.DatasourceProvider;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 为了将action的返回值设为forward了,所以只能调用invokeActionOnly,因此必须放在最后一个
 * 
 * @author 王永明
 * @since Apr 12, 2009 2:59:37 PM
 */
@Component
public class ForwardInterceptor extends AbstractInterceptor {
	private static final Logger log = Logger.getLogger(ForwardInterceptor.class);

	public String intercept(ActionInvocation invocation) throws Exception {
		try{
			
			//设置当前使用的数据源为用户所在所在租户使用的数据源
			String dsId=com.crm.framework.cache.Global.getCurrentSession().getUser().getTenant().getDataSourceId();
			DataSource ds=DatasourceProvider.getDatasource(dsId);
			com.crm.framework.cache.Global.setDataSource(ds);
			
			
			invocation.invokeActionOnly();
		}catch(Exception ex){
			CrmBaseAction baseAction = (CrmBaseAction) invocation.getAction();	
			Throwable e=ExceptionUtil.getOriginException(ex);
			if(e instanceof Exception){
				baseAction.setException((Exception)e);
			}else{
				throw new RuntimeException(ex);
			}
			
		}catch(Throwable tr){
			throw new RuntimeException(tr);
		}
		return Global.getConfig().getViewPostfix();
	}

}
