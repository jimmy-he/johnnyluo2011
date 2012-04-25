package com.tinet.ccic.wm.commons.dataSourse;


import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.tinet.ccic.wm.commons.util.QuerySelect;
import com.tinet.ccic.wm.commons.util.Select;

/**
 * 基于spring的jdbc类，如在dao中需要使用，请在将SpringJdbcTemplate注入
 * 基于spring的RowMapper实现面向对象
 * 以类似hibernate的Criteria方式查询
 * <p>
 * FileName：SpringJdbcTemplate.java
 * <p>
 * Copyright (c) 2006-2011 T&I Net Communication CO.,LTD. All rights reserved.
 * 
 * @author wanghl
 * @since 1.0
 * @version 1.0
 */
public class SpringJdbcTemplate {
	private JdbcTemplate jdbcTemplate;   
	private Object[] params;
	private  int[] types ;  

	/**
	private SpringJdbcTemplate(){
		BasicDataSource dataSource = new  BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://172.16.203.190:5432/CCIC2");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		setJdbcTemplate(dataSource);
	}**/
	
	

	/**
	 * jdbc sql语句查询方法
	 * @param sql
	 * @return
	 */
	public List jdbcForList(String sql) throws DataAccessException {
		return jdbcTemplate.queryForList(sql);
	}
	
	/***
	 * 查询器
	 * 支持分页,绝对值,模糊,大于，小于,between
	 * @param rowMapper
	 * @param jdbcSelect
	 * @return
	 */
	 public List findby(RowMapper rowMapper,JdbcSelect jdbcSelect) {  
		  		  
		  StringBuffer sqlStr =new  StringBuffer();
		  sqlStr.append(" SELECT * FROM ").append(jdbcSelect.getTable());
		  
		  //添加查询条件
		  if(jdbcSelect!=null){	
			   sqlStr.append(" where 1=1");
			    int num=0;			  
				for(Select select:jdbcSelect.getSelect()){					
					
					//查询条件间添加 and
					sqlStr.append(" and ");
					
					
					spellSQL(sqlStr,select);
					
					num++;
				}
				
				//共多少条
				if(jdbcSelect.getCount()!=null)
					sqlStr.append(" limit ").append(jdbcSelect.getCount());
				
				//
				if(jdbcSelect.getAddSQL()!=null)
				sqlStr.append(jdbcSelect.getAddSQL());
				
				//分页				
				if(jdbcSelect.getLimit()>0){
					sqlStr.append(" limit ").append(jdbcSelect.getLimit()).append(" offset ").append(jdbcSelect.getOffset());
				}
		  }
		  
		  //打印
		  System.out.println("springJDBC---");
		  BasicDataSource dataSource2 = (BasicDataSource)getDataSource();
		  System.out.println("URL---"+dataSource2.getUrl());
		  System.out.println(sqlStr);
		  System.out.println();
		  System.out.println();
		  
		 
		  
		  int size=jdbcSelect.getSelect().size();
		  for(Select select:jdbcSelect.getSelect()){
			  if(select.getSelectType().equals("in")){
				  size = size +(select.getValues().length-1);
			  }else if(select.getSelectType().equals("isNotNull")||select.getSelectType().equals("isNull")){
				  size--;
			  }
		  }
		  System.out.println("size--"+size);
		  params = new Object[size];
		  types = new int[size];  

		  //赋值
		  setValue(jdbcSelect);
		  
		  List items = jdbcTemplate.query(sqlStr.toString(),params,types,rowMapper);  
		  return items;  
	}  
	
	
	  

		/***
		 * 查询共多少条Count
		 * 支持分页,绝对值,模糊,大于，小于,between
		 * @param rowMapper
		 * @param jdbcSelect
		 * @return
		 */
		 public int findbyCount(JdbcSelect jdbcSelect) {  
			 			  
			  StringBuffer sqlStr =new  StringBuffer();
			  sqlStr.append(" SELECT count(*) FROM ").append(jdbcSelect.getTable());
			  
			  //添加查询条件
			  if(jdbcSelect!=null){		
				  sqlStr.append(" where 1=1");
				  
				    int num=0;			  
					for(Select select:jdbcSelect.getSelect()){											
						//查询条件间添加 and
						sqlStr.append(" and ");
					
						spellSQL(sqlStr,select);						
			  			num++;
		 			}						
					//共多少条
					if(jdbcSelect.getCount()!=null)
						sqlStr.append(" limit ").append(jdbcSelect.getCount());
					
					//
					if(jdbcSelect.getAddSQL()!=null)
					sqlStr.append(jdbcSelect.getAddSQL());
			  }
			  
			  //打印
			  System.out.println("springJDBC---");
			  BasicDataSource dataSource2 = (BasicDataSource)getDataSource();
			  System.out.println("URL---"+dataSource2.getUrl());
			  System.out.println(sqlStr);
			  System.out.println();
			  System.out.println();			  
			  
			  int size=jdbcSelect.getSelect().size();
			  for(Select select:jdbcSelect.getSelect()){
				  if(select.getSelectType().equals("in")){
					  size = size +(select.getValues().length-1);
				  }else if(select.getSelectType().equals("isNotNull")||select.getSelectType().equals("isNull")){
					  size--;
				  }
			  }
			  
			  params = new Object[size];
			  types = new int[size];  			  
			  //赋值
			  setValue(jdbcSelect);
		
			  int result = jdbcTemplate.queryForInt(sqlStr.toString(), params,types);
			  return result;  
		}  
	
		 /**
		  * 通过sql查询
		  * 类似于hibernate的find
		  * from student where name like 'wang'
		  * @param sql
		  */
		public List find(String sql,RowMapper rowMapper) throws DataAccessException {
					
			String jdbcSelect = "select * "+sql;
			System.out.println(jdbcSelect);
			Object[] params = new Object[]{};  
			int[] types = new int[]{};  
			List list =jdbcTemplate.query(jdbcSelect,params,types,rowMapper);  				
			return list;		 
		}	 
	 


	/**
	 * 执行insert,update,delete语句
	 * @param sql
	 */
	public void jdbcExecute(String sql) throws DataAccessException {
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 执行insert语句
	 * @param sql
	 */
	public void insert(String sql,Object[] objs,int[] types) throws DataAccessException {
		jdbcTemplate.update(sql, objs, types);		 
	}
	
	/**
	 * 执行通过主键查询实体类
	 * @param sql
	 */
	public Object get(String tableName,int id,RowMapper rowMapper) throws DataAccessException {
		String sql = "select * from "+tableName+" WHERE id = ?";
		Object[] params = new Object[]{id};  
		int[] types = new int[]{Types.INTEGER};  
		List list =jdbcTemplate.query(sql,params,types,rowMapper);  
		
		if(list!=null&&list.size()>0){
			return list.get(0); 
		}else{
			System.out.println("查询无结果:id="+id);
			return null;
		}
		 
	}
	
	
	/**
	 * 执行update语句
	 * @param sql
	 */
	public boolean jdbcExecuteUpdate(String sql) throws DataAccessException {
		int n=jdbcTemplate.update(sql);
		if(n>0){
			return true;
		}
		return false;	
	}

	
	/**
	 * 拼写sql
	 */
	private void spellSQL(StringBuffer sqlStr,Select select){
		//模糊查询 name like '%王%'
		if(select.getSelectType().equals("like")){						
			sqlStr.append(select.getPropertyName()).append(" like  ? ");}	
		
		//绝对值查询 name = '王'
		else if(select.getSelectType().equals("eq")){
			sqlStr.append(select.getPropertyName()).append(" =  ? ");}
		
		//不等于 name != '王'
		else if(select.getSelectType().equals("notEq")){
			sqlStr.append(select.getPropertyName()).append(" !=  ? ");}
		
	    // 大于  create_date > '2011-12-30'
		else if(select.getSelectType().equals("ge")){
			sqlStr.append(select.getPropertyName()).append(" >  ? ");}
		
		// 小于  create_date < '2011-12-30'
		else if(select.getSelectType().equals("le")){
			sqlStr.append(select.getPropertyName()).append(" <  ? ");}		
		
		/***
		 * 在。。之中
		 * name in ('小王','小李','小赵')
		 */
		else if(select.getSelectType().equals("in")){
			sqlStr.append(select.getPropertyName()).append(" in ( ");
			String str ="";
			for(String value:select.getValues()){
				str = str +" ?,";
			}
			str =str.substring(0, str.length()-1);
			sqlStr.append(str).append(") ");
		}		
		
		// 判断变量为空 isNull (name is null or cno ='')
		else if(select.getSelectType().equals("isNull")){
			sqlStr.append("(").append(select.getPropertyName()).append(" is null ")
			      .append(" or ").append(select.getPropertyName()).append(" = '')");
		}
		
		// 判断变量为不为空 isNotNull length(name)>0
		else if(select.getSelectType().equals("isNotNull")){
			sqlStr.append(" length(").append(select.getPropertyName()).append(")>0 ");
		}
	}
	
	
	//赋值	 
	 private void setValue(JdbcSelect jdbcSelect) {
		 //赋值
		  int num=0;
		  if(jdbcSelect!=null)
			 for(Select select:jdbcSelect.getSelect()){
			   if(!select.getSelectType().equals("isNotNull")&&!select.getSelectType().equals("isNull")){ 
					 if(select.getSelectType().equals("eq")||select.getSelectType().equals("notEq")){
						 params[num] = select.getObj();	
					 }else if(select.getSelectType().equals("like")){
						 params[num] = select.getStr();	 
					 }else if(select.getSelectType().equals("ge")){
						 params[num] = select.getGo();	 
					 }else if(select.getSelectType().equals("le")){
						 params[num] = select.getLo();	 
					 }
					
					 //类型赋值
					
						 if(select.getType().equals(DataType.number)){
							 types[num]=Types.INTEGER;
						 }else if(select.getType().equals(DataType.varchar)){
							 types[num]=Types.VARCHAR;
						 }else if(select.getType().equals(DataType.date)){
							 types[num]=Types.DATE;
						 }
			 
				 
				 //in类型
				 if(select.getSelectType().equals("in")){
					 for(String value:select.getValues()){
						 params[num] = value;
					 
						 if(select.getType().equals(DataType.number)){
							 types[num]=Types.INTEGER;
						 }else if(select.getType().equals(DataType.varchar)){
							 types[num]=Types.VARCHAR;
						 }else if(select.getType().equals(DataType.date)){
							 types[num]=Types.DATE;
						 }
						 num++;
					 }	 
				 }
				 num++;
			   }	 
				 //System.out.println("para---"+select.getObj()+"---go:"+select.getGo()+"--lo:"+select.getLo()+"---str:"+select.getStr());
		     }		
	 }
	
	 public void setJdbcTemplate(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	 }

	 public DataSource getDataSource() {
		return jdbcTemplate.getDataSource();
	 }
}
