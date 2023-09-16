package com.adamc.eventplannerbe.service;

import com.adamc.eventplannerbe.entities.Project;
import com.adamc.eventplannerbe.repos.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ArrayList<Project> getAllByName(String name) {
        ArrayList<Project> projects = new ArrayList<>();

        projects.addAll(projectRepository.findAllByName(name));

        return projects;
    }

    public boolean postProject(Project Project) {
        try {
            Project check = projectRepository.save(Project);
            return true;
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return false;
        }
    }
}
