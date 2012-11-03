package com.crm.codemagic.pdmparser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.dom4j.Element;
import org.dom4j.VisitorSupport;

/** 
 * @author 王永明
 * @date date 2009-3-16 下午09:21:59 
 * 
 */
/** 解析xml里的column节点,并将内容放入column对象中 */
public class ColumnVisitor extends VisitorSupport {
	
	private List<Column> columns = new ArrayList<Column>();
	
	/**
	 * 数据库的关键字集合
	 * */
	public static Set keywords = Keywords.getKeywords();
	
	public static  boolean isKeywords(String str){
		boolean result = keywords.contains(str.toUpperCase());
		return result;
	}
	
	public void visit(Element element) {
		if (!element.getName().equals("Column")) {
			return;
		}
		Column column =new Column();
		column.id=element.attributeValue("Id");
		for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
			Element el = it.next();
			parser(column,el);
			
		}
		columns.add(column);
	}

	/** 将一个column节点转换为Column */
	private Column parser(Column column,Element el) {
		if (el.getName().equals("Name")) {
			column.name=el.getText();
		}
		if (el.getName().equals("Code")) {
			column.code = el.getText();
			if(isKeywords(column.code)){
				JOptionPane.showMessageDialog(null, "字段"+column.code+"为数据库的保留字，请替换成别的单词！！！！");
				throw new RuntimeException("字段"+column.code+"为数据库的保留字，请替换成别的单词！！！！");
			}
		}
		if (el.getName().equals("DataType")) {
			column.dataType=el.getText();
			
		}
		if (el.getName().equals("Length")) {
			column.length=Integer.parseInt(el.getText());
		}
		if (el.getName().equals("Mandatory")) {
			if (el.getText().equals("1")) {
				column.mandatory=true;
			}
		}
		return column;
	}

	/**返回解析完毕的列*/
	public List<Column> getColumns() {
		return columns;
	}
}