package com.adamc.eventplannerbe.controllers;

import com.adamc.eventplannerbe.entities.*;
import com.adamc.eventplannerbe.mappers.ProjectPreviewMapper;
import com.adamc.eventplannerbe.requests.*;
import com.adamc.eventplannerbe.service.BoardService;
import com.adamc.eventplannerbe.service.ProjectService;
import com.adamc.eventplannerbe.service.TaskService;
import com.adamc.eventplannerbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;
    private final BoardService boardService;
    private final TaskService taskService;

    /**
     * GET REQUESTS
     */
    @GetMapping("")
    public ResponseEntity<ArrayList<ProjectPreview>> getProjects(Principal principal) {
        Optional<User> user = userService.getUserByEmail(principal.getName());
        ArrayList<ProjectPreview> projects = new ArrayList<>();

        user.ifPresent(value -> value.getProjects().forEach((project -> {
            projects.add(ProjectPreviewMapper.ProjectToPreview(project));
        })));

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Project> getProjectByName(@PathVariable Long id, Principal principal) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow();

        Project project = user.getProjects().stream().filter((p) -> Objects.equals(p.getId(), id)).findFirst().orElseThrow();

        return ResponseEntity.ok(project);
    }

    /**
     * PUT REQUESTS
     */
    @PutMapping("/add_board")
    public Project addBoard(@RequestBody AddBoardRequest addBoardRequest) {
        Project project = projectService.getOneById(addBoardRequest.getProjectId());

        projectService.addNewBoard(project, addBoardRequest.getBoardName());
        projectService.putProject(project);

        return projectService.getOneById(addBoardRequest.getProjectId());
    }

    @PutMapping("/add_task")
    public Project addTask(@RequestBody AddTaskRequest addTaskRequest) {
        Board board = boardService.getOneById(addTaskRequest.getBoardId());

        boardService.addNewTask(board, addTaskRequest.getTaskName(), addTaskRequest.getTaskDetails());
        boardService.putBoard(board);

        return projectService.getOneById(addTaskRequest.getProjectId());
    }

    @PutMapping("/edit_project")
    public Project editProject(@RequestBody EditProjectRequest editProjectRequest) {
        Project project = projectService.getOneById(editProjectRequest.getProjectId());

        projectService.editProject(project, editProjectRequest.getProjectName());
        projectService.putProject(project);

        return projectService.getOneById(editProjectRequest.getProjectId());
    }

    @PutMapping("/edit_board")
    public Project editBoard(@RequestBody EditBoardRequest editBoardRequest) {
        Board board = boardService.getOneById(editBoardRequest.getBoardId());

        boardService.editBoard(board, editBoardRequest.getBoardName());
        boardService.putBoard(board);

        return projectService.getOneById(editBoardRequest.getProjectId());
    }

    @PutMapping("/edit_task")
    public Project editTask(@RequestBody EditTaskRequest editTaskRequest) {
        Task task = taskService.getOneById(editTaskRequest.getTaskId());

        taskService.editTask(task, editTaskRequest.getTaskName(), editTaskRequest.getTaskDetails());
        taskService.putTask(task);

        return projectService.getOneById(editTaskRequest.getProjectId());
    }

    /**
     * POST REQUESTS
     */
    @PostMapping("/create")
    public String postProject(@RequestBody Project project) {
        if(Objects.isNull(project)) {
            return "Null";
        }

        if(project.getName().isEmpty()) {
            return "No name";
        }

        if(projectService.postProject(project)) {
            return "Created event " + project.getName() + " " + project.getId();
        } else {
            return "Failed";
        }
    }
}
