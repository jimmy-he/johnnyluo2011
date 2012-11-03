package com.crm.codemagic.creator.java;

import com.crm.codemagic.creator.common.TemplateUtil;
import com.crm.codemagic.pdmparser.Column;

/**
 * @description 根据表信息创建model
 * @author 王永明
 * @date 2009-3-23 下午01:10:48
 * 
 */
public class ServiceBaseCreator extends SimpleJavaCodeCreator {

	public String getCode() {
		StringBuffer sb = new StringBuffer();

		for (Column column : this.getTable().columns) {
			if(column.getJavaType().equals("String")){
				this.addLine(sb, "query.like(\"" + column.getJavaCode() + "\", " + this.getModelName() + ".get"
						+ column.getFistUpJavaCode() + "());");
			}else{
				this.addLine(sb, "query.eq(\"" + column.getJavaCode() + "\", " + this.getModelName() + ".get"
						+ column.getFistUpJavaCode() + "());");
			}
			
		}
		return super.getCode().replaceFirst("\\{queryCondition\\}", this.addTabs(sb.toString(),2));

	}

	@Override
	public String getClassName() {
		return this.getFirstUpModelName() + "ServiceBase";
	}

	@Override
	public String getPackageName() {
		return this.getRootPackage() + ".service";
	}

	@Override
	public String getTemplateName() {
		return "java/serviceBase";
	}

}
