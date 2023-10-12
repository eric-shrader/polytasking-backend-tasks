package com.polytasking.task_service.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.polytasking.task_service.entities.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.email = ?1")
    List<Task> findByEmail(String email);

    @Query("SELECT t FROM Task t WHERE t.email = ?1 AND t.type = ?2")
    List<Task> findByEmailAndType(String email, String type);

    @Query("SELECT t FROM Task t WHERE t.email = ?1 AND t.dueDate = ?2")
    List<Task> findByEmailAndDueDate(String email, LocalDate date);

    @Query("SELECT t FROM Task t WHERE t.email = ?1 AND t.dueDate BETWEEN ?2 AND ?3")
    List<Task> findByEmailAndDueDateBetween(String email, LocalDate beforeDate, LocalDate afterDate);

}