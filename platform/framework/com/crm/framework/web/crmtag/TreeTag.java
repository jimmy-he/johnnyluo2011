package com.crm.framework.web.crmtag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crm.framework.web.page.tree.Node;

/**
* 
 * @author 王永明
 * @since Apr 19, 2009 10:17:10 PM
 * */
public class TreeTag extends SimpleTag{

	private String node;
	private String boxName;
	private String treeName;
	private boolean openAll;
	private boolean hideButton;
	
	protected String getPrintStr() throws Exception {
		//按钮永远不显示2009-08-29
		hideButton = true;
		StringBuffer sb=new StringBuffer();
		if(boxName==null||boxName.equals("")){
			boxName="treeCheckBox";
		}
		if(treeName==null||treeName.equals("")){
			treeName="d";
		}
		
		//展开折叠方法	
		String openFunction="function openTree(el){"
			+"if (el.alt=='"+this.getText("unfold")+"'){"
			+treeName+".openAll();"
			+"el.alt='"+this.getText("fold")+"';"
			+"}else{" 
			+treeName+".closeAll();"
			+"el.alt='"+this.getText("unfold")+"';"
			+"}"
			+"}";
		
		this.addLine(sb, "<script language='Javascript' src='"+this.webRoot()+"/framework/tree/dtree/dtree.js'></script>");
		this.addLine(sb, "<link href='"+this.webRoot()+"/framework/tree/dtree/dtree.css' rel='stylesheet' type='text/css'>");
		this.addLine(sb, "<script language='javascript'>");
		this.addLine(sb, openFunction);
		if(!hideButton){
			if(openAll){
				sb.append("document.write('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+this.getButton(this.getText("fold"))+"');");
			}else{
				sb.append("document.write('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+this.getButton(this.getText("unfold"))+"');");
			}	
		}
			
		this.addLine(sb, treeName+" = new dTree('"+treeName+"');");
		List<Node> nodes=(List<Node>) this.getPageValue(pageContext, node);
		if(nodes==null){
			return "";
		}
		for(Node temp:nodes){
			sb.append(treeName+".add(");
			this.add(sb, temp.getId());
			this.add(sb, temp.getPid());
			this.add(sb, temp.getName());
			this.add(sb, temp.getUrl());
			this.add(sb, temp.getTitle());
			this.add(sb, temp.getTarget());
			this.add(sb, temp.getIcon());
			this.add(sb, temp.getIconOpen());
			this.add(sb, temp.getOpen());
			this.add(sb, temp.getSelectType());
			this.add(sb, temp.getValue());
			this.add(sb, temp.getBoundLowLevel());
			this.add(sb, temp.getValueReadonly());
			sb.append("\""+boxName+"\",");
			sb.append("\""+temp.getOnclickJs()+"\");");
			this.addLine(sb, "");
		}
		
		this.addLine(sb, "document.write("+treeName+");");
		if(openAll){
			this.addLine(sb, treeName+".openAll();");
		}	
		this.addLine(sb, "</script>");
		return sb.toString();
	}
	
	public String getButton(String value){
			
		Map map=new HashMap();
		map.put("onmouseout", "this.className=\\\'button49\\\'");
		map.put("onmouseover", "this.className=\\\'button49c\\\'");
		map.put("value", value);
		map.put("class", "button49");
		map.put("type", "button");
		map.put("onclick", "openTree(this)");
		return this.createEl("input", map);
	}
	
	private void add(StringBuffer sb,String value){
		if(value==null){
			value="";
			
		}
		value=value.replaceAll("'", "\\'").replaceAll("\\\"", "&quot;");
		sb.append("\""+value+"\",");
	}
	
	private void add(StringBuffer sb,boolean value){
		sb.append(value+",");
	}


	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getBoxName() {
		return boxName;
	}

	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public boolean getOpenAll() {
		return openAll;
	}

	public void setOpenAll(boolean openAll) {
		this.openAll = openAll;
	}


	public boolean getHideButton() {
		return hideButton;
	}

	public void setHideButton(boolean hideButton) {
		this.hideButton = hideButton;
	}
}
