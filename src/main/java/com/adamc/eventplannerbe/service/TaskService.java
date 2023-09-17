package com.adamc.eventplannerbe.service;

import com.adamc.eventplannerbe.entities.Task;
import com.adamc.eventplannerbe.repos.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     *  GET REQUESTS
     */
    public Task getOneById(Long id) {
        return taskRepository.findOneById(id);
    }

    /**
     * PUT REQUESTS
     */
    public Task putTask(Task task) {
        taskRepository.save(task);

        return task;
    }
    public Task editTask(Task task, String name, String details) {
        task.setName(name);
        task.setDetails(details);

        return task;
    }

}
