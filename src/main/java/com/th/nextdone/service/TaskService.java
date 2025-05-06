package com.th.nextdone.service;

import java.util.List;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.th.nextdone.model.Task;
import com.th.nextdone.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	public List<Task>findAll(){
		
		return taskRepository.findAll();
	}
	
	public Task findById(Long id) throws AccountException {
		
		Task task = taskRepository.findById(id).orElseThrow(() -> new AccountException("Task with ID " + id + " not found."));
		return task;
	}
	
	public void delete(Long id) {
		
		taskRepository.deleteById(id);
	}
	
	public Task markAsCompleted(Long id) throws AccountException {
		
		Task task = taskRepository.findById(id).orElseThrow(() -> new AccountException("Task with ID " + id + " not found."));
		task.setCompleted(true);
		return taskRepository.save(task);
	}
}
