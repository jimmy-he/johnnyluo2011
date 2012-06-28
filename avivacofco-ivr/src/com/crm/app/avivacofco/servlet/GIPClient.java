package com.crm.app.avivacofco.servlet;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import sun.misc.BASE64Decoder;

/**
 *@author 罗尧 Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-8-17 下午01:19:55
 */
public class GIPClient {
	// 创建SOAP header信息
	public OMElement createHeaderOME(String username, String queueId,
			String tradecode, String tradetype) {
		OMFactory factory = OMAbstractFactory.getOMFactory();
		OMNamespace SecurityElementNamespace = factory.createOMNamespace(
				"http://webservice.gip.sinosoft.com", "GIP");
		OMElement authenticationOM = factory.createOMElement("ClientInfo",
				SecurityElementNamespace);
		OMElement usernameOM = factory.createOMElement("UserCode",
				SecurityElementNamespace);
		OMElement queueIdOM = factory.createOMElement("QueueName",
				SecurityElementNamespace);
		OMElement tTradetype = factory.createOMElement("Tradetype",
				SecurityElementNamespace);
		OMElement tTradecode = factory.createOMElement("Tradecode",
				SecurityElementNamespace);

		usernameOM.setText(username);// 访问接口平台的用户

		queueIdOM.setText(queueId);// 访问接口平台的队列

		tTradetype.setText(tradetype);
		tTradecode.setText(tradecode);
		authenticationOM.addChild(usernameOM);
		authenticationOM.addChild(queueIdOM);
		authenticationOM.addChild(tTradetype);
		authenticationOM.addChild(tTradecode);
		/*
		 * System.out.println("*******");
		 * 
		 * System.out.println(authenticationOM);//生成的头消息
		 * System.out.println("***2****");
		 */
		return authenticationOM;
	}

	public String test(String xml, String url, String username, String queueId,
			String tradecode, String tradetype) {
		try {

			RPCServiceClient serviceClient = new RPCServiceClient();
			// 1.添加header信息*******************!!!!!!!!!!!!!!!!!!!!

			serviceClient.addHeader(createHeaderOME(username, queueId,
					tradecode, tradetype));

			// 2.axis2调用接口平台webservice程序
			Options options = serviceClient.getOptions();

			options.setProperty(
					org.apache.axis2.transport.http.HTTPConstants.CHUNKED,
					Boolean.FALSE);

			EndpointReference targetEPR = new EndpointReference(url);
			options.setTo(targetEPR);
			options.setAction("urn:recive");
			options.setTimeOutInMilliSeconds(new Long(10000000));

			Object[] opAddEntryArgs = new Object[] { xml }; // 调用方法要传递的参数

			QName opAddEntry = new QName("http://webservice.gip.sinosoft.com",
					"recive");

			Class<?>[] opReturnType = new Class[] { String.class };
			Object[] response = serviceClient.invokeBlocking(opAddEntry,
					opAddEntryArgs, opReturnType);

			// 3.
			String reXML = (String) response[0];// 接口平台返回的报文
			System.out.println("=======reXML=============");// 打印语句
			System.out.println("reXML语句由于过长，现在停止后台打印，如果需要请在GIPClient.java的93行修改");// 打印语句

			// 4.从接口平台返回的报文中获得从服务端返回的报文
			Document gipresult = DocumentHelper.parseText(reXML);
			Element gipresultele = gipresult.getRootElement();

			Element bytedest = gipresultele.element("bytedest");

			byte[] bytedestresult = getFromBASE64Ver2(bytedest.getText());

			return new String(bytedestresult);// 服务端返回的报文

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// 将 BASE64 编码的字符串 s 进行解码
	public static byte[] getFromBASE64Ver2(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return b;
		} catch (Exception e) {
			return null;
		}
	}
}
