package com.crm.framework.dao.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 系统内model对象内部用到的list,增加了一个方法,preInit,当list没初始化的时候通过这个方法的hql,datasourceId
 * @author 王永明
 * @since Mar 20, 2010 12:17:58 PM
 */
public interface CrmList extends List {
	public void preInit(String hql,String datasourceId);
}
