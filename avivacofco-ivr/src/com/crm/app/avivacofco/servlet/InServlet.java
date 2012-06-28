/**
 * 
 */
package com.crm.app.avivacofco.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *@author 罗尧 Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-8-6 下午02:51:00
 */
public class InServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InServlet() {
		super();
		try {
			SAXReader reader = new SAXReader();
			InputStream xmlFilePath = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(
							"PersonalRequest.xml");
			InputStream manager = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream("wsdl-address.xml");
			this.document = reader.read(xmlFilePath);
			this.sdocument = reader.read(manager);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here

	}

	private java.util.Vector cachedSerClasses = new java.util.Vector();
	private java.util.Vector cachedSerQNames = new java.util.Vector();
	private java.util.Vector cachedSerFactories = new java.util.Vector();
	private java.util.Vector cachedDeserFactories = new java.util.Vector();

	private Document document;
	private Document sdocument;
	static org.apache.axis.description.OperationDesc[] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[1];
		_initOperationDesc1();
	}

	private static void _initOperationDesc1() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("CCFunction");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("", "input"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://www.w3.org/2001/XMLSchema", "string"),
				java.lang.String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		oper.setReturnClass(java.lang.String.class);
		oper.setReturnQName(new javax.xml.namespace.QName("",
				"CCFunctionReturn"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[0] = oper;

	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("GBK");
		java.lang.String ret;
		ret = "";
		java.util.Date nowdate = new java.util.Date();
		java.text.SimpleDateFormat hmfromat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String strNowTime = hmfromat.format(nowdate);
		String TradeDate = strNowTime.substring(0, 10);
		String TradeTime = strNowTime.substring(11, strNowTime.length());
		System.out.println(TradeDate);
		System.out.println(TradeTime);
		/*
		 * 拿到xml并修改
		 */
		String xmlContent = this.document.asXML();
		String paraName = null;
		String paraValue = null;
		Enumeration EnumMap = (Enumeration) request.getParameterNames();
		while (EnumMap.hasMoreElements()) {
			paraName = (String) EnumMap.nextElement();
			paraValue = (String) request.getParameter(paraName);
			xmlContent = xmlContent.replace("${" + paraName + "}", paraValue);
		}
		xmlContent = xmlContent.replace("${TradeDate}", TradeDate);
		xmlContent = xmlContent.replace("${TradeTime}", TradeTime);

		/*
		 * 后台打印修改后的报文 System.out.println(xmlContent);
		 */
		System.out.println("修改后的报文成功拿到！");

		/*
		 * 测试得到的报文信息 打印 response.setCharacterEncoding("GBK"); PrintWriter out =
		 * response.getWriter(); out.print(xmlContent);
		 */

		/*
		 * 拿到wsdlsoapaddress的信息 wsdlsoap-address username password
		 */
		Element wsdlsoapaddress = (Element) sdocument
				.selectObject("/config/wsdl-message/wsdlsoap-address");
		Element username = (Element) sdocument
				.selectObject("/config/wsdl-message/username");
		Element password = (Element) sdocument
				.selectObject("/config/wsdl-message/password");
		java.lang.String UserName = username.getStringValue();
		java.lang.String PassWord = password.getStringValue();
		/*
		 * 调用web service
		 */
		try {
			java.lang.String endpoint = wsdlsoapaddress.getStringValue();
			Service service = new Service();
			Call call = (Call) service.createCall();
			// 得到账户
			call.setUsername(UserName);
			// 得到密码
			call.setPassword(PassWord);
			call.setOperation(_operations[0]);
			call.setUseSOAPAction(true);
			call.setSOAPActionURI("");
			call
					.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("http://DefaultNamespace",
					"CCFunction"));
			// setRequestHeaders(call);
			// setAttachments(call);

			ret = (String) call.invoke(new Object[] { xmlContent });
			//System.out.println("got '" + ret + "'"); //打印调用之后返回的报文信息
			System.out.println("成功拿到调用webservice接口并返回的报文信息！");
			System.out.println("got '" + ret + "'");
		} catch (Exception e) {
			System.err.println(e.toString());
		}

		/*
		 * 调用web service
		 */
		System.out.println("---------个险调用成功，并把数据返回给M3！----------");
		System.out.println("-----------------------------------------------------------------------------------");
		// response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		// response.setContentType("?xml version=1.0,charset=GBK");
		//response.setContentType("application/xml;charset=GBK");
		response.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();
		out.println(ret);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	
	public void init() throws ServletException {
		// Put your code here
	}
}
