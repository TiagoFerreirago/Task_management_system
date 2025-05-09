package com.th.nextdone.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.th.nextdone.dto.TaskDto;
import com.th.nextdone.exception.InvalidDataException;
import com.th.nextdone.exception.TaskNotFoundException;
import com.th.nextdone.model.Task;
import com.th.nextdone.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	public List<TaskDto>findAll(){
		List<Task> tasks = taskRepository.findAll();
		if(tasks.isEmpty()) {
			throw new TaskNotFoundException("No tasks found");
		}
		List<TaskDto> dtoList = tasks.stream()
		        .map(p -> new TaskDto(p.getId(), p.getTitle(), p.getDescription(), p.isCompleted(), p.getDataCreation()))
		        .collect(Collectors.toList());
		return dtoList;
	}
	
	public TaskDto findById(Long id) throws AccountException {
		
		Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found."));
		TaskDto dto = new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted(), task.getDataCreation());
		return dto;
	}
	
	public List<TaskDto> searchByDate(LocalDate date){
		
		if(date == null) {
			throw new IllegalArgumentException("Date cannot be null.");
		}
		List<Task> tasks = taskRepository.searchByDate(date);
		
		if(tasks.isEmpty()) {
			throw new TaskNotFoundException("No tasks found for the given date.");
		}
		List<TaskDto> dtoList = tasks.stream().map(p ->
		new TaskDto(p.getId(), p.getTitle(), p.getDescription(), p.isCompleted(), p.getDataCreation())).collect(Collectors.toList());
		return dtoList;
	}
	
	public List<TaskDto> searchBetweenPeriod(LocalDate firstDate, LocalDate lastDate){
		
		if (firstDate == null || lastDate == null) {
	        throw new IllegalArgumentException("Dates cannot be null.");
	    }
		
		if (firstDate.isAfter(lastDate)) {
	        throw new IllegalArgumentException("The start date cannot be later than the end date.");
	    }
		
		List<Task> tasks = taskRepository.searchBetweenPeriod(firstDate, lastDate);
		
		if(tasks.isEmpty()) {
			throw new TaskNotFoundException("No tasks found in the specified period.");
		}
		List<TaskDto> dtoList = tasks.stream().map(p ->
		new TaskDto(p.getId(), p.getTitle(), p.getDescription(), p.isCompleted(), p.getDataCreation())).collect(Collectors.toList());
		return dtoList;
	}
	
	public List<TaskDto> searchByStatus(boolean status){
		
		List<Task> tasks = taskRepository.searchByStatus(status);
		 if (tasks.isEmpty()) {
		        throw new TaskNotFoundException("No tasks found with status: " + status);
		 }
		 List<TaskDto> dtoList = tasks.stream().map(p ->
			new TaskDto(p.getId(), p.getTitle(), p.getDescription(), p.isCompleted(), p.getDataCreation())).collect(Collectors.toList());
		return dtoList;
	}
	
	public TaskDto save(TaskDto taskDto) {
		
		Task task = new Task(taskDto);
		
		if(task.getTitle() == null || task.getTitle().isEmpty()) {
			throw new InvalidDataException("Task title is required");
		}
		if(task.getDescription() == null || task.getDescription().isEmpty()) {
			throw new InvalidDataException("Task description is required");
		}
		taskRepository.save(task);
		TaskDto dto = new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted(), task.getDataCreation());
		
		return dto;
	}
	
	public void delete(Long id) {
		Task task = taskRepository.findById(id).orElseThrow(() ->  new TaskNotFoundException("Task with ID " + id + " not found."));
		taskRepository.delete(task);
	}
	
	public TaskDto markAsCompleted(Long id) throws AccountException {
		
		Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found."));
		task.setCompleted(true);
		taskRepository.save(task);
		TaskDto dto = new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted(), task.getDataCreation());
		return dto;
	}
}
