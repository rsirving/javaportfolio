package com.project.javaportfolio.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="tags")
public class Tag{
	@Id
	@GeneratedValue
	private long id;

	@NotNull
	private String name;

	// Member variables and annotations go here.
	
	@DateTimeFormat(pattern="MM:dd:yyyy HH:mm:ss")
	private Date createdAt;
	
	@DateTimeFormat(pattern="MM:dd:yyyy HH:mm:ss")
	private Date updatedAt;

	@PrePersist
	public void onCreate(){this.createdAt = new Date();}
	@PreUpdate
	public void onUpdate(){this.updatedAt = new Date();}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="questions_tags",
		joinColumns = @JoinColumn(name="tag_id"),
		inverseJoinColumns = @JoinColumn(name="question_id")
	)
	private List<Question> questions;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	// Setters and Getters go here

	public Tag(){}
	
	public Tag(String name){
		this.name = name;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Question> getQuestions(){
		return questions;
	}
	public void setQuestions(List<Question> questions){
		this.questions = questions;
	}
}