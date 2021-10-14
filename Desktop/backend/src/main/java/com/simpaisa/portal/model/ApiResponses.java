package com.simpaisa.portal.model;

public class ApiResponses {
	public String status;
	public String message;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "apiResponse [status=" + status + ", message=" + message + "]";
	}
	

}
