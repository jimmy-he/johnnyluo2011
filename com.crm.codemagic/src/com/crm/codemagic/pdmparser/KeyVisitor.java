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
public class KeyVisitor extends VisitorSupport {
	private List<Key> keys=new ArrayList<Key>();
	private List<Column> columns;
	
	public void setColumns(List<Column> columns){
		this.columns=columns;
	}
	
	public void visit(Element element) {

		if (!element.getName().equals("Key")) {
			return;
		}
		Key key=new Key();
		key.id=element.attributeValue("Id");
		
		for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
			Element el = it.next();
			parser(key,el);
			
		}
		keys.add(key);
	}

	/** 将一个table元素转换为Table */
	private Key parser(Key key,Element el) {
		if (el.getName().equals("Name")) {
			key.name=el.getText();;
		}
		if (el.getName().equals("Code")) {
			key.code=el.getText();
		}
		if (el.getName().equals("Key.Columns")) {
			List<Column> columns=new ArrayList();
			for(Iterator<Element> it=el.elementIterator();it.hasNext();){
				String columnId=it.next().attributeValue("Ref");
				columns.add(getColumn(columnId));
			}
			key.columns=columns;
			
		}
		return key;
	}

	/**返回解析完毕的table*/
	public List<Key> getKey() {
		return keys;
	}
	
	private Column getColumn(String id){
		for(Column column:columns){
			if(column.id.equals(id)){
				return column;
			}
		}
		return null;
	}
}