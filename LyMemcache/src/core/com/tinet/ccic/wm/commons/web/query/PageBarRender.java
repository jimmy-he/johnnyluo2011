package com.tinet.ccic.wm.commons.web.query;
/**
 * 渲染出分页条，展现在页面上。
 *<p>
 * 文件名： Page.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public interface PageBarRender {
	
	/**
	 * 将对象Page渲染成页面显示的分页条。
	 * @return 页面显示的string。
	 */
	public String render(Page page);
	
}
