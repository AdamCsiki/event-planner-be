package com.adamc.eventplannerbe;

import com.adamc.eventplannerbe.validator.ProjectValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
    void invalidProjects() {
        assertFalse(projectValidator.isBoardsNonNull(null));
    }
}
