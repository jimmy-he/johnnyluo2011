package com.crm.codemagic.creator.java;

import com.crm.codemagic.creator.common.TemplateUtil;
import com.crm.codemagic.pdmparser.Column;

/** 
*  根据表信息创建model
 * @author 王永明
 * @since 2009-3-23 下午01:10:48
 * 
 */
public class ModelBaseCreator extends SimpleJavaCodeCreator {
	
	
	public String getCode(){
		String code=super.getCode();		
		//引入的包
		StringBuffer imports=new StringBuffer();
		//是否已引入java.util.Date
		boolean importData=false;
		
		StringBuffer fields=new StringBuffer();		
		//字段
		for(Column column:this.getTable().getColumns()){
			fields.append(this.getField(column));
			if(!importData&&column.getJavaType().equals("Date")){
				this.addLine(imports,"import java.util.Date;");
				importData=true;
			}
		}
		
		//get和set
		StringBuffer getAndSet=new StringBuffer();
		for(Column column:this.getTable().getColumns()){
			getAndSet.append(this.getGetter(column));
			getAndSet.append(this.getSetter(column ));
		}
			
		return super.getCode()
			.replaceFirst("\\{import\\}",imports.toString())
			.replaceFirst("\\{fields\\}",this.addTabs(fields.toString()))
			.replaceFirst("\\{getAndSet\\}",this.addTabs(getAndSet.toString()));
	}
	
	
	
	private String getField(Column column){
		String field=TemplateUtil.getTemplate("java/field");
		return this.replaceTemplate(field, column);
	}
	
	private String getGetter(Column column){
		String get=null;
		if(this.getTable().pkColum.code.equals(column.code)){
			get=TemplateUtil.getTemplate("java/getId");
		}else{
			get=TemplateUtil.getTemplate("java/get");
		}
		return this.replaceTemplate(get,column);
	}
	
	private String getSetter(Column column){
		String set=TemplateUtil.getTemplate("java/set");
		return this.replaceTemplate(set,column);
	}
	
	private String replaceTemplate(String str,Column column){
		return str.replaceAll("\\{type\\}", column.getJavaType())
		.replaceAll("\\{code\\}", column.getJavaCode())
		.replaceAll("\\{Code\\}", column.getFistUpJavaCode())
		.replaceAll("\\{name\\}", column.name)
		.replaceAll("\\{length\\}", column.length+"")
		.replaceAll("\\{columnCode\\}", column.code);
		
	
	}
	

	@Override
	public String getClassName() {
		return this.getFirstUpModelName()+"Base";
	}

	@Override
	public String getPackageName() {
		return this.getRootPackage()+".model";
	}

	@Override
	public String getTemplateName() {
		return "java/modelBase";
	}

}
