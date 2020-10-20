package br.com.metrocamp.livraria.util.url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import br.com.metrocamp.livraria.controller.Definitions;


public class URLUtils {

	public static String encode(String url){
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String decode(String url){
		if(url == null)
			return null;
			
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getSubdomain(StringBuffer urlObj) {
		String url = urlObj.toString();
		if(url.contains("//") && url.contains(".")) {
			int idx1 = url.indexOf("//") + "//".length();
			int idx2 = url.indexOf(".");
			return url.substring(idx1, idx2);
		}
		return null;
	}
	
	
	public static String getContextURL(String url) {
		url = url.replace(Definitions.APPLICATION_CONTEXT, "");
		
		if(url.indexOf("?") != -1){
			url = url.substring(0, url.indexOf("?"));
		}
		return url;
	}
}
