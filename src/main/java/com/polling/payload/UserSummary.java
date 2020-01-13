package com.polling.payload;

public class UserSummary {
	private Long id;
	private String username;
	private String name;
	
	public UserSummary(Long id, String username, String name) {
		this.id = id;
		this.username = username;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setIt(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUserName(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
