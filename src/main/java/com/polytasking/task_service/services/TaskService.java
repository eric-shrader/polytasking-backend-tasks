package com.polytasking.task_service.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.polytasking.task_service.comparators.TaskDueDateComparator;
import com.polytasking.task_service.comparators.TaskFrequencyComparator;
import com.polytasking.task_service.comparators.TaskTypeComparator;
import com.polytasking.task_service.entities.Task;
import com.polytasking.task_service.repositories.TaskRepository;

import lombok.Data;

@Service
@Data
public class TaskService {

    private final TaskRepository repo;

    public List<Task> getTasksByFilter(String email, Map<String, String> filters, String[] sort) {
        // a bunch of local variables we need
        List<Task> tasks;
        ZoneOffset zoneOffset = ZoneOffset.of("-05:00");
        LocalDate today = LocalDate.now(zoneOffset);
        LocalTime startTime = LocalTime.of(0, 0, 0);
        int lengthOfMonth = LocalDate.now().lengthOfMonth();
        LocalDate endOfMonth = today.withDayOfMonth(lengthOfMonth);
        LocalDate endOfLastMonth = today.minusMonths(1).withDayOfMonth(today.minusMonths(1).lengthOfMonth());
        LocalDate firstDayOfTheWeek = getFirstDayOfTheWeek(today);
        LocalDate firstDayOfNextWeek = firstDayOfTheWeek.plusWeeks(1);

        tasks = repo.findByEmail(email); // get users tasks from database

        // this is the filtering logic
        Iterator<Task> taskIterator = tasks.iterator();
        while (taskIterator.hasNext()) {
            Task task = taskIterator.next();
            // for filtering by type
            if (filters.get("type") != null && !task.getType().equals(filters.get("type"))) {
                taskIterator.remove();
                // for filtering by frequency
            } else if (filters.get("frequency") != null && !task.getFrequency().equals(filters.get("frequency"))) {
                taskIterator.remove();
                // for filtering by dueDate. This is a bit more complicated because we have to
                // check what the interval is and if the date of the task falls between it
            } else if (filters.get("dueDate") != null) {
                if (filters.get("dueDate").equals("Today") && !task.getDueDate().isEqual(today)) {
                    taskIterator.remove();
                } else if (filters.get("dueDate").equals("Week")
                        && (!task.getDueDate().isBefore(firstDayOfNextWeek)
                                || !task.getDueDate().isAfter(firstDayOfTheWeek.minusDays(1)))) {
                    taskIterator.remove();
                } else if (filters.get("dueDate").equals("Month")
                        && (!task.getDueDate().isBefore(endOfMonth)
                                || !task.getDueDate().isAfter(endOfLastMonth))) {
                    taskIterator.remove();
                }
            }
        }

        // this loop adds the due date as a timestamp and sets the status of the task
        for (Task task : tasks) {
            task.setDueTimeStamp(OffsetDateTime.of(task.getDueDate(), startTime, zoneOffset));
            updateStatus(task, today);
        }

        orderTasks(tasks, sort);

        // finally its over
        return tasks;
    }

    public List<Task> signOffOnTasks(List<Task> tasks) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            LocalDate dueDate = task.getDueDate();
            switch (task.getFrequency()) {
                case "Once":
                    repo.delete(task);
                    iterator.remove();
                    break;
                case "Daily":
                    task.setDueDate(dueDate.plusDays(1));
                    break;
                case "Weekly":
                    task.setDueDate(dueDate.plusWeeks(1));
                    break;
                case "Monthly":
                    task.setDueDate(dueDate.plusMonths(1));
                    break;
            }
        }
        repo.saveAll(tasks);
        return tasks;
    }

    public void updateStatus(Task task, LocalDate today) {
        if (today.isAfter(task.getDueDate())) {
            task.setStatus("Overdue");
        } else if (task.getDueDate().isEqual(today)) {
            task.setStatus("Due Soon");
        } else {
            task.setStatus("Upcoming");
        }
    }

    public LocalDate getFirstDayOfTheWeek(LocalDate today) {
        LocalDate firstDayOfTheWeek = today;
        while (firstDayOfTheWeek.getDayOfWeek() != DayOfWeek.SUNDAY) {
            firstDayOfTheWeek = firstDayOfTheWeek.minusDays(1);
        }
        return firstDayOfTheWeek;
    }

    public void orderTasks(List<Task> tasks, String[] sort) {
        Comparator<Task> comparator;
        String orderType = sort[0];
        switch (orderType) { // determine how we should sort the tasks
            case "type":
                comparator = new TaskTypeComparator();
                break;
            case "frequency":
                comparator = new TaskFrequencyComparator();
                break;
            case "dueDate":
                comparator = new TaskDueDateComparator();
                break;
            default:
                comparator = new TaskDueDateComparator();
        }

        if (sort[1].equals("ascending") || sort[1].equals("none")) {
            Collections.sort(tasks, comparator);
        } else if (sort[1].equals("descending")) {
            Collections.sort(tasks, comparator);
            Collections.reverse(tasks);
        } else { // then we dont need to order
            return;
        }
    }
}
