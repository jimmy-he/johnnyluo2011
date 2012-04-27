package com.crm.framework.web.crmtag;

import java.util.HashMap;
import java.util.Map;

/**
* 
 * @author 王永明
 * @since Apr 12, 2009 4:31:29 PM
 */
public class QueryTag extends SimpleTag {
	private String prefix;

	protected String getPrintStr() {
		Map map=new HashMap();
		map.put("onclick", "general.query(fm)");
		map.put("value", "查 询");
		map.put("type", "button");
		map.put("class", "button49");
		map.put("onmouseover", "this.className='button49c'");
		map.put("onmouseout", "this.className='button49'");
		map.putAll(this.getDefMap());
		String text=this.createEl("input",map);
		if (notNull(prefix)) {
			text += "<input type='hidden' name='prefix' value='" + prefix + "' />";
		}
		return text;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
