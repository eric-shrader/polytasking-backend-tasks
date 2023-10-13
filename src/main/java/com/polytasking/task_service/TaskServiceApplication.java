package com.polytasking.task_service;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.polytasking.task_service.entities.Task;
import com.polytasking.task_service.repositories.TaskRepository;

@SpringBootApplication
public class TaskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskServiceApplication.class, args);
	}

	// @Bean
	// public CommandLineRunner dataLoader(TaskRepository repo) {
	// return args -> {
	// Task task1 = new Task();
	// task1.setName("clean the house");
	// task1.setType("House");
	// task1.setEmail("ericshrader101@gmail.com");
	// task1.setDueDate(LocalDate.now());
	// task1.setFrequency("Once");
	// Task task2 = new Task();
	// task2.setName("clean the garage");
	// task2.setType("Car");
	// task2.setEmail("bloop@gmail.com");
	// task2.setDueDate(LocalDate.now());
	// task2.setFrequency("Once");
	// Task task3 = new Task();
	// task3.setName("wash the car");
	// task3.setType("Car");
	// task3.setDueDate(LocalDate.now());
	// task3.setEmail("ericshrader101@gmail.com");
	// task3.setFrequency("Daily");
	// Task task4 = new Task();
	// task4.setName("clean the garage");
	// task4.setType("House");
	// task4.setEmail("ericshrader101@gmail.com");
	// task4.setDueDate(LocalDate.now());
	// task4.setFrequency("Monthly");
	// Task task5 = new Task();
	// task5.setName("do the dishes");
	// task5.setType("House");
	// task5.setEmail("ericshrader101@gmail.com");
	// task5.setDueDate(LocalDate.now().plusDays(7));
	// task5.setFrequency("Weekly");
	// Task task6 = new Task();
	// task6.setName("vaccuum");
	// task6.setType("House");
	// task6.setEmail("ericshrader101@gmail.com");
	// task6.setDueDate(LocalDate.now().minusDays(7));
	// task6.setFrequency("Monthly");
	// repo.save(task1);
	// repo.save(task2);
	// repo.save(task3);
	// repo.save(task4);
	// repo.save(task5);
	// repo.save(task6);
	// };
	// }

}
