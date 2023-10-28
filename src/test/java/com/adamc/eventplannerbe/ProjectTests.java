package com.adamc.eventplannerbe;

import com.adamc.eventplannerbe.entities.Project;
import com.adamc.eventplannerbe.validator.ProjectValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTests {
    private final ProjectValidator projectValidator = new ProjectValidator();

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\r", "\t", "\n"})
    void invalidNameTest(String input) {
        assertFalse(projectValidator.isNameValid(input));
    }

    @Test
    void nullNameTest() {
        assertFalse(projectValidator.isNameValid(null));
    }

    @Test
    void nullDetailsTest() {
        assertFalse(projectValidator.isDetailsValid(null));
    }

    @Test
    void invalidProjectsTest() {
        assertFalse(projectValidator.isBoardsNonNull(null));
    }

    @Test
    void projectBuilderTest() {
        Date testDate = new Date();

        Project project = new Project("Project", "Details", testDate);

        Project projectBuilt = new Project
                .Builder()
                .name("Project")
                .details("Details")
                .deadLine(testDate)
                .build();

        assertThat(project)
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(projectBuilt);
    }
}
