package com.polytasking.task_service.comparators;

import java.util.Comparator;

import com.polytasking.task_service.entities.Task;

public class TaskFrequencyComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        String frequency1 = task1.getFrequency();
        String frequency2 = task2.getFrequency();
        int number1 = convert(frequency1);
        int number2 = convert(frequency2);
        if (number1 < number2) {
            return -1;
        } else if (number1 == number2) {
            return 0;
        } else {
            return 1;
        }
    }

    private int convert(String frequency) {
        if (frequency.equals("Once")) {
            return 0;
        } else if (frequency.equals("Daily")) {
            return 1;
        } else if (frequency.equals("Weekly")) {
            return 2;
        } else if (frequency.equals("Monthly")) {
            return 3;
        } else
            return -1;
    }
}