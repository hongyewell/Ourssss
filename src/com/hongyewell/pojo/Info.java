package com.hongyewell.pojo;


public class Info {
	private Author author;
	private String content;
	private int id;
	private String publishedDate;
	private String title;
	
	public Info(){
		
	}

	public Info(com.hongyewell.pojo.Author author, String content, int id,
			String publishedDate, String title) {
		this.author = author;
		this.content = content;
		this.id = id;
		this.publishedDate = publishedDate;
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

}
