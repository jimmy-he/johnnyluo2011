package com.tinet.ccic.wm.commons.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.tinet.ccic.wm.commons.Constants;
import com.tinet.ccic.wm.commons.CCICException;

/**
 * 为Criterion生成查询条件
 *<p>
 * 文件名： CriterionUtil.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class CriterionUtil {
	private static String logger = Constants.COMMONS;// LogHandler.getLogger(getClass());
	
	public static void appendCriteria(Criteria criteria, Map<String, Criteria> subCrierias, 
								String method, String prop, Object value) throws Exception {

		// 对in操作的数据再进行一下特殊处理
		if (Constants.RICH_RULE_IN.equals(method)) {
			Object[] arrValue = (Object[])value;
			
			if (arrValue.length == 1) {
				method = Constants.RICH_RULE_EQUAL;
				value = arrValue[0];
			} else {
				value = java.util.Arrays.asList(arrValue);
			}
		}
		
		if (prop.indexOf('.') == -1) {
			criteria.add(getCriterian(method, prop, value));
		} else {
			// 对空属性进行初始化，以便可以设置值。
			Criteria tempCriteria = initSubCriteria(criteria, subCrierias, prop);
			tempCriteria.add(getCriterian(method, StringUtils.substringAfterLast(prop, "."), value));
		}
		
	}
	
	/** 
	 * @param method 规则eq, lt, like(START)等
	 * @param prop 属性名称，要求是最终的属性名称不能带有'.'的复合属性
	 * @value 属性值，类型与属性值匹配的属性值
	 */
	private static Criterion getCriterian(String method, String prop, Object value) {
		if (Constants.RICH_RULE_EQUAL.equals(method)) {
			return Restrictions.eq(prop, value);
		} else if (Constants.RICH_RULE_GREATEQUAL.equals(method)) {
			return Restrictions.ge(prop, value);
		} else if (Constants.RICH_RULE_LITTLEEQUAL.equals(method)) {
			return Restrictions.le(prop, value);
		} else if (Constants.RICH_RULE_LITTLE.equals(method)) {
			return Restrictions.lt(prop, value);
		} else if (Constants.RICH_RULE_GREAT.equals(method)) {
			return Restrictions.gt(prop, value);
		} else if (Constants.RICH_RULE_NOTEQUAL.equals(method)) {
			return Restrictions.ne(prop, value);
		} else if (Constants.RICH_RULE_IN.equals(method)) {
			return Restrictions.in(prop, (List)value);
		} else if (Constants.RICH_RULE_GREATEQUAL.equals(method)) {
			return Restrictions.ge(prop, value);
		} else {
			String strMatchMode = StringUtils.substringBetween(method, "(", ")");
			MatchMode matchMode = null;
			if (strMatchMode == null || "ANYWHERE".equals(strMatchMode)) {
				matchMode = MatchMode.ANYWHERE;
			} else if (strMatchMode.equals("START")) {
				matchMode = MatchMode.START;
			} else if (strMatchMode.equals("END")) {
				matchMode = MatchMode.END;
			}
			
			if (method.startsWith(Constants.RICH_RULE_LIKE)) {
				return Restrictions.like(prop, (String)value, matchMode);
			} else {
				return Restrictions.ilike(prop, (String)value, matchMode);
			}
		}
	}
	
	private static Criteria initSubCriteria(Criteria rootCriteria, Map<String, Criteria> subCrierias, String prop) {
		int fromIndex = 0;
		int pIndex = prop.indexOf('.', fromIndex);
		Criteria parent = rootCriteria;
		Criteria tempCriteria = null;
		
		while (pIndex != -1) {
			String subProp = prop.substring(0, pIndex);
			tempCriteria = subCrierias.get(subProp);
			if (tempCriteria == null) {
				tempCriteria = parent.createCriteria(prop.substring(fromIndex, pIndex));
				subCrierias.put(subProp, tempCriteria);
			}
			
			parent = tempCriteria;
			fromIndex = pIndex + 1;
			pIndex = prop.indexOf('.', fromIndex);
		}
		
		return tempCriteria;
	}
	
	
	public static void addCriteriaOrder(Criteria criteria, Map<String, Criteria> subCriterias, 
										Map orderMap) throws Exception {
		for (Iterator it = orderMap.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry entry = (Map.Entry)it.next();
			String prop = (String)entry.getKey();
			String order = (String)entry.getValue();
			Criteria tempCriteria = null;
			
			if (prop.indexOf('.') == -1) {
				tempCriteria = criteria;
			} else {
				tempCriteria = initSubCriteria(criteria, subCriterias, prop);
				prop = StringUtil.substringAfterLast(prop, ".");
			}
			
			if (Constants.ORDER_ASCEND.equalsIgnoreCase(order)) {
				tempCriteria.addOrder(Order.asc(prop));
			} else {
				tempCriteria.addOrder(Order.desc(prop));
			}
		}
	}
	
	public void generateCriteria(Object criteria, String key, Object tempStr,
			int type, boolean flag) {

		Criterion criterion = null;
		if(tempStr instanceof String){
		String tempString =((String)tempStr).trim();
		tempString = tempString.replaceAll("　", " ");
		tempString = tempString.replaceAll("＝", "=");
		tempString = tempString.replaceAll("＋", "+");
		tempString = tempString.replaceAll("－", "-");
		tempString = tempString.replaceAll("—", "-");
		
		int indexFlag = tempString.indexOf(" ");
		int count = 0;// 如果是最前面的则需要有“+”“-”符号开头，否则可以不要
		
		while (indexFlag >= 0 || count == 0) {
			String frontStr = null;
			if (indexFlag > 0 || indexFlag == 0
					&& tempString.trim().indexOf(" ") > 0) {
				if (indexFlag == 0) {
					indexFlag = tempString.trim().indexOf(" ");
				}
				frontStr = tempString.trim().substring(0, indexFlag).trim();
			} else {
				frontStr = tempString.trim();
			}
			
			if (type == 0) {// 0代表字符型
				if (indexFlag >= 0) {
					// 空格数大于0，代表有多值，多值情况使用or的关系
					Criterion criterionTemp = createStringCriterion(key,
							frontStr, false, flag);
					if (count == 0) {
						criterion = criterionTemp;
					} else {
						criterion = Restrictions.or(criterion, criterionTemp);
					}
				} else {
					criterion = createStringCriterion(key, frontStr, false,
							flag);
				}
			} else if (type == 1) {// 1代表数字型
				if (indexFlag >= 0) {
					Criterion criterionTemp = createNumCriterion(key, count,
							frontStr);
					if (count == 0) {
						criterion = criterionTemp;
					} else {
						criterion = Restrictions.or(criterion, criterionTemp);
					}
				} else {
					criterion = createNumCriterion(key, count, frontStr);
				}

			} else if (type == 8 || type == 12) {// 8,12代表日期型
				// if (DateUtil.checkIsDate(frontStr)) {
				if (indexFlag >= 0) {// 多值情况
					Criterion criterionTemp = createDateCriterion(key,
							frontStr, count, type, flag);
					if (count == 0) {
						criterion = criterionTemp;
					} else {
						criterion = Restrictions.or(criterion, criterionTemp);
					}
				} else {
					criterion = createDateCriterion(key, frontStr, count, type,
							flag);
				}
			} else if (type == 10) {// 10代表大字符串
				if (indexFlag >= 0) {
					Criterion criterionTemp = createBigStringCriterion(key,
							frontStr, flag);
					if (count == 0) {
						criterion = criterionTemp;
					} else {
						criterion = Restrictions.or(criterion, criterionTemp);
					}
				} else {
					criterion = createBigStringCriterion(key, frontStr, flag);
				}

			}
		
			count++;
			if (indexFlag > -1) {
				tempString = tempString.trim().substring(indexFlag);
				indexFlag = tempString.indexOf(" ");
			}
		}
		}else{
		   criterion = Restrictions.eq(key, tempStr);
		}
		if (criterion != null) {
			criterion = Restrictions.conjunction().add(criterion);
			if (criteria instanceof Criteria) {
				((Criteria) criteria).add(criterion);
			} else if (criteria instanceof Criterion) {
				((Conjunction) criteria).add(criterion);
			}
		}	
		
	}

	/**
	 * @author Jathy_Lee
	 * @version 1.0 功能描述：
	 * @see
	 * @param flag标志为是否按默认的字符串匹配后缀模式
	 * @return
	 * @exception CCICException
	 */
	protected Criterion createStringCriterion(String key, String frontStr,
			boolean flag, boolean isCriteria) {
		Criterion criterionTemp = null;
		if (frontStr.startsWith("%") || frontStr.endsWith("%")) {
			if (!isCriteria) {
				criterionTemp = Expression.like((String) key, "'" + frontStr
						+ "'");
			} else {
				criterionTemp = Expression.like((String) key, frontStr);
			}

		} else if (flag) {
			if (!isCriteria) {
				criterionTemp = Expression.like((String) key, "'" + frontStr
						+ "%'");
			} else {

				criterionTemp = Expression.like((String) key, frontStr + "%");
			}

		} else {
			if (!isCriteria) {
				criterionTemp = Expression.eq((String) key, "'" + frontStr
						+ "'");
			} else {
				criterionTemp = Expression.eq((String) key, frontStr);
			}
		}
		return criterionTemp;
	}
	/**
	 * @author Jathy_Lee
	 * @version 1.0 功能描述：
	 * @see
	 * @param flag标志为是否按默认的字符串匹配后缀模式
	 * @return
	 * @exception CCICException
	 */
	protected Criterion createDateCriterion(String key, String frontStr,
			int count, int type, boolean isCriteria) {
		Criterion criterionTemp = null;
		String suff = null;
		String pref = null;
		if (type == 12) {
			suff = "01010000";// 月日包含小时分最小值
			pref = "12312359"; // 月日小时分最大值
		} else if (type == 8) {
			suff = "0101";// 月日最小值
			pref = "1231"; // 月日最大值
		}
		if (frontStr.startsWith("+") || count > 0) {// 有+号开头的
			if (count == 0) {
				frontStr = frontStr.substring(1);
			}
			if (frontStr.indexOf("-") >= 0) {// 有+号开头并有中间有-号
				String tempFrontStr = frontStr.substring(0, frontStr
						.indexOf("-"));
				frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				int lengthFront = tempFrontStr.length();
				int lengthEnd = frontStr.length();
				if (!tempFrontStr.equals("") && !frontStr.equals("")) {
					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						StringBuffer bufSql2 = new StringBuffer();
						bufSql.append("'").append(tempFrontStr).append(
								suff.substring(8 - (12 - lengthFront))).append(
								"'");
						bufSql2.append("'").append(frontStr).append(
								pref.substring(8 - (12 - lengthEnd))).append(
								"'");
						criterionTemp = Expression.and(Expression.ge(key,
								bufSql.toString()), Expression.le(key, bufSql2
								.toString()));
						bufSql = null;
						bufSql2 = null;

					} else {
						criterionTemp = Expression.and(Expression.ge(key,tempFrontStr+ suff.substring(8 - (12 - lengthFront))),
										Expression.le(key,frontStr+ pref.substring(8 - (12 - lengthEnd))));
					}

				} else if (tempFrontStr.equals("") && !frontStr.equals("")) {// 前面部分为空

					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						bufSql.append("'").append(frontStr).append(
								pref.substring(8 - (12 - lengthEnd))).append(
								"'");
						criterionTemp = Expression.le(key, bufSql.toString());
						bufSql = null;

					} else {
						criterionTemp = Expression.le(key, frontStr
								+ pref.substring(8 - (12 - lengthEnd)));

					}

				} else if (!tempFrontStr.equals("") && frontStr.equals("")) {// 后面部分为空

					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						bufSql.append("'").append(tempFrontStr).append(
								suff.substring(8 - (12 - lengthFront))).append(
								"'");
						criterionTemp = Expression.ge(key, bufSql.toString());
						bufSql = null;

					} else {
						criterionTemp = Expression.ge(key, tempFrontStr
								+ suff.substring(8 - (12 - lengthFront)));

					}
				}

			} else {// 只有+号开头的（这样肯定是一个值的情况），大于当前时间的所有日期
				if (frontStr.indexOf("+") >= 0) {
					frontStr = frontStr.substring(frontStr.indexOf("+") + 1);
				} else if (frontStr.indexOf("-") >= 0) {
					frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				}
				int length = frontStr.length();

				if (!isCriteria) {
					StringBuffer bufSql = new StringBuffer();
					bufSql.append("'").append(frontStr).append(
							suff.substring(8 - (12 - length))).append("'");
					criterionTemp = Expression.ge(key, bufSql.toString());
					bufSql = null;
				} else {
					criterionTemp = Expression.ge(key, frontStr
							+ suff.substring(8 - (12 - length)));

				}
			}

		} else if (frontStr.startsWith("-") || count > 0) {// 有-号开头
			if (count == 0) {
				frontStr = frontStr.substring(1);
			}
			if (frontStr.indexOf("+") >= 0) {// 有-号开头并且中间有+号
				String tempFrontStr = frontStr.substring(0, frontStr
						.indexOf("+"));
				frontStr = frontStr.substring(frontStr.indexOf("+") + 1);
				int lengthFront = tempFrontStr.length();
				int lengthEnd = frontStr.length();

				if (!tempFrontStr.equals("") && !frontStr.equals("")) {

					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						StringBuffer bufSql2 = new StringBuffer();
						bufSql.append("'").append(tempFrontStr).append(
								suff.substring(8 - (12 - lengthFront))).append(
								"'");
						bufSql2.append("'").append(frontStr).append(
								pref.substring(8 - (12 - lengthEnd))).append(
								"'");
						criterionTemp = Expression.and(Expression.le(key,
								bufSql.toString()), Expression.ge(key, bufSql2
								.toString()));
						bufSql = null;
						bufSql2 = null;

					} else {
						criterionTemp = Expression
								.and(
										Expression
												.le(
														key,
														tempFrontStr
																+ suff
																		.substring(8 - (12 - lengthFront))),
										Expression
												.ge(
														key,
														frontStr
																+ pref
																		.substring(8 - (12 - lengthEnd))));

					}

					// criterionTemp = Expression.sql(bufSql.toString());
				} else if (tempFrontStr.equals("") && !frontStr.equals("")) {// 前面部分为空

					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						bufSql.append("'").append(frontStr).append(
								pref.substring(8 - (12 - lengthEnd))).append(
								"'");
						criterionTemp = Expression.ge(key, bufSql.toString());
						bufSql = null;

					} else {
						criterionTemp = Expression.ge(key, frontStr
								+ pref.substring(8 - (12 - lengthEnd)));

					}
				} else if (!tempFrontStr.equals("") && frontStr.equals("")) {// 后面部分为空

					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						bufSql.append("'").append(tempFrontStr).append(
								suff.substring(8 - (12 - lengthFront))).append(
								"'");
						criterionTemp = Expression.le(key, bufSql.toString());
						bufSql = null;

					} else {
						criterionTemp = Expression.le(key, tempFrontStr
								+ suff.substring(8 - (12 - lengthFront)));

					}

				}
			} else {// 只有-号开头（这样肯定是一个值的情况），小于当前时间的所有日期
				if (frontStr.indexOf("+") >= 0) {
					frontStr = frontStr.substring(frontStr.indexOf("+") + 1);
				} else if (frontStr.indexOf("-") >= 0) {
					frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				}
				int length = frontStr.length();

				if (!isCriteria) {
					StringBuffer bufSql = new StringBuffer();
					bufSql.append("'").append(frontStr).append(
							pref.substring(8 - (12 - length))).append("'");
					criterionTemp = Expression.le(key, bufSql.toString());

				} else {
					criterionTemp = Expression.le(key, frontStr
							+ pref.substring(8 - (12 - length)));

				}
			}

		} else {// 没有+号或者-号开头的情况
			if (frontStr.indexOf("-") >= 0) {// 有-号的情况下，条件都是在当年、当月、或者当日、当时以内
				String tempFrontStr = frontStr.substring(0, frontStr
						.indexOf("-"));
				frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				int lengthFront = tempFrontStr.length();
				int lengthEnd = frontStr.length();

				if (!isCriteria) {
					StringBuffer bufSql = new StringBuffer();
					StringBuffer bufSql2 = new StringBuffer();
					bufSql.append("'").append(tempFrontStr).append(
							suff.substring(8 - (12 - lengthFront))).append("'");
					bufSql2.append("'").append(frontStr).append(
							pref.substring(8 - (12 - lengthEnd))).append("'");

					criterionTemp = Expression.and(Expression.ge(key, bufSql
							.toString()), Expression
							.le(key, bufSql2.toString()));
					bufSql = null;
					bufSql2 = null;
				} else {
					criterionTemp = Expression.and(Expression.ge(key,
							tempFrontStr
									+ suff.substring(8 - (12 - lengthFront))),
							Expression.le(key, frontStr
									+ pref.substring(8 - (12 - lengthEnd))));
				}
			} else {// 单个值并且没有+或者-号的情况下，条件都是在当年、当月、或者当日、当时、当分以内
				if (!isCriteria) {
				criterionTemp = Expression.like(key, "'"+frontStr + "%'");
				}else{
					criterionTemp = Expression.like(key, frontStr + "%");
				}
			}
		}
		return criterionTemp;
	}
	protected Criterion createBigStringCriterion(String key, String frontStr,
			boolean isCriteria) {
		Criterion criterionTemp = null;
		if (frontStr.startsWith("=") || frontStr.endsWith("=")) {
			if(frontStr.startsWith("=")){
				String tempStr = frontStr.substring(1);
				if (!isCriteria) {
					criterionTemp = Expression.eq((String) key, "'" + tempStr
							+ "'");
				} else {
					criterionTemp = Expression.eq((String) key, tempStr);
				}
			}else{
				String tempStr = frontStr.substring(0,frontStr.length()-1);
				if (!isCriteria) {
					criterionTemp = Expression.eq((String) key, "'" + tempStr
							+ "'");
				} else {
					criterionTemp = Expression.eq((String) key, tempStr);
				}
			}

		} else {
			if (!isCriteria) {
				criterionTemp = Expression.like((String) key, "'%" + frontStr
						+ "%'");
			} else {
				criterionTemp = Expression.like((String) key, "%" + frontStr
						+ "%");
			}

		}
		return criterionTemp;
	}
	protected Criterion createNumCriterion(String key, int count,
			String frontStr) {
		
		Criterion criterionTemp = null;
		if (frontStr.startsWith("+") || count > 0) {// 以+号开头
			if (count == 0) {
				frontStr = frontStr.substring(1);
			}
			if (frontStr.indexOf("-") >= 0) {// 以加号开头并且有减号
				String tempFrontStr = frontStr.substring(0, frontStr
						.indexOf("-"));
				frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				if (!tempFrontStr.equals("") && !frontStr.equals("")) {
//					 数字型
					if (((String) key).endsWith("_LONG")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_LONG"));
						criterionTemp = Expression.and(Expression.ge(key,
								Long.valueOf(tempFrontStr)), Expression.le(key, Long.valueOf(frontStr)));
					} else if (((String) key)
							.endsWith("_INTEGER")) {
						key = ((String) key).substring(
										0,
										((String) key)
												.lastIndexOf("_INTEGER"));
						criterionTemp = Expression.and(Expression.ge(key,
								Integer.valueOf(tempFrontStr)), Expression.le(key, Integer.valueOf(frontStr)));
					} else if (((String) key)
							.endsWith("_DOUBLE")) {
						key = ((String) key)
								.substring(0, ((String) key)
										.lastIndexOf("_DOUBLE"));
						criterionTemp = Expression.and(Expression.ge(key,
								Double.valueOf(tempFrontStr)), Expression.le(key, Double.valueOf(frontStr)));
					} else if (((String) key)
							.endsWith("_FLOAT")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_FLOAT"));
						criterionTemp = Expression.and(Expression.ge(key,
								Float.valueOf(tempFrontStr)), Expression.le(key, Float.valueOf(frontStr)));
					}
				} else if (tempFrontStr.equals("") && !frontStr.equals("")) {// 前面部分为空
					if (((String) key).endsWith("_LONG")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_LONG"));
						criterionTemp = Expression.le(key, Long.valueOf(frontStr));
					} else if (((String) key)
							.endsWith("_INTEGER")) {
						key = ((String) key)
								.substring(
										0,
										((String) key)
												.lastIndexOf("_INTEGER"));
						criterionTemp = Expression.le(key, Integer.valueOf(frontStr));
					} else if (((String) key)
							.endsWith("_DOUBLE")) {
						key = ((String) key)
								.substring(0, ((String) key)
										.lastIndexOf("_DOUBLE"));
						criterionTemp = Expression.le(key, Double.valueOf(frontStr));
					} else if (((String) key)
							.endsWith("_FLOAT")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_FLOAT"));
						criterionTemp = Expression.le(key, Float.valueOf(frontStr));
					}
					
				} else if (!tempFrontStr.equals("") && frontStr.equals("")) {// 后面部分为空
					if (((String) key).endsWith("_LONG")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_LONG"));
						criterionTemp = Expression.ge(key, Long.valueOf(tempFrontStr));
					} else if (((String) key)
							.endsWith("_INTEGER")) {
						key = ((String) key)
								.substring(
										0,
										((String) key)
												.lastIndexOf("_INTEGER"));
						criterionTemp = Expression.ge(key, Integer.valueOf(tempFrontStr));
					} else if (((String) key)
							.endsWith("_DOUBLE")) {
						key = ((String) key)
								.substring(0, ((String) key)
										.lastIndexOf("_DOUBLE"));
						criterionTemp = Expression.ge(key, Double.valueOf(tempFrontStr));
					} else if (((String) key)
							.endsWith("_FLOAT")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_FLOAT"));
						criterionTemp = Expression.ge(key, Float.valueOf(tempFrontStr));
					}
					//criterionTemp = Expression.ge(key, tempFrontStr);
				}
			} else {
				if (((String) key).endsWith("_LONG")) {
					key = ((String) key).substring(0,
							((String) key)
									.lastIndexOf("_LONG"));
					criterionTemp = Expression.ge(key, Long.valueOf(frontStr));
				} else if (((String) key)
						.endsWith("_INTEGER")) {
					key = ((String) key)
							.substring(
									0,
									((String) key)
											.lastIndexOf("_INTEGER"));
					criterionTemp = Expression.ge(key, Integer.valueOf(frontStr));
				} else if (((String) key)
						.endsWith("_DOUBLE")) {
					key = ((String) key)
							.substring(0, ((String) key)
									.lastIndexOf("_DOUBLE"));
					criterionTemp = Expression.ge(key, Double.valueOf(frontStr));
				} else if (((String) key)
						.endsWith("_FLOAT")) {
					key = ((String) key).substring(0,
							((String) key)
									.lastIndexOf("_FLOAT"));
					criterionTemp = Expression.ge(key, Float.valueOf(frontStr));
				}
			//	criterionTemp = Expression.ge(key, frontStr);
			}
		} else if (frontStr.startsWith("-") || count > 0) {
			if (count == 0) {
				frontStr = frontStr.substring(1);
			}
			if (frontStr.indexOf("+") >= 0) {
				String tempFrontStr = frontStr.substring(0, frontStr
						.indexOf("+"));
				frontStr = frontStr.substring(frontStr.indexOf("+") + 1);
				if (!tempFrontStr.equals("") && !frontStr.equals("")) {
					if (((String) key).endsWith("_LONG")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_LONG"));
						criterionTemp = Expression.and(Expression.le(key,
								Long.valueOf(tempFrontStr)), Expression.ge(key, Long.valueOf(frontStr)));
					} else if (((String) key)
							.endsWith("_INTEGER")) {
						key = ((String) key)
								.substring(
										0,
										((String) key)
												.lastIndexOf("_INTEGER"));
						criterionTemp = Expression.and(Expression.le(key,
								Integer.valueOf(tempFrontStr)), Expression.ge(key, Integer.valueOf(frontStr)));
					} else if (((String) key)
							.endsWith("_DOUBLE")) {
						key = ((String) key)
								.substring(0, ((String) key)
										.lastIndexOf("_DOUBLE"));
						criterionTemp = Expression.and(Expression.le(key,
								Double.valueOf(tempFrontStr)), Expression.ge(key, Double.valueOf(frontStr)));
					} else if (((String) key)
							.endsWith("_FLOAT")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_FLOAT"));
						criterionTemp = Expression.and(Expression.le(key,
								Float.valueOf(tempFrontStr)), Expression.ge(key, Float.valueOf(frontStr)));
					}
				//	criterionTemp = Expression.and(Expression.le(key,
						//	tempFrontStr), Expression.ge(key, frontStr));
				} else if (tempFrontStr.equals("") && !frontStr.equals("")) {// 前面部分为空
					if (((String) key).endsWith("_LONG")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_LONG"));
						criterionTemp = Expression.ge(key, Long.valueOf(frontStr));
					} else if (((String) key)
							.endsWith("_INTEGER")) {
						key = ((String) key)
								.substring(
										0,
										((String) key)
												.lastIndexOf("_INTEGER"));
						criterionTemp = Expression.ge(key, Integer.valueOf(frontStr));
					} else if (((String) key)
							.endsWith("_DOUBLE")) {
						key = ((String) key)
								.substring(0, ((String) key)
										.lastIndexOf("_DOUBLE"));
						criterionTemp = Expression.ge(key, Double.valueOf(frontStr));
					} else if (((String) key)
							.endsWith("_FLOAT")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_FLOAT"));
						criterionTemp = Expression.ge(key, Float.valueOf(frontStr));
					}
				//	criterionTemp = Expression.ge(key, frontStr);
				} else if (!tempFrontStr.equals("") && frontStr.equals("")) {// 后面部分为空
					if (((String) key).endsWith("_LONG")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_LONG"));
						criterionTemp = Expression.le(key, Long.valueOf(tempFrontStr));
					} else if (((String) key)
							.endsWith("_INTEGER")) {
						key = ((String) key)
								.substring(
										0,
										((String) key)
												.lastIndexOf("_INTEGER"));
						criterionTemp = Expression.le(key, Integer.valueOf(tempFrontStr));
					} else if (((String) key)
							.endsWith("_DOUBLE")) {
						key = ((String) key)
								.substring(0, ((String) key)
										.lastIndexOf("_DOUBLE"));
						criterionTemp = Expression.le(key, Double.valueOf(tempFrontStr));
					} else if (((String) key)
							.endsWith("_FLOAT")) {
						key = ((String) key).substring(0,
								((String) key)
										.lastIndexOf("_FLOAT"));
						criterionTemp = Expression.le(key, Float.valueOf(tempFrontStr));
					}
					//criterionTemp = Expression.le(key, tempFrontStr);
				}
			} else {
				if (((String) key).endsWith("_LONG")) {
					key = ((String) key).substring(0,
							((String) key)
									.lastIndexOf("_LONG"));
					criterionTemp = Expression.le(key, Long.valueOf(frontStr));
				} else if (((String) key)
						.endsWith("_INTEGER")) {
					key = ((String) key)
							.substring(
									0,
									((String) key)
											.lastIndexOf("_INTEGER"));
					criterionTemp = Expression.le(key, Integer.valueOf(frontStr));
				} else if (((String) key)
						.endsWith("_DOUBLE")) {
					key = ((String) key)
							.substring(0, ((String) key)
									.lastIndexOf("_DOUBLE"));
					criterionTemp = Expression.le(key, Double.valueOf(frontStr));
				} else if (((String) key)
						.endsWith("_FLOAT")) {
					key = ((String) key).substring(0,
							((String) key)
									.lastIndexOf("_FLOAT"));
					criterionTemp = Expression.le(key, Float.valueOf(frontStr));
				}
			//	criterionTemp = Expression.le(key, frontStr);
			}
		} else {
			if (frontStr.indexOf("-") >= 0) {// 有-号的情况下，条件都是在当年、当月、或者当日、当时以内
				String tempFrontStr = frontStr.substring(0, frontStr
						.indexOf("-"));
				frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				
				if (((String) key).endsWith("_LONG")) {
					key = ((String) key).substring(0,
							((String) key)
									.lastIndexOf("_LONG"));

					criterionTemp = Expression.and(Expression.ge(key, Long.valueOf(tempFrontStr)), Expression.le(key, Long.valueOf(frontStr)));
				} else if (((String) key)
						.endsWith("_INTEGER")) {
					key = ((String) key)
							.substring(
									0,
									((String) key)
											.lastIndexOf("_INTEGER"));
					criterionTemp = Expression.and(Expression.ge(key, Integer.valueOf(tempFrontStr)), Expression.le(key, Integer.valueOf(frontStr)));
				} else if (((String) key)
						.endsWith("_DOUBLE")) {
					key = ((String) key)
							.substring(0, ((String) key)
									.lastIndexOf("_DOUBLE"));
					criterionTemp = Expression.and(Expression.ge(key, Double.valueOf(tempFrontStr)), Expression.le(key, Double.valueOf(frontStr)));
				} else if (((String) key)
						.endsWith("_FLOAT")) {
					key = ((String) key).substring(0,
							((String) key)
									.lastIndexOf("_FLOAT"));
					criterionTemp = Expression.and(Expression.ge(key, Float.valueOf(tempFrontStr)), Expression.le(key, Float.valueOf(frontStr)));
				}
			}else{
				if (((String) key).endsWith("_LONG")) {
					key = ((String) key).substring(0,
							((String) key)
									.lastIndexOf("_LONG"));
					criterionTemp = Expression.eq(key, Long.valueOf(frontStr));
				} else if (((String) key)
						.endsWith("_INTEGER")) {
					key = ((String) key)
							.substring(
									0,
									((String) key)
											.lastIndexOf("_INTEGER"));
					criterionTemp = Expression.eq(key, Integer.valueOf(frontStr));
				} else if (((String) key)
						.endsWith("_DOUBLE")) {
					key = ((String) key)
							.substring(0, ((String) key)
									.lastIndexOf("_DOUBLE"));
					criterionTemp = Expression.eq(key, Double.valueOf(frontStr));
				} else if (((String) key)
						.endsWith("_FLOAT")) {
					key = ((String) key).substring(0,
							((String) key)
									.lastIndexOf("_FLOAT"));
					criterionTemp = Expression.eq(key, Float.valueOf(frontStr));
				}
			}
		
		//	criterionTemp = Expression.eq(key, frontStr);
		}
		return criterionTemp;
	}
	
}
