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

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *@author 罗尧 Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-8-17 下午01:19:32
 */

class StaticIO {

	public static Document document;
	public static Document sdocument;
	static {
		try {

			SAXReader reader = new SAXReader();
			InputStream xmlFilePath = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream("groupRequest.xml");
			InputStream manager = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream("wsdl-address.xml");
			document = reader.read(xmlFilePath);
			sdocument = reader.read(manager);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}

public class GroupServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GroupServlet() {
		super();
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

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("GBK");
		// 获取当前本机日期和时间
		java.util.Date nowdate = new java.util.Date();
		java.text.SimpleDateFormat hmfromat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String strNowTime = hmfromat.format(nowdate);
		String RequestDate = strNowTime.substring(0, 10);
		String RequestTime = strNowTime.substring(11, strNowTime.length());
		System.out.println(RequestDate);
		System.out.println(RequestTime);
		/*
		 * 拿到报文并修改
		 */
		String xmlContent = StaticIO.document.asXML();
		String paraName = null;
		String paraValue = null;
		Enumeration EnumMap = (Enumeration) request.getParameterNames();
		while (EnumMap.hasMoreElements()) {
			paraName = (String) EnumMap.nextElement();
			paraValue = (String) request.getParameter(paraName);
			xmlContent = xmlContent.replace("${" + paraName + "}", paraValue);
		}
		xmlContent = xmlContent.replace("${RequestDate}", RequestDate);
		xmlContent = xmlContent.replace("${RequestTime}", RequestTime);

		/*
		 * 拿到团险报文账号密码和相关信息 wsdlsoap-address tradecode tradetype username queueId
		 */
		Element wsdlsoapaddress = (Element) StaticIO.sdocument
				.selectObject("/config/wsdl-group/wsdlsoap-address");
		Element Tradecode = (Element) StaticIO.sdocument
				.selectObject("/config/wsdl-group/tradecode");
		Element Tradetype = (Element) StaticIO.sdocument
				.selectObject("/config/wsdl-group/tradetype");
		Element Username = (Element) StaticIO.sdocument
				.selectObject("/config/wsdl-group/username");
		Element QueueId = (Element) StaticIO.sdocument
				.selectObject("/config/wsdl-group/queueId");

		String url = wsdlsoapaddress.getStringValue();
		String tradecode = Tradecode.getStringValue();
		String tradetype = Tradetype.getStringValue();
		String username = Username.getStringValue();
		String queueId = QueueId.getStringValue();

		/*
		 * 后台打印报文 System.out.println(xmlContent);
		 */
		System.out.println("修改后的request报文成功拿到！");
		System.out.println(xmlContent);
		System.out.println("----------------------------");

		/*
		 * 开始调用接口平台 开始调用webservice并返回信息
		 */

		GIPClient tA = new GIPClient();
		String result = tA.test(xmlContent, url, username, queueId, tradecode,
				tradetype);
		System.out.println("=========调用webservice返回的报文信息:成功拿到！===========");// 打印语句
		System.out.println("打印团险报文信息-----："+result);// 打印语句
		System.out.println("---------------------------");
		System.out.println("团险调用成功并成功返回给M3！");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		//response.setContentType("application/xml;charset=GBK");
		response.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();
		
		out.println(result);
		
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
