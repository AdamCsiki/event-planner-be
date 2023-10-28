package com.adamc.eventplannerbe.controllers;

import com.adamc.eventplannerbe.entities.Project;
import com.adamc.eventplannerbe.entities.Task;
import com.adamc.eventplannerbe.requests.EditTaskRequest;
import com.adamc.eventplannerbe.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("projects/boards/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    /**
     * PUT REQUESTS
     */
    @PutMapping("/{id}/edit_task")
    public Task editTask(@RequestBody EditTaskRequest editTaskRequest, @PathVariable Long id) {
        Task task = taskService.getOneById(id);

        taskService.editTask(task, editTaskRequest.getTaskName(), editTaskRequest.getTaskDetails());
        taskService.putTask(task);

        return taskService.getOneById(id);
    }
}
