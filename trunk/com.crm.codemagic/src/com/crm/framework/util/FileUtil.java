package com.crm.framework.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author 王永明
 * @date Apr 17, 2009 12:54:41 AM
 */
public class FileUtil {

	public static void getAllFile(List<File> list, String root) {
		File f = new File(root);
		if (f.isDirectory()) {
			File[] fList = f.listFiles();
			for (int j = 0; j < fList.length; j++) {
				if (fList[j].isDirectory()) {
					getAllFile(list, fList[j].getPath());
				}
				if (fList[j].isFile()) {
					list.add(fList[j]);
				}
			}
		}
	}

	public static String readFile(String filePath,String charsetname) {
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			StringBuffer buffer = new StringBuffer();
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream,charsetname));
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
	
	public static String readFile(String filePath) {
		return readFile(filePath,"UTF-8");
	}

	
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
	
	


}
