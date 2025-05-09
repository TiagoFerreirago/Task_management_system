package com.th.nextdone.controller;

import java.time.LocalDate;
import java.util.List;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

import com.th.nextdone.dto.TaskDto;
import com.th.nextdone.service.TaskService;

@RestController
@RequestMapping("/task/v1")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@GetMapping
	public ResponseEntity<List<TaskDto>>findAll(){
		
		List<TaskDto>tasksDto = taskService.findAll();
		return ResponseEntity.ok().body(tasksDto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TaskDto>findById(@PathVariable("id")Long id) throws AccountException{
		
		TaskDto taskDto = taskService.findById(id);
		return ResponseEntity.ok().body(taskDto);
	}
	
	@GetMapping(value ="/by-date")
	public ResponseEntity<List<TaskDto>> searchByDate(@RequestParam("date")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){
		
		List<TaskDto>tasksDto = taskService.searchByDate(date);
		return ResponseEntity.ok().body(tasksDto);
	}
	
	@GetMapping(value = "/between-date")
	public ResponseEntity<List<TaskDto>> searchBetweenPeriod(@RequestParam("firstDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
	@RequestParam("lastDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate lastDate){
		
		List<TaskDto> tasksDto = taskService.searchBetweenPeriod(firstDate, lastDate);
		return ResponseEntity.ok().body(tasksDto);
	}
	
	@GetMapping(value = "/completed")
	public ResponseEntity<List<TaskDto>> searchByStatus(@RequestParam("status")boolean status){
		
		List<TaskDto> tasksDto = taskService.searchByStatus(status);
		return ResponseEntity.ok().body(tasksDto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void>delete(@PathVariable("id")Long id){
		
		taskService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<TaskDto>save(@RequestBody TaskDto taskDto){
		
		TaskDto entityDto = taskService.save(taskDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TaskDto>completed(@PathVariable("id")Long id) throws AccountException{
		
		TaskDto taskDto = taskService.markAsCompleted(id);
		return ResponseEntity.ok().body(taskDto);
	}
}
