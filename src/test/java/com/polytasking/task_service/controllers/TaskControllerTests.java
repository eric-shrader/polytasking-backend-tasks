// package com.polytasking.task_service.controllers;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import com.polytasking.task_service.entities.Task;
// import com.polytasking.task_service.repositories.TaskRepository;
// import com.polytasking.task_service.services.TaskService;

// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import java.time.LocalDate;

// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.mockito.Mockito.when;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

// @WebMvcTest(value = TaskController.class)
// @AutoConfigureMockMvc(addFilters = false)
// public class TaskControllerTests {

// @Autowired
// private MockMvc mockMvc;

// @MockBean
// private TaskService service;
// @MockBean
// private TaskRepository repo;

// @Test
// public void createTaskTest() throws Exception {

// Task task = new Task();
// task.setName("vaccuum");
// task.setType("House");
// task.setDueDate(LocalDate.of(2023, 10, 5));
// task.setFrequency("Once");
// task.setEmail("ericshrader101@gmail.com");
// when(repo.save(task)).thenReturn(task);

// mockMvc.perform(post("/api/tasks")
// .contentType(MediaType.APPLICATION_JSON)
// .content(
// "{\"name\":\"vaccuum\",\"type\":\"House\",\"dueDate\":\"2023-10-05T05:00:00.000Z\",\"frequency\":\"Once\",\"email\":\"ericshrader101@gmail.com\"}"))
// .andExpect(status().isCreated())
// .andExpect(content().contentType(MediaType.APPLICATION_JSON));
// }
// }
