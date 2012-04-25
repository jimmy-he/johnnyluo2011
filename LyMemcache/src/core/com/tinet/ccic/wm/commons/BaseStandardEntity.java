package com.tinet.ccic.wm.commons;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 基础标准实体类。
 *<p>
 * 文件名： BaseStandardEntity.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class BaseStandardEntity extends BaseObject {

	private Integer id;
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof BaseStandardEntity))
			return false;
		final BaseStandardEntity object = (BaseStandardEntity) o;
		
		// 如果两个id都为空，则认为是不等的对象
		if (this.id == null && object.getId() == null) {
			return false;
		}
		
		return new EqualsBuilder().append(getId(), object.getId()).isEquals();
	}

	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * 主键
	 * 
	 * @hibernate.id generator-class="native"
	 */
	public Integer getId() {
		return id;
	}




	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.getId()).toHashCode();
	}
	
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, TO_STRING_STYLE);
		builder.append("id", id);
		Field[] fields = this.getClass().getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		for (Field field : fields) {
			try {
				if (Modifier.isTransient(field.getModifiers())) {
					continue;
				}
				Object obj = field.get(this);
				if (obj instanceof Collection) {
					StringBuffer sb = new StringBuffer();
					sb.append("[");
					String classType = null;
					for (Object item : (Collection) obj) {
						if (item instanceof BaseStandardEntity) {
							BaseStandardEntity be = (BaseStandardEntity) item;
							classType = be.getClass().getSimpleName();
							sb.append(be.getId() + ",");
						}
					}
					if (sb.indexOf(",") > -1) {
						sb = new StringBuffer(sb.substring(0, sb
								.lastIndexOf(",")));
					}
					sb.append("]");
					if (classType != null) {
						builder.append(field.getName() + "<" + classType + ">",
								sb.toString());
					} else {
						builder.append(field.getName(), sb.toString());
					}

				} else if (obj instanceof BaseStandardEntity) {
					builder.append(field.getName() + "<"
							+ obj.getClass().getSimpleName() + ">",
							((BaseStandardEntity) obj).getId());
				} else {
					builder.append(field.getName(), obj);
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return builder.toString();
	}
}
