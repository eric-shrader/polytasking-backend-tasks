package com.polytasking.task_service.forms;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.polytasking.task_service.entities.Task;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Component
public class TaskForm {

    @NotBlank
    @Size(min = 5, max = 50)
    private String name;

    private String type;

    private String Frequency;

    @NotNull
    @FutureOrPresent
    private LocalDate dueDate;

    @Email
    private String email;

    public Task toTask() {
        Task task = new Task();
        task.setName(name);
        task.setType(type);
        task.setFrequency(Frequency);
        task.setDueDate(dueDate);
        task.setEmail(email);
        return task;
    }

}
