package com.adamc.eventplannerbe.service;

import com.adamc.eventplannerbe.entities.*;
import com.adamc.eventplannerbe.mappers.ProjectPreviewMapper;
import com.adamc.eventplannerbe.repos.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final BoardService boardService;

    /**
     * GET REQUESTS
     */
    public ArrayList<Project> getAllByName(String name) {
        ArrayList<Project> projects = new ArrayList<>();

        projects.addAll(projectRepository.findAllByName(name));

        return projects;
    }

    public ArrayList<Project> getAllByUser() {
        ArrayList<Project> projects = new ArrayList<>();

        projects.addAll(projectRepository.findAll());

        return projects;
    }

    public ArrayList<ProjectPreview> getAllPreview() {
        ArrayList<ProjectPreview> projectPreviews = new ArrayList<>();

        projectRepository.findAllPreview().forEach((project -> {
            projectPreviews.add(ProjectPreviewMapper.ProjectToPreview(project));
        }));

        return projectPreviews;
    }

    public Project getOneById(Long id) {
        return projectRepository.findOneById(id);
    }

    /**
     * PUT REQUESTS
     */
    public Project addNewBoard(Project project, String name) {
        Board newBoard = new Board(name, "");

        project.addBoard(newBoard);

        return project;
    }

    public Board addNewTask(Long boardId, String name, String details) {
        Board board = boardService.getOneById(boardId);

        Task task = new Task(name, details);

        board.addTask(task);

        return board;
    }

    public Project putProject(Project project) {
        projectRepository.save(project);

        return projectRepository.findOneById(project.getId());
    }

    /**
     * POST REQUESTS
     */
    public boolean postProject(Project Project) {
        try {
            Project check = projectRepository.save(Project);
            return true;
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return false;
        }
    }

    public Project editProject(Project project, String projectName) {
        project.setName(projectName);

        return project;
    }

    /**
     * DELETE REQUESTS
     */
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}
