package com.crm.framework.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import com.crm.framework.common.util.JavaTypeUtil;
import com.crm.framework.common.util.StringUtil;

/**
 * 
 * @author 王永明
 * @since Mar 20, 2010 2:38:48 PM
 */
public class MappingField {
	private Method method;
	private String fieldName;
	private Class returnType;
	private Column column;
	private OneToMany oneToMany;
	private String joinColumn;
	private ManyToOne manyToOne;
	private Class listType;
	private boolean isPk;
	private Lob lob;
	private OneToOne oneToOne;
	
	
	public MappingField (Field field,MappingEntity entity){
		fieldName = field.getName();
		returnType=field.getType();
		if(returnType==List.class){
			ParameterizedTypeImpl pType = (ParameterizedTypeImpl)field.getGenericType();
			listType = (Class) pType.getActualTypeArguments()[0];
		}
		//设置对应数据库列名
		column=field.getAnnotation(Column.class);
		
	
		if(fieldName.equals(entity.getPkName())){
			isPk=true;
		}
		
		lob=field.getAnnotation(Lob.class);
	}
	
	
	
	
	
	
	public MappingField (Method method){
		this.method=method;
		fieldName = StringUtil.getFieldName(method.getName());
		returnType=method.getReturnType();
	
		manyToOne=method.getAnnotation(ManyToOne.class);
		oneToMany=method.getAnnotation(OneToMany.class);
		oneToOne=method.getAnnotation(OneToOne.class);
		
		JoinColumn jc=method.getAnnotation(JoinColumn.class);
		if(jc!=null){
			joinColumn=jc.name();
			if(StringUtil.isNull(joinColumn)){
				joinColumn=fieldName;
			}
		}
		column= method.getAnnotation(Column.class);
		
		if(returnType==List.class){
			ParameterizedTypeImpl pType = (ParameterizedTypeImpl) method.getGenericReturnType();
			listType = (Class) pType.getActualTypeArguments()[0];
		}
		
		
		isPk=(method.getAnnotation(Id.class)!=null);
	}
	
	
	
	
	public boolean isList(){
		return listType!=null;
	}

	public Method getMethod() {
		return method;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Class getReturnType() {
		return returnType;
	}

	public String getColumn() {
		if(column!=null){
			return column.name();
		}
		return JavaTypeUtil.toDatabaseName(fieldName);
	}

	public OneToMany getOneToMany() {
		return oneToMany;
	}

	public String getJoinColumn() {
		return joinColumn;
	}

	public ManyToOne getManyToOne() {
		return manyToOne;
	}
	
	
	public OneToOne getOneToOne() {
		return oneToOne;
	}

	public Class getListType() {
		return listType;
	}

	public boolean getLazy() {
		return manyToOne!=null||oneToMany!=null;
	}
	
	public String getName(){
		return this.getFieldName();
	}
	
	public Class getType(){
		return this.getReturnType();
	}
	
	public String getLength(){
		if(lob!=null){
			return "4000";
		}
		if(column==null){
			return "255";
		}else{	
			return column.length()+"";
		}
		
	
	}


	public boolean getIsProperty() {
		return JavaTypeUtil.isSimple(this.getReturnType());
	}


	public boolean getIsOneToMany() {
		return this.getOneToMany()!=null;
	}

	public boolean getIsManyToOne() {
		return this.getManyToOne()!=null;
	}
	
	public boolean getIsPk(){
		return isPk;
	}


	public boolean getIsOneToOne() {
		return this.getOneToOne()!=null;
	}
}


