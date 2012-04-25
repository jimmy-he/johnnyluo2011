package com.tinet.ccic.wm.commons.util;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.tinet.ccic.wm.commons.Constants;
/**
 * 类型转换工具类。
 *<p>
 * 文件名： TypeConverterUtil.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class TypeConverterUtil {
	
	public static Map<Class, Map<String, Class>> classTypeCache = Collections.synchronizedMap(new WeakHashMap());
	
	public static List convertValue(Class rootClass, String prop, List values) {
		Class toType = null;

		try {
			toType = getToType(rootClass, prop);
			if (toType == String.class) {
				return values;
			} else if (toType == null) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		
		List realTypeValues = new ArrayList(values.size());
		
		for (Iterator it = values.iterator(); it.hasNext();) {
			Object value = it.next();
			try {
				if (value instanceof String) {
					realTypeValues.add(convertSingleValue((String)value, toType));
				} else if (value.getClass().isArray()) {
					Object[] arrRealTypeValue = new Object[((String[])value).length];
					for (int i = 0; i < arrRealTypeValue.length; i++) {
						arrRealTypeValue[i] = convertSingleValue(((String[])value)[i], toType);
					}
					realTypeValues.add(arrRealTypeValue);
				} 
			} catch (Exception e) {
				return null;
			}
		}
		
		return realTypeValues;
	}
	
	private static Class getToType(Class rootClass, String prop) throws Exception {
		Map<String, Class> typeMap = classTypeCache.get(rootClass);
		if (typeMap == null) {
			typeMap = new java.util.Hashtable();
			classTypeCache.put(rootClass, typeMap);
		} else {
			Class rClazz = typeMap.get(prop);
			if (rClazz != null) {
				return rClazz;
			}
		}
		
		Class parentClass = rootClass;
		String[] props = StringUtils.split(prop, '.');
		
		for(String subprop : props) {
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(parentClass);
			parentClass = null;
			for (PropertyDescriptor pd : pds) {
				if (subprop.equals(pd.getName())) {
					parentClass = pd.getPropertyType();
					break;
				}
			}
			
			if (parentClass == null) {
				return null;
			}
		}
		
		typeMap.put(prop, parentClass);
		return parentClass; 
	}
	
	private static Object convertSingleValue(String value, Class toType) throws Exception{
		Object result = null;

		if (toType == boolean.class || toType == Boolean.class) {
			result = doConvertToBoolean(value);
		} else if (Date.class.isAssignableFrom(toType)) {
			result = doConvertToDate(value);
		} else if (toType == Character.class || toType == char.class) {
			result = doConvertToCharacter(value);
		} else if (Number.class.isAssignableFrom(toType)) {
			result = doConvertToNumber(value, toType);
	    }

		return result;
	}

	private static Object doConvertToCharacter(Object value) {
	    if (value instanceof String) {
	        String cStr = (String) value;
	        return (cStr.length() > 0) ? new Character(cStr.charAt(0)) : null;
	    }
	
	    return null;
	}
	
	private static Object doConvertToBoolean(Object value) {
	    if (value instanceof String) {
	        String bStr = (String) value;
	        return Boolean.valueOf(bStr);
	    }
	
	    return null;
	}

	   


	private static Object doConvertToDate(String value) {
		Date result = null;
	        		
		SimpleDateFormat d2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		SimpleDateFormat[] dfs = {d1, d2}; //added RFC 3339 date format (XW-473)
        for (int i = 0; i < dfs.length; i++) {
            try {
                Date check = dfs[i].parse(value);

                if (check != null) {
                    result = check;
                	break;
                }
            } catch (ParseException ignore) {
            	// do nothing
            }
        }

        return result;
	}

    private static Object doConvertToNumber(String value, Class toType) throws Exception {
    	Object result = null;
//    	Constructor ct = toType.getConstructor(new Class[]{String.class});
//    	return ct.newInstance(value);

    	if ((toType == Integer.class) || (toType == Integer.TYPE)) {
            result = new Integer(value);
    	} else if ((toType == Double.class) || (toType == Double.TYPE)) {
            result = new Double(value);
    	} else if ((toType == Byte.class) || (toType == Byte.TYPE)) {
            result = new Byte(value);
    	} else if ((toType == Short.class) || (toType == Short.TYPE)) {
            result = new Short(value);
    	} else if ((toType == Long.class) || (toType == Long.TYPE)) {
            result = new Long(value);
    	} else if ((toType == Float.class) || (toType == Float.TYPE)) {
            result = new Float(value);
    	} else if (toType == BigInteger.class) {
            result = new BigInteger(value);
    	} else if (toType == BigDecimal.class) {
            result = new BigDecimal(value);
    	}
    	
    	return result;
    }
}
