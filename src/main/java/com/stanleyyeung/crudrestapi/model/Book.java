package com.stanleyyeung.crudrestapi.model;

public class Book {

	private long id;
	private String title;
	private String author;
	private Boolean published;

	public Book() {

	}
	
	public Book(long id, String title, String author, Boolean published) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.published = published;
	}

	public long getId() {
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}
	
}
