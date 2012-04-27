package com.crm.framework.web.crmtag;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.enums.ClassType;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.util.StringUtil;

/**
 * 
 * @author 王永明
 * @since Mar 24, 2010 3:12:19 PM
 */
public class EnumTag extends SimpleTag {
	private static Logger log=Logger.getLogger(EnumTag.class);
	/**对应枚举的类如果不是在com.crm.framework.common.enums下要写全名*/
	private String enumClass; 
	/**select框,第一行显示的值*/
	private String firstName;
	/**选中的值*/
	private String selectedValue;

	/**key所对应的enum属性值*/
	private String keyFiled;
	
	@Override
	protected String getPrintStr()   {
		try {
			if(StringUtil.isNotNull(selectedValue)){
				selectedValue=this.getPageValue(pageContext, selectedValue).toString();
			}
						
			StringBuffer sb=new StringBuffer();
			sb.append(this.createEl("select", this.getDefMap()));
			
			if(firstName!=null&&!firstName.equals("")){
				firstName=this.getText(firstName);
				this.addLine(sb, "<option value=''>"+firstName+"</option>");
			}
			
			Class clazz=null;
			
			if(enumClass.indexOf('.')==-1){
				//如果是类名称就直接用com.crm.framework.common.enums下的类,获得市对应action所在的enums包
				String pkg=ClassType.action.getOrtherPackage(this.getAction().getClass(), ClassType.enums);
				String clsName=pkg+"."+StringUtil.firstUp(enumClass);
				try{	
					clazz=Class.forName(clsName);
				} catch (ClassNotFoundException e) {
					clazz=Class.forName("com.crm.framework.common.enums"+"."+StringUtil.firstUp(enumClass));
				}
			}else{
				clazz=Class.forName(enumClass);
			}
			
			clazz=CrmBeanFactory.getImplClass(clazz);
			
			for(Field f:clazz.getFields()){
				String key=f.getName();
				if(StringUtil.isNotNull(keyFiled)){
					Object o=f.get(clazz);
					key=(String) BeanUtil.getValue(o,keyFiled);
				}
	
				String value=this.getText(StringUtil.firstLower(clazz.getSimpleName())+"."+f.getName());
				if(key.equals(selectedValue)){
					this.addLine(sb, "<option value='"+key+"' selected='selected'>"+value+"</option>");
				}else{
					this.addLine(sb, "<option value='"+key+"'>"+value+"</option>");
				}
				
			}
			this.addLine(sb, "</select>");
			return sb.toString();
				
		} catch (Exception e) {
			log.warn("找不到枚举:"+e);
			throw new RuntimeException(e);
		}
		
	}


	public String getEnumClass() {
		return enumClass;
	}


	public void setEnumClass(String enumClass) {
		this.enumClass = enumClass;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getSelectedValue() {
		return selectedValue;
	}


	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}


	public String getKeyFiled() {
		return keyFiled;
	}


	public void setKeyFiled(String keyFiled) {
		this.keyFiled = keyFiled;
	}
	

}
