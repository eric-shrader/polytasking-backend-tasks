package com.polytasking.task_service.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.polytasking.task_service.entities.Task;
import com.polytasking.task_service.forms.TaskForm;
import com.polytasking.task_service.repositories.TaskRepository;
import com.polytasking.task_service.services.TaskService;

import jakarta.validation.Valid;
import lombok.Data;

@RestController
@RequestMapping(path = "/api/tasks", produces = "application/json")
@CrossOrigin(origins = { "http://localhost:3000", "https://polytasking.com" })
@Data
public class TaskController {

    public final TaskRepository repo;

    private final TaskService service;

    @GetMapping
    public Iterable<Task> getTasks(@RequestParam(required = false) String type,
            @RequestParam(required = false) String frequency,
            @RequestParam(required = false) String dueDate,
            @RequestParam String sortType,
            @RequestParam String sortOrder,
            Authentication authentication) {

        Jwt token = (Jwt) authentication.getPrincipal();
        Map<String, Object> claims = token.getClaims();
        String email = (String) claims.get("email");

        Map<String, String> filters = new HashMap<String, String>();
        filters.put("type", type);
        filters.put("frequency", frequency);
        filters.put("dueDate", dueDate);

        String[] sort = { sortType, sortOrder };

        List<Task> tasks = service.getTasksByFilter(email, filters, sort);
        return tasks;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody @Valid TaskForm taskform) {
        Task task = taskform.toTask();
        return repo.save(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @PutMapping(consumes = "application/json")
    public void signOffOnTasks(@RequestBody List<Task> tasks) {
        service.signOffOnTasks(tasks);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
