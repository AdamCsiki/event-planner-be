package com.adamc.eventplannerbe.mappers;

import com.adamc.eventplannerbe.entities.Project;
import com.adamc.eventplannerbe.entities.ProjectPreview;

import java.util.ArrayList;

public class ProjectPreviewMapper {

    public static ProjectPreview ProjectToPreview(Project project) {
        return new ProjectPreview()
                .id(project.getId())
                .name(project.getName())
                .deadLine(project.getDeadLine());
    }

    public static ArrayList<ProjectPreview> ProjectsToPreviews(ArrayList<Project> projects) {
        ArrayList<ProjectPreview> projectPreviews = new ArrayList<>();

        projects.forEach((project -> {
            projectPreviews.add(ProjectToPreview(project));
        }));

        return projectPreviews;
    }
}
