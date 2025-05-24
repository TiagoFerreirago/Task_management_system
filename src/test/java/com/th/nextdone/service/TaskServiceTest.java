package com.th.nextdone.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.th.nextdone.TaskMock;
import com.th.nextdone.dto.TaskDto;
import com.th.nextdone.email.EmailService;
import com.th.nextdone.exception.CustomIllegalArgumentException;
import com.th.nextdone.exception.InvalidDataException;
import com.th.nextdone.exception.TaskNotFoundException;
import com.th.nextdone.model.Task;
import com.th.nextdone.repository.TaskRepository;
import com.th.nextdone.security.model.User;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
	
	private TaskMock input;
	@Mock
	private TaskRepository taskRepository;
	@Mock
	private EmailService emailService;
	@InjectMocks
	private TaskService taskService;

	@BeforeEach
	void setUp() throws Exception {
		input = new TaskMock();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {

		List<Task> tasks = input.mockTaskList();
		
		when(taskRepository.findAll()).thenReturn(tasks);
		
		List<TaskDto> result = taskService.findAll();
		
		assertNotNull(result);
		assertEquals(10, result.size());
		
		TaskDto dtoOne = result.get(1);
		assertNotNull(dtoOne.id());
		assertEquals(true,dtoOne.completed());
		assertEquals(LocalDate.of(2025, 4, 20), dtoOne.dataCreation());
		assertEquals("Description test 1", dtoOne.description());
		assertEquals("Title test 1", dtoOne.title());
		
		TaskDto dtoFour = result.get(4);
		assertNotNull(dtoFour.id());
		assertEquals(false, dtoFour.completed());
		assertEquals(LocalDate.of(2025, 4, 20), dtoFour.dataCreation());
		assertEquals("Description test 4", dtoFour.description());
		assertEquals("Title test 4", dtoFour.title());
		
		TaskDto dtoNine = result.get(9);
		assertNotNull(dtoNine.id());;
		assertEquals(false,dtoNine.completed());
		assertEquals(LocalDate.of(2025, 4, 20), dtoNine.dataCreation());
		assertEquals("Description test 9", dtoNine.description());
		assertEquals("Title test 9", dtoNine.title());
		
	}

	@Test
	void testFindById() throws AccountException {

		Task task  = input.mockTask(1);
		task.setId(1L);
		
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		
		TaskDto result = taskService.findById(1L);
		
		assertNotNull(result);
		assertEquals(true, result.completed());
		assertEquals(LocalDate.of(2025, 4, 20), result.dataCreation());
		assertEquals("Description test 1", result.description());
		assertEquals("Title test 1", result.title());
	}

	@Test
	void testSearchByDate() {

		LocalDate date = LocalDate.of(2025, 5, 10);
		Task task = new Task();
		task.setDataCreation(date);
		
		when(taskRepository.searchByDate(date)).thenReturn(List.of(task));
		
		List<TaskDto> tasks = taskService.searchByDate(date);
		
		assertNotNull(tasks);
		assertEquals(1, tasks.size());
		assertEquals(date, tasks.get(0).dataCreation());
	}

	@Test
	void testSearchBetweenPeriod() {

		LocalDate startDate = LocalDate.of(2025, 5, 1);
		LocalDate endDate = LocalDate.of(2025, 5, 15);
		
		Task task = input.mockTask(1);
		task.setDataCreation(LocalDate.of(2025, 5, 10));
		
		when(taskRepository.searchBetweenPeriod(startDate, endDate)).thenReturn(List.of(task));
		
		List<TaskDto> tasks = taskService.searchBetweenPeriod(startDate, endDate);
		
		assertNotNull(tasks);
		assertEquals(1 , tasks.size());
		assertTrue(tasks.get(0).dataCreation().isAfter(startDate.minusDays(1)));
	}

	@Test
	void testSearchByStatus() {

		Task task = input.mockTask(1);
		task.setCompleted(true);
		
		when(taskRepository.searchByStatus(true)).thenReturn(List.of(task));
		
		List<TaskDto> tasks = taskService.searchByStatus(true);
		
		assertNotNull(tasks);
		assertEquals(1, tasks.size());
		assertTrue(tasks.get(0).completed());
	    assertEquals("Title test 1", tasks.get(0).title());
	}

	@Test
	void testSave() {
		Task task = input.mockTask(1);;
		Task persisted = task;
		persisted.setId(1L);
		
		TaskDto dto = input.mockTaskDto(1);
		
		when(taskRepository.save(any(Task.class))).thenReturn(persisted);
		
		TaskDto result = taskService.save(dto);
		
		assertNotNull(result.id());
		assertEquals(false, result.completed());
		assertEquals(LocalDate.of(2025, 5, 25), result.dataCreation());
		assertEquals("Description test 1", result.description());
		assertEquals("Title test 1", result.title());
		
	}

	@Test
	void testDelete() {
		Task task = input.mockTask(1);
		task.setId(1L);
		
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		taskService.delete(1L);
		
		verify(taskRepository, times(1)).findById(anyLong());
		verify(taskRepository, times(1)).delete(task);
		verifyNoMoreInteractions(taskRepository);
	}

	@Test
	void testMarkAsCompleted() throws AccountException {

	
		Task task = input.mockTask(1);
		task.setCompleted(false);
		
		User user = new User();
		user.setEmail("test@gmail.com");
		task.setUser(user);
		
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		when(taskRepository.save(any(Task.class))).thenReturn(task);
		
		TaskDto result = taskService.markAsCompleted(1L);
		
		assertNotNull(result);
		assertEquals(true, result.completed());
		
		verify(taskRepository).save(argThat(t -> t.isCompleted()));
		
		verify(emailService,times(1)).sendEmail(eq("test@gmail.com"), eq("Task Completed!"), contains("has completed successfully"));
		
		
	}
	
	//Test Exceptions
	@Test
	void testFindByIdTaskNotFoundException() {
		
		Exception ex = assertThrows(TaskNotFoundException.class, ()->{
			taskService.findById(99L);
		});
		
		String expectedMessage = "Task with ID " + 99 + " not found.";
		String generatedMessage = ex.getMessage();
		
		assertTrue(generatedMessage.contains(expectedMessage));
	}
	
	@Test
	void testSearchByDateNullDateException() {
		
		Exception ex = assertThrows(CustomIllegalArgumentException.class, () -> {
			taskService.searchByDate(null);
		});
		String expectedMessage = "Date cannot be null.";
		String generatedMessage = ex.getMessage();
		
		assertTrue(generatedMessage.contains(expectedMessage));
	}
	
	@Test
	void testSaveEmptyTitleOrDescriptionException(){
		
		Exception ex = assertThrows(InvalidDataException.class, () -> {
			taskService.save(new TaskDto(null, "", "description", false, null, null));
			
		});
				
		String expectedMessage = "Task title is required.";
		String generatedMessage = ex.getMessage();
		assertTrue(generatedMessage.contains(expectedMessage));
	}
	
	@Test
	void testSaveEmptyDescriptionException(){
		
		Exception ex = assertThrows(InvalidDataException.class, () -> {
			taskService.save(new TaskDto(null, "titulo", "", false, null, null));
			
		});
		
		String expectedMessage = "Task description is required.";
		String generatedMessage = ex.getMessage();
		assertTrue(generatedMessage.contains(expectedMessage));
	}
	


}
