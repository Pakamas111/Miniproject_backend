package com.example.demo.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Date bookDate;
	
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "courseId")
	private Course course;
	
	public User getUser() {
		return user;
	}
	
	public User setUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user ;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public Course setCouse() {
		return course;
	}
	
	public void setCOurae(Course course) {
		this.course = course;
	}
	
	public Book() {
		super();
	}
	
	public Book(Integer id, Integer courseId, Date bookDate) {
		super();
		this.id = id;
		this.bookDate = bookDate;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
}
