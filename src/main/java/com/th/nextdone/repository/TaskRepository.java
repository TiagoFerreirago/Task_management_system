package com.th.nextdone.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.th.nextdone.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	//Pesquisar por Data
	@Query("SELECT t From Task t WHERE t.dataCreation = :date")
	List<Task> searchByDate(@Param(value = "date") LocalDate date);
	//Pesquisar entre datas
	@Query("SELECT t FROM Task t WHERE t.dataCreation BETWEEN :firstDate AND :lastDate")
	List<Task> searchBetweenPeriod(@Param(value = "firstDate")LocalDate firstDate, @Param(value = "lastDate")LocalDate lastDate);
	//Pesquisar por status
	@Query("SELECT t FROM Task t WHERE t.completed = :status")
	List<Task> searchByStatus(@Param(value = "status")boolean status);
}
