package com.liferay.yubikey.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



import org.apache.log4j.Logger;


public class HTTPUtil {
	
	private static final Logger logger = Logger.getLogger("HTTPUtil");	
	

	public static URL getURL(String endpoint) throws MalformedURLException {
		logger.info("endpoint url: "+endpoint);
		return new URL(endpoint);
	}
	
	
	public static void sendGetClassic(URL url) throws Exception{
		
		logger.info("HTTPUtil - sendGetClassic");
		
		URL obj = url;
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		con.setRequestMethod("GET");
		 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
  		
	}
	
	
}



