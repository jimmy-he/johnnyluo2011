/**
 ***********************************************
 * @Title     ArrayList.java					   
 * @Pageage   com.test.Standard			
 *Copyright:Copyright(c) 2013
 *Company:北京天润融通科技有限公司
 *	   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @date       2013-3-23 上午9:25:58	
 * @version V1.0   
 ***********************************************
 */
package com.test.Standard;

/**
 ***********************************************
 * @ClassName:ArrayList					   		   
 * @author    罗尧   Email:j2ee.xiao@gmail.com 
 * @date      2013-3-23 上午9:25:58	  
 ***********************************************
 */
public class ArrayList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//插入排序法
			int a[]={1,19,2,30,22,4,7,5,3,8,11,29};
			   int temp=0;  
			    for(int i=1;i<a.length;i++){  
			       int j=i-1;  
			       temp=a[i];  
			       for(;j>=0&&temp<a[j];j--){  
				       a[j+1]=a[j];                       //将大于temp的值整体后移一个单位  
				       }  
				       a[j+1]=temp;  
			    }  
			    for(int i=0;i<a.length;i++)  
			       System.out.println(a[i]);
			    
			    
		    
		//简单选择排序
		    int b[]={1,19,2,30,22,4,7,5,3,8,11,29};
		    int position=0;  
	        for(int i=0;i<b.length;i++){  
	              
	            int j=i+1;  
	            position=i;  
	            int temp1=a[i];  
	            for(;j<b.length;j++){  
	            if(b[j]<temp1){  
	                temp1=b[j];  
	                position=j;  
	            }  
	            }  
	            b[position]=b[i];  
	            b[i]=temp1;  
	        }  
	        for(int i=0;i<b.length;i++)  
	            System.out.println(b[i]); 
	        
	        
	     //冒泡排序
	        int c[]={1,19,2,30,22,4,7,5,3,8,11,29};
	        int temp2=0;  
	        for(int i=0;i<c.length-1;i++){  
	            for(int j=0;j<c.length-1-i;j++){  
	            if(c[j]>c[j+1]){  
	                temp2=c[j];  
	                c[j]=c[j+1];  
	                c[j+1]=temp2;  
	            }  
	            }  
	        }  
	        for(int i=0;i<c.length;i++)  
	        System.out.println(c[i]);    
	        
	    
	     //二分查找法
	}
}
