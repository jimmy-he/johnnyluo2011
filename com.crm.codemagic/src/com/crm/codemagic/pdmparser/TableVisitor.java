package com.crm.codemagic.pdmparser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.VisitorSupport;


/** 
 * @author 王永明
 * @date date 2009-3-16 下午03:55:33 
 * 
 */
/** 解析xml里的table节点,并将内容放入Table对象中 */
public class TableVisitor extends VisitorSupport {
	private List<Table> tables=new ArrayList<Table>();

	public void visit(Element element) {

		if (!element.getName().equals("Table")) {
			return;
		}
		Table table=new Table();
		table.id=element.attributeValue("Id");
		for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
			Element el = it.next();
			parser(table,el);
			
		}
		tables.add(table);
	}

	/** 将一个table元素转换为Table */
	private Table parser(Table table,Element el) {
		if (el.getName().equals("Name")) {
			table.name=el.getText();
		}
		if (el.getName().equals("Code")) {
			table.code=el.getText();
		}
		if (el.getName().equals("Columns")) {
			ColumnVisitor columnVisitor = new ColumnVisitor();
			el.accept(columnVisitor);
			table.columns=columnVisitor.getColumns();
		}
		if (el.getName().equals("Keys")) {
			KeyVisitor keyVisitor = new KeyVisitor();
			keyVisitor.setColumns(table.getColumns());
			el.accept(keyVisitor);
			table.keys=keyVisitor.getKey();
		}
		if(el.getName().equals("PrimaryKey")){
			Element element=(Element)el.elementIterator().next();
			String keyId=element.attributeValue("Ref");
			for(Key key:table.keys){
				if(key.id.equals(keyId)){
					table.pkColum=key.columns.get(0);
					break;
				}
			}
		}
		
		return table;
	}

	/**返回解析完毕的table*/
	public List<Table> getTable() {
		return tables;
	}
	
}