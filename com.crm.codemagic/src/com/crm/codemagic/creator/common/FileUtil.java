package com.crm.codemagic.creator.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @description
 * @author 王永明
 * @date May 25, 2009 1:04:17 AM
 * */
public class FileUtil {
	/**
	 * 生成文件
	 * 
	 * @throws IOException
	 */
	public static String createClass(String projectPath,String packageName,String className,String content) throws IOException {
		String directory = projectPath + "/src/" + packageName.replaceAll("\\.", "/") + "/";
		File directoryFile = new File(directory);
		directoryFile.mkdirs();
		String fileName = directory + "/" + className+ ".java";
		return createFile(fileName,content);
	}
	
	public static String createFile(String fileName,String content)throws IOException {
		File file = new File(fileName);
		file.createNewFile();
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		BufferedWriter bufferedWriter = new BufferedWriter(osw);
		bufferedWriter.write(content);
		bufferedWriter.flush();
		bufferedWriter.close();
		return fileName;
	}
	
	
	
	/**
	 * 通过名称获得相应的文件模板
	 * 
	 * @param name 模板名称
	 */
	public static String readFile(String name) {
		try {
			FileInputStream fileInputStream = new FileInputStream(name);
			StringBuffer buffer = new StringBuffer();
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
			line = reader.readLine();
			while (line != null) {
				buffer.append(line);
				buffer.append("\n");
				line = reader.readLine();
			}
			return buffer.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
