package br.com.metrocamp.livraria.util.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

public class ServerUtils {

	public static Integer getIntParam(String name, HttpServletRequest request) {
		String s = request.getParameter(name);
		
		try {
			return Integer.parseInt(s);
		} catch(Exception e) {}
		return null;
	}
	
	public static Boolean getBooleanParam(String name, HttpServletRequest request) {
		String s = request.getParameter(name);
		
		try {
			return Boolean.parseBoolean(s);
		} catch(Exception e) {}
		return null;
	}
	
	public static String getStringParam(String name, HttpServletRequest request) {
		return request.getParameter(name);
	}
	
	public static <T extends Enum>T getEnumParam(String name, Class clazz, HttpServletRequest request) {
		String str = request.getParameter(name);
		return (T)Enum.valueOf(clazz, str);
	}
	
	public static void responseText(String content, HttpServletResponse response, int status){
			
		try {
			response.setContentType("text/plain; charset=utf-8");
			response.setStatus(status);
		    response.setHeader("Access-Control-Allow-Origin", "*");
		    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		    response.setHeader("Access-Control-Max-Age", "3600");
		    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			PrintWriter out;
			out = response.getWriter();
			out.print(content);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void responseJson(String content, HttpServletResponse response, int status){
		
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(status);
		    response.setHeader("Access-Control-Allow-Origin", "*");
		    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		    response.setHeader("Access-Control-Max-Age", "3600");
		    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			PrintWriter out;
			out = response.getWriter();
			out.print(content);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void response(ServerMessage message, HttpServletResponse response) {
		String json = message.toJson();
	
		switch(message.getType()) {
			case SUCCESS:
				ServerUtils.responseText(json, response, HttpServletResponse.SC_OK);
				break;
			case ERROR:
				ServerUtils.responseText(json, response, HttpServletResponse.SC_BAD_REQUEST);
				break;
		}
	}
	
	public static void responseError(String resp, HttpServletResponse response) {
		ServerUtils.responseText(resp, response, HttpServletResponse.SC_BAD_REQUEST);
	}
	
	public static void responseImage(File file, ServletContext ctx, HttpServletResponse response) throws IOException {
        String mimeType = ctx.getMimeType(file.getAbsolutePath());
        response.setContentType(mimeType != null? mimeType:"application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"currentScore.jpg\"");
        response.addHeader("Access-Control-Allow-Origin", "*");
        FileUtils.copyFile(file, response.getOutputStream());
	}
	
	public static void responseFile(File file, ServletContext ctx, HttpServletResponse response) throws IOException {
        String mimeType = ctx.getMimeType(file.getAbsolutePath());
        response.setContentType(mimeType != null? mimeType:"application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\""+file.getName()+"\"");
        response.addHeader("Access-Control-Allow-Origin", "*");
        FileUtils.copyFile(file, response.getOutputStream());
	}
	
	public static boolean errorNullParam(Object param, String paramName, HttpServletResponse response) {
		if(param == null) {
			ServerUtils.responseError(paramName + " is null", response);
			return true;
		}
		return false;
	}
}
