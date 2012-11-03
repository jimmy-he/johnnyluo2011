package com.crm.framework.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * @description
 * @author 王永明
 * @date Apr 12, 2009 8:57:01 PM
 */
public class BeanUtil {
	public static Object create(Object object, Class clazz) {
		Object newObj = null;
		try {
			newObj = clazz.newInstance();
			for (Method set : clazz.getMethods()) {
				if (set.getParameterTypes().length != 1) {
					continue;
				}
				String name = set.getName();
				if (!name.startsWith("set")) {
					continue;
				}
				String getName = "get" + name.substring(3);
				Method get = object.getClass().getMethod(getName, null);
				Object value = get.invoke(object, null);
				if (value != null) {
					set.invoke(newObj, value);
				}
			}
			return newObj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static Object getValue(Object obj, String expression) {
		try {
			if (obj == null) {
				return null;
			}
			return Ognl.getValue(expression, obj);
		} catch (OgnlException e) {
			return null;
		}
	}

	public static Object getFieldValue(Object obj, String fieldName) {
		try {
			return Ognl.getValue(fieldName, obj);
		} catch (OgnlException e) {
			throw new RuntimeException(e);
		}
	}

	/** 将list转为map */
	public static Map toMap(List list, String idName) {
		if (list == null) {
			return new HashMap();
		}
		Map map = new HashMap();
		for (Object o : list) {
			try {
				Object id = Ognl.getValue(idName, o);
				map.put(id, o);
			} catch (OgnlException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return map;
	}

	public static void copyTo(Object from, Object to, String[] fields) {
		try {
			for (String expression : fields) {
				Object value = Ognl.getValue(expression, from);
				Ognl.setValue(expression, to, value);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public static void copyFieldsTo(Object from, Object to) {
		List<String> fields = new ArrayList();
		for (Method method : to.getClass().getMethods()) {
			String methodName = method.getName();
			if (methodName.startsWith("set")) {
				String name = StringUtil.getFieldName(method.getName());
				fields.add(name);
			}
		}
		try {
			for (String expression : fields) {
				Object value = Ognl.getValue(expression, from);
				Ognl.setValue(expression, to, value);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public static void setListField(List list, String name, String value) {
		try {
			for (Object o : list) {
				Ognl.setValue(name, o, value);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void setField(Object o, String name, Object value) {
		try {

			Ognl.setValue(name, o, value);

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}


	public static boolean hasMethodName(Object obj, String name) {
		for (Method method : obj.getClass().getMethods()) {
			if (method.getName().equals("name")) {
				return true;
			}
		}
		return false;
	}


}
