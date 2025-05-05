package com.th.nextdone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.th.nextdone.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
