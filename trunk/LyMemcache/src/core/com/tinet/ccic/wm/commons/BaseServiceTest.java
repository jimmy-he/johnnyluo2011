package com.tinet.ccic.wm.commons;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
/**
 * 基础业务逻辑测试类。
 *<p>
 * 文件名： BaseServiceTest.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-core.xml" })
public class BaseServiceTest extends
					AbstractTransactionalJUnit4SpringContextTests {

}
