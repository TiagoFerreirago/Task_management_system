package com.th.nextdone.controller;

import java.time.LocalDate;
import java.util.List;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@GetMapping(value ="/by-date")
	public ResponseEntity<List<Task>> searchByDate(@RequestParam("date")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){
		
		List<Task>tasks = taskService.searchByDate(date);
		return ResponseEntity.ok().body(tasks);
	}
	@GetMapping(value = "/beetwen-date")
	public ResponseEntity<List<Task>> searchBeetwenPeriod(@RequestParam("firstDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
	@RequestParam("lastDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate lastDate){
		
		List<Task> tasks = taskService.searchBeetwenPeriod(firstDate, lastDate);
		return ResponseEntity.ok().body(tasks);
	}
	@GetMapping(value = "/status")
	public ResponseEntity<List<Task>> searchByStatus(@RequestParam("status")boolean status){
		
		List<Task> tasks = taskService.searchByStatus(status);
		return ResponseEntity.ok().body(tasks);
	}
	
	@DeleteMapping(value = "/id")
	public ResponseEntity<Void>delete(@PathVariable("id")Long id){
		
		taskService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Task>save(@RequestBody Task task){
		
		Task entity = taskService.save(task);
		return ResponseEntity.ok().body(entity);
	}
	
	@PutMapping
	public ResponseEntity<Task>completed(@PathVariable("id")Long id) throws AccountException{
		
		Task task = taskService.markAsCompleted(id);
		return ResponseEntity.ok().body(task);
	}
}
