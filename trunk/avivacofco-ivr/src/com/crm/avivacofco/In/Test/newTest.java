/**
 * 
 */
package com.crm.avivacofco.In.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-25   下午07:54:31
 */
public class newTest {

	public static void main(String[] args) {
		 String groupinput=
				"<?xml version=\"1.0\" encoding=\"GBK\"?>"+
				"<ACLife>"+
					"<ACLifeBase>"+
						"<TransNo></TransNo>"+
						"<TransType id=\"1\">测试中文222</TransType>"+
						"<TransType id=\"2\">测试1111</TransType>"+
						"<TransType>测试中文</TransType>"+
						"<TransSource>003</TransSource>"+
						"<TransTarget>01</TransTarget>"+
					"</ACLifeBase>"+					
					"<ACLifeRequest>"+
							"<a>11111</a>"+
							"<b>22222</b>"+
							"<GrpRiskInfo id=\"1\">"+
									"<RiskName>中英建筑工程团体意外伤害保险</RiskName>"+   
									"<RiskAmnt>100000</RiskAmnt>"+ 
									"<RiskCvalidate>2010-04-14</RiskCvalidate>"+ 
									"<RiskEnddate>2011-04-09</RiskEnddate>"+
							 "</GrpRiskInfo>"+
							 "<GrpRiskInfo id=\"2\">"+
									"<RiskName>中英附加建筑工程意外伤害团体医疗保险</RiskName>"+   
									"<RiskAmnt>10000</RiskAmnt>"+   
									"<RiskCvalidate>2010-04-14</RiskCvalidate> "+ 
									"<RiskEnddate>2011-04-09</RiskEnddate>"+  
							 "</GrpRiskInfo>"+
					"</ACLifeRequest>"+
				"</ACLife>";	
		
		 SAXReader saxReader = new SAXReader(false);		
		 try {
			Document doc = saxReader.read(new StringReader(groupinput));
			
			Map<Object, Object> map = new HashMap<Object, Object>();
			List<Element> list = doc.selectNodes("/ACLife/ACLifeRequest/GrpRiskInfo");
			String  listid = null;
			for(Element n : list)
			{
				listid=n.attribute("id").getText();
				map.put(listid, n);			
			}
			
			//拿取缓存值
			for(int i=1;i<=map.size();i++){
				Element x= (Element) map.get(String.valueOf(i));
				Element element = null;
				String RiskName="";
				String RiskAmnt="";
				String RiskCvalidate="";
				String RiskEnddate="";
				String xString="100000";
				for(Iterator<Element> j = x.elements().iterator();j.hasNext();)
				{
					 element = (Element)j.next();
					
					if ("RiskName".equals(element.getName())) {
						RiskName=element.getText();
					}
					if ("RiskAmnt".equals(element.getName())) {
						RiskAmnt=element.getText();
						}
					if ("RiskCvalidate".equals(element.getName())) {
						RiskCvalidate=element.getText();
						}
					if ("RiskEnddate".equals(element.getName())) {
						RiskEnddate=element.getText();
						}
					
				}
				if(xString.equals(RiskAmnt)){
					System.out.println("真的！");
				}
				System.out.println("投保险种"+i+"保单号为"+RiskName+"金额为"+RiskAmnt+"保单开始日期"+RiskCvalidate+"保单结束日期"+RiskEnddate);
			}
			
			
			//syso("保险类型为"+riskname+“保险金额为”+ RiskAmnt);
			
			
			
//				cBean.setMap();
//				-------------------
//				cBean.getMap();
				
				
/*循环输出		for(int i=0;i<riskInfos.length;i++){
				  String riskname=risknames[i];
				  String RiskAmnt=RiskAmnt[i];
				syso("保险类型为"+riskname+“保险金额为”+ RiskAmnt);
				}*/
//				cBean.setListId(listid);
//				cBean.setNameValue(namevalue);
//				cacheMgr.put("ChannelNo:" + channelNo, cBean);
			
		} catch (DocumentException e) {
			System.out.println("返回的报文出现异常！");
			e.printStackTrace();
		}
	 }
}
