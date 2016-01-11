package com.hongyewell.pojo;



public class Author {
	private String username;
	private Integer id;
	/**
	 * 头像url
	 */
	private String photoUrl;
	
	public Author(){
		
	}
	public Author(String username, Integer id) {
		this.username = username;
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
