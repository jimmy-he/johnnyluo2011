package com.crm.framework.validate;

import java.util.Map;
import java.util.Map.Entry;

import com.crm.framework.action.SimpleAction;
import com.crm.framework.common.enums.ClassType;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.model.MappingCache;
import com.crm.framework.model.MappingEntity;
import com.crm.framework.validate.cache.ValidateConfigCache;
import com.crm.framework.validate.validateType.ValidateType;

/**
 * 什么都不验证的验证类
 * @author 王永明
 * @since May 20, 2010 8:00:49 PM
 */
public class NotValidate extends SimpleValidate {
	/** 新增 */
	public void insert(SimpleAction action) {
	}

	/** 删除 */
	public void delete(SimpleAction action) {
	}

	/** 批量删除 */
	public void deleteList(SimpleAction action) {

	}

	/** 更新数据,忽略为null的字段 */
	public void update(SimpleAction action) {
	
	}

	/** 更新数据,包括为null的字段 */
	public void updateFull(SimpleAction action) {
	
	}

	/** 查询 */
	public void query(SimpleAction action) {

	}

	/** 下一页 */
	public void queryContinue(SimpleAction action) {

	}

	/** 查看详细 */
	public void view(SimpleAction action) {
	}
	
}
