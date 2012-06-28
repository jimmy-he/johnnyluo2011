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

import com.ibm.db2.jcc.a.e;


/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-25   下午07:54:31
 */
public class NNewTest {

	public static void main(String[] args) {
		 String groupinput=
				"<?xml version=\"1.0\" encoding=\"GBK\"?>"+
				"<ACLife>"+
					"<PolicyInfo id=\"1\">"+
						"<AGMT_ID1>111111</AGMT_ID1>"+
						"<BaseInfo>"+
							"<AGMT_ID>001111110</AGMT_ID>"+
							"<CHNL_ROLEPLY_ID>D#3#30200004</CHNL_ROLEPLY_ID>"+
						"</BaseInfo>"+
					"</PolicyInfo>"+
					"<PolicyInfo id=\"2\">"+
						"<BaseInfo>"+
						"<AGMT_ID>002222220</AGMT_ID>"+
						"<CHNL_ROLEPLY_ID>D#3#30200004</CHNL_ROLEPLY_ID>"+
						"</BaseInfo>"+
					"</PolicyInfo>"+		
					"<PolicyInfo id=\"3\">"+
					"<BaseInfo>"+
					"<AGMT_ID>003333330</AGMT_ID>"+
					"<CHNL_ROLEPLY_ID>D#3#30200004</CHNL_ROLEPLY_ID>"+
					"</BaseInfo>"+
				"</PolicyInfo>"+		
				"</ACLife>";	
		 SAXReader saxReader = new SAXReader(false);		
		 try {
			Document doc = saxReader.read(new StringReader(groupinput));
			
			Map<Object, Object> map = new HashMap<Object, Object>();
			Map<Object, Object> map1 = new HashMap<Object, Object>();
			List<Element> list = doc.selectNodes("/ACLife/PolicyInfo");
			//System.out.println("-----list------"+list);
			List<Element> list1 = doc.selectNodes("/ACLife/PolicyInfo/BaseInfo");
			//System.out.println("-----list1------"+list1);
			String  listid = null;
			int num=0;
			for(Element n : list)
			{
				System.out.println("111");			
				listid=n.attribute("id").getText();				
				Element nn = list1.get(num);
				num++;
				//System.out.println("map1："+listid+"-"+nn);
				map1.put(listid, nn);
				//System.out.println("map："+listid+"-"+map1);
				map.put(listid, map1);			
			}
				System.out.println("map的长度为："+map.size());
				System.out.println("map1的长度为："+map1.size());
				Element element = null;
				String AGMT_ID = null;
				String CHNL_ROLEPLY_ID=null;
				String xxxS="002222220";
			for(int i=1;i<=map.size();i++){
				Element x = (Element) map1.get(String.valueOf(i));
				//System.out.println("x="+x);
				
				for(Iterator<Element> j = x.elements().iterator();j.hasNext();)
				{
					element = (Element)j.next();
					//System.out.println(element);
					if ("AGMT_ID".equals(element.getName())) {
						AGMT_ID=element.getText();
					}
					if("CHNL_ROLEPLY_ID".equals(element.getName())){
						CHNL_ROLEPLY_ID=element.getText();
					}
				}
				
				System.out.println("保单号码为："+AGMT_ID.replaceAll(".*(.{6})", "$1")+"数据为："+CHNL_ROLEPLY_ID);
				//System.out.println("----------------------");
				
				System.out.println("xxxS-----:"+xxxS);
				System.out.println("AGMT_ID:"+AGMT_ID);
				if(xxxS.equals(AGMT_ID)){
					System.out.println("1:"+xxxS.equals(AGMT_ID));
					System.out.println("1----------------真的！----------------");
				}else {
					System.out.println("2:"+xxxS.equals(AGMT_ID));
					System.out.println("2----------------jia的！----------------");
				}
				
			}
			
			
			/*//拿取缓存值
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
					
					if ("BaseInfo".equals(element.getName())) {
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
			}*/
			
			
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
