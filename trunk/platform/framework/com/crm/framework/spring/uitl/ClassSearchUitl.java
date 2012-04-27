
package com.crm.framework.spring.uitl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import com.crm.framework.common.util.StringUtil;


/**
 * 
 * @author 王永明
 * @since Mar 19, 2010 12:38:14 PM
 */
public class ClassSearchUitl {

	/** 获得某个包下的所有类,不包括内部类 */
	public static List<Class> getClass(String packageName) {
		List list = new ArrayList();
		String RESOURCE_PATTERN = "**/*.class";
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
				+ ClassUtils.convertClassNameToResourcePath(packageName) + RESOURCE_PATTERN;
		try {
			Resource[] resource = resourcePatternResolver.getResources(pattern);
			for (Resource rs : resource) {
				
				String fileName=rs.getURL().getPath();
				
				if(fileName.indexOf('$')!=-1){
					continue;
				}
				
				//对于在jar包里的类,去掉jar!/以前的东西
				fileName=StringUtil.getLastAfter(fileName, "jar!/");
				
				//在classes里的去掉classes以前的东西
				fileName=StringUtil.getLastAfter(fileName, "classes/");
				
				fileName=fileName.replaceAll("/", ".");
			
				fileName=StringUtil.getLastBefore(fileName, ".class");
								
				list.add(Class.forName(fileName));		
				
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	
	/** 找到某个包下特定类型的类
	 * @param annotationTypes 类上标记的annotation*/
	public static List<Class> getMappingClass(String[] packagesToScan,Class[] annotationTypes) throws Exception {
		List<TypeFilter> filters=new ArrayList();
		for(Class clazz:annotationTypes){
			filters.add( new AnnotationTypeFilter(clazz,false));
		}
		
		List clazz=new ArrayList();
		String RESOURCE_PATTERN = "**/*.class";
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		if (packagesToScan == null) {
			return clazz;
		}
		for (String pkg : packagesToScan) {
			String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
			Resource[] resources = resourcePatternResolver.getResources(pattern);
			MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
			for (Resource resource : resources) {
				if (resource.isReadable()) {
					MetadataReader reader = readerFactory.getMetadataReader(resource);
					String className = reader.getClassMetadata().getClassName();
					if (matchesFilter(reader, readerFactory,filters)) {
						clazz.add(resourcePatternResolver.getClassLoader().loadClass(className));
					}
				}
			}
		}
		return clazz;
	}

	private static boolean matchesFilter(MetadataReader reader, MetadataReaderFactory readerFactory,List<TypeFilter> filters) throws IOException {
		if (filters != null) {
			for (TypeFilter filter : filters) {
				if (filter.match(reader, readerFactory)) {
					return true;
				}
			}
		}
		return false;
	}


}
