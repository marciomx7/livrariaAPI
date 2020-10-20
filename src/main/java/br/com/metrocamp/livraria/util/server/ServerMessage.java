package br.com.metrocamp.livraria.util.server;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class ServerMessage {

	public enum Type{SUCCESS,ERROR};
	
	private String	message;
	private Type	type;
	
	public ServerMessage(String message, Type type) {
		this.message = message;
		this.type = type;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public String toJson() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("type", type.toString().toLowerCase());
		map.put("message", message);
		return new Gson().toJson(map);
	}
}
