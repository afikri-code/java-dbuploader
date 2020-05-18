package de.ksirisoft.tools.db.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class Config {
	private static Properties config;
	
	public static void init() throws Exception {
		String runtimeDir = System.getenv("DBUPLOADER_RUNTIME_DIR");
		if(StringUtils.isBlank(runtimeDir)) {
		  throw new  Exception("System variable DBUPLOADER_RUNTIME_DIR not defined");	
		}
		config = new Properties();
		config.load(new FileInputStream(new File(runtimeDir+"/config/dbuploader.properties")));
	}
	
	public static String getProperty(String key) {
		if(config != null) {
			return config.getProperty(key);
		}else {
			return null;
		}
		
	}
	
}
