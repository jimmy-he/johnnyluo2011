package com.crm.framework.web.fileListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.crm.framework.common.config.PathConvertor;
import com.crm.framework.common.util.FileUtil;
import com.crm.framework.common.util.file.FileListener;

/**
 * 
 * @author 王永明
 * @since 2011-3-9 上午11:11:44
 */
public class FileListenerConfig {
	private String filePath;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getListenerClass() {
		return listenerClass;
	}
	public void setListenerClass(String listenerClass) {
		this.listenerClass = listenerClass;
	}
	/**要监听的文件*/
	public List<File> getFile(){
		File file=new  File(PathConvertor.getClassPath()+filePath);
		if(file.isDirectory()){
			return FileUtil.getAllFile(file.getAbsolutePath());
		}else{
			List list=new ArrayList();
			list.add(file);
			return list;
		}
	}
	/**要监听文件处理类*/
	public FileListener getListener(){
		try {
			 FileListener ls=(FileListener) Class.forName(this.getListenerClass()).newInstance();
			 ls.setListenerConfig(this);
			 return ls;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**是否是要监听的文件*/
	public boolean isListenFile(File file){
		for(File f:this.getFile()){
			if(f.getAbsolutePath().equals(file.getAbsolutePath())){
				return true;
			}
		}
		return false;
	}
	
	private String listenerClass;
}
