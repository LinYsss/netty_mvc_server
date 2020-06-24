package com.waya.demo.util.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.waya.demo.util.mybatis.PondSqlFactory;
import com.waya.demo.util.utils.ClassUtil;

public final class MappingHelper {
	
	private static final String SUFFIX = "xml";
	private static Logger logger = LoggerFactory.getLogger(MappingHelper.class);
	
	/**
	 * <p>开始扫描单个包
	 * @param packages
	 * @return
	 * @throws ScanApplicationException 
	 */
	public static void scanClass(String packages) {
		logger.debug("Start scanning packets: " + packages);
		URL url = ClassUtil.getClassLoader().getResource(packages.replace(".", "/"));
		if(url == null) {
			url = ClassUtil.getClassLoader().getResource(packages.replace(".", "\\"));
		}
		String filePath = null;
		try {
			filePath = URLDecoder.decode(url.getFile(), "utf-8");
			filePath = filePath.substring(1);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   scanClass(packages, Paths.get(filePath));
		
	}
	
	/**
	 * <p>递归扫描所有包
	 * @param packageName
	 * @param dir
	 * @throws ScanApplicationException
	 */
	public static void scanClass(String packageName, Path dir) {
		DirectoryStream<Path> stream = null;
		try {
			stream = Files.newDirectoryStream(dir);
		}catch (IOException e) {
			logger.error("Scan packet error: " + dir.getFileName(), e);
		}
		for(Path path : stream) {
			String fileName = String.valueOf(path.getFileName());
			if(fileName.indexOf(".") > -1) {
				add(packageName + "." + fileName);
			}else {
				scanClass(packageName + "." + fileName, path);
			}
		}
	}
	
	
	public static void add(String resource)   {
		if(resource.endsWith(SUFFIX)) {
			xml(resource.substring(0, resource.length() - 4).replace(".", "/") + ".xml");
		}
	}
	
	private static void xml(String resource)  {
		logger.debug("Loading mybatis mapping file: " + resource);
		InputStream inputStream = null;
		ErrorContext.instance().resource(resource);
		Collection<SqlSessionFactory> lists = PondSqlFactory.getSqlFactoryMap().values();
		for(SqlSessionFactory sqlSessionFactory : lists) {
			try {
				inputStream = Resources.getResourceAsStream(resource);
			}catch (IOException e) {
				
				logger.error("Loading mybatis mapping file error: " + resource, e);
			}
			XMLMapperBuilder mapperParser = new XMLMapperBuilder(
					inputStream,
					sqlSessionFactory.getConfiguration(),
					resource,
					sqlSessionFactory.getConfiguration().getSqlFragments());
	        mapperParser.parse();
		}
	}

}
