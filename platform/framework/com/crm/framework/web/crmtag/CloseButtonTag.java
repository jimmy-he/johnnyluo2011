package com.crm.framework.web.crmtag;

/**
* 
 * @author 王永明
 * @since Apr 12, 2009 5:53:17 PM
 * */
public class CloseButtonTag extends SimpleTag{

	@Override
	protected String getPrintStr() throws Exception {
		return "<input class='button49' onmouseover='this.className=\"button49c\"' onmouseout='this.className=\"button49\"' type='button' onclick='common.closeWin()' value='关 闭'>";
	}

}
