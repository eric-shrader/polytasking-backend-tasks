package com.polytasking.task_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.polytasking.task_service.entities.Task;
import com.polytasking.task_service.repositories.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Mock
    TaskRepository repo;

    @InjectMocks
    TaskService service;

    // testing updateStatus
    @Test
    public void StatusShouldBeOverDue() {
        LocalDate today = LocalDate.now();
        Task task = new Task();
        task.setDueDate(today.minusDays(1));
        service.updateStatus(task, today);
        assertEquals("Overdue", task.getStatus());
    }

    @Test
    public void StatusShouldBeDueSoon() {
        LocalDate today = LocalDate.now();
        Task task = new Task();
        task.setDueDate(today);
        service.updateStatus(task, today);
        assertEquals("Due Soon", task.getStatus());
    }

    @Test
    public void StatusShouldBeUpcoming() {
        LocalDate today = LocalDate.now();
        Task task = new Task();
        task.setDueDate(today.plusDays(5));
        service.updateStatus(task, today);
        assertEquals("Upcoming", task.getStatus());
    }

    // testing signOffOnTasks
    @Test
    public void TaskShouldBeDeleted() {
        Task task = new Task();
        task.setFrequency("Once");
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(task);
        tasks = service.signOffOnTasks(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void dueDateShouldBeTommorrow() {
        Task task = new Task();
        LocalDate today = LocalDate.now();
        task.setDueDate(today);
        task.setFrequency("Daily");
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(task);
        tasks = service.signOffOnTasks(tasks);
        assertEquals(today.plusDays(1), task.getDueDate());
    }

    @Test
    public void dueDateShouldBeNextWeek() {
        Task task = new Task();
        LocalDate today = LocalDate.now();
        task.setDueDate(today);
        task.setFrequency("Weekly");
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(task);
        tasks = service.signOffOnTasks(tasks);
        assertEquals(today.plusWeeks(1), task.getDueDate());
    }

    @Test
    public void dueDateShouldBeNextMonth() {
        Task task = new Task();
        LocalDate today = LocalDate.now();
        task.setDueDate(today);
        task.setFrequency("Monthly");
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(task);
        tasks = service.signOffOnTasks(tasks);
        assertEquals(today.plusMonths(1), task.getDueDate());
    }

}