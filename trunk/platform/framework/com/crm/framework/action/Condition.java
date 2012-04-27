package com.crm.framework.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.crm.framework.common.util.LogUtil;
import com.crm.framework.common.util.StringUtil;

/**
 * 通用查询操作的查询条件,因为是通过的操作所以只能是通过sql或者hql进行查询
 * @author 王永明
 * @since 2010-9-12 下午05:23:24
 */
public class Condition  implements Serializable{
	
	private List<Param> params=new ArrayList();
	private String sql;
	private String count;
	private String orderField;
	private int pageNo=1;
	private List<String> titles;
	
	
	public List<String> getTitles() {
		System.out.println("titles:"+titles);
		if(titles==null){
			if(StringUtil.isNotNull(sql)){
				titles=this.getTitle(sql);
			}else{
				titles=this.getTitle(hql);
			}
			
		}
		return titles;
	}
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	private int rowsPerPage=7;
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	private String orderType;
	public String getCount() {
		if(StringUtil.isNull(count)){
			String condition="";
			if(StringUtil.isNotNull(hql)){
				condition = hql.trim().substring(hql.indexOf("from"));
			}else{
				condition = sql.trim().substring(sql.indexOf("from"));
			}
			count="select count(*) "+condition;
		}
		return count;
	}
	
	
	public void setCount(String count) {
		this.count = count;
	}
	private String hql;
	
	public List<Param> getParams() {
		return params;
	}
	public void setParams(List<Param> params) {
		this.params = params;
	}
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getHql() {
		return hql;
	}
	public void setHql(String hql) {
		this.hql = hql;
	}
	public Object[] getRealParams() {
		if(params==null){
			params=new ArrayList();
		}
		Object[] o=new Object[params.size()];
		for(int i=0;i<o.length;i++){
			o[i]=params.get(i).getRealVale();
		}		
		return o;	
	}
	
	/**获得标题,如果查询的列包含as则用as后面的值,否则直接用列名
	 * select userId,userCode as 用户代码 from User,返回[userId,用户代码]
	 * */
	private List<String> getTitle(String hql) {
		hql = hql.trim();
		String select = hql.trim().substring(6, hql.indexOf("from"));
		String[] fields = select.split(",");
		List<String> titles = new ArrayList();
		for (int i=1 ;i<fields.length;i++) {
			String[] title = fields[i].split(" as ");
			if (title.length == 1) {
				titles.add(title[0].trim());
			} else {
				titles.add(title[1].trim());
			}
		}
		return titles;
	}
	
	/**增加查询条件*/
	public void addCondition(List<Param> queryParam) {
		if(queryParam==null){
			return;
		}
		for(Param p:queryParam){
			if(StringUtil.isNull(p.getValue())){
				continue;
			}
			if(StringUtil.isNotNull(sql)){
				if(StringUtil.isNull(p.getType())||p.getType().equals("String")){
					sql+=" and "+p.getCode()+" like ?";
				}else{
					sql+=" and "+p.getCode()+"=?";
				}
				
				
			}else{
				if(StringUtil.isNull(p.getType())||p.getType().equals("String")){
					hql+=" and "+p.getCode()+" like ?";
				}else{
					hql+=" and "+p.getCode()+"=?";
				}	
			}
			if(StringUtil.isNull(p.getType())||p.getType().equals("String")){
				count+=" and "+p.getCode()+" like ?";
			}else{
				count+=" and "+p.getCode()+"=?";
			}	
			
			params.add(p);
		}
		
	}
}




