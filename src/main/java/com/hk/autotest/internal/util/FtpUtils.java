package com.hk.autotest.internal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpUtils {


	private static final Logger logger = LoggerFactory
			.getLogger(FtpUtils.class);
	
	public static String convertToHttpProdSite(String originalSite){
//		ftp://cbuilder-storage.dev.sh.ctripcorp.com/2015-03-13/1102440/ANDROID_1150181/ANDROID_1150181.zip
//      http://cbuilder-storage.dev.sh.ctripcorp.com/prod/2015-03-13/1102440/ANDROID_1150181/ANDROID_1150181.zip	
		
		logger.info(originalSite+" is going to be converted.");
		
		originalSite = originalSite.substring("ftp://".length(), originalSite.length());
		
		int index = originalSite.indexOf("/");
		
		originalSite = "http://" + originalSite.substring(0, index) + "/prod" + originalSite.substring(index, originalSite.length());
			
		logger.info("Has converted to " + originalSite);
		
		return originalSite;
	}
	
	
}
