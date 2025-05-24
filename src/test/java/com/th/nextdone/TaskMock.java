package com.th.nextdone;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.th.nextdone.dto.TaskDto;
import com.th.nextdone.model.Task;

public class TaskMock {

	
	public Task mockTask() {
		return mockTask(1);
	}
	
	public TaskDto mockTaskDto() {
		return mockTaskDto(1);
	}
	
	public Task mockTask(long number) {
		Task task = new Task();
		task.setCompleted(number == 1?true : false);
		task.setDataCreation(LocalDate.of(2025, 04, 20));
		task.setDescription("Description test " + number);
		task.setId(number);
		task.setTitle("Title test "+number);
		
		return task;
	}
	
	public TaskDto mockTaskDto(long number) {
		TaskDto dto = new TaskDto(number,
				"Title test "+number,
				"Description test "+number,
				number == 2?true:false,
						LocalDate.of(2025, 05, 25), null);
		return dto;
	}
	
	public List<Task> mockTaskList(){
		
		List<Task> tasks = new ArrayList<Task>();
		for (int i = 0 ; i < 10 ; i++) {
			tasks.add(mockTask(i));
		}
		return tasks;
	}
	
	public List<TaskDto> mockTaskDtoList(){
		
		List<TaskDto> tasksDto = new ArrayList<>();
		for (int i = 0 ; i < 10 ; i++) {
			tasksDto.add(mockTaskDto(i));
		}
		return tasksDto;
	}
}
