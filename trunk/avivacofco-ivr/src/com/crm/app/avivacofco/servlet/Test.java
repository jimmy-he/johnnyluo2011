/**
 * 
 */
package com.crm.app.avivacofco.servlet;

import org.dom4j.Element;

/**
 *@author 罗尧 Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-28 下午02:11:42
 */
public class Test {
	public static void main(String[] args) {
		Element wsdlsoapaddress = (Element) StaticIO.sdocument.selectSingleNode("/config/wsdl-group/wsdlsoap-address");
		Element Tradecode = (Element) StaticIO.sdocument
				.selectObject("/config/wsdl-group/tradecode");
		Element Tradetype = (Element) StaticIO.sdocument
				.selectObject("/config/wsdl-group/tradetype");
		Element Username = (Element) StaticIO.sdocument
				.selectObject("/config/wsdl-group/username");
		Element QueueId = (Element) StaticIO.sdocument
				.selectObject("/config/wsdl-group/queueId");
		
		System.out.println(wsdlsoapaddress.getStringValue());
	}
}
