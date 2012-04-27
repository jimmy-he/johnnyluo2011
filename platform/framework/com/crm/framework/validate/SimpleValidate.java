package com.crm.framework.validate;

import java.util.Map;
import java.util.Map.Entry;

import com.crm.framework.action.SimpleAction;
import com.crm.framework.common.enums.ClassType;
import com.crm.framework.common.util.BeanUtil;
import com.crm.framework.common.util.StringUtil;
import com.crm.framework.model.MappingCache;
import com.crm.framework.model.MappingEntity;
import com.crm.framework.validate.cache.ValidateConfigCache;
import com.crm.framework.validate.validateType.ValidateType;

/**
 * 
 * @author 王永明
 * @since Apr 16, 2010 10:36:21 AM
 */
abstract public class SimpleValidate {
	/** 新增 */
	public void insert(SimpleAction action) {
		Class model=ClassType.action.getOrtherType(action.getClass(), ClassType.model);
		MappingEntity maping=MappingCache.getMapping(model);
		String pk=StringUtil.firstLower(model.getSimpleName())+"."+maping.getPkName();
		Map<String,String> map=ValidateConfigCache.getConfig(action.getClass());
		for(Entry<String,String> en:map.entrySet()){
			for(String str:en.getValue().split(",")){
				//跳过主键
				if(en.getKey().endsWith(pk)){
					continue;
				}
				
				//跳过数据转换的验证
				if(str.startsWith(ValidateType.DATE)
						||str.startsWith(ValidateType.DATE_TIME)){
					continue;
				}
				if(str.startsWith(ValidateType.NUMBER)){
					continue;
				}
				Assert.validate(action, en.getKey(), str);
			}
		}
	}

	/** 删除 */
	public void delete(SimpleAction action) {
		Class model=ClassType.action.getOrtherType(action.getClass(), ClassType.model);
		MappingEntity maping=MappingCache.getMapping(model);
		String pkName=StringUtil.firstLower(model.getSimpleName())+"."+maping.getPkName();
		Assert.notNull(action, pkName);
	}

	/** 批量删除 */
	public void deleteList(SimpleAction action) {

	}

	/** 更新数据,忽略为null的字段 */
	public void update(SimpleAction action) {
		Class model=ClassType.action.getOrtherType(action.getClass(), ClassType.model);
		MappingEntity maping=MappingCache.getMapping(model);
		String pkName=StringUtil.firstLower(model.getSimpleName())+"."+maping.getPkName();
		Assert.notNull(action, pkName);
	}

	/** 更新数据,包括为null的字段 */
	public void updateFull(SimpleAction action) {
		Map<String,String> map=ValidateConfigCache.getConfig(action.getClass());
		for(Entry<String,String> en:map.entrySet()){
			for(String str:en.getValue().split(",")){			
				//跳过数据转换的验证
				if(str.startsWith(ValidateType.DATE)
						||str.startsWith(ValidateType.DATE_TIME)){
					continue;
				}
				if(str.startsWith(ValidateType.NUMBER)){
					continue;
				}
				Assert.validate(action, en.getKey(), str);
			}
		}
	}

	/** 查询 */
	public void query(SimpleAction action) {

	}

	/** 下一页 */
	public void queryContinue(SimpleAction action) {

	}

	/** 查看详细 */
	public void view(SimpleAction action) {
		Class model=ClassType.action.getOrtherType(action.getClass(), ClassType.model);
		MappingEntity maping=MappingCache.getMapping(model);
		String pkName=StringUtil.firstLower(model.getSimpleName())+"."+maping.getPkName();
		Assert.notNull(action, pkName);
	}
	

}



