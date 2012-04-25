package com.tinet.ccic.wm.commons.dataSourse;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.tinet.ccic.wm.commons.util.Select;

/**
 * JDBC查询器
 * <p>
 * FileName：JdbcSelect.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD. All rights reserved.
 * 
 * @author wanghl
 * @since 1.0
 * @version 1.0
 */
public class JdbcSelect implements Serializable
{
   private String selectType;//查询类型:eq等于,like 模糊查询,gt不等于,in,ge大于,le小于,between在...之间,addSql添加sql
   private List<Select> select;
   private String table;//表名
   private Integer  limit;
   private Integer  offset;
   private String addSQL;//增加纯sql
   private Integer  count;
  




public JdbcSelect(String table){
	   this.table=table;
	   List list= new ArrayList();
	   this.select =list; 
	   limit=0;
	   offset=0;
   }
   
   
   /***
    * 绝对值查询 !=
    * 例 t.studentName != '王'
    * @param propertyName
    * @param value
    */
   @SuppressWarnings("unchecked")
   public  void notEq(String propertyName, Object value,DataType dataType){
	   if(value==null||value.equals("")){
		   return;
	   }
	  // this.criteria.add(Restrictions.eq(propertyName, value));
	   Select select =new Select();
	   select.setSelectType("notEq");
	   select.setPropertyName(propertyName);
	   select.setObj(value);
	   select.setType(dataType);
	   this.select.add(select);
   }
   
   
   /***
    * 绝对值查询
    * 例 t.studentName = '王'
    * @param propertyName
    * @param value
    */
   @SuppressWarnings("unchecked")
   public  void eq(String propertyName, Object value,DataType dataType){
	   if(value==null||value.equals("")){
		   return;
	   }
	  // this.criteria.add(Restrictions.eq(propertyName, value));
	   Select select =new Select();
	   select.setSelectType("eq");
	   select.setPropertyName(propertyName);
	   select.setObj(value);
	   select.setType(dataType);
	   this.select.add(select);
   }
   

   /***
    * 模糊查询
    * 例 t.studentName like '%王%'
    * @param propertyName
    * @param value
    */
   @SuppressWarnings("unchecked")
   public  void like(String propertyName, String value, DataType dataType){
	   if(value==null||value.equals("")) {
		 return;
	   }   
	   value="%"+value+"%" ;
	   //this.criteria.add(Restrictions.like(propertyName, value));
	   Select select =new Select();
	   select.setSelectType("like");
	   select.setPropertyName(propertyName);
	   select.setStr(value);
	   select.setType(dataType);
	   this.select.add(select);
   }
   
   
   
   
   /****
    * 小于
    * @param propertyName 变量
    * @param value
    */
   @SuppressWarnings("unchecked")
   public void le(String propertyName, Object value,DataType dataType)
   {
	   if(value==null||value.equals("")) {
		  return;
	   }   
       //this.criteria.add(Restrictions.le(propertyName, value));
	   Select select =new Select();
	   select.setSelectType("le");
	   select.setPropertyName(propertyName);
	   select.setLo(value);
	   select.setType(dataType);
	   this.select.add(select);
   }

   /****
    * 大于
    * @param propertyName 变量
    * @param value
    */
   @SuppressWarnings("unchecked")
   public void ge(String propertyName, Object value,DataType dataType)
   {
	   if(value==null||value.equals("")) {
		  return;
	   }   
       //this.criteria.add(Restrictions.ge(propertyName, value));
	   Select select =new Select();
	   select.setSelectType("ge");
	   select.setPropertyName(propertyName);
	   select.setGo(value);
	   select.setType(dataType);
	   this.select.add(select);
   }
   
   /****
    * 在。。之中
    * name in ('小王','小李','小赵')
    * @param propertyName 变量
    * @param value
    */
   @SuppressWarnings("unchecked")
   public void in(String propertyName,String[] values,DataType dataType)
   {
	   if(values==null) {
		  return;
	   }   
       //this.criteria.add(Restrictions.ge(propertyName, value));
	   Select select =new Select();
	   select.setSelectType("in");
	   select.setPropertyName(propertyName);
	   select.setValues(values);
	   select.setType(dataType);
	   this.select.add(select);
   }
   
   /****
    * 判断变量为空
    * (name is null or cno ='')
    * @param propertyName 变量
    */
   @SuppressWarnings("unchecked")
   public void isNull(String propertyName, DataType dataType)
   {
	   Select select =new Select();
	   select.setSelectType("isNull");
	   select.setPropertyName(propertyName);
	   this.select.add(select);
   }
   
   /****
    * 判断变量不为空
    * length(name)>0
    * @param propertyName 变量
    */
   @SuppressWarnings("unchecked")
   public void isNotNull(String propertyName, DataType dataType) {
	   Select select =new Select();
	   select.setSelectType("isNotNull");
	   select.setPropertyName(propertyName);
	   this.select.add(select);
	}
   
   
   /****
    * 以..开始
    * name like '王%'
    * @param propertyName 变量
    */
   @SuppressWarnings("unchecked")
   public void startWith(String propertyName, String value, DataType dataType) {
	   if(value==null||value.equals("")) {
			 return;
		}   
		value=value+"%" ;
		//this.criteria.add(Restrictions.like(propertyName, value));
		Select select =new Select();
		select.setSelectType("like");
		select.setPropertyName(propertyName);
		select.setStr(value);
		select.setType(dataType);
		this.select.add(select);
	}
   /***
    * 在...之间
    * 例  between go and lo
    * @param propertyName  变量
    * @param lo  大于
    * @param go  小于
    */
//   @SuppressWarnings("unchecked")
//   public void between(String propertyName, Object go, Object lo,DataType dataType) {
//	   if(go!=null) {
//		  ge(propertyName, go,dataType);
//	   }   
//	   if(lo!=null) {
//		  le(propertyName, lo,dataType);
//	   }   
//	}
   
   
   /***
    * 分页
    * 例  limit 10 offset 1
    * @param propertyName  变量
    * @param limit  
    * @param offset  
    */
   @SuppressWarnings("unchecked")
   public void limit( int limit, int offset) {
	   this.limit  = limit;
	   this.offset = offset;
   }
   
   /***
    * 分页
    * 例  limit 10 
    * @param count   
    */
   @SuppressWarnings("unchecked")
   public void count( int count) {
	   this.count  = count;
   }
   
   
   /***
    * 手动拼写sql
    *   
    * @param sql  
    */
   @SuppressWarnings("unchecked")
   public void addSQL(String sql) {
	   this.addSQL = sql;
   }
   
   
   
   
   
   
   
   
   
	public String getTable() {
	return table;
	}





	public void setTable(String table) {
	this.table = table;
	}





	public String getSelectType() {
		return selectType;
	}
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}


	public List<Select> getSelect() {
		return select;
	}


	public void setSelect(List<Select> select) {
		this.select = select;
	}


	public Integer getLimit() {
		return limit;
	}


	public void setLimit(Integer limit) {
		this.limit = limit;
	}


	public Integer getOffset() {
		return offset;
	}


	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getAddSQL() {
		return addSQL;
	}


	public void setAddSQL(String addSQL) {
		this.addSQL = addSQL;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}

 
}
