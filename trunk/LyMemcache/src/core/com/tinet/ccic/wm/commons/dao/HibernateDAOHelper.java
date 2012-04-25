package com.tinet.ccic.wm.commons.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.tinet.ccic.wm.commons.Constants;

/**
* Hibernate框架SQL语句查询相关类
*<p>
* 文件名： HibernateDAOHelper.java
*<p>
* Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
* @author 周营昭
* @since 1.0
* @version 1.0
*/
public class HibernateDAOHelper {
    
    private static Log logger = LogFactory.getLog(HibernateDAOHelper.class);
    
    private Map<String, String> aliasesMap;
    
    protected final static int DEFAULT_BATCH_SIZE = Constants.DEFAULT_BATCH_SIZE;
    
    protected final static String ALIAS_PREFIX = "alias_";
    
    public final static String COUNT_ALIAS = "c";
    
    private SessionFactory sessionFactory;
    
    private HibernateDAOHelper() {
        
    }
    
    public HibernateDAOHelper(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Assemble the SQL which carry out the sum of results.
     * 
     * @param SQL
     * @param the summed column's name
     * @param alias
     * @return assembled SQL
     */
    public static String getCountSql(String sql, String paginationKey, String alias) {
        String trueAlias = (alias == null) ? COUNT_ALIAS : alias;
        if (StringUtils.isEmpty(sql)) {
            return null;
        } else {
            sql = StringUtils.lowerCase(sql);
        }
        return (new StringBuilder()).append("select count(").append(paginationKey).append(") as ").append(trueAlias).append(" ").append(sql.substring(sql.indexOf("from"))).toString();
    }

    /**
     * Check whether write operations are allowed on the given Session.
     * <p>Default implementation throws an InvalidDataAccessApiUsageException in
     * case of <code>FlushMode.NEVER/MANUAL</code>.
     * 
     * @param the instance of template
     * @param current hibernate session
     * @throws InvalidDataAccessApiUsageException if write operations are not allowed
     * @see #setCheckWriteOperations
     * @see #getFlushMode()
     * @see #FLUSH_EAGER
     * @see org.hibernate.Session#getFlushMode()
     * @see org.hibernate.FlushMode#NEVER
     * @see org.hibernate.FlushMode#MANUAL
     */
    public static void checkWriteOperationAllowed(HibernateTemplate template, Session session) throws InvalidDataAccessApiUsageException {
        if (template.isCheckWriteOperations() && template.getFlushMode() != HibernateTemplate.FLUSH_EAGER &&
                session.getFlushMode().lessThan(FlushMode.COMMIT)) {
            throw new InvalidDataAccessApiUsageException(
                    "Write operations are not allowed in read-only mode (FlushMode.NEVER/MANUAL): " +
                    "Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
        }
    }
    
    /**
     * 根据name生成获得别名（多个别名以|分割）并以当前的aliasKey保存在HashMap中 因为该DAO为单例，所以需要一个进入query时唯一的aliasKey作为区分
     * 
     * @param criteria
     * @param name
     * @param aliasKey
     * @return
     */
    private String generateAlias(Criteria criteria, String name, String aliasKey) {
        int aliasPos = StringUtils.indexOf(name, ":");
        if (aliasPos != -1) {
        	int joinType = name.charAt(aliasPos - 1) == '[' ? (name.charAt(aliasPos + 1) == ']' ? CriteriaSpecification.FULL_JOIN : CriteriaSpecification.LEFT_JOIN) : CriteriaSpecification.INNER_JOIN;
            String trueName = (joinType == CriteriaSpecification.FULL_JOIN) ? StringUtils.substring(name, aliasPos + 2) : StringUtils.substring(name, aliasPos + 1);
            if (StringUtils.indexOf(trueName, ".") == -1) {
                logger.error("ERROR:" + name + " -- the parameter name with alias should contain a . char!");
                return null;
            }
            String alias = StringUtils.substringBefore(trueName, ".");
            if (aliasesMap == null) {
                aliasesMap = new HashMap<String, String>();
            }
            String aliases = aliasesMap.get(aliasKey);
            if (aliases == null)
                aliases = "";
            if (StringUtils.indexOf(aliases, alias + '|') == -1) {
                criteria = joinType == CriteriaSpecification.INNER_JOIN ? criteria.createAlias(StringUtils.substringBefore(name, ":"), alias, joinType) : criteria.createAlias(StringUtils.substringBefore(name, "[:"), alias, joinType);
                aliases = aliases + alias + '|';
                aliasesMap.put(aliasKey, aliases);
            }
            name = trueName;
            if (StringUtils.lastIndexOf(name, ":") != -1) {
                name = generateAlias(criteria, name, aliasKey);
            }
        }
        return name;
    }
    
   
    /**
     * 内部类，服务于distinct，实现distinct整个类，但关联数据则不能获得
     * 
     * @author leon
     */
    @SuppressWarnings("unchecked")
    class PrefixAliasToBeanResultTransformer implements ResultTransformer {
        
        private final Class resultClass;
        
        private Setter[] setters;
        
        private PropertyAccessor propertyAccessor;
        
        public PrefixAliasToBeanResultTransformer(Class resultClass) {
            if (resultClass == null)
                throw new IllegalArgumentException("resultClass cannot be null");
            this.resultClass = resultClass;
            propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[] {
                    PropertyAccessorFactory.getPropertyAccessor(resultClass, null),
                    PropertyAccessorFactory.getPropertyAccessor("field") });
        }
        
        public Object transformTuple(Object[] tuple, String[] aliases) {
            Object result;
            
            try {
                if (setters == null) {
                    setters = new Setter[aliases.length];
                    
                    for (int i = 0; i < aliases.length; i++) {
                        String alias = aliases[i];
                        
                        if (alias != null) {
                            if (alias.startsWith(ALIAS_PREFIX)) {
                                alias = alias.substring(ALIAS_PREFIX.length());
                            }
                            
                            setters[i] = propertyAccessor.getSetter(resultClass, alias);
                        }
                    }
                }
                
                result = resultClass.newInstance();
                
                for (int i = 0; i < aliases.length; i++) {
                    if (setters[i] != null) {
                        setters[i].set(result, tuple[i], null);
                    }
                }
            } catch (InstantiationException e) {
                throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
            } catch (IllegalAccessException e) {
                throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
            }
            
            return result;
        }
        
        public List transformList(List collection) {
            return collection;
        }
        
    }
    
    /**
     * 获取持久类主键属性名
     * 
     * @param entityClass
     * @return
     */
    @SuppressWarnings("unchecked")
    private String getIdentityPropertyName(Class entityClass) {
        ClassMetadata classMetadata = getSessionFactory().getClassMetadata(entityClass);
        return classMetadata.getIdentifierPropertyName();
    }
    
    /**
     * 获取所有属性名的投影
     * 
     * @param entityClass
     * @return
     */
    @SuppressWarnings("unchecked")
    private Projection getClassProjectionList(Class entityClass) {
        ClassMetadata classMetadata = getSessionFactory().getClassMetadata(entityClass);
        // String identityPropertyName =
        // classMetadata.getIdentifierPropertyName();
        String[] properties = classMetadata.getPropertyNames();
        ProjectionList list = Projections.projectionList();
        // list.add(Projections.property(identityPropertyName),
        // identityPropertyName);
        for (int i = 0; i < properties.length; ++i) {
            list.add(Projections.property(properties[i]), ALIAS_PREFIX + properties[i]);
        }
        return list;
    }
    
    public void clearAlias(String alias) {
        if (aliasesMap != null) {
            /* 清除别名用到的临时变量 */
            aliasesMap.remove(alias);
        }
    }
    

}
