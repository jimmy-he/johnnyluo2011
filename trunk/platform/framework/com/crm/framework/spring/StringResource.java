package com.crm.framework.spring;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.springframework.core.io.Resource;

import com.crm.framework.common.util.xml.XmlUtil;

/**
 * 动态加载springBean的时候要求入参是Resource,目前没找到将Stirng转为Resource的方法
 * 自己写了一个StringResource通过sring创建Resource
 * @author 王永明
 * @since Jan 18, 2010 3:27:13 PM
 */
public class StringResource implements org.springframework.core.io.Resource {

	private String str;
	
	public StringResource(String str) {
		this.str = str;
	}

	public Resource createRelative(String paramString) throws IOException {
		throw new RuntimeException("unsupport method");
	}

	public boolean exists() {
		throw new RuntimeException("unsupport method");
	}

	public String getDescription() {
		return XmlUtil.formate(str);
	}

	public File getFile() throws IOException {
		throw new RuntimeException("unsupport method");
	}

	public String getFilename() {
		throw new RuntimeException("unsupport method");
	}

	public URI getURI() throws IOException {
		throw new RuntimeException("unsupport method");
	}

	public URL getURL() throws IOException {
		throw new RuntimeException("unsupport method");
	}

	public boolean isOpen() {
		return false;
	}

	public boolean isReadable() {
		return true;
	}

	public long lastModified() throws IOException {
		throw new RuntimeException("unsupport method");
	}

	public InputStream getInputStream() throws IOException {
		return new java.io.ByteArrayInputStream(str.getBytes());
	}

}
