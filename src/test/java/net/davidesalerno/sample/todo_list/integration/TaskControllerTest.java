package net.davidesalerno.sample.todo_list.integration;

import lombok.SneakyThrows;
import net.davidesalerno.sample.todo_list.connector.KafkaProducerConnector;
import net.davidesalerno.sample.todo_list.connector.KakfkaListenerConnector;
import net.davidesalerno.sample.todo_list.controller.TaskController;
import net.davidesalerno.sample.todo_list.entity.Log;
import net.davidesalerno.sample.todo_list.entity.Task;
import net.davidesalerno.sample.todo_list.repository.TaskRepository;
import net.davidesalerno.sample.todo_list.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
	@Mock
	TaskRepository taskRepository;
	@Mock
	KafkaProducerConnector kafkaProducerConnector;

	@Mock
	KakfkaListenerConnector kakfkaListenerConnector;

	@SneakyThrows
	@Test
	void whenThereAreSomeTasksEverythinIsOk() {
		Task task1 = new Task("Test 1", false);
		Task task2 = new Task("Test 2", true);
		List<Task> allTasks = List.of(task1,task2);

		doNothing().when(kafkaProducerConnector).sendMessage(any(), any(Log.class));
		when(taskRepository.findAll()).thenReturn(allTasks);
		TaskService taskService = new TaskService(taskRepository,kafkaProducerConnector);
		TaskController taskController = new TaskController(taskService);

		ResponseEntity<List<Task>> resultAllTasks = taskController.getAllTasks();
		assertThat(resultAllTasks).isNotNull();
		assertThat(resultAllTasks.getStatusCode().value()).isEqualTo(200);
		assertThat(resultAllTasks.getBody().size()).isEqualTo(2);

		List<Task> completedTasks = List.of(task2);
		when(taskRepository.findByCompletedTrue()).thenReturn(completedTasks);
		ResponseEntity<List<Task>> resultCompletedTasks = taskController.getAllCompletedTasks();
		assertThat(resultCompletedTasks).isNotNull();
		assertThat(resultCompletedTasks.getStatusCode().value()).isEqualTo(200);
		assertThat(resultCompletedTasks.getBody().size()).isEqualTo(1);

		List<Task> incompletedTasks = List.of(task1);
		when(taskRepository.findByCompletedFalse()).thenReturn(incompletedTasks);
		ResponseEntity<List<Task>> resultIncompletedTasks = taskController.getAllIncompleteTasks();
		assertThat(resultIncompletedTasks).isNotNull();
		assertThat(resultIncompletedTasks.getStatusCode().value()).isEqualTo(200);
		assertThat(resultIncompletedTasks.getBody().size()).isEqualTo(1);

	}

}
