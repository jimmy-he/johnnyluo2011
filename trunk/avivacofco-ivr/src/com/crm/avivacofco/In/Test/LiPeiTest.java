/**
 * 
 */
package com.crm.avivacofco.In.Test;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-11-19   下午04:04:24
 */
public class LiPeiTest {

	public static void main(String[] args){
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
						+ "<RiskAmnt>100001*</RiskAmnt>"
						+ "<RiskCvalidate>2010-11-14</RiskCvalidate>"
						+ "<RiskEnddate>2011-04-05</RiskEnddate>" 
						+ "</GrpRiskInfo>"
					+ "<GrpRiskInfo id=\"2\">"
						+ "<RiskName>中英团体意外伤害保险</RiskName>"
						+ "<RiskAmnt>100002**</RiskAmnt>"
						+ "<RiskCvalidate>2010-8-14</RiskCvalidate>"
						+ "<RiskEnddate>2011-04-06</RiskEnddate>" 
					+ "</GrpRiskInfo>"
					+ "<GrpRiskInfo id=\"3\">"
						+ "<RiskName>中英附加意外伤害团体医疗保险</RiskName>"
						+ "<RiskAmnt>100003***</RiskAmnt>"
						+ "<RiskCvalidate>2010-9-14</RiskCvalidate>"
						+ "<RiskEnddate>2011-04-07</RiskEnddate>" 
					+ "</GrpRiskInfo>"
					+ "<GrpRiskInfo id=\"4\">"
						+ "<RiskName>中英附加意外伤害团体医疗保险test</RiskName>"
						+ "<RiskAmnt>100004****</RiskAmnt>"
						+ "<RiskCvalidate>2012-6-14</RiskCvalidate>"
						+ "<RiskEnddate>2011-04-07</RiskEnddate>" 
				+ "</GrpRiskInfo>"
				+ "</Insured>" + 
				"</GrpInsurance>" + 
			"</ACLife>";
		
		
		SAXReader saxReader = new SAXReader(false);
		long riskCvaliDate=0;
		long halfYearTime=0;	
		
		 List list=new ArrayList();
		 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 Date date=new Date();
		 long nowtime=date.getTime();
		 
		 String nowString=df.format(date);
	     System.out.println("当前日期为："+nowString);           
		 String halfYear=df.format(new Date(date.getTime()- (long)182 * 24 * 60 * 60 * 1000));
		 System.out.println("半年范围的日期：" + halfYear);
		 System.out.println(nowtime+"---当前日期的毫秒值");
		 
		 try {
			 	date=df.parse(halfYear);			
				halfYearTime=date.getTime();
				System.out.println(halfYearTime+"---半年范围的日期的毫秒值：");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		try {
			StringBuffer strbuf = new StringBuffer();
			String GrpcontNo="000001";
			Document doc = saxReader.read(new StringReader(groupinput));
			Element root = doc.getRootElement();			
			Node grpInsuranceNode = root.selectSingleNode("GrpInsurance[ends-with(GrpcontNo, '" + GrpcontNo + "')]");			
			if(null != grpInsuranceNode){
				System.out.println(grpInsuranceNode.valueOf("RiskSaleCompany"));												
				String riskSaleCompany = grpInsuranceNode.valueOf("RiskSaleCompany");				
				String grpcontNo=grpInsuranceNode.valueOf("GrpcontNo");
				// 获取多个Insured
                List<? extends Node> insuredNodes = grpInsuranceNode.selectNodes("Insured");
                // 判断是否存在Insured
                if (null != insuredNodes&& !insuredNodes.isEmpty()) {                               
                    for (Node insuredNode : insuredNodes) {                    	
                        String insuredName = insuredNode.valueOf("InsuredName");
                        String grpInsuranceState = insuredNode.valueOf("GrpInsuranceState");                     
                    	List<? extends Node> grpRiskInfoNodes = insuredNode.selectNodes("GrpRiskInfo");
                        if (null != grpRiskInfoNodes) {
                           // System.out.println("==> GrpRiskInfo size: " + grpRiskInfoNodes.size());
                            for (Node grpRiskInfoNode : grpRiskInfoNodes) {
                                String riskName = grpRiskInfoNode.valueOf("RiskName");
                                String riskAmnt = grpRiskInfoNode.valueOf("RiskAmnt");
                                String riskCvalidate = grpRiskInfoNode.valueOf("RiskCvalidate");
                                try{
								 	date=df.parse(riskCvalidate);			
								 	riskCvaliDate=date.getTime();
									//System.out.println("受理日期为：的毫秒值："+riskCvaliDate);
								}catch (ParseException e) {
									e.printStackTrace();
								}
                                String riskEnddate = grpRiskInfoNode.valueOf("RiskEnddate");
                                if(riskCvaliDate>=halfYearTime&&riskCvaliDate<=nowtime){
                                	String xString="保单号码为："+grpcontNo+","+"保险人为："+insuredName+","+"保险类型为："+grpInsuranceState+","+"保单类型为："+riskName+","+"保险金额为："+riskAmnt+","+"开始时间："+riskCvalidate+","+"结束时间："+riskEnddate+","+"销售单位："+ riskSaleCompany;
                                	TestB b =new TestB();
                                	b.s=xString;
                                	b.date=riskCvaliDate;
                                	//long time=halfYearTime;
                                	list.add(b);
                                	
                                	//System.out.println(xString);
                                }
                               	
                        }
                            System.out.println("list的长度为："+list.size()); 
                            
                            Comparator comp = new Mycomparator();
                            Collections.sort(list,comp);                                                 
                            for(int i=0;i<list.size();i++){
                            	TestB b =(TestB)list.get(i);
                            	System.out.println(b.date);
                            	System.out.println("N|"+b.s);
                            	if(i==1){
                            		break;
                            	}
                            }
                            
                    }
                }
                    }
               

            }
			else{
            	System.out.println("您输入的保单号码不存在！");
            }

			
		}catch (Exception e) {
			e.getStackTrace();
			System.out.println("出错啦！");
		}
		
	}
}
