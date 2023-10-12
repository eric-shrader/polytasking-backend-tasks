package com.polytasking.task_service.comparators;

import java.util.Comparator;

import com.polytasking.task_service.entities.Task;

public class TaskDueDateComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        return task1.getDueDate().compareTo(task2.getDueDate());
    }
}
