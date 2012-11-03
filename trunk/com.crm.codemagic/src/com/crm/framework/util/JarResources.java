package com.crm.framework.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public final class JarResources {
	
	public boolean debugon = false;
	private Hashtable htsizes = new Hashtable();
	private Hashtable htjarcontents = new Hashtable();
	private String jarfilename;
	
	public JarResources(String jarfilename) throws Exception{
		this.jarfilename = jarfilename;
		init();
	}
	
	private void init() throws Exception{
		try {
			// extracts just sizes only. 
			ZipFile zf = new ZipFile(jarfilename);
			Enumeration e = zf.entries();
			while (e.hasMoreElements()) {
				ZipEntry ze = (ZipEntry)e.nextElement();
				if (debugon) {
	                 System.out.println(dumpZipEntry(ze));
				}
				htsizes.put(ze.getName(),new Integer((int)ze.getSize()));
	          }
	          zf.close();
	          // extract resources and put them into the hashtable.
	          FileInputStream fis = new FileInputStream(jarfilename);
	          BufferedInputStream bis = new BufferedInputStream(fis);
	          ZipInputStream zis = new ZipInputStream(bis);
	          ZipEntry ze = null;
	          while ((ze = zis.getNextEntry()) != null) {
	        	  if (ze.isDirectory()) {
	        		  continue;////啊哟!没有处理子目录中的资源啊
	        	  }
	        	  if (debugon) {
	        		  System.out.println(
	        				  "ze.getName()="+ze.getName()+","+"getSize()="+ze.getSize()
	        		  );
	        	  }
	        	  int size = (int)ze.getSize();
	        	  // -1 means unknown size. 
	        	  if (size == -1) {
	        		  size = ((Integer)htsizes.get(ze.getName())).intValue();
	        	  }
	        	  byte[] b = new byte[(int)size];
	        	  int rb = 0;
	        	  int chunk = 0;
	        	  while (((int)size - rb) > 0) {
	        		  chunk = zis.read(b,rb,(int)size - rb);
	        		  if(chunk == -1) {
	        			  break;
	        		  }
	        		  rb += chunk;
	        	  }
	        	  // add to internal resource hashtable
	        	  htjarcontents.put(ze.getName(),b);
	        	  if (debugon) {
	        		  System.out.println(
	        				  ze.getName()+" rb="+rb+
	        				  ",size="+size+
	        				  ",csize="+ze.getCompressedSize()
	        		  );
	        	  }
	          }
		} catch (NullPointerException e) {
			System.out.println("done.");
			throw e;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Dumps a zip entry into a string.
	 * @param ze a ZipEntry
	 */
	private String dumpZipEntry(ZipEntry ze) {
		StringBuffer sb = new StringBuffer();
		if (ze.isDirectory()) {
			sb.append("d "); 
		} else {
			sb.append("f "); 
		}
		if (ze.getMethod()==ZipEntry.STORED) {
			sb.append("stored   "); 
		} else {
			sb.append("defalted ");
		}
		sb.append(ze.getName());
		sb.append("\t");
		sb.append(""+ze.getSize());
		if (ze.getMethod()==ZipEntry.DEFLATED) {
			sb.append("/"+ze.getCompressedSize());
		}
		return (sb.toString());
	}
	   
	/**
	 * Extracts a jar resource as a blob.
	 * @param name a resource name.
	 */
	public byte[] getResource(String name) {
		return (byte[])htjarcontents.get(name);
	}
	
	/**
	 * @param String jarFileName 绝对路径的JAR文件 
	 * @param String resourceName 资源文件的路径  形如  bin/template/java/action.template
	 * 即前面不要/
	 * @throws UnsupportedEncodingException 
	 * */
	public static String getResourceContent(String jarFileName, String resourceName) throws Exception{
		JarResources jr = new JarResources(jarFileName);
		byte[] buff = jr.getResource(resourceName);
		return new String(buff,"UTF-8");
	}
	
	public static void main(String[] args) throws Exception {
		String jar = "D:\\eclipse\\plugins\\com.crm.codemagic_1.0.1.jar";
		String resource = "bin/com/crm/codemagic/pdmparser/dbKeyWords.txt";
		System.out.println(getResourceContent(jar,resource));
	}

}
