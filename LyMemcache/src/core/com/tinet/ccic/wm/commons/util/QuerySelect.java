package com.tinet.ccic.wm.commons.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class QuerySelect
implements Serializable
{
   private String selectType;//查询类型:eq等于,like 模糊查询,gt不等于,in,ge大于,le小于,between在...之间,addSql添加sql
   private List<Select> select;

   
 
   public QuerySelect(){
	   List list= new ArrayList();
	   this.select =list; 
   }
   
   
   
   
   
   /***
    * 绝对值查询
    * 例 t.studentName = '王'
    * @param propertyName
    * @param value
    */
   @SuppressWarnings("unchecked")
   public  void eq(String propertyName, Object value){
	   if(value==null){
		   return;
	   }
	  // this.criteria.add(Restrictions.eq(propertyName, value));
	   Select select =new Select();
	   select.setSelectType("eq");
	   select.setPropertyName(propertyName);
	   select.setObj(value);
	   this.select.add(select);
   }
   
   
   /***
    * 模糊查询
    * 例 t.studentName like '%王%'
    * @param propertyName
    * @param value
    */
   @SuppressWarnings("unchecked")
   public  void like(String propertyName, String value){
	   if(value==null) {
		 return;
	   }   
	   value="%"+value+"%" ;
	   //this.criteria.add(Restrictions.like(propertyName, value));
	   Select select =new Select();
	   select.setSelectType("like");
	   select.setPropertyName(propertyName);
	   select.setStr(value);
	   this.select.add(select);
   }
   
   
   /****
    * 小于
    * @param propertyName 变量
    * @param value
    */
   @SuppressWarnings("unchecked")
   public void le(String propertyName, Object value)
   {
	   if(value==null) {
		  return;
	   }   
       //this.criteria.add(Restrictions.le(propertyName, value));
	   Select select =new Select();
	   select.setSelectType("le");
	   select.setPropertyName(propertyName);
	   select.setLo(value);
	   this.select.add(select);
   }

   /****
    * 大于
    * @param propertyName 变量
    * @param value
    */
   @SuppressWarnings("unchecked")
   public void ge(String propertyName, Object value)
   {
	   if(value==null) {
		  return;
	   }   
       //this.criteria.add(Restrictions.ge(propertyName, value));
	   Select select =new Select();
	   select.setSelectType("ge");
	   select.setPropertyName(propertyName);
	   select.setGo(value);
	   this.select.add(select);
   }
   
   /***
    * 在...之间
    * 例  between go and lo
    * @param propertyName  变量
    * @param lo  大于
    * @param go  小于
    */
   @SuppressWarnings("unchecked")
   public void between(String propertyName, Object lo, Object go) {
	   if(lo==null) {
		  ge(propertyName, lo);
	   }   
	   if(go==null) {
		  le(propertyName, lo);
	   }   
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
 
}
