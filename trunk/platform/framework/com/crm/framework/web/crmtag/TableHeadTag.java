package com.crm.framework.web.crmtag;


public class TableHeadTag extends SimpleTag {

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	protected String getPrintStr() throws Exception {
	
		title = this.getText(title);

		StringBuffer sb = new StringBuffer();
		addLine(sb, "<table border='0' align='center' cellpadding='0' cellspacing='0' class='none'>");
			addLine(sb, "<tr><td width='100%' height='28' class='titleMiddle'> ");
				addLine(sb, "<table width='100%' height='28' border='0' cellpadding='0' cellspacing='0'>");
					addLine(sb, "<tr>");
						addLine(sb,"<td width='10' class='titleLeft'></td>");
						addLine(sb, "<td class='formtitle'>" + title + "</td>");
						addLine(sb,"<td width='10' class='titleRight'></td>");
					addLine(sb, "</tr>");
				addLine(sb, "</table>");
		addLine(sb, "</td></tr></table>");
		return sb.toString();
	}

}
