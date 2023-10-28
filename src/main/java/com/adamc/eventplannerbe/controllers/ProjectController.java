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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

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

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectByName(@PathVariable Long id, Principal principal) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow();

        Project project = user.getProjects().stream().filter((p) -> Objects.equals(p.getId(), id)).findFirst().orElseThrow();

        return ResponseEntity.ok(project);
    }

    /**
     * PUT REQUESTS
     */
    @PutMapping("/add")
    public ResponseEntity<User> putProject(@RequestBody AddProjectRequest addProjectRequest, Principal principal) throws ParseException {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow();

        Project newProject = new Project
                .Builder()
                .name(addProjectRequest.getName())
                .deadLine(addProjectRequest.getDeadLine())
                .build();

        user.getProjects().add(newProject);

        userService.putUser(user);

        return ResponseEntity.ok(userService.getUserByEmail(principal.getName()).orElseThrow());
    }

    @PutMapping("/{id}/add_board")
    public Project addBoard(@RequestBody AddBoardRequest addBoardRequest, @PathVariable Long id) {
        Project project = projectService.getOneById(id);

        projectService.addNewBoard(project, addBoardRequest.getBoardName());
        projectService.putProject(project);

        return projectService.getOneById(id);
    }



    @PutMapping("/edit_project")
    public Project editProject(@RequestBody EditProjectRequest editProjectRequest) {
        Project project = projectService.getOneById(editProjectRequest.getProjectId());

        projectService.editProject(project, editProjectRequest.getProjectName());
        projectService.putProject(project);

        return projectService.getOneById(editProjectRequest.getProjectId());
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

    /**
     * DELETE REQUESTS
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id, Principal principal) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow();

        Project project = projectService.getOneById(id);

        user.getProjects().remove(project);

        userService.putUser(user);

        return ResponseEntity.ok("Deleted " + id);
    }
}
