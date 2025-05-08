package com.th.nextdone.service;

import java.time.LocalDate;
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
	
	public List<Task> searchByDate(LocalDate date){
		
		List<Task> tasks = taskRepository.searchByDate(date);
		return tasks;
	}
	
	public List<Task> searchBeetwenPeriod(LocalDate firstDate, LocalDate lastDate){
		
		List<Task> tasks = taskRepository.searchBeetwenPeriod(firstDate, lastDate);
		return tasks;
	}
	
	public List<Task> searchByStatus(boolean status){
		
		List<Task> tasks = taskRepository.searchByStatus(status);
		return tasks;
	}
	
	public Task save(Task task) {
		
		return taskRepository.save(task);
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
