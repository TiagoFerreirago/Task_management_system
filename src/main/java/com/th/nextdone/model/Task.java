package com.th.nextdone.model;

import java.time.LocalDate;

import com.th.nextdone.dto.TaskDto;
import com.th.nextdone.security.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 200, nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	
	private boolean completed;
	
	private LocalDate dataCreation = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Task() {}
	public Task(TaskDto dto) {
		setId(dto.id());
		setTitle(dto.title());
		setDescription(dto.description());
		setCompleted(dto.completed());
		setDataCreation(dto.dataCreation());
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public LocalDate getDataCreation() {
		return dataCreation;
	}

	public void setDataCreation(LocalDate dataCreation) {
		this.dataCreation = dataCreation;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
