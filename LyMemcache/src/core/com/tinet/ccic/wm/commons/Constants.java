package com.tinet.ccic.wm.commons;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tinet.ccic.wm.commons.util.ConfigurableContants;

/**
 * 常量类。
 *<p>
 * 文件名： Constants.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class Constants extends ConfigurableContants {
	/**
	 * locale bundel file name,like messages_zh_CN.properties.
	 */
	// 静态初始化读入framework.properties中的设置
	static {
		init("/conf/framework.properties");
	}

	/**
	 * 从framework.properties中读取constant.message_bundle_key的值，如果配置文件不存在或配置文件中不存在该值时，
	 * 取定义的默认值。
	 */
	public final static String MESSAGE_BUNDLE_KEY = getProperty(
			"constant.message_bundle_key", "i18n/messages");

	public final static String ERROR_BUNDLE_KEY = getProperty(
			"constant.error_bundle_key", "i18n/errors");

	// 每页显示记录数
	public final static int DEFAULT_PAGE_SIZE = Integer.parseInt(getProperty(
			"constant.default_page_size", String.valueOf(15)));

	public final static String USER_IN_SESSION = getProperty(
			"constant.user_in_session", "loginUser");

	public final static String DEFAULT_MENU_TARGET = getProperty(
			"menu.default.target", "mainFrame");

	public final static String DEFAULT_MENU_IMAGE_PATH = getProperty(
			"menu.default.image.path", "/images/icon/16x16/");

	/*
	 * #如果需要把某个查询条件字段作为form表单对应的实体的某个属性时需要加入search参数， #如果有此属性，系统则将该属性对应到实体属性，
	 * #以便于数据类型可以根据实体属性数据类型自动转换。
	 */
	public final static String DEFAULT_SEARCH_FLAG = getProperty("search",
			"search");

	// #页面框架查询form名称定义
	public final static String DEFAULT_QUERYFORM_NAME = getProperty(
			"queryForm", "queryForm");

	// #页面框架查询MAP form名称定义
	public final static String DEFAULT_SEARCHFORM_NAME = getProperty(
			"searchForm", "searchForm");

	// #查询参数回显，flag标志代表如果form表单属性名称和url参数名称一致时，
	// #以url为优先，可以覆盖form表单相应属性内容。
	public final static String DEFAULT_URL_FLAG = getProperty("flag", "flag");

	public final static String DEFAULT_NO_PAGE_FLAG = getProperty("noShowPage",
			"noShowPage");

	// #查询条件字段如果不是实体对应form的某个属性时,使用search_前缀命名，包括url参数。
	public final static String DEFAULT_PREFIX_SEARCH = getProperty(
			"prefixsearch", "search_");

	// #列表显示时的总行数变量定义
	public final static String DEFAULT_TOTALROWS_NAME = getProperty(
			"totalRows", "totalRows");

	// #列表显示时，列表实体参数定义
	public final static String DEFAULT_LIST_ENTITY = getProperty("entities",
			"entities");

	// #列表页面的列表主健名称，如checkbox的id名称
	public final static String DEFAULT_LIST_ITEM = getProperty("itemlist",
			"itemlist");

	// #列表名称
	public final static String DEFAULT_TABLE_NAME = getProperty("tablename",
			"ec");

	// #统一处理查询时定义的参数：sql代表sql或者hql语句；args代表参数;types代表类型
	public final static String DEFAULT_SQLORHQL_NAME = getProperty("sql", "sql");

	public final static String DEFAULT_ARGS_NAME = getProperty("args", "args");

	public final static String DEFAULT_TYPES_NAME = getProperty("types",
			"types");

	// #框架主健的名称
	public final static String DEFAULT_ID_NAME = getProperty("ID", "id");
	
	/** 对应于Ext treepanel，传递节点id */
	public final static String DEFAULT_TREENODE_NAME = "node";

	// #由框架统一处理码表，需要用前缀标注，此字段为码表。所以码表字段的名称命名需要加入此前缀
	public final static String DEFAULT_PREFIX_CODE = getProperty("prefixcode",
			"EW_");

	// #码表值分隔符，由于本系统码表的值可能是多个字段组合，所以需要分隔符
	public final static String DEFAULT_SPLIT_FLAG = getProperty("splitflag",
			"|");

	public final static int DEFAULT_SIZE_THRESHOLD = Integer
			.parseInt(getProperty("thresholdsize", "1024000"));

	public final static String DEFAULT_UPLOAD_PATH = getProperty("upload_path",
			"d:/temp");

	// #行级权限中本组织替代符
	public final static String DEFAULT_ORG_COND = getProperty("security.org",
			"&");

	// #行级权限中用户替代符
	public final static String DEFAULT_USER_COND = getProperty("security.user",
			"#");

	// #行级权限中下级组织替代符
	public final static String DEFAULT_SUBORG_COND = getProperty(
			"security.suborg", "@");
	
	//#行级权限中分局或直属单位中所有单位替代符
	public final static String DEFAULT_ALLORG_COND = getProperty(
			"security.allorg", "A");

	// #行级权限中配置文件的模块属性标示
	public final static String DEFAULT_MODUELATTR_XML = getProperty(
			"security.moduelattr", "name");

	// #行级权限中配置文件的权限属性标示
	public final static String DEFAULT_ACLATTR_XML = getProperty(
			"security.aclattr", "role");

	// #行级权限中表名别名标示
	public final static String DEFAULT_ALIAS_FLAG = getProperty(
			"security.aliasflag", "/");

	// #数据权限配置文件别名前缀
	public final static String DEFAULT_SECURITY_CONFIGFILEALIAS = getProperty(
			"security.cofigfilealias", "DATA_");

	// #数据权限默认角色
	public final static String DEFAULT_SECURITY_DEFAULTROLE = getProperty(
			"security.defaultrole", "ALLROLE");

	// 树型菜单根目录显示标题
	public final static String TREE_ROOT_TITLE = getProperty("tree.root.title",
			"根目录");

	/** The name of the ResourceBundle used in this application */
	public static final String BUNDLE_KEY = "ApplicationResources";

	/** The encryption algorithm key to be used for passwords */
	public static final String ENC_ALGORITHM = "algorithm";

	/** A flag to indicate if passwords should be encrypted */
	public static final String ENCRYPT_PASSWORD = "encryptPassword";

	/** File separator from System properties */
	public static final String FILE_SEP = System.getProperty("file.separator");

	/** User home from System properties */
	public static final String USER_HOME = System.getProperty("user.home")
			+ FILE_SEP;

	/** The name of the configuration hashmap stored in application scope. */
	public static final String CONFIG = "appConfig";

	/**
	 * Session scope attribute that holds the locale set by the user. By setting
	 * this key to the same one that Struts uses, we get synchronization in
	 * Struts w/o having to do extra work or have two session-level variables.
	 */
	public static final String PREFERRED_LOCALE_KEY = "org.apache.struts.action.LOCALE";

	/**
	 * The request scope attribute under which an editable user form is stored
	 */
	public static final String USER_KEY = "userForm";

	/**
	 * The request scope attribute that holds the user list
	 */
	public static final String USER_LIST = "userList";

	/**
	 * The request scope attribute for indicating a newly-registered user
	 */
	public static final String REGISTERED = "registered";

	/**
	 * The name of the Administrator role, as specified in web.xml
	 */
	public static final String ADMIN_ROLE = "admin";

	/**
	 * The name of the User role, as specified in web.xml
	 */
	public static final String USER_ROLE = "user";

	/**
	 * The name of the user's role list, a request-scoped attribute when
	 * adding/editing a user.
	 */
	public static final String USER_ROLES = "userRoles";

	/**
	 * The name of the available roles list, a request-scoped attribute when
	 * adding/editing a user.
	 */
	public static final String AVAILABLE_ROLES = "availableRoles";

	public static final String ERROR_KEY = "error_key";

	// 定义跟踪分页查询页面的URL地址,因为页面需要引用，请勿修改其值
	public static final String BACK_QUERY_URL = "backQueryURL";

	// 保存当前Struts访问路径，用于页面EL输出当前ServletPath，请勿修改其值
	public static final String CURRENT_SERVLET_PATH = "servletPath";

	public static final String BASE_PATH = "basePath";

	// 系统标示代码
	public static final String SYSTEM_SIGN = getProperty("system.sign", "jeaw");
	
	public static final String SYSTEM_CAS_SIGN = "cas";

	// 历史表前缀
	public static final String HISYTORY_PREFIX = getProperty("history.prefix",
			"H");
	//xml file name
	public static final String XML_FILENAME = getProperty("xmlfileName",
	"ccic-config.xml");
//	CONFIG_FILE
	public static final String CONFIG_FILE = getProperty("CONFIG_FILE",
	"com.jeaw.config");
	
	public static final String STATUS_INVALID = "0";

	public static final String STATUS_VALID = "1";

	public static final String USER_ONLINE = "1";

	public static Map statusEnum() {
		Map<String, String> statusEnum = new LinkedHashMap<String, String>();
		statusEnum.put(STATUS_VALID, "有效");
		statusEnum.put(STATUS_INVALID, "无效");
		return statusEnum;
	}

	// 实体查看标识
	public static final String PERSISTENCE_OPERATION_QUERY = getProperty(
			"entity.query", "Q");
	
	// 实体新增标识
	public static final String PERSISTENCE_OPERATION_CREATE = getProperty(
			"entity.add", "A");

	// 实体更新标识
	public static final String PERSISTENCE_OPERATION_UPDATE = getProperty(
			"entity.update", "U");

	// 实体删除标识
	public static final String PERSISTENCE_OPERATION_DELETE = getProperty(
			"entity.delete", "D");

	public static Map operationEnum() {
		Map<String, String> operationEnum = new LinkedHashMap<String, String>();
		operationEnum.put(PERSISTENCE_OPERATION_CREATE, "创建");
		operationEnum.put(PERSISTENCE_OPERATION_UPDATE, "更新");
		operationEnum.put(PERSISTENCE_OPERATION_DELETE, "删除");
		return operationEnum;
	}

	public static final String SESSION_COUNT_KEY = "onlineSessionCount";

	public static final String LOGINED_KEY = "logined";

	public static final String SEX_MALE = "M";

	public static final String SEX_FEMALE = "F";

	public static Map sexEnum() {
		Map<String, String> sexEnum = new LinkedHashMap<String, String>();
		sexEnum.put(SEX_MALE, "男");
		sexEnum.put(SEX_FEMALE, "女");
		return sexEnum;
	}

	public static final String BOOLEAN_TRUE = "1";
	
	//几个特殊角色
	//超级管理员角色
	public static final String SECURITY_SUPERADMIN = "1";
	//系统数据管理员角色
	public static final String SECURITY_SYSTEMDATAADMIN = "2";
	//本单位数据操作员角色
	public static final String SECURITY_LOCALDATAADMIN = "3";
	//串并案角色
	public static final String SECURITY_CBAADMIN = "4";
//	机动车管理员角色
	public static final String SECURITY_JDCADMIN = "5";
	//分局管理员
	public static final String SECURITY_FJADMIN = "6";
	
	public static final String BOOLEAN_FALSE = "0";

	public static Map booleanEnum() {
		Map<String, String> booleanEnum = new LinkedHashMap<String, String>();
		booleanEnum.put(BOOLEAN_TRUE, "是");
		booleanEnum.put(BOOLEAN_FALSE, "否");
		return booleanEnum;
	}
	//数据权限级别设置，1--9级
	public static final int DATA_LELVL_1 = 1;
	public static final int DATA_LELVL_2 = 2;
	public static final int DATA_LELVL_3 = 3;
	public static final int DATA_LELVL_4 = 4;
	public static final int DATA_LELVL_5 = 5;
	public static final int DATA_LELVL_6 = 6;
	public static final int DATA_LELVL_7 = 7;
	public static final int DATA_LELVL_8 = 8;
	public static final int DATA_LELVL_9 = 9;
	
	public static Map dataLelvlEnum() {
		Map dataLelvlEnum = new LinkedHashMap();
		dataLelvlEnum.put(DATA_LELVL_1, "一级");
		dataLelvlEnum.put(DATA_LELVL_2, "二级");
		dataLelvlEnum.put(DATA_LELVL_3, "三级");
		dataLelvlEnum.put(DATA_LELVL_4, "四级");
		dataLelvlEnum.put(DATA_LELVL_5, "五级");
		dataLelvlEnum.put(DATA_LELVL_6, "六级");
		dataLelvlEnum.put(DATA_LELVL_7, "七级");
		dataLelvlEnum.put(DATA_LELVL_8, "八级");
		dataLelvlEnum.put(DATA_LELVL_9, "九级");
		return dataLelvlEnum;
	}
	
	public static final String STATUS_AUTH = "1";

	public static final String STATUS_UNAUTH = "0";

	public static final String RESOURCE_URL = "URL";

	public static final String RESOURCE_FUNCTION = "FUNCTION";

	public static final String RESOURCE_COMPONENT = "COMPONENT";

	public static Map resTypeEnum() {
		Map resTypeEnum = new LinkedHashMap();
		resTypeEnum.put("URL", RESOURCE_URL);
		resTypeEnum.put("FUNCTION", RESOURCE_FUNCTION);
		resTypeEnum.put("COMPONENT", RESOURCE_COMPONENT);
		return resTypeEnum;
	}

	public static Map authEnum() {
		Map authEnum = new LinkedHashMap();
		authEnum.put(STATUS_AUTH, "已授权");
		authEnum.put(STATUS_UNAUTH, "未授权");
		return authEnum;
	}

	public static final String CACHE_MENU = "1";

	public static final String CACHE_SYSCODE = "2";

	public static final String CACHE_RESAUTH = "3";
	
	public static final String CACHE_USERAUTH = "4";
	
	public static final String CACHE_DATAAUTH = "5";

	public static Map cacheType() {
		Map cacheType = new LinkedHashMap();
		cacheType.put(CACHE_MENU, "菜单缓存");
		cacheType.put(CACHE_SYSCODE, "码表缓存");
		cacheType.put(CACHE_RESAUTH, "资源权限缓存");
		cacheType.put(CACHE_USERAUTH, "用户权限缓存");
		cacheType.put(CACHE_DATAAUTH, "数据权限缓存");
		return cacheType;
	}

	//组织机构类型
	
	public static final String ORANIZATION_BRANCH = getProperty(
			"oranization.branch", "01");
	
	public static final String ORANIZATION_STATION = getProperty(
			"oranization.station", "02");
	
	public static final String ORANIZATION_PUC = getProperty(
			"oranization.puc", "99");

	public static Map orgTypeEnum() {
		Map<String, String> orgTypeEnum = new LinkedHashMap<String, String>();
		orgTypeEnum.put("","");
		orgTypeEnum.put(ORANIZATION_BRANCH,"分局");
		orgTypeEnum.put(ORANIZATION_STATION,"派出所");
		return orgTypeEnum;
	}
	
	
	//组织机构市局直属单位标示
	public final static String ORANIZATION_PUC_FLAG = "1";
	
	public static final String UNVALID_VALUE = "-1";

	public static final String NORMAL_VALUE = "0";

	public static final String STATUS = "status";

	/*
	 * 系统包前缀
	 */
	public static final String SYSTEM_PACKAGE = "com.jeaw";

	/*
	 * log4j.logger.COM.DC.BUSINESS=DEBUG,BUSINESS #信息子系统
	 * log4j.logger.COM.DC.INFO=DEBUG,INFO #系统管理子系统
	 * log4j.logger.COM.DC.SYSTEM=DEBUG,SYSTEM #信息交互子系统
	 * log4j.logger.COM.DC.INFOCOMM=DEBUG,INFOCOMM #系统公共部分（除架构之外）
	 * log4j.logger.COM.DC.COMMONS=DEBUG,COMMONS #系统中每个业务模块的SQL语句
	 * log4j.logger.COM.DC.SQL=DEBUG,SQL
	 */

	public static final String COMMONS = "COM.DC.COMMONS";

	public static final String SQL = "COM.DC.SQL";

	/**
	 * 测试用例使用的定义Spring Context 文件集合的字符串.
	 */
	public static final String DEFAULT_CONTEXT = "classpath*:spring/*.xml";

	/**
	 * 测试用例使用的定义Spring在测试时特别设置的Context 文件集合的字符串.
	 */
	public static final String DEFAULT_TEST_CONTEXT = "classpath*:spring/test/*.xml";

	public final static int DEFAULT_BATCH_SIZE = 30;

	// 案件关联组织表中的关联组织类型
	public static final String GROUPTYPE_GROUP = "group";

	public static final String GROUPTYPE_COMMIT = "commit";

	public static final String GROUPTYPE_INFRA = "infra";
	
	//#判断是否同一分局代码位数
	public final static String SECURITY_UNIBRANCH = getProperty(
			"security.unibranch", "6");
	//#判断是否同一直属单位代码位数
	public final static String SECURITY_UNIDIRECTU = getProperty(
			"security.unidirectu", "8");

	//非正常流程类型，需要这些特殊处理，可以增加相应的配置
	//表示默认的非正常流程，本单位数据操作员拥有这种流程权限
	public final static String SECURITY_DEFAULTTYPE = getProperty(
			"security.defaulttype", "1");
	
	public final static String SECURITY_JDCTTYPE = getProperty(
			"security.jdctype", "2");
	
	public final static String CACHE_PROVIDER = "cache.provider_class";
	
	public final static String isRemote = "isremote";
	
	public final static String FLAG_CASE = "case";
	public final static String FLAG_PEOPLE = "people";
	
	
	
	/*****************************************************************
	 * Added by zhouyingzhao, from 2009-6-1;
	 *****************************************************************/
	/** 判断是否是通过adjx请求的对象 */
	public static final String AJAX_REQUEST_INDEX = "X-Requested-With";
	
	public static final String CODECLASS_NAMESPACE = "com.jeaw.system.code.sysCode";
	
	public static final String FIELDS_ARRAY = "fieldsArray";
	
	public static final String EXT_AVOID_CACHE_PARAM = "_dc";
	
	/** action中将setResult的结果处理结果放置在request范围内的标示 */
	public static final String RESULT_INDEX = "result_index";
	
	/** action中将putInfoResult的结果处理结果放置在request范围内的标示，用Map保存 */
	public static final String RESULT_MAP_INDEX = "result_map_index";
	
	public static final String JSON_MSG = "msg";
	
	public static final String CUSTOM_JSONHELPER = "custom_jsonhelper";
	
	public static final String JSON_NOT_OUTPUT_SUCCESS = "json_not_out_success";
	
	//////////////////////////////////////////////////////////////////////
	public static final String INITIAL_PASSWORD = "000000";
	
	
	
	/** 系统中树的虚拟根ID */
	public final static String VIRTUAL_TREE_ROOT = "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ";
	
	/** 查询条件中的分页参数，起始记录数 */
	public static final String PARAMETER_RECORDSTART = "start";
	/** 查询条件中的分页参数，每页显示条数 */
	public static final String PARAMETER_PAGESIZE = "limit";
	
	public static final String PARAMETER_ORDER_INDEX = "order_index";
	
	public static final String PARAMETER_RULE_INDEX = "rule_index";
	
	public static final String ORDER_ASCEND = "asc";
	public static final String ORDER_DECEND = "desc";
	
	public static final String ECSIDE_PREFIX = "ec_";
	public static final String ECSIDE_START = "ec_";
	
	public static final String RICH_RULE_NOTEQUAL = "ne";
	public static final String RICH_RULE_LIKE = "like";
	public static final String RICH_RULE_ILIKE = "ilike";
	public static final String RICH_RULE_GREAT = "gt";
	public static final String RICH_RULE_LITTLE = "lt";
	public static final String RICH_RULE_LITTLEEQUAL = "le";
	public static final String RICH_RULE_GREATEQUAL = "ge";
	public static final String RICH_RULE_EQUAL = "eq";
	public static final String RICH_RULE_IN = "in";
	 
}
