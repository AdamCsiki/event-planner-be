package com.adamc.eventplannerbe;

import com.adamc.eventplannerbe.entities.Project;
import com.adamc.eventplannerbe.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserTests {
    private final UserValidator userValidator = new UserValidator();
    @ParameterizedTest
    @ValueSource(strings = {"aa ", "a a", "a!!!", "   "})
    void invalidNameTest(String input) {
        assertFalse(userValidator.isNameValid(input));
    }

    @Test
    void nullNameTest() {
        assertFalse(userValidator.isNameValid(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"@", ".com", " ", " @ ", "aaa@@aaa", "abc abc.com"})
    void invalidEmailTest(String input) {
        assertFalse(userValidator.isEmailValid(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", "-_=+[]{};:'\",<.>/?\\|"})
    void invalidPassword(String input) {
        assertFalse(userValidator.isPasswordValid(input));
    }

    @Test
    void invalidProjects() {
        assertFalse(userValidator.isProjectsNonNull(null));
    }
}
