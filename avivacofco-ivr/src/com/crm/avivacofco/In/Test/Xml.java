/**
 * 
 */
package com.crm.avivacofco.In.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-11-19   下午05:50:07
 */
public class Xml {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		String[] x={"2008-05-12","2009-05-12","2010-05-12","2007-05-12","2006-05-12","2005-05-12"};
		String x1="2005-05-12";
		String x2="2006-05-12";
		String x3="2007-05-12";
		String x4="2008-05-12";
		String x5="2009-05-12";
		String x6="2010-05-12";
		
		//String i="2010-11-25";    //半年内的日期   减去182.5天
//		Calendar calendar=Calendar.getInstance();   
//		 calendar.setTime(new Date());
//		  System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期 
//		  calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);//让日期加1   
//		  System.out.println(calendar.get(Calendar.DATE));//加1之后的日期Top

		Date d=new Date();
		long ll=d.getTime();
		long opq = 0;
		System.out.println(ll);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); 
        String nowString=df.format(d);
        String halfYear=df.format(new Date(d.getTime()- (long)182 * 24 * 60 * 60 * 1000));
        System.out.println("今天的日期："+nowString);   
        System.out.println("本年内的日期：" + halfYear);
        String xx="2010-11-20";		
		try {
			 d=df.parse(halfYear);			
			opq=d.getTime();
			System.out.println(opq);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        if(opq>ll){
        	System.out.println("半年内的这个日期点比当前时间点大");
        }else{
        	System.out.println("当前时间点比半年内的这个日期点大");
        }
	}

}
