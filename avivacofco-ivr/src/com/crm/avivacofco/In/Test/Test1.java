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

import com.crm.avivacofco.In.Imple.GrpBean;

/**
 *@author 罗尧 Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-25 下午07:54:31
 */
public class Test1 {

	public static void main(String[] args) {
		String groupinput = "<?xml version=\"1.0\" encoding=\"GBK\"?>"
				+ "<ACLife>" +
				"<GrpInsurance id=\"1\">"
					+ "<GrpcontNo>000001</GrpcontNo>"
					+ "<RiskSaleCompany>北京分公司本部</RiskSaleCompany>"
					+ "<Insured id=\"1\">"  
						+"<InsuredName>张三</InsuredName>"
						+"<GrpInsuranceState>自然终止</GrpInsuranceState>"
						+ "<GrpRiskInfo id=\"1\">"
							+ "<RiskName>中英一年期团体定期寿险</RiskName>"
							+ "<RiskAmnt>100001</RiskAmnt>"
							+ "<RiskCvalidate>2010-04-14</RiskCvalidate>"
							+ "<RiskEnddate>2011-04-05</RiskEnddate>" 
							+ "</GrpRiskInfo>"
					+ "<GrpRiskInfo id=\"2\">"
						+ "<RiskName>中英团体意外伤害保险</RiskName>"
						+ "<RiskAmnt>100002</RiskAmnt>"
						+ "<RiskCvalidate>2010-04-14</RiskCvalidate>"
						+ "<RiskEnddate>2011-04-06</RiskEnddate>" 
						+ "</GrpRiskInfo>"
					+ "<GrpRiskInfo id=\"3\">"
						+ "<RiskName>中英附加意外伤害团体医疗保险</RiskName>"
						+ "<RiskAmnt>100003</RiskAmnt>"
						+ "<RiskCvalidate>2010-04-14</RiskCvalidate>"
						+ "<RiskEnddate>2011-04-07</RiskEnddate>" 
					+ "</GrpRiskInfo>"
					+ "</Insured>" + 
					"</GrpInsurance>" + 
					"<GrpInsurance id=\"2\">"
						+ "<GrpcontNo>000002</GrpcontNo>"
						+ "<RiskSaleCompany>北京分公司本部</RiskSaleCompany>"
						+ "<Insured id=\"1\">" + 
							"<InsuredName>李四</InsuredName>"
						+"<GrpInsuranceState>未承保</GrpInsuranceState>"
							+ "<GrpRiskInfo id=\"1\">"
								+ "<RiskName>中英一年期团体定期寿险</RiskName>"
								+ "<RiskAmnt>100004</RiskAmnt>"
								+ "<RiskCvalidate>2010-04-14</RiskCvalidate>"
								+ "<RiskEnddate>2011-04-08</RiskEnddate>" 
								+ "</GrpRiskInfo>"						
							+ "<GrpRiskInfo id=\"2\">"
								+ "<RiskName>中英团体重大疾病保险</RiskName>"
								+ "<RiskAmnt>100005</RiskAmnt>"
								+ "<RiskCvalidate>2010-04-14</RiskCvalidate>"
								+ "<RiskEnddate>2011-04-09</RiskEnddate>" 
							+"</GrpRiskInfo>"
				+ "</Insured>" +
				"</GrpInsurance>" + 
				"</ACLife>";
		SAXReader saxReader = new SAXReader(false);

		try {
			Document doc = saxReader.read(new StringReader(groupinput));

			Map<Object, Object> map = new HashMap<Object, Object>();
			List<Element> list = doc.selectNodes("/ACLife/GrpInsurance");		
			for (Iterator it = list.iterator(); it.hasNext();) { // 1
				Element provinceEle = (Element) it.next();
				String proId = provinceEle.attributeValue("id");
				
				Element element = null;
				String GrpcontNo = "";
				String RiskSaleCompany="";
				for (Iterator<Element> j = provinceEle.elements().iterator(); j.hasNext();) {
					element = (Element) j.next();
					if ("GrpcontNo".equals(element.getName())) {
						GrpcontNo = element.getText();
						System.out.println("GrpcontNo为最外层："+"团单号码为："+GrpcontNo);
					}
					if ("RiskSaleCompany".equals(element.getName())) {
						RiskSaleCompany = element.getText();
						System.out.println("RiskSaleCompany为最外层："+"团单号码为："+RiskSaleCompany);
					}
				}
				List InsuredList = provinceEle.elements("Insured");
				Map<Object, Object> map1 = new HashMap<Object, Object>();
				for (Iterator InsuredIt = InsuredList.iterator(); InsuredIt
						.hasNext();) {// 2
					Element InsureEle = (Element) InsuredIt.next();
					String InsureId = InsureEle.attributeValue("id");
					
					String InsuredName = "";
					String GrpInsuranceState = "";
					for (Iterator<Element> j = InsureEle.elements().iterator(); j
							.hasNext();) {
						element = (Element) j.next();
						if ("InsuredName".equals(element.getName())) {
							InsuredName=element.getText();
						} else if ("GrpInsuranceState".equals(element.getName())) {
							GrpInsuranceState = element.getText();
						}
					}
					List grpRiskInfoList = InsureEle.elements("GrpRiskInfo");
					Map<Object, Object> map2 = new HashMap<Object, Object>();
					for (Iterator grpRiskInfo = grpRiskInfoList.iterator(); grpRiskInfo
							.hasNext();) { // 3
						Element info = (Element) grpRiskInfo.next();
						String id = info.attributeValue("id");
						//System.out.println("GrpInsurance = " + proId+ "    Insured = " + InsureId+ "  GrpRiskInfo =" + id);
						GrpBean bean = new GrpBean();
						for (Iterator<Element> j = info.elements().iterator(); j
								.hasNext();)//
						{
							element = (Element) j.next();
							// System.out.println(element);
							if ("RiskName".equals(element.getName())) {
								bean.setRiskName(element.getText());
							} else if ("RiskAmnt".equals(element.getName())) {
								bean.setRiskAmnt(element.getText());
							} else if ("RiskCvalidate"
									.equals(element.getName())) {
								bean.setRiskCvalidate(element.getText());
							} else if ("RiskEnddate".equals(element.getName())) {
								bean.setRiskEnddate(element.getText());
							}
						}
						bean.setGrpcontNo(GrpcontNo);
						bean.setRiskSaleCompany(RiskSaleCompany);
						bean.setInsuredName(InsuredName);
						bean.setGrpInsuranceState(GrpInsuranceState);
						map2.put(id, bean);
					}
					map1.put(InsureId, map2);
				}
				map.put(proId, map1);
			}
			System.out.println("map的长度为：" + map.size());
			
			
			//取数据
			String baodan="000001";   //已知的保单号，取拿取此保单号的基本信息？？？？？
			String xString=null;
			GrpBean grpbean = null;
			Iterator it = map.entrySet().iterator();
			int i=0;
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				Object key = entry.getKey();
				Map value = (Map) entry.getValue();
				Iterator it2 = value.entrySet().iterator();
				while (it2.hasNext()) {
					Map.Entry entry2 = (Map.Entry) it2.next();
					Object key2 = entry2.getKey();
					Map value2 = (Map) entry2.getValue();
					Iterator it3 = value2.entrySet().iterator();
					while (it3.hasNext()) {
						i++;
						Map.Entry entry3 = (Map.Entry) it3.next();
						grpbean = (GrpBean) entry3.getValue();
						 xString=grpbean.getGrpcontNo();	
					}
					
				}
				System.out.println("团体保单号"+grpbean.getGrpcontNo()+"为"+grpbean.getGrpInsuranceState()+","+"被保险人"+grpbean.getInsuredName()+","+"投保险种"+i+grpbean.getRiskName());
			}
			
			//System.out.println(xString);
		} catch (DocumentException e) {
			System.out.println("返回的报文出现异常！");
			e.printStackTrace();
		}
		
	}
}
