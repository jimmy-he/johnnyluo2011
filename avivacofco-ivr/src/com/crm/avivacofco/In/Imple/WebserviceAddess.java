/**
 * 
 */
package com.crm.avivacofco.In.Imple;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-31   下午02:20:34
 */
class StaticIO {
	public static Document Personaldocument;
	public static Document Personalwsdladdress;
	public static Document Groupdocument;
	public static Document Groupwsdladdress;
static {
	try {
		SAXReader reader = new SAXReader();
		InputStream PersonalFilePath = Thread.currentThread().getContextClassLoader().getResourceAsStream("PersonalRequest.xml");
		InputStream Personalxml = Thread.currentThread().getContextClassLoader().getResourceAsStream("wsdl-address.xml");
		InputStream groupFilePath = Thread.currentThread().getContextClassLoader().getResourceAsStream("groupRequest.xml");
		InputStream groupxml = Thread.currentThread().getContextClassLoader().getResourceAsStream("wsdl-address.xml");
		Personaldocument=reader.read(PersonalFilePath);
		Personalwsdladdress=reader.read(Personalxml);
		Groupdocument = reader.read(groupFilePath);
		Groupwsdladdress = reader.read(groupxml);
	} catch (DocumentException e) {
		e.printStackTrace();
	}
	
}
}
public class WebserviceAddess {	
	public static void main(String[] args) {
		Element wsdlsoapaddress = (Element) StaticIO.Groupwsdladdress.selectObject("/config/wsdl-group/wsdlsoap-address");
		Element Tradecode = (Element) StaticIO.Groupwsdladdress.selectObject("/config/wsdl-group/tradecode");
		Element Tradetype = (Element) StaticIO.Groupwsdladdress.selectObject("/config/wsdl-group/tradetype");
		Element Username = (Element) StaticIO.Groupwsdladdress.selectObject("/config/wsdl-group/username");
		Element QueueId = (Element) StaticIO.Groupwsdladdress.selectObject("/config/wsdl-group/queueId");
		
		System.out.println(wsdlsoapaddress.getStringValue());
		System.out.println(Tradecode.getStringValue());
		System.out.println(Username.getStringValue());
	}
}
