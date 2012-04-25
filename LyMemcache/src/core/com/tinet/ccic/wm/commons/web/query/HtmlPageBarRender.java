package com.tinet.ccic.wm.commons.web.query;

/**
 * 渲染出Html元素，展现在页面上。
 *<p>
 * 文件名： HtmlPageBarRender.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class HtmlPageBarRender implements PageBarRender{

	public String render(Page page) {
		if (page.getTotalPageCount() == 0) {
			return "<div style='width:810px;text-align:left; border:0px red solid'>没有查询到任何结果</div>";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("第<span>")
		  .append(page.getCurrentPageNo())
		  .append("/")
		  .append(page.getTotalPageCount())
		  .append("页&nbsp;共")
		  .append(page.getTotalCount())
		  .append("</span>条&nbsp;&nbsp;&nbsp;");
		
		// 显示首页，上一页
		if (page.getCurrentPageNo() == 1) {
			sb.append("首页&nbsp;&nbsp;&nbsp;上一页&nbsp;&nbsp;&nbsp;");
		} else {
			sb.append("<a start='0' href='#'>首页</a>&nbsp;&nbsp;&nbsp;<a start='")
			  .append((page.getCurrentPageNo() - 2) * page.getPageSize())
			  .append("' href='#'>上一页</a>&nbsp;&nbsp;&nbsp;");
		}
		
		// 显示尾页，下一页
		if (page.getCurrentPageNo() == page.getTotalPageCount()) {
			sb.append("下一页&nbsp;&nbsp;&nbsp;尾页&nbsp;&nbsp;&nbsp;");
		} else {
			sb.append("<a start='")
			  .append(page.getCurrentPageNo() * page.getPageSize())
			  .append("' href='#'>下一页</a>&nbsp;&nbsp;&nbsp;<a start='")
			  .append((page.getTotalPageCount() - 1) * page.getPageSize())
			  .append("' href='#'>尾页</a>&nbsp;&nbsp;&nbsp;");
		}
		// 跳转至某页
		sb.append("转至<input type='text' id='page-turn-to-txtinput' style='width:23px;' maxlength='4' value='")
			.append(page.getCurrentPageNo()).append("' />页&nbsp;<img id='page-turn-to-img'  style='cursor:hand;' max='")
			.append(page.getTotalPageCount()).append("' alt='go' />&nbsp;&nbsp;");
				
		// 每页显示记录数
		String optionSelected_10 = "";
		String optionSelected_20 = "";
		String optionSelected_50 = "";
		String optionSelected_100 = "";
		switch (page.getPageSize()) {
			case 10:
				optionSelected_10 = " selected";
				break;
			case 20:
				optionSelected_20 = " selected";
				break;
			case 50:
				optionSelected_50 = " selected";
				break;
			case 100:
				optionSelected_100 = " selected";
				break;
			default:
				break;
		}
		sb.append("每页<select name='pageSize' id='pageSize' style='width:43px'>")
			.append("<option value='10'").append(optionSelected_10).append(">10</option>")
			.append("<option value='20'").append(optionSelected_20).append(">20</option>")
			.append("<option value='50'").append(optionSelected_50).append(">50</option>")
			.append("<option value='100'").append(optionSelected_100).append(">100</option>")
			.append("</select>条");
		
		return sb.toString();
	}
	
}
