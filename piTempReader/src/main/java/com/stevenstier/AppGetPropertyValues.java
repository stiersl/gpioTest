package com.stevenstier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class AppGetPropertyValues {
	
	String result = "";
	InputStream inputStream;
 
	public String getPropValues() throws IOException {
		System.out.println("|                Getting Property Values                         |");
		System.out.println("------------------------------------------------------------------");
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
			String devicecode = prop.getProperty("devicecode");

			Application.DEVICE_CODE = devicecode;
			result = "devicecode = " + devicecode ;
			System.out.println("| " + result + "\n| Program Ran on " + time );
			System.out.println("------------------------------------------------------------------");
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
}
