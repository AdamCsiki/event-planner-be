package com.adamc.eventplannerbe.controller;

import com.adamc.eventplannerbe.entities.Project;
import com.adamc.eventplannerbe.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("")
    public ArrayList<Project> getProjectsByQuery(@RequestParam(required = false) String query) {
        return projectService.getAllByName(query);
    }

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
