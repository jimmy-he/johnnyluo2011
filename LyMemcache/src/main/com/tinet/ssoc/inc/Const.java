package com.tinet.ssoc.inc;

public class Const {
	
	
	/*
	 * trunk trunk_type,status definition
	 */
	public static final int TRUNK_TRUNK_TYPE_GENERAL = 1;
	public static final int TRUNK_TRUNK_TYPE_FAX = 2;
	public static final int TRUNK_STATUS_UNUSED  = 1;
	public static final int TRUNK_STATUS_INUSE = 2;
	
	/*
	 *  entity sex,entity_parent,certificate_type default value definition
	 */
	public static final int ENTITY_SEX_DEFAULT = 0;
	public static final int ENTITY_ENTITY_PARENT_DEFAULT = 0;
	public static final int ENTITY_CERTIFICATE_TYPE_DEFAULT = 0;
	public static final String ENTITY_ENTITY_PWD_DEFAULT = "123456";
	public static final String ENTITY_COUNTRY_DEFAULT = "China";
	public static final String ENTITY_LANGUAGE_DEFAULT = "zh-CN";
	
	/*
	 * entity status definition
	 */
	public static final int ENTITY_STATUS_OK = 1;
	public static final int ENTITY_STATUS_FORBIDDEN = 2;
	public static final int ENTITY_STATUS_LOCK =3;
	
	/*
	 * entity entity_type definition
	 */
	public static final int ENTITY_ENTITY_TYPE_AGENT1 = 1;
	public static final int ENTITY_ENTITY_TYPE_AGENT2 = 2;
	public static final int ENTITY_ENTITY_TYPE_ENTERPRISE = 3;
	public static final int ENTITY_ENTITY_TYPE_DIRECT_SECTION = 4;
	public static final int ENTITY_ENTITY_TYPE_DIRECT_MANAGER = 5;
	public static final int ENTITY_ENTITY_TYPE_ADMIN = 11;
	public static final int ENTITY_ENTITY_TYPE_CASHIER =12;
	public static final int ENTITY_ENTITY_TYPE_CUSTOMER_SERVICE = 13;
	public static final int ENTITY_ENTITY_TYPE_CUSTOMER_SERVICE_MAINTAINER = 14;
	public static final int ENTITY_ENTITY_TYPE_AREA_SUPERVISOR = 15;
	public static final int ENTITY_ENTITY_TYPE_CHANNEL_MANAGER = 16;
	
	/*
	 * entity sex definition
	 */
	public static final int ENTITY_SEX_MALE = 0;
	public static final int ENTITY_SEX_FEMALE = 1;
	
	/*
	 * entity certificate_type definition
	 */
	public static final int ENTITY_CERTIFICATE_TYPE_IDENTITY = 0;
	public static final int ENTITY_CERTIFICATE_TYPE_PASSPORT = 1;
	public static final int ENTITY_CERTIFICATE_TYPE_OTHER = 2;
}
