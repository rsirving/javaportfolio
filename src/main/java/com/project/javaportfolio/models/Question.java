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
@Table(name="questions")
public class Question{
	@Id
	@GeneratedValue
	private long id;

	@NotNull
	private String body;

	// Member variables and annotations go here.
	
	@DateTimeFormat(pattern="MM:dd:yyyy HH:mm:ss")
	private Date createdAt;
	
	@DateTimeFormat(pattern="MM:dd:yyyy HH:mm:ss")
	private Date updatedAt;

	@PrePersist
	public void onCreate(){this.createdAt = new Date();}
	@PreUpdate
	public void onUpdate(){this.updatedAt = new Date();}

	@OneToMany(mappedBy="question", fetch = FetchType.LAZY)
	private List<Answer> answers;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name="questions_tags",
		joinColumns = @JoinColumn(name="question_id"),
		inverseJoinColumns = @JoinColumn(name="tag_id")
	)
	private List<Tag> tags;
	
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

	public Question(){}
	
	public Question(String body, List<Tag> tags){
		this.body = body;
		this.tags = tags;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<Answer> getAnswers(){
		return answers;
	}
	public void setAnswers(List<Answer> answers){
		this.answers = answers;
	}
	public List<Tag> getTags(){
		return tags;
	}
	public void setTags(List<Tag> tags){
		this.tags = tags;
	}
}