package br.com.botcity.screenshotupload.model;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="agentError",noClassnameStored=true)
public class AgentError {
	
	@Id
	private Integer id;
	private Integer taskID;
	private String message;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getTaskID() {
		return taskID;
	}

	public void setTaskID(Integer taskID) {
		this.taskID = taskID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<AgentError> listErrors() {
		List<AgentError> listErrors = new ArrayList<AgentError>();
		return listErrors;
	}
	
	public AgentError(Integer id, Integer taskID, String message) {
		this.id = id;
		this.taskID = taskID;
		this.message = message;
	}

	public AgentError() {

	}
}
