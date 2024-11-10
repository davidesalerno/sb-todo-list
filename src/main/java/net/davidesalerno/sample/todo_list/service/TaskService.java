package net.davidesalerno.sample.todo_list.service;

import net.davidesalerno.sample.todo_list.connector.KafkaProducerConnector;
import net.davidesalerno.sample.todo_list.entity.Log;
import net.davidesalerno.sample.todo_list.entity.Task;
import net.davidesalerno.sample.todo_list.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final KafkaProducerConnector kafkaProducerService;
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository, KafkaProducerConnector kafkaProducerService){
        this.taskRepository = taskRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public Task createNewTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTask() {
        propagateLog();
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        propagateLog();
        return taskRepository.getById(id);
    }

    public List<Task> findAllCompletedTask() {
        propagateLog();
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> findAllInCompleteTask() {
        propagateLog();
        return taskRepository.findByCompletedFalse();
    }

    public void deleteTask(Long id) {
        propagateLog();
        taskRepository.deleteById(id);
    }

    public Task updateTask(Task task) {
        propagateLog();
        return taskRepository.save(task);
    }

    @Async
    public void propagateLog() {
        String method = callerName();
        Log log = Log.builder().path(method).date(new Date()).build();
        try {
            kafkaProducerService.sendMessage("log", log);
        } catch (IOException e) {
            logger.warn("Unable to send message on Kafka due to: ", e);
        }
    }

    private String callerName(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        return stackTraceElements[3].getMethodName();

    }


}