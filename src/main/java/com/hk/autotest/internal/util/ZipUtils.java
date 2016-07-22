package com.hk.autotest.internal.util;

import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.commons.lang3.StringUtils;

import com.hk.autotest.exception.CapException;
import com.hk.autotest.exception.AppDownloadException;

public class ZipUtils {

	public static String unzip(String filePath){
		String unzipFile = null;
	    try {
	         ZipFile zipFile = new ZipFile(filePath);
	         String segment = zipFile.getFile().getName().substring(0, zipFile.getFile().getName().lastIndexOf("."));
	         String parentPath = zipFile.getFile().getParentFile().getAbsolutePath();
	         String destination = parentPath + "/" + segment;
	         
	         if(!new File(destination).exists()){
	        	 new File(destination).mkdirs();
	         }
	         zipFile.extractAll(destination);
	         String[] fileNames = new File(destination).list();
	         for(String fileName: fileNames){
	        	 if(StringUtils.endsWithIgnoreCase(fileName, ".apk")||StringUtils.endsWithIgnoreCase(fileName, ".ipa")){
	        		 unzipFile = destination + "/" + fileName;
	        		 break;
	        	 }
	         }
	    } catch (ZipException e) {
	    	throw new AppDownloadException(
					"Can not unzip downloaded app:" + filePath);
	    } 
	    return unzipFile;
	}
	
	public static String unzip(String filePath, String destination) {
		try {
			ZipFile zipFile = new ZipFile(filePath);
			File destFile = new File(destination);
			if(!destFile.exists()) {
				destFile.mkdir();
			}
			zipFile.setFileNameCharset("UTF-8");
			zipFile.extractAll(destination);
		} catch (ZipException e) {
			throw new CapException("Extract File Failed! File: " + filePath);
		}
		return destination;
	}

	
}
