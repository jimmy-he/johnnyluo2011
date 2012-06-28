/**
 * 
 */
package com.crm.avivacofco.In.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.crm.avivacofco.In.BeanFactory.CacheBeanFactory;
import com.crm.avivacofco.In.Cache.CacheMgr;
import com.crm.avivacofco.In.CacheBean.AbstractCacheBean;
import com.crm.avivacofco.In.Imple.IvrWork;

/**
 *@author 罗尧  Email:j2ee.xiao@gmail.com
 *@version V1.0 2010-10-24   下午07:33:47
 */
public class Inservlet extends HttpServlet {
	static Logger logger = Logger.getLogger(Inservlet.class.getName());
	
	/**
	 * Constructor of the object.
	 */
	public Inservlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/* 统一的入口点
	 * <p>1.0.0.0 罗尧创建 2010-10-26</p>
	 * @see com.crm.avivacofco.In.Servlet
	 */
	public  void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("GBK");		
		response.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();

		//ID号
		String id_NO=request.getParameter("ID_NO");
		//String id_NO=new String(request.getParameter("ID_NO").getBytes("iso-8859-1"),"utf-8");
		//id_NO = java.net.URLDecoder.decode(id_NO,"UTF-8"); 
		logger.info("传入的ID号："+id_NO);
		//通道号
		String channelNo=request.getParameter("ChannelNo");
		logger.info("传入的通道号："+channelNo);
		//命令号
		String commandNo=request.getParameter("CommandNo");
		logger.info("传入的命令号："+commandNo);
		CacheMgr cacheMgr=CacheMgr.getInstance();
		logger.debug("在servlet中拿到缓存对象ID"+cacheMgr);
		//System.out.println("在servlet中拿到缓存对象ID"+cacheMgr+"-------------------------------");
		AbstractCacheBean  cBean = null;		
		List channelNoList = (List)cacheMgr.get("channelNoList");
		if(channelNoList != null){
			System.out.println("--------if----通道号这个List里面不为空-----");
			Iterator iterator = channelNoList.iterator();
			System.out.println("channelNoList里面的通道号为："+iterator.next());
			//if(channelNoList.indexOf(channelNo)!=-1)
			//while(iterator.hasNext()){
				//String tempChannelNoString = (String)iterator.next();
				//System.out.println("channelNoList里面的通道号为："+tempChannelNoString);
				//if(iterator.next().equals(channelNo)){
				if(channelNoList.indexOf(channelNo)!=-1){
					System.out.println("------if-----传入的通道号和list里面的通道号有匹配的------------");
					if("000001".equals(commandNo)||"000005".equals(commandNo)){
						cacheMgr.remove("ChannelNo:" + channelNo);
						System.out.println("---------根据通道号清空缓存！--------");
						System.out.println("清空之后查看缓存对象："+cacheMgr.get("ChannelNo:"+channelNo));
						cBean = CacheBeanFactory.getCacheBeanInstance(channelNo,commandNo,id_NO);
						cacheMgr.put("ChannelNo:" + channelNo,cBean);
						System.out.println("put完当前新的缓存之后查看缓存对象："+cacheMgr.get("ChannelNo:"+channelNo));
					}else{
						cBean = (AbstractCacheBean)cacheMgr.get("ChannelNo:"+channelNo);
//						for (Object key : cacheMgr.map.keySet()) 
//						{	        
//							System.out.print("==> key:" + key + " | val:" + cacheMgr.map.get(key));	  
//						} 
						cBean.setID_NO(id_NO);
						cacheMgr.put("ChannelNo:"+channelNo,cBean);
					}					
				}else{
					System.out.println("----else-----传入的channelNo不能匹配list里面的通道号-------");
//			          channelNoList = new ArrayList();
//			          channelNoList.add(channelNo);
//			          CacheMgr.put("channelNoList", channelNoList);
//			          cBean = CacheBeanFactory.getCacheBeanInstance(channelNo, commandNo, id_NO);
//			          CacheMgr.put("ChannelNo:" + channelNo, cBean);
					 AbstractCacheBean absBean = (AbstractCacheBean) cacheMgr.get("ChannelNo:" + channelNo);
					 if (null == absBean) {                        
						 System.out.println(absBean);                        
						 //channelNoList = new ArrayList();                        
						 channelNoList.add(channelNo);                        
						 cacheMgr.put("channelNoList", channelNoList);                       
						 cBean = CacheBeanFactory.getCacheBeanInstance(channelNo,commandNo, id_NO);                       
						 cacheMgr.put("ChannelNo:" + channelNo, cBean);                    
						 } else {                        
							 cBean = absBean;                   
							 } 
				}
			//}
		}else{
			System.out.println("------else-----通道号这个list里面为空--------");
			channelNoList = new ArrayList();
			channelNoList.add(channelNo);
			Iterator iterator = channelNoList.iterator();
			cacheMgr.put("channelNoList", channelNoList);			
			cBean = CacheBeanFactory.getCacheBeanInstance(channelNo,commandNo,id_NO);
			cacheMgr.put("ChannelNo:" + channelNo,cBean);		
			System.out.println("put完当前新的缓存之后查看缓存对象："+cacheMgr.get("ChannelNo:"+channelNo));
		}		
		System.out.println("在servlet中获得commandNo参数"+commandNo);		
		if(commandNo.equals("000001")){
			cBean.sfYanZheng();  //身份验证查询
			//System.out.println("这个地方走了吗？-------111身份验证查询");
			logger.info("--inservlet-----111身份验证查询");
		}if(commandNo.equals("000002")){
			cBean.bdYanZheng();   //保单验证查询
			//System.out.println("这个地方走了吗？-------222保单验证查询");
			logger.info("---inservlet----222保单验证查询");			
		}if(commandNo.equals("000003")){
			cBean.liPei();         //理赔查询
			//System.out.println("这个地方走了吗？-------333理赔查询");
			logger.info("---inservlet----333理赔查询");
		}if(commandNo.equals("000004")){
			//System.out.println("这个地方走了吗？-------444保单信息查询");
			logger.info("---inservlet----444保单信息查询");
			cBean.bdXinXi();       //保单信息查询
		}if(commandNo.equals("000005")){
			//System.out.println("这个地方走了吗？-------555工号验证");
			logger.info("---inservlet----555工号验证");
			cBean.ZHjianChe();     //工号验证
		}if(commandNo.equals("000006")){
			//System.out.println("这个地方走了吗？-------666");
			logger.info("----inservlet-------代理人理赔查询");
			cBean.PolicyStatus();      //SP保单状态查询返回8个值
		}if(commandNo.equals("000008")){
			//System.out.println("这个地方走了吗？-------888保单资料及续期收费查询");
			logger.info("---inservlet----888保单资料及续期收费查询");
			cBean.bdzlxqsf();   //保单资料及续期收费查询
		}
		
		
		
		
		if(commandNo.equals("111111")){
			logger.info("-------万能险当月结算利率查询---inservlet----111111");
			cBean.AllPowerfulType();
		}
		if(commandNo.equals("222221")){
			logger.info("-------精彩连连利率查询---inservlet----222221");
			cBean.WonderfulRepeatedly();
		}
		if(commandNo.equals("222222")){
			logger.info("-------精彩连连利率查询B---inservlet----222222");
			cBean.WonderfulRepeatedlyB();
		}
		if(commandNo.equals("333333")){
			//System.out.println("这个地方走了吗？-------月红利存储利率查询333333");
			logger.info("-------月红利存储利率查询---inservlet----333333");
			cBean.ExistJing();     //生存金累积利率查询
		}
		if(commandNo.equals("444444")){
			//System.out.println("这个地方走了吗？-------借款利率查询444444");
			logger.info("-------万能险借款利率查询----inservlet-----444444");
			cBean.BorrowFunds();     //万能险借款利率查询
		}
		if(commandNo.equals("555555")){
			logger.info("-------留存满一年生存金适用利率查询---inservlet----555555");
			cBean.SubsistOverOneYear();
		}
		
		
		System.out.println("servlet完毕，开始进入实现类！-------------------实现类执行完毕，开始输出----------");
		System.out.println("==============================================================================");
		out.append(cBean.getReturnString());

			
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
