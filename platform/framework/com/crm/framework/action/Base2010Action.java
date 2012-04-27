package com.crm.framework.action;

import com.crm.framework.dao.selector.Query;
import com.crm.framework.service.ServiceFactory;
import com.crm.framework.service.SimpleService;

/**
 * 针对10界面的样式弄的action类,将每页默认显示条数设为10条
 * @author 王永明
 * @since 2010-10-30 上午11:12:33
 */
public class Base2010Action extends SimpleAction {
	public static final int ROWS_PER_PAGE=10; 
	
	
	/**查询*/
	public void query(){
		SimpleService service=ServiceFactory.getService(SimpleService.class);
		Query query=Query.forClass(this.getTarget().getClass());
		query.addSample(this.getTarget());
		if(pageRecord!=null){
			query.setOrder(pageRecord.getOrderField(), pageRecord.getOrderType());
		}
		pageRecord=service.queryPage(query, 1, ROWS_PER_PAGE);	
	}
}
