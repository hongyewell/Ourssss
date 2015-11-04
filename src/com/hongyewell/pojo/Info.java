package com.hongyewell.pojo;


public class Info {
	private String content;
	private String author;
	private String publishedDate;
	private int id;
	private String title;
	
	public Info(){
		
	}

	public Info(String content, String author, String publishedDate, int id,
			String title) {
		this.content = content;
		this.author = author;
		this.publishedDate = publishedDate;
		this.id = id;
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
