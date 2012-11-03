package com.crm.codemagic.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class ColumnLayout extends Layout {
	
	public static final int MARGIN = 4; 
	public static final int SPACING = 2; 
	Point [] sizes; 
	int maxWidth, totalHeight; 
	
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) { 
		Control children[] = composite.getChildren(); 
		if (flushCache || sizes == null || sizes.length != children.length) { 
			initialize(children); 
		} 
		int width = wHint, height = hHint; 
		if (wHint == SWT.DEFAULT) 
			width = maxWidth; 
		if (hHint == SWT.DEFAULT) 
			height = totalHeight; 
		return new Point(width + 2 * MARGIN, height + 2 * MARGIN); 
	} 
	
	protected void layout(Composite composite, boolean flushCache) { 
		Control children[] = composite.getChildren(); 
		if (flushCache || sizes == null || sizes.length != children.length) { 
			initialize(children); 
		} 
		Rectangle rect = composite.getClientArea(); 
		int x = MARGIN, y = MARGIN; //计算最大宽度  
		int width = Math.max(rect.width - 2 * MARGIN, maxWidth); 
		for (int i = 0; i < children.length; i++) { 
			int height = sizes[i].y; //设置子组件的位置 
			children[i].setBounds(x, y, width, height); //计算当前组件的y轴的坐标  
			y += height + SPACING; 
		} 
	} 
	
	void initialize(Control children[]) { 
		maxWidth = 0; 
		totalHeight = 0; 
		sizes = new Point [children.length]; 
		for (int i = 0; i < children.length; i++) { 
			//计算子组件的大小  
			sizes[i] = children[i].computeSize(SWT.DEFAULT, SWT.DEFAULT, true); 
			maxWidth = Math.max(maxWidth, sizes[i].x); 
			totalHeight += sizes[i].y; 
		} 
		totalHeight += (children.length - 1) * SPACING; 
	}
	
}
