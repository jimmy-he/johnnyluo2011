package com.crm.framework.web.fileListener;

import java.io.File;

import org.apache.log4j.Logger;

import com.crm.base.permission.taskcode.cache.FilterFieldCache;
import com.crm.base.permission.taskcode.cache.TaskCodeCache;
import com.crm.framework.common.beanfactory.CrmBeanFactory;
import com.crm.framework.common.config.PathConvertor;
import com.crm.framework.common.util.file.FileListener;

/**
 * 
 * @author 王永明
 * @since 2011-3-9 上午11:31:32
 */
public class TaskCodeListener implements FileListener {
	final File tfile = new File(PathConvertor.getTaskCodeFile());
	private FileListenerConfig config;
	private static Logger log = Logger.getLogger(TaskCodeListener.class);

	public void fileChanged(File file) {
		if (!file.getPath().equals(tfile.getPath())) {
			return;
		}
		log.debug("功能代码配置文件修改,清除功能代码缓存");
		TaskCodeCache tcache = CrmBeanFactory.getBean(TaskCodeCache.class);
		tcache.clear();

		FilterFieldCache cache = CrmBeanFactory.getBean(FilterFieldCache.class);
		cache.clear();
	}

	public void setListenerConfig(FileListenerConfig config) {
		this.config=config;
		// TODO Auto-generated method stub
		
	}

}
