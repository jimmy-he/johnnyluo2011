/**
 * 
 */
package com.crm.avivacofco.In.Imple;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import antlr.Lookahead;

import com.crm.DBManager.DBConnection;
import com.crm.app.avivacofco.servlet.GIPClient;
import com.crm.avivacofco.In.Cache.CacheMgr;
import com.crm.avivacofco.In.CacheBean.AbstractCacheBean;

/**
 *@author 罗尧 Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-25 下午05:33:24
 */
public class IvrWork extends AbstractCacheBean {
	static Logger logger = Logger.getLogger(IvrWork.class.getName());

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
	 * @param channelNo
	 * @param commandNo
	 * @param idNO
	 * IvrWork构造方法
	 */
	public IvrWork(String channelNo, String commandNo, String idNO) {
		super.setChannelNo(channelNo);
		super.setID_NO(idNO);
		super.setCommandNo(commandNo);
		
	}
	/*  身份证验证
	 * commandNo--000001
	 * 调用webservice查询相应的字段，判断此身份证是否存在，并缓存所需要的信息
	 * <p>1.0.0.0 罗尧创建 2010-10-26</p>
	 * @see com.crm.avivacofco.In.CacheBean.AbstractCacheBean#sfYanZheng()
	 */
	@Override
	public String sfYanZheng() {
		StringBuffer out = new StringBuffer();
		logger.info("进入身份验证方法------11111");
		System.out.println("进入身份验证方法------11111");
		CacheMgr cacheMgr = CacheMgr.getInstance();
		String channelNo = super.getChannelNo();
		System.out.println("在工号检测中拿到channelNo:"+channelNo);
		logger.info("在工号检测中拿到channelNo:"+channelNo);
		AbstractCacheBean cBean = (AbstractCacheBean) cacheMgr.get("ChannelNo:"+ channelNo);
		System.out.println("cBean拿到缓存ID：---------"+cBean);
		logger.info("cBean拿到缓存ID：---------"+cBean);
		String ID_NO=cBean.getID_NO();
		System.out.println("拿到传递进入的身份证号码："+ID_NO);
		logger.info("拿到传递进入的身份证号码："+ID_NO);
		String id_no=ID_NO.replaceAll(".*(.{1})", "$1");
		System.out.println("身份证最后一位！"+id_no);
		logger.info("身份证最后一位！"+id_no);
		String newid_no="";
		if(id_no.equals("*")){
			newid_no="X";
			ID_NO=ID_NO.replace(id_no, newid_no);
			System.out.println("替换后的身份证号码为：----"+ID_NO);
			logger.info("替换后的身份证号码为：----"+ID_NO);
		}
		
		java.util.Date nowdate = new java.util.Date();
		java.text.SimpleDateFormat hmfromat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strNowTime = hmfromat.format(nowdate);
		String TradeDate = strNowTime.substring(0, 10);
		String TradeTime = strNowTime.substring(11, strNowTime.length());
		System.out.println("调用开始日期："+TradeDate);
		logger.info("调用开始日期："+TradeDate);
		System.out.println("调用开始时间"+TradeTime);
		logger.info("调用开始时间"+TradeTime);
		System.out.println("---------开始调用个险---------");
		logger.info("---------**************开始调用个险***************---------");
		java.lang.String oneresult;
		oneresult="";
		
		
		try {           
			java.lang.String input=
			"<?xml version=\"1.0\" encoding=\"GBK\"?>       "+
			"<TradeData>                                    "+
			"  <BaseInfo>                                   "+
			"    <TradeCode>00030002</TradeCode>            "+
			"    <TradeTime>"+TradeTime+"</TradeTime>       "+
			"    <TotalRowNum />                            "+
			"    <ResultCode>0</ResultCode>                 "+
			"    <ResultMsg />                              "+
			"    <TradeType>WEBSITE</TradeType>             "+
			"    <PageRowNum>5</PageRowNum>                 "+
			"    <OrderFlag>0</OrderFlag>                   "+
			"    <SubTradeCode>1</SubTradeCode>             "+
			"    <TradeSeq>0001101420070311000001</TradeSeq>"+
			"    <PageFlag>1</PageFlag>                     "+
			"    <RowNumStart>1</RowNumStart>               "+
			"    <OrderField />                             "+
			"    <Operator>WEB</Operator>                   "+
			"    <TradeDate>"+TradeDate+"</TradeDate>       "+
			"  </BaseInfo>                                  "+
			"<InputData>"+			
		    "<IDENTY_NO>"+ID_NO+"</IDENTY_NO>"+
		    "<Filter>BaseInfo,RiskInfo,PersonInfo,AgentInfo_PERSON_AGENT,AgentInfo_BROKER,AgentInfo_BANK_BOS,AgentInfo_DM_AGENTHIS,ZMAN,CLMREGP,CLMDEATH,NoticeInfo</Filter>"+
		    "</InputData>"+
			"  <OutputData />                               "+
			"</TradeData>                                   ";
			String endpoint = "http://10.145.6.40:9080/CRMHT/QueryService.jws";
			Service service = new Service();
			Call call = (Call) service.createCall();
			//设置用户名
	        call.setUsername("CRMUSER");
	        //设置密码
	        call.setPassword("crmpsw");
	        call.setOperation(_operations[0]);
	        call.setUseSOAPAction(true);
	        call.setSOAPActionURI("");
	        call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("http://DefaultNamespace", "CCFunction"));
			//setRequestHeaders(call);
		    //setAttachments(call);
			oneresult = (String) call.invoke(new Object[] {input});
			//System.out.println("得到的数据---" + oneresult);//个险得到的报文信息
			logger.info("得到的数据---" + oneresult);
			//out.append(ret);
		} catch (Exception e) {
			System.err.println(e.toString());
			logger.debug(e.toString());
		}
		System.out.println("---------个险调用成功----------");
		logger.info("---------个险调用成功----------");
		SAXReader saxReader = new SAXReader(false);
		Element onenumber = null;
		Document onedoc=null;				
		try {
			onedoc = saxReader.read(new StringReader(oneresult));
			onenumber = (Element)onedoc.selectSingleNode("/TradeData/OutputData/ACLife/PolicyInfo");	
			cBean.setOnenumber(onenumber);
			cacheMgr.put("ChannelNo:"+ channelNo, cBean);
		} catch (Exception e) {
			e.printStackTrace();
			out.append("N|个险接口调用出现问题");
			logger.debug("N|个险接口调用出现问题");
		}		
		/*
		 * 团险接口操作
		 */
		System.out.println("---------开始调用团险----------");
		logger.info("---------开始调用团险----------");
		String groupinput=
			"<?xml version=\"1.0\" encoding=\"GBK\"?>"+
			"<ACLife>"+
				"<ACLifeBase>"+
					"<TransNo></TransNo>"+
				"<TransType>005</TransType>"+
					"<TransSource>003</TransSource>"+
					"<TransTarget>01</TransTarget>"+
				"</ACLifeBase>"+				
				"<ACLifeRequest>"+
					"<RequestDate>"+TradeDate+"</RequestDate>"+
			  		"<RequestTime>"+TradeTime+"</RequestTime>"+
				 	"<PolicyInfo>"+
						"<IdNo>"+ID_NO+"</IdNo>"+
					"</PolicyInfo>"+
				"</ACLifeRequest>"+
			"</ACLife>";
		String url ="http://192.168.1.35:8080/GIP/services/GIPService?wsdl";
		String tradecode ="";
		String tradetype ="04";
		String username ="Group";
		String queueId ="Group";
		GIPClient tA = new GIPClient();
		String groupresult = tA.test(groupinput, url, username, queueId, tradecode,tradetype);
		System.out.println("=========调用webservice返回的报文信息:成功拿到！===========");// 打印语句
		logger.info("=========调用webservice返回的报文信息:成功拿到！===========");
		System.out.println(groupresult);// 团险得到的报文信息
		logger.info(groupresult);
		System.out.println("---------团险调用成功----正在验证数据------");
		System.out.println();
		
		Element groupidnumber = null;
		Element cardgroupidnumber=null;	
		Document groupdoc = null;
		
		try {
			groupdoc = saxReader.read(new StringReader(groupresult));	
			
			groupidnumber = (Element)groupdoc.selectSingleNode("/ACLife/ACLifeResponse/RetContent/Response/GrpInsuranceInfo/GrpInsurance");
			cBean.setGroupidnumber(groupidnumber);
			cardgroupidnumber=(Element)groupdoc.selectSingleNode("/ACLife/ACLifeResponse/RetContent/Response/ContCardInfo/ContCard");																			
			cBean.setCardgroupidnumber(cardgroupidnumber);	
			cacheMgr.put("ChannelNo:"+ channelNo, cBean);
		} catch (Exception e) {
			e.printStackTrace();
			out.append("N|团险接口调用出现问题");
		}	
		/*====================================================================================================================*/
		/*
		 * 进行身份证的验证
		 */
		System.out.println("---------开始进入身份证验证----------");
		if(ID_NO.equals("")){
			out.append("3|");
		}else 			
			if(onenumber!=null||groupidnumber!=null||cardgroupidnumber!=null){
				//-------------------个险中有这个身份证号码的情况下-----------------------
				if(onenumber!=null){  
					out.append("2|");
					System.out.println("-------个险中有这个身份证 !----开始缓存个险数据-----");					
					logger.info("-------个险中有这个身份证 !----开始缓存个险数据-----");
					cBean.setOnexml(oneresult);
					cacheMgr.put("ChannelNo:"+ channelNo, cBean);
					System.out.println("------------个险缓存数据完毕！-------------------");
					logger.info("------------个险缓存数据完毕！-------------------");
				}
					//-------------------团险中有这个身份证号码的情况下-----------------------
				if(groupidnumber!=null){
					out.append("2|");
					System.out.println("-------团单中有这个身份证 !-----开始缓存团险数据----");
					logger.info("-------团单中有这个身份证 !-----开始缓存团险数据----");
					cBean.setGroupxml(groupresult);
					cacheMgr.put("ChannelNo:"+ channelNo, cBean);//放入缓存中         
				}
				//-------------------卡单中有这个身份证号码的情况下-----------------------
				if(cardgroupidnumber!=null){
					out.append("2|");
					System.out.println("-------卡单中有这个身份证 !-----开始缓存卡单数据----");
					logger.info("-------卡单中有这个身份证 !-----开始缓存卡单数据----");
					cBean.setGroupxml(groupresult);//卡单信息
					cacheMgr.put("ChannelNo:"+ channelNo, cBean);//放入缓存中
			}
								
		}
		else {
				System.out.println("------没有这个身份证---------");
				logger.debug("------没有这个身份证---------");
				out.append("1|");			
		}
		String returnString=out.toString();
		cBean.setReturnString(returnString);
		cacheMgr.put("ChannelNo:" + channelNo, cBean);
		return out.toString();
	
	}
	
	/* 保单验证
	 * commandNo--000002
	 * 验证此保单号在个险和团险中是否存在
	 * <p>1.0.0.0 罗尧创建 2010-10-26</p>
	 * @see com.crm.avivacofco.In.CacheBean.AbstractCacheBean#bdYanZheng()
	 */
	@Override
	public String bdYanZheng() {
		System.out.println("进入保单验证方法------22222");
		System.out.println("操作时间！--"+new Date());
		StringBuffer out = new StringBuffer();
		CacheMgr cacheMgr = CacheMgr.getInstance();
		String channelNo = super.getChannelNo();
		System.out.println("拿到channelNo:"+channelNo);
		AbstractCacheBean cBean = (AbstractCacheBean) cacheMgr.get("ChannelNo:"+ channelNo);
		System.out.println("cBean拿到缓存ID：---------"+cBean);		
		String ID_NO=cBean.getID_NO();
		System.out.println("拿到传递进入的保单号码："+ID_NO);
		
		
			if(cBean.getOnenumber()!=null||cBean.getGroupidnumber()!=null||cBean.getCardgroupidnumber()!=null){
				if(cBean.getOnenumber()!=null){//------------如果个险中存在返回值的话，开始验证保单号码-----------------
				/*
				 * 拿取个险中存在的保单号
				 */
				System.out.println("进入个险保单验证");
				SAXReader saxReader = new SAXReader(false);
				String onexml=cBean.getOnexml();
				Document doc;
				try {
					doc = saxReader.read(new StringReader(onexml));
					Element root = doc.getRootElement();
					Node one_policy_no = root.selectSingleNode("OutputData/ACLife/PolicyInfo/BaseInfo[ends-with(POLICY_NO, '" + ID_NO + "')]");
					if(null != one_policy_no){
						System.out.println("此保单号存在，验证通过！并且缓存----此保单号属于个险的保单号"+ID_NO);
						System.out.println("缓存的6位保单号码："+ID_NO);
						cBean.setSix_one_no(ID_NO);
						String policy_no= one_policy_no.valueOf("POLICY_NO");         //完整保单号码
						String company_code= one_policy_no.valueOf("COMPANY_CODE");   //分公司号码
						System.out.println("缓存完整的保单号码："+policy_no);
						System.out.println("缓存分公司号码："+company_code);
						cBean.setPolicy_no(policy_no);
						cBean.setCompany_code(company_code);
						out.append("Y|");
					}else{
						out.append("N|输入错误，请重新输入，返回上层菜单请按星号，结束请挂机！");
					}
				} catch (DocumentException e) {
					e.printStackTrace();
				}				
			}
			if(cBean.getGroupidnumber()!=null){//------------如果保单号在团险中存在-----------------
				/*
				 * 拿取团险中存在的保单号
				 */
				System.out.println("进入团单验证");
				SAXReader saxReader = new SAXReader(false);
				String groupxml=cBean.getGroupxml();
				Document doc;
				try {
					doc = saxReader.read(new StringReader(groupxml));
					Element root = doc.getRootElement();
					Node group_policy_no = root.selectSingleNode("ACLifeResponse/RetContent/Response/GrpInsuranceInfo/GrpInsurance[ends-with(GrpcontNo, '" + ID_NO + "')]");
					if(null != group_policy_no){
						System.out.println("此保单号存在，验证通过！并且缓存----此保单号属于团险的保单号"+ID_NO);
						System.out.println("缓存的6位保单号码："+ID_NO);
						cBean.setSix_group_no(ID_NO);
						out.append("Y|");
					}else{
						out.append("N|输入错误，请重新输入，返回上层菜单请按星号，结束请挂机！");
					}
				} catch (DocumentException e) {
					e.printStackTrace();
				}		
			}
			if(cBean.getCardgroupidnumber()!=null){//------------如果保单号在卡单中存在-----------------
				/*
				 * 拿取卡单中存在的保单号
				 */
				System.out.println("进入卡单验证");
				SAXReader saxReader = new SAXReader(false);
				String groupxml=cBean.getGroupxml();
				Document doc;
				try {
					doc = saxReader.read(new StringReader(groupxml));
					Element root = doc.getRootElement();
					Node group_policy_no = root.selectSingleNode("ACLifeResponse/RetContent/Response/ContCardInfo/ContCard[ends-with(ContCardNo, '" + ID_NO + "')]");
					if(null != group_policy_no){
						System.out.println("此保单号存在，验证通过！并且缓存----此保单号属于卡单的保单号"+ID_NO);
						System.out.println("缓存的6位保单号码："+ID_NO);
						cBean.setSix_card_no(ID_NO);
						out.append("Y|");
					}
					else{
						out.append("N|输入错误，请重新输入，返回上层菜单请按星号，结束请挂机！");
					}
				} catch (DocumentException e) {
					e.printStackTrace();
				}		
			}
		}else{
			System.out.println("此保单号不存在！");
			out.append("N|输入错误，请重新输入，返回上层菜单请按星号，结束请挂机！");
		}	
		String returnString=out.toString();
		cBean.setReturnString(returnString);
		cacheMgr.put("ChannelNo:" + channelNo, cBean);
		return out.toString();
	}
	
	/* 	保单信息基本信息查询
	 *  commandNo--000004
	 *  拿到个险和团险中的保单基本信息，合成语音串
	 *  <p>1.0.0.0 罗尧创建 2010-10-26</p>
	 * @see com.crm.avivacofco.In.CacheBean.AbstractCacheBean#bdXinXi()
	 */
	@Override
	public String bdXinXi() {
		System.out.println("进入保单基本信息查询------commandNo参数000004");
		logger.info("进入保单基本信息查询------commandNo参数000004");
		System.out.println("操作时间！--"+new Date());
		StringBuffer out = new StringBuffer();
		CacheMgr cacheMgr = CacheMgr.getInstance();
		String channelNo = super.getChannelNo();
		System.out.println("拿到channelNo:"+channelNo);
		AbstractCacheBean cBean = (AbstractCacheBean) cacheMgr.get("ChannelNo:"+ channelNo);
		System.out.println("cBean拿到缓存ID：---------"+cBean);
		
		//判断
		String one_no=cBean.getSix_one_no();
		System.out.println("6位个险保单号码："+one_no);
		String grp_no=cBean.getSix_group_no();
		System.out.println("6位团险团单号码："+grp_no);
		String card_no=cBean.getSix_card_no();
		System.out.println("6位团险卡单号码："+card_no);
		if(one_no!=null&&one_no!=""  ||   grp_no!=null&&grp_no!=""  ||   card_no!=null&&card_no!=""){
				if(one_no!=null&&one_no!="")
				{	
					System.out.println("---------个险有此基本信息------------");
					/*
					 * 拿取个险中的数据
					 */
					SAXReader saxReader = new SAXReader(false);
					String onexml=cBean.getOnexml();
					//out.append(onexml);
					StringBuffer strbuf = new StringBuffer();
					Document doc;
					Node one_policy_no = null;
					String policy_no=null;
					String company_code=null;
					String newpayment_freq = null;
					String nextDay="";
					String yearString="";//年
					String mothString="";//月
					String dateString="";//日
					String logicNumbers="";
					String baofei="";
					try {
							doc = saxReader.read(new StringReader(onexml));
							Element root = doc.getRootElement();
							one_policy_no = root.selectSingleNode("OutputData/ACLife/PolicyInfo/BaseInfo[ends-with(POLICY_NO, '" + one_no + "')]");							
							policy_no= one_policy_no.valueOf("POLICY_NO");         //保单号码
//							cBean.setPolicy_no(policy_no);
							System.out.println("保单号码为："+policy_no);
							company_code= one_policy_no.valueOf("COMPANY_CODE");   //分公司号码
							System.out.println("分公司号码为："+company_code);
							baofei=one_policy_no.valueOf("STD_INSTAL_PREM_1");
							String payment_freq =one_policy_no.valueOf("PAYMENT_FREQ");
							System.out.println("缴别代码为："+payment_freq);
							if(payment_freq.equals("00")){
								newpayment_freq="一次性缴费";
							}
							if(payment_freq.equals("01")){
								newpayment_freq="每年";
								//baofei=one_policy_no.valueOf("SNGL_PREMS");
							}
							if(payment_freq.equals("02")){
								newpayment_freq="每半年";
							}
							if(payment_freq.equals("04")){
								newpayment_freq="每季度";
							}
							if(payment_freq.equals("12")){
								newpayment_freq="每月";
							}		
							System.out.println("缴别属性为："+newpayment_freq);
						}catch (DocumentException e)
							{
							e.printStackTrace();
							}	
						//用xml里面的保单号和分公司号调用存储过程，拿到下次应缴日期
						Connection conn=DBConnection.getConnection();
						ResultSet rs = null;
						CallableStatement statement=null;		
						try {
							String callExchangeMoneyStr = "{call ODSCIF.SP_IVR_POLICY_INFO(?,?)}"; 
							statement = conn.prepareCall(callExchangeMoneyStr);
							statement.setString(1,company_code); 
							statement.setString(2,policy_no); 
							statement.execute();
							rs=statement.getResultSet();
							if(rs.next()){
								System.out.println("-------成功调用存储过程-----------");
								nextDay=rs.getString(8);								
								System.out.println("下一应缴日为:"+nextDay);
								logicNumbers=rs.getString(9);
								System.out.println("逻辑标识为："+logicNumbers);
								
							}else {
								out.append("存储过程中没有拿到值！");
							}					
						}catch (SQLException e) {
							e.printStackTrace();
							System.out.println("存储过程调用失败！");
						}
						if(logicNumbers.equals("1")){
							System.out.println("---------逻辑标识符为1----------");
							yearString=nextDay.substring(0,4);
							mothString=nextDay.substring(4,6);
							dateString =nextDay.substring(6,8);
							strbuf.append("Y|"+"保单号" +policy_no+ ","); 
							strbuf.append("为" + one_policy_no.valueOf("STATUS_NAME") + "状态"+",");
							strbuf.append("保费" + baofei+"元"+newpayment_freq+",");	
							strbuf.append("下次应缴保费日为"+yearString+"年"+mothString+"月"+dateString+"日");
							strbuf.append(" 重听   请按零,  返回上层菜单请按星号键,  如需查询其他保单请按  一,  结束请挂机");							
							System.out.println(strbuf.toString());
							out.append(strbuf.toString());
						}if(logicNumbers.equals("2")){
							System.out.println("---------逻辑标识符为2----------");
							strbuf.append("Y|"+"保单号" +policy_no+ ","); 
							strbuf.append("为" + one_policy_no.valueOf("STATUS_NAME") + "状态"+",");
							strbuf.append("保费" + baofei+"元"+newpayment_freq+",");	
							//strbuf.append("下次应缴保费日为"+yearString+"年"+mothString+"月"+dateString+"日");
							strbuf.append(" 重听   请按零,  返回上层菜单请按星号键,  如需查询其他保单请按  一,  结束请挂机");							
							System.out.println(strbuf.toString());
							out.append(strbuf.toString());
						}if(logicNumbers.equals("3")){
							System.out.println("---------逻辑标识符为3----------");
							strbuf.append("Y|"+"保单号" +policy_no+ ","); 
							strbuf.append("为" + one_policy_no.valueOf("STATUS_NAME") + "状态"+",");
							strbuf.append("保费" + baofei+"元"+newpayment_freq+",");	
							//strbuf.append("下次应缴保费日为"+yearString+"年"+mothString+"月"+dateString+"日");
							strbuf.append(" 重听   请按零,  返回上层菜单请按星号键,  如需查询其他保单请按  一,  结束请挂机");							
							System.out.println(strbuf.toString());
							out.append(strbuf.toString());
						}
						
				}
				//团险基本信息-------------------------------------------------------------------------------------
				if(grp_no!=null&&grp_no!="")
				{
					System.out.println("团险有此基本信息");
					SAXReader saxReader = new SAXReader(false);
					String groupxml=cBean.getGroupxml();
					StringBuffer strbuf = new StringBuffer();
					Document doc;
					Node group_policy_no = null;					
					try {
							doc = saxReader.read(new StringReader(groupxml));
							Element root = doc.getRootElement();
							group_policy_no = root.selectSingleNode("ACLifeResponse/RetContent/Response/GrpInsuranceInfo/GrpInsurance[ends-with(GrpcontNo, '" + grp_no + "')]");							
								System.out.println(group_policy_no.valueOf("RiskSaleCompany"));												
								String riskSaleCompany = group_policy_no.valueOf("RiskSaleCompany");
								strbuf.append("Y|"+"团体保单号" +group_policy_no.valueOf("GrpcontNo") + ",");
								// 获取多个Insured
				                List<? extends Node> insuredNodes = group_policy_no.selectNodes("Insured");
				                // 判断是否存在Insured
				                if (null != insuredNodes&& !insuredNodes.isEmpty()) {
				                    System.out.println("==> Insured size: " + insuredNodes.size());                  
				                    for (Node insuredNode : insuredNodes) {
				                     	
				                        String insuredName = insuredNode.valueOf("InsuredName");
				                        String grpInsuranceState = insuredNode.valueOf("GrpInsuranceState");
				                        strbuf.append("保险人" + insuredName + ",");				                        
				                        strbuf.append("保单状态" + grpInsuranceState + ",");				                      
				                        // 获取GrpRiskInfo
				                    	List<? extends Node> grpRiskInfoNodes = insuredNode.selectNodes("GrpRiskInfo");
				                        if (null != grpRiskInfoNodes) {
				                            System.out.println("==> GrpRiskInfo size: " + grpRiskInfoNodes.size());
				                            for (Node grpRiskInfoNode : grpRiskInfoNodes) {
				                                String riskName = grpRiskInfoNode.valueOf("RiskName");
				                                String riskAmnt = grpRiskInfoNode.valueOf("RiskAmnt");
				                                String riskCvalidate = grpRiskInfoNode.valueOf("RiskCvalidate");
				                                String riskEnddate = grpRiskInfoNode.valueOf("RiskEnddate");
				                                strbuf.append("投保险种" + grpRiskInfoNode.valueOf("@id") + ",");
				                                strbuf.append(riskName + ",");
				                                strbuf.append("保险金额" + riskAmnt +"元"+ ",");
				                                strbuf.append("保险责任生效日期" + riskCvalidate + ",");
				                                strbuf.append("保险责任终止日期" + riskEnddate + ",");
				                            	}
				                        	}
				                    	}
				                    }
			System.out.println(strbuf.toString()+"销售单位"+ riskSaleCompany+"重听   请按零,  返回上层菜单请按星号键,  如需查询其他保单请按  一,  结束请挂机");	
			out.append(strbuf.toString()+"销售单位"+ riskSaleCompany+"重听   请按零,  返回上层菜单请按星号键,  如需查询其他保单请按  一,  结束请挂机");
					}catch (Exception e)
						{
						System.out.println("团单的保单信息出错啦！");
						}			
				}
				//卡单基本信息----------------------------------------------------------------------------------
				if(card_no!=null&&card_no!=""){
					System.out.println("卡单有此基本信息");
					SAXReader saxReader = new SAXReader(false);
					String groupxml=cBean.getGroupxml();
					StringBuffer strbuf = new StringBuffer();
					Document doc;
					Node card_policy_no = null;					
					try {
							doc = saxReader.read(new StringReader(groupxml));
							Element root = doc.getRootElement();
							card_policy_no = root.selectSingleNode("ACLifeResponse/RetContent/Response/ContCardInfo/ContCard[ends-with(ContCardNo, '" + card_no + "')]");							
								strbuf.append("Y|"+"卡单类型"+card_policy_no.valueOf("ContCardName")+",");
								strbuf.append("卡单号" +card_policy_no.valueOf("ContCardNo") + ",");
								
								// 获取多个Insured
				                List<? extends Node> insuredNodes = card_policy_no.selectNodes("Insured");
				                // 判断是否存在Insured
				                if (null != insuredNodes&& !insuredNodes.isEmpty()) {
				                    System.out.println("==> Insured size: " + insuredNodes.size());                  
				                    for (Node insuredNode : insuredNodes) {                   	
				                        strbuf.append("为" +  insuredNode.valueOf("ContCardState") + "状态");				                        
				                        strbuf.append("保险费用"+card_policy_no.valueOf("ContCardPrem")+",");				                      
				                        // 获取GrpRiskInfo
				                    	List<? extends Node> grpRiskInfoNodes = insuredNode.selectNodes("CardRiskInfo");
				                        if (null != grpRiskInfoNodes) {
				                            System.out.println("==> GrpRiskInfo size: " + grpRiskInfoNodes.size());
				                            for (Node grpRiskInfoNode : grpRiskInfoNodes) {
				                                String riskName = grpRiskInfoNode.valueOf("RiskName");
				                                String riskAmnt = grpRiskInfoNode.valueOf("RiskAmnt");
				                                String riskCvalidate = grpRiskInfoNode.valueOf("RiskCvalidate");
				                                String riskEnddate = grpRiskInfoNode.valueOf("RiskEnddate");
				                                strbuf.append("投保险种" + grpRiskInfoNode.valueOf("@id") + ",");
				                                strbuf.append(riskName + ",");
				                                strbuf.append("保险金额" + riskAmnt +"元"+ ",");
				                                strbuf.append("保险责任生效日期" + riskCvalidate + ",");
				                                strbuf.append("保险责任终止日期" + riskEnddate + ",");
				                            	}
				                        	}
				                    	}
				                    }
				                
				                System.out.println(strbuf.toString()+card_policy_no.valueOf("RiskSaleCompany"));
				                out.append(strbuf.toString()+","+"销售单位："+card_policy_no.valueOf("RiskSaleCompany")+"重听   请按零,  返回上层菜单请按星号键,  如需查询其他保单请按  一,  结束请挂机");
					}catch (Exception e)
						{
						System.out.println("团单的保单信息出错啦！");
						}
				}	
				
			}else{
				System.out.println("此保单号不存在保单信息！-------00000");
				out.append("N|输入错误，请重新输入，返回上层菜单请按星号，结束请挂机！");
			}
	
			
		String returnString=out.toString();
		cBean.setReturnString(returnString);
		cacheMgr.put("ChannelNo:" + channelNo, cBean);
		return out.toString();
	}

	

	/* 个险理赔
	 * commandNo--000003
	 * 个险理赔信息的相关
	 * <p>1.0.0.0 罗尧创建 2010-10-26</p>
	 * @see com.crm.avivacofco.In.CacheBean.AbstractCacheBean#liPei()
	 */
	@Override
	public String liPei() {
		StringBuffer out = new StringBuffer();
		System.out.println("进入理赔信息查询------3333");
		System.out.println("操作时间！--"+new Date());
		CacheMgr cacheMgr = CacheMgr.getInstance();
		String channelNo = super.getChannelNo();
		System.out.println("在理赔信息查询中拿到channelNo:"+channelNo);
		AbstractCacheBean cBean = (AbstractCacheBean) cacheMgr.get("ChannelNo:"+ channelNo);
		
		
		String one_no=cBean.getSix_one_no();
		System.out.println("6位个险保单号码："+one_no);
		String policy_no=cBean.getPolicy_no();
		System.out.println("完整个险保单号码："+policy_no);
		
//		String x="aaaaaabbbbbcccccc";
//		if(!x.equals("")&&x!=null){
//			System.out.println("N|团险没有理赔信息！返回上层菜单请按星号键，结束请挂机！");
//			out.append("N|团险没有理赔信息！返回上层菜单请按星号键，结束请挂机！");
//		}
		
		if(one_no!=null&&one_no!="")
		{	
			SAXReader saxReader = new SAXReader(false);
			String onexml=cBean.getOnexml();
			StringBuffer strbuf = new StringBuffer();
			Document doc;
			Element onenumber = null;
			Element clm_regp_t = null;
			Element clm_death_t=null;
			long crtdateTime=0;
			long halfYearTime=0;	
			long dteregTime=0;
			 List list=new ArrayList();
			 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			 Date date=new Date();
			 long nowtime=date.getTime();
			 System.out.println("当前日期的毫秒值："+nowtime);
			 String nowString=df.format(date);
		     System.out.println("当前日期为："+nowString);           
			 String halfYear=df.format(new Date(date.getTime()- (long)182 * 24 * 60 * 60 * 1000));
			 System.out.println("半年范围的日期：" + halfYear);
			 try {
				 	date=df.parse(halfYear);			
					halfYearTime=date.getTime();
					System.out.println("半年范围的日期的毫秒值："+halfYearTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			try {
				doc = saxReader.read(new StringReader(onexml));
				onenumber = (Element)doc.selectSingleNode("/TradeData/OutputData/ACLife/PolicyInfo");	
				if(onenumber!=null){
					Element root = doc.getRootElement();
					Node one_policy_no = root.selectSingleNode("OutputData/ACLife/PolicyInfo/BaseInfo[ends-with(POLICY_NO, '" + policy_no + "')]");
					if(one_policy_no!=null){
					String baodanString=one_policy_no.valueOf("POLICY_NO");
					System.out.println("保单号码为："+baodanString);
						//strbuf.append("保单号码为："+baodanString+",");
					clm_regp_t=(Element)doc.selectSingleNode("/TradeData/OutputData/ACLife/PolicyInfo/CLM_REGP_T/Item");
					clm_death_t=(Element)doc.selectSingleNode("/TradeData/OutputData/ACLife/PolicyInfo/CLM_DEATH_T/Item");
					if(clm_regp_t!=null||clm_death_t!=null){
						if(clm_regp_t!=null){
							System.out.println("---------进入普通理赔信息！---------");
							//List<? extends Node> insuredNodes = one_policy_no.selectNodes("../CLM_REGP_T");
							Node clm_regp_t_node = one_policy_no.selectSingleNode("../CLM_REGP_T");
							 List<? extends Node> itemNodes = clm_regp_t_node.selectNodes("Item");
							 if (null != itemNodes&& !itemNodes.isEmpty()) {
							 for (Node insuredNode : itemNodes) {	
								 String id=insuredNode.valueOf("@id");
								 String crtdate=insuredNode.valueOf("CRTDATE");
								 System.out.println("受理日期为："+id+"-------------------------"+","+crtdate);
								 try{
									 	date=df.parse(crtdate);			
									 	crtdateTime=date.getTime();
										System.out.println("受理日期为：的毫秒值："+crtdateTime);
									}catch (ParseException e) {
										e.printStackTrace();
									}
								 String  clm_typ=insuredNode.valueOf("CLM_TYP");
								 System.out.println("理赔类型为："+id+","+clm_typ);
								 	//strbuf.append("理赔类型为："+clm_typ+",");
								 String clm_status=insuredNode.valueOf("CLM_STATUS");
								 System.out.println("理赔状态代码为："+id+","+clm_status);
								
								 String aprv_dte=insuredNode.valueOf("APRV_DTE");
								 System.out.println("结案日期1："+id+","+aprv_dte);
								 
								 String cancel_dte=insuredNode.valueOf("CANCEL_DTE");
								 System.out.println("结案日期2："+id+","+cancel_dte);
								 String jiean_dte="";
								 String pay=insuredNode.valueOf("PAY");
								 System.out.println("理赔支付金额为:"+id+","+pay);
								 String newPay=pay+"元";
								 String sjpay="";
								 
								 
								 String new_clm_typ = null;
								 if(clm_typ.equals("MP")){
									 new_clm_typ="医疗给付";
								 }if(clm_typ.equals("WP")){
									 new_clm_typ="免缴保费";	
								 }if(clm_typ.equals("DP")){
									 new_clm_typ="重大理赔";
								 }if(clm_typ.equals("CP")){
									 new_clm_typ="重大理赔";
								 }
								 String valid_flag=insuredNode.valueOf("VALID_FLAG");
								 System.out.println("满足这个条件等于2的情况："+id+"------"+valid_flag);
								 if(crtdateTime>=halfYearTime&&crtdateTime<=nowtime&&!valid_flag.equals("2")){
									 
									 String lipeianjianzhuangtai = null;
									 String xString=null;
									 if(clm_status.equals("PA")||clm_status.equals("PN")){
										 lipeianjianzhuangtai="已受理";
xString="保单号码为："+baodanString+","+"受理日期为："+crtdate+","+"理赔类型为："+new_clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"理赔支付金额为:"+"暂未结算"+",";
System.out.println("保单号码为："+baodanString+","+"受理日期为："+crtdate+","+"理赔类型为："+new_clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"理赔支付金额为:"+"暂未结算"+",");									 
									 }if(clm_status.equals("AL")||clm_status.equals("BT")||clm_status.equals("CL")||clm_status.equals("MT")||clm_status.equals("IP")){
										 lipeianjianzhuangtai="已结案";
										 
										 if(clm_status.equals("AL")||clm_status.equals("BT")||clm_status.equals("IP")){
											 jiean_dte=aprv_dte;
											 sjpay=newPay;
										 } if(clm_status.equals("CL")){
											 jiean_dte=cancel_dte;
											 sjpay="撤单";
										 } if(clm_status.equals("MT")){
											 jiean_dte=cancel_dte;
											 sjpay="0元";
										 }	if(clm_status.equals("")||clm_status==null){
											 sjpay="0元";
										 }	
										 if(clm_typ.equals("WP")){
											 sjpay="0元";
										 }
xString="保单号码为："+baodanString+","+"受理日期为："+crtdate+","+"理赔类型为："+new_clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"结案日期："+aprv_dte+","+"理赔支付金额为:"+sjpay+",";
System.out.println("保单号码为："+baodanString+","+"受理日期为："+crtdate+"理赔类型为："+new_clm_typ+"理赔案件状态为："+lipeianjianzhuangtai+"结案日期："+aprv_dte+"理赔支付金额为:"+sjpay+",");
									 }
									 
							PayBean payBean =new PayBean();
							payBean.information=xString;
							payBean.date=crtdateTime;
							//long time=halfYearTime;
							list.add(payBean);
							}
						}
					}							 
				}
						if(clm_death_t!=null){
							System.out.println("------------进入死亡理赔信息！--------------");
							Node clm_death_t_node= one_policy_no.selectSingleNode("../CLM_DEATH_T");
							 List<? extends Node> itemNodes = clm_death_t_node.selectNodes("Item");
							 if (null != itemNodes&& !itemNodes.isEmpty()) {
								 for (Node insuredNode : itemNodes) {
									 String idd=insuredNode.valueOf("@id");
									 String dtereg=insuredNode.valueOf("DTEREG");
									 System.out.println("受理日期为："+idd+"-------------------------"+","+dtereg);
									 try {
										 	date=df.parse(dtereg);			
										 	dteregTime=date.getTime();
											System.out.println("受理日期为：的毫秒值："+dteregTime);
										} catch (ParseException e) {
											e.printStackTrace();
										}
									 String  clm_typ="重大理赔";
									 System.out.println("理赔类型为："+idd+","+clm_typ);
									 	//strbuf.append("理赔类型为"+clm_typ+",");
									 
									 	//strbuf.append("理赔案件状态为："+lipeianjianzhuangtai+",");
									 String dteappr=insuredNode.valueOf("DTEAPPR");
									 System.out.println("结案日期："+idd+","+dteappr);

									 String clamstat_clmh=insuredNode.valueOf("CLAMSTAT_CLMH");
									 System.out.println("理赔状态代码为："+idd+","+clamstat_clmh);
									 								 								 
									 String actvalue=insuredNode.valueOf("ACTVALUE");
									 System.out.println("理赔支付金额为:"+idd+","+actvalue);
									 String newActvalue=actvalue+"元";
									 
									 String debit_amt=insuredNode.valueOf("DEBIT_AMT");
									 System.out.println("重大理赔：合计金额为："+idd+","+debit_amt);
									 
									 String LIFE_CLMD=insuredNode.valueOf("LIFE_CLMD");
									 String COVERAGE=insuredNode.valueOf("COVERAGE");
									 String RIDER=insuredNode.valueOf("RIDER");										 								
				if(dteregTime>=halfYearTime&&dteregTime<=nowtime){
					String lipeianjianzhuangtai = null;
					if(!(LIFE_CLMD.equals("01") && COVERAGE.equals("01") && RIDER.equals("00"))){
						System.out.println("!(LIFE_CLMD.equals(01) && COVERAGE.equals(01) && RIDER.equals(00))====此条件成立！");						
						String xString=null;
						 if(clamstat_clmh.equals("RG")||dteappr.equals("2999-12-31")){
							 lipeianjianzhuangtai="已受理";
xString="保单号码为："+baodanString+","+"受理日期为："+dtereg+","+"理赔类型为："+clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"理赔支付金额为:"+"暂未结算"+",";
System.out.println(xString="保单号码为："+baodanString+","+"受理日期为："+dtereg+","+"理赔类型为："+clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"理赔支付金额为:"+"暂未结算"+",");
						 }if(!dteappr.equals("2999-12-31")){
							 lipeianjianzhuangtai="已结案";
System.out.println("保单号码为："+baodanString+"受理日期为："+dtereg+"理赔类型为："+clm_typ+"理赔案件状态为："+lipeianjianzhuangtai+"结案日期："+dteappr+"理赔支付金额为:"+debit_amt);
xString="保单号码为："+baodanString+","+"受理日期为："+dtereg+","+"理赔类型为："+clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"结案日期："+dteappr+","+"理赔支付金额为:"+debit_amt+"元"+",";
						 }						 											
									PayBean payBean =new PayBean();
									payBean.information=xString;
									payBean.date=crtdateTime;
									//long time=halfYearTime;
									list.add(payBean);
					}else{
						String xString=null;
						if(clamstat_clmh.equals("RG")||dteappr.equals("2999-12-31")){
							lipeianjianzhuangtai="已受理";
xString="保单号码为："+baodanString+","+"受理日期为："+dtereg+","+"理赔类型为："+clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"理赔支付金额为:"+"暂未结算"+",";
System.out.println("保单号码为："+baodanString+","+"受理日期为："+dtereg+","+"理赔类型为："+clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"理赔支付金额为:"+"暂未结算"+",");
						}if(!dteappr.equals("2999-12-31")){
							lipeianjianzhuangtai="已受理";
							String nowActvalue="";
							 if(clamstat_clmh.equals("CL")){
								 nowActvalue="0";
							 }
							 if(clamstat_clmh.equals("DE")){
								 nowActvalue="撤单";
//xString="保单号码为："+baodanString+","+"受理日期为："+dtereg+","+"理赔类型为："+clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"结案日期："+dteappr+","+"理赔支付金额为:"+nowActvalue+",";
							 }
							 if(clamstat_clmh.equals("AP")){
								 nowActvalue=newActvalue;
							 }
xString="保单号码为："+baodanString+","+"受理日期为："+dtereg+","+"理赔类型为："+clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"结案日期："+dteappr+","+"理赔支付金额为:"+nowActvalue+",";
System.out.println("保单号码为："+baodanString+"受理日期为："+dtereg+"理赔类型为："+clm_typ+"理赔案件状态为："+lipeianjianzhuangtai+"结案日期："+dteappr+"理赔支付金额为:"+nowActvalue+",");
						}
						
								    PayBean payBean =new PayBean();
									payBean.information=xString;
									payBean.date=crtdateTime;
									//long time=halfYearTime;
									list.add(payBean);	 
						}
					} 			
								 }
							 }							 							 
						}	
						
						if(list.size()==0){
						System.out.println("list的长度为："+list.size()+"没有满足条件的保单！"); 
						out.append("N|对不起,您要查询的半年内的理赔保单不存在，如须查询其它保单请按1。返回上层菜单请按星号键，结束请挂机");
						}	                    
	                    Comparator comp = new InfoCirculating();
	                    Collections.sort(list,comp); 
	                    out.append("Y|");
	                    for(int i=0;i<list.size();i++){
	                    	PayBean payBean =(PayBean)list.get(i);
	                    	System.out.println(payBean.date);
	                    	System.out.println(payBean.information);
	                    	out.append(payBean.information);
	                    	//out.append("</br>");
	                    	if(i==4){
	                    		break;
	                    	}
	                    }
						out.append("重听请按0，需要听下一条记录请按#号,返回上层菜单请按*号，如须查询其它保单请按1");
					}else{
						System.out.println("此保单号没有理赔信息");
						out.append("N|对不起,您要查询的理赔保单不存在，如须查询其它保单请按1。返回上层菜单请按星号键，结束请挂机");						
						}												
				}else{
					System.out.println("此保单号码没有保单信息！");
				}
				}
			}catch (DocumentException e)
				{
				e.printStackTrace();
				System.out.println("个险接口调用失败");
				out.append("N|个险接口调用失败");
				}
		}else{
			out.append("N|对不起,您要查询的理赔保单不存在，如须查询其它保单请按1。返回上层菜单请按星号键，结束请挂机");
		}
		
		String returnString=out.toString();
		cBean.setReturnString(returnString);
		cacheMgr.put("ChannelNo:" + channelNo, cBean);
		return out.toString();
		
	}

	
	
	/* 工号验证检测
	 * commandNo--000005
	 * 工号验证检测，并且缓存调用存储过程得到的分公司号
	 * <p>1.0.0.0 罗尧创建 2010-10-26</p>
	 * @see com.crm.avivacofco.In.CacheBean.AbstractCacheBean#ZHjianChe()
	 */
	@Override
	public String ZHjianChe() {
		StringBuffer out = new StringBuffer();
		System.out.println("进入工号检测方法------55555");
		System.out.println("操作时间！--"+new Date());
		CacheMgr cacheMgr = CacheMgr.getInstance();
		String channelNo = super.getChannelNo();
		System.out.println("在工号检测中拿到channelNo:"+channelNo);
		AbstractCacheBean cBean = (AbstractCacheBean) cacheMgr.get("ChannelNo:"+ channelNo);
		System.out.println("cBean拿到缓存ID：---------"+cBean);
		String xString = (String) cBean.getID_NO();
		System.out.println("拿到传入的工号和渠道号串，并进行拆解---"+xString);
		String qudao = xString.substring(0,1);
		cBean.setQudao(qudao);
		String agentno = xString.substring(1, xString.length());
		//System.out.println("代理人工号为："+agentno);
		cBean.setAgentNO(agentno);
		//cacheMgr.put("ChannelNo:"+ channelNo, cBean);
		System.out.println("获取输入的渠道类型代号：" + qudao);
		System.out.println("获取代理人工号：" + agentno);	
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		CallableStatement statement = null;
		try {
			String callExchangeMoneyStr = "{call ODSCIF.SP_IVR_AGENTS_INFO(?,?)}";
			statement = conn.prepareCall(callExchangeMoneyStr);
			statement.setString(1, agentno);
			statement.setString(2, qudao);
			statement.execute();
			rs = statement.getResultSet();
			if (rs.next()) {
				// System.out.println("*********拿到的数据库值********："+rs.getString(1));				
				//得到的分公司号，用做下一个存储过程的参数
				//得到的分公司号，改为用做下一个webservice的参数
				String cp_no=rs.getString(2);
				System.out.println("拿到存储过程中的的分公司号码为："+cp_no);
				cBean.setCp_NO(cp_no);
				//得到的身份证号码，out出后六位
				String sf=rs.getString(3);
				System.out.println("身份证号码为："+sf);
				String endsf=sf.replaceAll(".*(.{6})", "$1");
				System.out.println("身份证号码后六位："+endsf);
				if(endsf!=null&&!endsf.equals("")){
					out.append("Y" +endsf+"|");	
				}else{
					endsf="110110";
					out.append("Y"+endsf+"|");
				}
				cBean.setSF(endsf);
				
				//cacheMgr.put("ChannelNo:" + channelNo, cBean);

			} else {
				System.out.println("********！数据库中没有此条记录！********");
				out.append("N|");
				
				
			}
		} catch (SQLException e){
			e.printStackTrace();
			System.out.println("您输入的条件超出范围！请查证后重新输入！");
		}try {
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String returnString=out.toString();
		cBean.setReturnString(returnString);
		cacheMgr.put("ChannelNo:" + channelNo, cBean);
		return out.toString();
	}
	
	
	/* 新契约
	 * 查询给定保单的状态
	 * commandNo--000006
	 * SP查询给定保单的状态，并且缓存调用存储过程之后拿到的保费、下一次应缴日期
	 * <p>1.0.0.0 罗尧创建 2010-10-26</p>
	 * ======================================
	 * 需求更改：
	 * 拿到分公司号和传入的保单号调用webservice查询此保单的理赔信息 
	 * <p>罗尧2010-11-22</p>
	 * @see com.crm.avivacofco.In.CacheBean.AbstractCacheBean#xinQiYue()
	 */
	@Override
	public String PolicyStatus() {
		StringBuffer out=new StringBuffer();
		System.out.println("进入新契约查询方法------666666");
		System.out.println("操作时间！--"+new Date());
		CacheMgr cacheMgr = CacheMgr.getInstance();
		System.out.println("-----------"+cacheMgr);	
		String channelNo = super.getChannelNo();
		System.out.println("通道号："+channelNo);
		AbstractCacheBean  cBean = (AbstractCacheBean)cacheMgr.get("ChannelNo:" + channelNo);
		
		String cp_no=cBean.getCp_NO();
		System.out.println("获取缓存中的分公司号："+cp_no);
		String ba_no=this.getID_NO();
		System.out.println("获取输入的保单号："+ba_no);
		String qudao_no=cBean.getQudao();
		System.out.println("渠道号码为"+qudao_no);
		String twobaodanString;
		String afterbaodanString = null;
		if(qudao_no.equals("1")){
			afterbaodanString=ba_no;
		}else
		if(qudao_no.equals("2")){
			if(ba_no.substring(0,2).equals("**")){
				twobaodanString="BV";
				afterbaodanString=twobaodanString+ba_no.substring(2,8);
			}else
			if(ba_no.substring(0,1).equals("*")){
				twobaodanString="B";
				afterbaodanString=twobaodanString+ba_no.substring(1,8);
			}
		}else
		if(qudao_no.equals("3")){
			if(ba_no.substring(0,2).equals("**")){
				twobaodanString="AC";
				afterbaodanString=twobaodanString+ba_no.substring(2,8);
			}else
			if(ba_no.substring(0,1).equals("*")){
				twobaodanString="A";
				afterbaodanString=twobaodanString+ba_no.substring(1,8);
			}
		}
		System.out.println("修改后的保单号为："+afterbaodanString);
		
		java.util.Date nowdate = new java.util.Date();
		java.text.SimpleDateFormat hmfromat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strNowTime = hmfromat.format(nowdate);
		String TradeDate = strNowTime.substring(0, 10);
		String TradeTime = strNowTime.substring(11, strNowTime.length());
		System.out.println("调用开始日期："+TradeDate);
		System.out.println("调用开始时间"+TradeTime);
		System.out.println("---------开始保单号调用个险---------");
		java.lang.String ret;
		ret="";
		try {           
			java.lang.String input=
			"<?xml version=\"1.0\" encoding=\"GBK\"?>       "+
			"<TradeData>                                    "+
			"  <BaseInfo>                                   "+
			"    <TradeCode>00030001</TradeCode>            "+
			"    <TradeTime>"+TradeTime+"</TradeTime>       "+
			"    <TotalRowNum/>                           	"+
			"    <ResultCode>0</ResultCode>                 "+
			"    <ResultMsg/>                              	"+
			"    <TradeType>WEBSITE</TradeType>             "+
			"    <PageRowNum>5</PageRowNum>                 "+
			"    <OrderFlag>0</OrderFlag>                   "+
			"    <SubTradeCode>1</SubTradeCode>             "+
			"    <TradeSeq>0001101420070311000001</TradeSeq>"+
			"    <PageFlag>1</PageFlag>                     "+
			"    <RowNumStart>1</RowNumStart>               "+
			"    <OrderField />                             "+
			"    <Operator>WEB</Operator>                   "+
			"    <TradeDate>"+TradeDate+"</TradeDate>       "+
			"  </BaseInfo>                                  "+
			"<InputData>"+	
			"<Company>"+cp_no+"</Company>"+
		    "<PolicyNo>"+afterbaodanString+"</PolicyNo>"+
		    "<Filter>BaseInfo,RiskInfo,PersonInfo,AgentInfo_PERSON_AGENT,AgentInfo_BROKER,AgentInfo_BANK_BOS,AgentInfo_DM_AGENTHIS,ZMAN,CLMREGP,CLMDEATH,NoticeInfo</Filter>"+
		    "</InputData>"+
			"  <OutputData />                               "+
			"</TradeData>                                   ";
			String endpoint = "http://10.145.6.40:9080/CRMHT/QueryService.jws";
			Service service = new Service();
			Call call = (Call) service.createCall();
			//设置用户名
	        call.setUsername("CRMUSER");
	        //设置密码
	        call.setPassword("crmpsw");
	        call.setOperation(_operations[0]);
	        call.setUseSOAPAction(true);
	        call.setSOAPActionURI("");
	        call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("http://DefaultNamespace", "CCFunction"));
			//setRequestHeaders(call);
		    //setAttachments(call);
			ret = (String) call.invoke(new Object[] {input});
			//System.out.println("得到保单号码+分公司号调用webservice的数据---" + ret);//保单号码得到报文信息
			//out.append(ret);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		System.out.println("---------保单号码调用webservice成功----------");
		
		SAXReader saxReader = new SAXReader(false);
		Document onedoc=null;
		Element onenumber = null;
		Element clm_regp_t = null;
		Element clm_death_t=null;
		long crtdateTime=0;
		long dteregTime=0;
		long halfYearTime=0;
//		StringBuffer strbuf = new StringBuffer();
		List list=new ArrayList();
		 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 Date date=new Date();
		 long nowtime=date.getTime();		 
		 String nowString=df.format(date);
	     System.out.println("当前日期为："+nowString);           
		 String halfYear=df.format(new Date(date.getTime()- (long)182 * 24 * 60 * 60 * 1000));
		 System.out.println("半年范围的日期：" + halfYear);
		 System.out.println("当前日期的毫秒值："+nowtime);
		 try {
			 	date=df.parse(halfYear);			
				halfYearTime=date.getTime();
				System.out.println("半年范围的日期的毫秒值："+halfYearTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		try {
			onedoc = saxReader.read(new StringReader(ret));
			onenumber = (Element)onedoc.selectSingleNode("/TradeData/OutputData/ACLife/PolicyInfo");	
			if(onenumber!=null){
				Element root = onedoc.getRootElement();
				Node one_policy_no = root.selectSingleNode("OutputData/ACLife/PolicyInfo/BaseInfo[ends-with(POLICY_NO, '" + afterbaodanString + "')]");
				if(null !=one_policy_no){
				String baodanString=one_policy_no.valueOf("POLICY_NO");
				System.out.println("保单号码为："+baodanString);					
				clm_regp_t=(Element)onedoc.selectSingleNode("/TradeData/OutputData/ACLife/PolicyInfo/CLM_REGP_T/Item");
				clm_death_t=(Element)onedoc.selectSingleNode("/TradeData/OutputData/ACLife/PolicyInfo/CLM_DEATH_T/Item");
				if(clm_regp_t!=null||clm_death_t!=null){
					System.out.println("普通理赔或者死亡理赔有信息！");
					if(clm_regp_t!=null){
						System.out.println("------**********************--------进入普通理赔信息！----------**********************--------");
						Node clm_regp_t_node = one_policy_no.selectSingleNode("../CLM_REGP_T");
						 List<? extends Node> itemNodes = clm_regp_t_node.selectNodes("Item");
						 if (null != itemNodes&& !itemNodes.isEmpty()) {
						 for (Node insuredNode : itemNodes) {
							 String id=insuredNode.valueOf("@id");
							 String crtdate=insuredNode.valueOf("CRTDATE");
							 System.out.println("受理日期为："+id+"-------------------------"+","+crtdate);
							 try {
								 	date=df.parse(crtdate);			
								 	crtdateTime=date.getTime();
									System.out.println("受理日期为：的毫秒值："+crtdateTime);
								} catch (ParseException e) {
									e.printStackTrace();
								}
							 String  clm_typ=insuredNode.valueOf("CLM_TYP");
							 System.out.println("理赔类型为："+id+","+clm_typ);
							 
							 String clm_status=insuredNode.valueOf("CLM_STATUS");
							 System.out.println("理赔状态代码为："+id+","+clm_status);
							 
							 String aprv_dte=insuredNode.valueOf("APRV_DTE");
							 System.out.println("结案日期1："+id+","+aprv_dte);
							 
							 String cancel_dte=insuredNode.valueOf("CANCEL_DTE");
							 System.out.println("结案日期2："+id+","+cancel_dte);
							 String jiean_dte="";
							 String pay=insuredNode.valueOf("PAY");
							 System.out.println("理赔支付金额为:"+id+","+pay);
							 String newPay=pay+"元";
							 String sjpay="";
							 String new_clm_typ = null;
							 if(clm_typ.equals("MP")){
								 new_clm_typ="医疗给付";
							 }if(clm_typ.equals("WP")){
								 new_clm_typ="免缴保费";	
							 }if(clm_typ.equals("DP")){
								 new_clm_typ="重大理赔";
							 }if(clm_typ.equals("CP")){
								 new_clm_typ="重大理赔";
							 }
							 String valid_flag=insuredNode.valueOf("VALID_FLAG");
							 System.out.println("满足这个条件等于2的情况："+id+"------"+valid_flag);
							 if(crtdateTime>=halfYearTime&&crtdateTime<=nowtime&&!valid_flag.equals("2")){
								 String xString=null;
								 String lipeianjianzhuangtai = null;
								 if(clm_status.equals("PA")||clm_status.equals("PN")){
									 lipeianjianzhuangtai="已受理";
xString="保单号码为："+baodanString+","+"受理日期为："+crtdate+","+"理赔类型为："+new_clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"理赔支付金额:"+"暂未理算"+",";
System.out.println("普通理赔--"+"保单号码为："+baodanString+","+"受理日期为："+crtdate+","+"理赔类型为："+new_clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"理赔支付金额:"+"暂未理算"+",");
								 }if(clm_status.equals("AL")||clm_status.equals("BT")||clm_status.equals("CL")||clm_status.equals("MT")||clm_status.equals("IP")){
									 lipeianjianzhuangtai="已结案";	
									 
									 if(clm_status.equals("AL")||clm_status.equals("BT")||clm_status.equals("IP")){
										 jiean_dte=aprv_dte;
										 sjpay=newPay;
									 } if(clm_status.equals("CL")){
										 jiean_dte=cancel_dte;
										 sjpay="撤单";
									 } if(clm_status.equals("MT")){
										 jiean_dte=cancel_dte;
										 sjpay="0元";
									 }	if(clm_status.equals("")||clm_status==null){
										 sjpay="0元";
									 }	
									 if(clm_typ.equals("WP")){
										 sjpay="0元";
									 }
xString="保单号码为："+baodanString+","+"受理日期为："+crtdate+","+"理赔类型为："+new_clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"结案日期："+jiean_dte+","+"理赔支付金额为:"+sjpay+",";
System.out.println("普通理赔--"+"保单号码为："+baodanString+","+"受理日期为："+crtdate+","+"理赔类型为："+new_clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"结案日期："+jiean_dte+","+"理赔支付金额为:"+sjpay+",");
								 }
							PayBean payBean =new PayBean();
							payBean.information=xString;
							payBean.date=crtdateTime;
							//long time=halfYearTime;
							list.add(payBean);								 
						}
						 	}
						 }
					}
					if(clm_death_t!=null){
						System.out.println("-------*************************---------进入死亡理赔信息！-------------***************************------------");
						Node clm_death_t_node= one_policy_no.selectSingleNode("../CLM_DEATH_T");
						 List<? extends Node> itemNodes = clm_death_t_node.selectNodes("Item");
						 if (null != itemNodes&& !itemNodes.isEmpty()) {
							 for (Node insuredNode : itemNodes) {
								 String idd=insuredNode.valueOf("@id");
								 String dtereg=insuredNode.valueOf("DTEREG");
								 System.out.println("受理日期为："+idd+"-------------------------"+","+dtereg);
								 try {
									 	date=df.parse(dtereg);			
									 	dteregTime=date.getTime();
										System.out.println("受理日期为：的毫秒值："+dteregTime);
									} catch (ParseException e) {
										e.printStackTrace();
									}
								 String  clm_typ="重大理赔";
								 System.out.println("理赔类型为："+idd+","+clm_typ);
								 	//strbuf.append("理赔类型为"+clm_typ+",");

								 String dteappr=insuredNode.valueOf("DTEAPPR");
								 System.out.println("结案日期："+idd+","+dteappr);
								 
								 String clamstat_clmh=insuredNode.valueOf("CLAMSTAT_CLMH");
								 System.out.println("理赔状态代码为："+idd+","+clamstat_clmh);
								 								 								 
								 String actvalue=insuredNode.valueOf("ACTVALUE");
								 System.out.println("理赔支付金额为:"+idd+","+actvalue);
								 //String newActvalue=actvalue+"元";
								 
								 String debit_amt=insuredNode.valueOf("DEBIT_AMT");
								 System.out.println("重大理赔：合计金额为："+idd+","+debit_amt);
								 String newDebit_amt=debit_amt+"元";
								 String LIFE_CLMD=insuredNode.valueOf("LIFE_CLMD");
								 String COVERAGE=insuredNode.valueOf("COVERAGE");
								 String RIDER=insuredNode.valueOf("RIDER");
								 
						if(dteregTime>=halfYearTime&&dteregTime<=nowtime){ 
							//String deathString=null;
							String lipeianjianzhuangtai = null;
								 if((LIFE_CLMD.equals("01") && COVERAGE.equals("01") && RIDER.equals("00"))){
							System.out.println("(LIFE_CLMD.equals(01) && COVERAGE.equals(01) && RIDER.equals(00))====此条件成立！");									 							
									 System.out.println("if三个equals不满足------------------！");
									 String xString=null;									 
									 if(clamstat_clmh.equals("RG")||dteappr.equals("2999-12-31")){
										 lipeianjianzhuangtai="已受理";
xString="保单号码为："+baodanString+","+"受理日期为："+dtereg+","+"理赔类型为："+clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"理赔支付金额:"+"暂未结算"+",";
System.out.println("三个为空的条件满足"+"2999-12-31"+"保单号码为："+baodanString+","+"受理日期为："+dtereg+","+"理赔类型为："+clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"理赔支付金额:"+"暂未结算"+",");									 
									 }if(!dteappr.equals("2999-12-31")){
										 lipeianjianzhuangtai="已结案";
										 
										 String nowDebit_amt="";
										 if(clamstat_clmh.equals("CL")){
											 nowDebit_amt="0元";
										 }
										 if(clamstat_clmh.equals("DE")){
											 nowDebit_amt="撤单";
										 }
										 if(clamstat_clmh.equals("AP")){
											 nowDebit_amt=newDebit_amt;
										 }
xString="保单号码为："+baodanString+","+"受理日期为："+dtereg+","+"理赔类型为："+clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"结案日期："+dteappr+","+"理赔支付金额为:"+nowDebit_amt+",";
System.out.println("三个为空的条件满足"+"!2999-12-31"+"保单号码为："+baodanString+","+"受理日期为："+dtereg+","+"理赔类型为："+clm_typ+","+"理赔案件状态为："+lipeianjianzhuangtai+","+"结案日期："+dteappr+","+"理赔支付金额为:"+nowDebit_amt+",");									
									 }
									 								 									 
									 PayBean payBean =new PayBean();
										payBean.information=xString;
										payBean.date=dteregTime;
										//long time=halfYearTime;
										list.add(payBean); 
								 }else {
						System.out.println("(LIFE_CLMD.equals(01) && COVERAGE.equals(01) && RIDER.equals(00))====此条件不成立！");
						System.out.println("死亡理赔中没有符合条件的理赔信息！");
								}
							}else {
								System.out.println("死亡理赔半年内没有符合条件的理赔信息！");
							}

							 }
						 }
						 
					}
					if(list.size()==0){
					System.out.println("list的长度为："+list.size());
					out.append("N|对不起,您要查询的半年内的理赔保单不存在，如须查询其它保单请按1。返回上层菜单请按星号键，结束请挂机");
					}
                    Comparator comp = new InfoCirculating();
                    Collections.sort(list,comp); 
                    out.append("Y|");
                    for(int i=0;i<list.size();i++){
                    	PayBean payBean =(PayBean)list.get(i);
                    	System.out.println(payBean.date);
                    	System.out.println(payBean.information);
                    	out.append(payBean.information);
                    	//out.append("</br>");
                    	if(i==4){
                    		break;
                    	}
                    }
                    out.append("重听请按0,返回上层菜单请按*号，如须查询其它保单请按1");
				}else{
					System.out.println("此保单号没有理赔信息1");
					out.append("N|此保单号没有理赔信息,返回上层菜单请按 星号,退出请挂机1");
					}	
			}else{
				System.out.println("此保单号没有理赔信息2");
				out.append("N|此保单号没有理赔信息,返回上层菜单请按 星号,退出请挂机2");
				}	
			}else{
				System.out.println("此保单号码没有保单信息！");
				out.append("N|此保单号码没有保单信息,返回上层菜单请按 星号,退出请挂机！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			out.append("N|保单号码接口调用失败");
		}	
		
		String returnString=out.toString();
		cBean.setReturnString(returnString);
		cacheMgr.put("ChannelNo:" + channelNo, cBean);
		return  out.toString();

		
	}
	
	
	/* 保单资料及续期收费查询
	 * commandNo--000008
	 * 查询保单状态、保费、下次应缴日期
	 * <p>1.0.0.0 罗尧创建 2010-10-26</p>
	 * @see com.crm.avivacofco.In.CacheBean.AbstractCacheBean#sfYanZheng()
	 */
	@Override
	public String PolicyInformationAndRenewalFeeInquiry(){		
		System.out.println("保单资料及续期收费查询方法------88888888");
		System.out.println("操作时间！--"+new Date());
		StringBuffer out=new StringBuffer();
		CacheMgr cacheMgr = CacheMgr.getInstance();
		System.out.println("-----------"+cacheMgr);	
		String channelNo = super.getChannelNo();
		System.out.println("通道号为："+channelNo);
		AbstractCacheBean  cBean = (AbstractCacheBean)cacheMgr.get("ChannelNo:" + channelNo);
		String cp_no=(String) cBean.getCp_NO();
		System.out.println("获取缓存中的分公司号："+cp_no);
		String ba_no=this.getID_NO();
		System.out.println("获取输入的保单号："+ba_no);
		String qudao_no=cBean.getQudao();
		System.out.println("渠道号码为"+qudao_no);
		String twobaodanString;
		String afterbaodanString = null;
		if(qudao_no.equals("1")){
			afterbaodanString=ba_no;
		}else
		if(qudao_no.equals("2")){
			if(ba_no.substring(0,2).equals("**")){
				twobaodanString="BV";
				afterbaodanString=twobaodanString+ba_no.substring(2,8);
			}else
			if(ba_no.substring(0,1).equals("*")){
				twobaodanString="B";
				afterbaodanString=twobaodanString+ba_no.substring(1,8);
			}
		}else
		if(qudao_no.equals("3")){
			if(ba_no.substring(0,2).equals("**")){
				twobaodanString="AC";
				afterbaodanString=twobaodanString+ba_no.substring(2,8);
			}else
			if(ba_no.substring(0,1).equals("*")){
				twobaodanString="A";
				afterbaodanString=twobaodanString+ba_no.substring(1,8);
			}
		}
		System.out.println("修改后的保单号为："+afterbaodanString);
		Connection conn=DBConnection.getConnection();
		ResultSet rs = null;
		CallableStatement statement=null;		
		try {
			String callExchangeMoneyStr = "{call ODSCIF.SP_IVR_POLICY_INFO(?,?)}"; 
			statement = conn.prepareCall(callExchangeMoneyStr);
			statement.setString(1,cp_no); 
			statement.setString(2,afterbaodanString); 
			statement.execute();
			rs=statement.getResultSet();
			if(rs.next()){
				System.out.println("-------成功拿到数据-----------");
				String bdh=rs.getString(3);
				System.out.println("保单号为--------------------------------------"+bdh);
				//得到本次调用的代理人号
				String dlr=rs.getString(4);
				System.out.println("代理人工号为-------"+dlr);
				String fxzt=rs.getString(5);
				System.out.println("风险状态为：-------"+fxzt);
				//得到缴别
				String jb=rs.getString(6);
				System.out.println("缴别为：-----"+jb);
				//保费
				String amount=rs.getString(7);
				System.out.println("保费为："+amount);
				String nextTime=rs.getString(8);
				System.out.println("下一应缴日为------------"+nextTime);
				String LogicNumbers=rs.getString(9);
				System.out.println("得到的逻辑编号为："+LogicNumbers);
				//进行风险状态代码识别
				String zhuangtai;
				if( fxzt.equals("IF"))
				{
					zhuangtai = "为有效状态";
				}
				else if(fxzt.equals("MA"))
				{
					zhuangtai = "为满期状态";
				}
				else if(fxzt.equals("EX"))
				{
					zhuangtai = "为保险期间届满状态";
				}
				else if(fxzt.equals("LA"))
				{
					zhuangtai = "为失效状态";
				}
				else if(fxzt.equals("TR"))
				{
					zhuangtai = "为效力终止状态";
				}
				else if(fxzt.equals("SU"))
				{
					zhuangtai = "为退保状态";
				}
				else if(fxzt.equals("CF"))
				{
					zhuangtai = "为退保状态";
				}
				else if(fxzt.equals("PU"))
				{
					zhuangtai = "为减额缴清状态";
				}
				else
				{
					zhuangtai = "暂时无法查询";
				}
				
				//进行缴别转换
				String jiaofeileixingString = null;
				if(jb.equals("00")){
					jiaofeileixingString="一次性缴费";
				}
				if(jb.equals("01")){
					jiaofeileixingString="每年";
				}
				if(jb.equals("02")){
					jiaofeileixingString="每半年";
				}
				if(jb.equals("04")){
					jiaofeileixingString="每季度";
				}
				if(jb.equals("12")){
					jiaofeileixingString="每月";
				}
				//第一个存储过程缓存的代理人工号
				String onedlr=cBean.getAgentNO();
				System.out.println("第一个存储过程缓存的代理人工号:"+onedlr);
					//进行判断
				//if(dlr.equals(onedlr)){	
					if(LogicNumbers.equals("1")){
						String yearString=nextTime.substring(0,4);				
						String mothString=nextTime.substring(4,6);
						String dateString =nextTime.substring(6,8);
					System.out.println("Y|保单"+bdh+zhuangtai+"   保费是"+amount+"元"+jiaofeileixingString+"  下次应缴日为"+yearString+"年"+mothString+"月"+dateString+"日");
					out.append("Y|保单"+bdh+zhuangtai+", 保费"+jiaofeileixingString+amount+"元"+", 下次应缴日为"+yearString+"年"+mothString+"月"+dateString+"日"+"   重听   请按零,  返回上层菜单请按星号键,  如需查询其他保单请按  一,  结束请挂机");
					}else if(LogicNumbers.equals("2")){
						System.out.println("Y|保单"+bdh+zhuangtai+"   保费是"+amount+"元"+jiaofeileixingString+"重听   请按零,  返回上层菜单请按星号键,  如需查询其他保单请按  一,  结束请挂机" );
						out.append("Y|保单"+bdh+zhuangtai+", 保费"+jiaofeileixingString+amount+"元"+",   重听   请按零,  返回上层菜单请按星号键,  如需查询其他保单请按  一,  结束请挂机");
					}else if(LogicNumbers.equals("3")){
						System.out.println("Y|保单"+bdh+zhuangtai+"   保费是"+amount+"元"+jiaofeileixingString);
						out.append("Y|保单"+bdh+zhuangtai+","+"   重听   请按零,  返回上层菜单请按星号键,  如需查询其他保单请按  一,  结束请挂机");
					}
					//}else{
				//	out.append("前后代理人工号不符");
				//}								
			}else {
				System.out.println("********！数据库中没有此条记录！********");	
				out.append("N|");				
			}
		} catch (SQLException e) 
			{		
			e.printStackTrace();
			System.out.println("您输入的条件超出范围！请查证后重新输入！");
			}
		
		String returnString=out.toString();
		cBean.setReturnString(returnString);
		cacheMgr.put("ChannelNo:" + channelNo, cBean);
		return  out.toString();
		
	}

}
