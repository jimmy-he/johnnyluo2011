/**
 * 
 */
package com.jtapi.test;

import java.io.File;

import javax.telephony.Address;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jtapi.app.ProvideServer;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2011-3-10   上午09:54:43
 */
public class CallXmlTest {
	private ProvideServer callserver = new ProvideServer();
	
	 public void getxml() throws DocumentException{
		 try {
			 	File f = new File("src/CallManagerPhone.xml");
			    SAXReader reader = new SAXReader();
			    Document doc = reader.read(f);
			    Element element = (Element)doc.selectObject("/callmanager/providerName");
			    Element elementlogin = (Element)doc.selectObject("/callmanager/login");
			    Element elementpasswd = (Element)doc.selectObject("/callmanager/passwd");
			    
			    callserver.setProviderName(element.getStringValue());
			    callserver.setLogin(elementlogin.getStringValue());
				callserver.setPasswd(elementpasswd.getStringValue());
				
				System.out.println(callserver.getProviderName()+"-------"+callserver.getLogin()+"------------"+callserver.getPasswd());
				/*
				String[] telarray = xString.split(",");
		        for (int i = 0; i < telarray.length; i++) {
		            System.out.println(telarray[i]);
		        }
		       */
		        
		} catch (Exception e) {		
		}		    
	   }
	public static void main(String[] args) throws DocumentException {
		CallXmlTest callXmlTest=new CallXmlTest();
		callXmlTest.getxml();
	  
		

	}

}
