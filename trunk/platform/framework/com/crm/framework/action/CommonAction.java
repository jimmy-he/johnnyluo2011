package com.crm.framework.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.crm.base.permission.common.page.GeneralPage;
import com.crm.framework.common.enums.YesOrNo;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.dao.selector.PageRecord;
import com.crm.framework.dao.selector.PageableSelector;
import com.crm.framework.dao.selector.Selector;
import com.crm.framework.service.ServiceFactory;

/**
 * 通用的action类,一些通用操作的工具类,提供GeneralAction不能提供的功能
 * 用法为 common-synQuery.action?condition.hql=***
 * 
 * @author 王永明
 * @since 2010-9-12 下午05:16:46
 */
@Component
@Scope("prototype")
public class CommonAction extends SimpleAction {
	private static Logger log=Logger.getLogger(CommonAction.class);

	/**因为页面的弹出框只能用generalPage接收参数,所以第一次查询是条件都放在generalPage里*/
	private GeneralPage generalPage;

	/**将查询结果用__隔开组成一个字符串*/
	private List<String> resultString;
	
	private Condition condition;

	/**在查询结果列表里附加的查询条件*/
	private List<Param>  queryParam;
	private String conditionString;
	
	public String getConditionString() {
		return conditionString;
	}


	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
	}


	public List<Param> getQueryParam() {
		return queryParam;
	}


	public void setQueryParam(List<Param> queryParam) {
		this.queryParam = queryParam;
	}


	
	/**通过通用的查询条件查询*/
	public PageRecord queryByCondition(Condition condition) {

		PageableSelector sel=null;
		if(StringUtil.isNotNull(condition.getSql())){
			sel=PageableSelector.forSql(condition.getSql(), 
					condition.getCount(), condition.getOrderField(), 
					condition.getOrderType(), condition.getRealParams());
			log.debug("sql:"+condition.getSql());
		}else{
			sel=PageableSelector.forHql(condition.getHql(), 
					condition.getCount(), condition.getOrderField(), 
					condition.getOrderType(), condition.getRealParams());
			log.debug("hql:"+condition.getHql()+","+condition.getCount());
		}
		PageRecord pageRecord= ServiceFactory.getCurentService().selectPage(sel,this.getPageRecord().getPageNo(), this.getPageRecord().getRowsPerPage());
		List<Object[]> list=pageRecord.getResult();
		resultString=new ArrayList();
		for(Object[] os:list){
			StringBuffer result=new StringBuffer();
			for(Object o:os){
				result.append(o+"__");
			}
			resultString.add(result.substring(0,result.length()-2));
		}
		
		return pageRecord;
	}
	
	/**异步查询*/
	public void synQuery(){
		Selector sel=null;
		if(StringUtil.isNotNull(condition.getSql())){
			sel=Selector.forSql(condition.getSql(), condition.getRealParams());		
			log.debug(condition.getSql());
		}else{
			log.debug(condition.getHql());
			sel=Selector.forHql(condition.getHql(), condition.getRealParams());
		}
		List list=null;
		if(pageRecord!=null){
			list=ServiceFactory.getCurentService().select(sel,pageRecord.getFirstRow(),pageRecord.getRowsPerPage());
		
		}else{
			list=ServiceFactory.getCurentService().select(sel);	
		}

		super.writeJson(list);
	}
	
	
	public void query(){		
		if(condition==null){
			condition=new Condition();
			condition.setSql(generalPage.getSql());
			condition.setHql(generalPage.getHql());
			String qtype=generalPage.getShowType();
			queryParam=new ArrayList();
			if(StringUtil.isNotNull(qtype)){
				String[] queryTypes=qtype.split(",");
				for(String str:queryTypes){
					String[] s=str.split("=");
					Param pm=new Param();
					pm.setCode(s[0]);
					pm.setName(s[1]);
					queryParam.add(pm);
				}
			}
		}
		
		this.pageRecord=this.queryByCondition(condition);
		conditionString=BeanUtil.toString(condition);
		this.setForward("/framework/common/commonQuery.jsp");
	}

	
	public void queryContinue(){
		Condition newCondition=(Condition) BeanUtil.toObject(conditionString);
		newCondition.setPageNo(pageRecord.getPageNo());
		newCondition.setRowsPerPage(pageRecord.getRowsPerPage());

		newCondition.addCondition(queryParam);

		condition=(Condition) BeanUtil.toObject(conditionString);
		this.pageRecord=this.queryByCondition(newCondition)	;
	}
	
	
	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	public void setGeneralPage(GeneralPage generalPage) {
		this.generalPage = generalPage;
	}
	
	public List<String> getResultString() {
		return resultString;
	}


	public void setResultString(List<String> resultString) {
		this.resultString = resultString;
	}


	public GeneralPage getGeneralPage() {
		return generalPage;
	}



	public YesOrNo getMulti() {
		if(generalPage.getCheckBox()){
			return YesOrNo.Y;
		}else{
			return YesOrNo.N;
		}
		
	}

	/**获得显示用查询条件,因为页面上要每次显示两个,所以条件没两个分一组*/
	public List<Param[]> getQueryShowParam() {
		List result=new ArrayList();
		if(queryParam==null||queryParam.size()==0){
			return result;
		}
		if(queryParam.size()%2!=0){
			queryParam.add(new Param());
		}
		
		for(int i=0;i<queryParam.size();i+=2){
			Param[] pm=new Param[]{queryParam.get(i),queryParam.get(i+1)};
			result.add(pm);
		}
		return result;
	}
	
}
