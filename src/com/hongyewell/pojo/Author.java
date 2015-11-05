package com.hongyewell.pojo;



public class Author {
	private String username;
	private int id;
	
	public Author(){
		
	}
	public Author(String username, int id) {
		this.username = username;
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
