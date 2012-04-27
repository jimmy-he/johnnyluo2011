package com.crm.framework.web.fileListener;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;

import com.crm.framework.common.config.PathConvertor;
import com.crm.framework.common.util.file.FileListener;

/**
 * 
 * @author 王永明
 * @since 2011-3-9 下午01:41:40
 */
public class Log4jListener implements FileListener {
	private FileListenerConfig config;
	final File logFile = new File(PathConvertor.getClassPath() + "/log4j.properties");
	public void fileChanged(File file) {
		if (!file.getPath().equals(logFile.getPath())) {
			return;
		}
		PropertyConfigurator.configure(logFile.getAbsolutePath());
	}
	public void setListenerConfig(FileListenerConfig config) {
		this.config=config;
	}

}
