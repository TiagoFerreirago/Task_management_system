package com.th.nextdone.controller;

import java.util.List;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.th.nextdone.model.Task;
import com.th.nextdone.service.TaskService;

@RestController
@RequestMapping("/task/v1")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@GetMapping
	public ResponseEntity<List<Task>>findAll(){
		
		List<Task>tasks = taskService.findAll();
		return ResponseEntity.ok().body(tasks);
	}
	@GetMapping(value = "/id")
	public ResponseEntity<Task>findById(@PathVariable("id")Long id) throws AccountException{
		
		Task task = taskService.findById(id);
		return ResponseEntity.ok().body(task);
	}
	@DeleteMapping(value = "/id")
	public ResponseEntity<Void>delete(@PathVariable("id")Long id){
		
		taskService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@PutMapping
	public ResponseEntity<Task>completed(@PathVariable("id")Long id) throws AccountException{
		
		Task task = taskService.markAsCompleted(id);
		return ResponseEntity.ok().body(task);
	}
}
