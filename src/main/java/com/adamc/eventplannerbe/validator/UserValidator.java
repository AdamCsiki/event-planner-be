package com.adamc.eventplannerbe.validator;

import com.adamc.eventplannerbe.entities.Project;
import com.adamc.eventplannerbe.entities.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class UserValidator {

    public boolean isUserValid(User user) {
        return user != null &&
                isNameValid(user.getName()) &&
                isEmailValid(user.getEmail()) &&
                isPasswordValid(user.getPassword()) &&
                isProjectsNonNull(user.getProjects());
    }

    public boolean isNameValid(String name) {
        return name != null && !name.isEmpty() && name.matches("^[A-Za-z0-9]*$") && name.length() <= 25;
    }

    public boolean isEmailValid(String email) {
        return email != null && !email.isEmpty() && EmailValidator.getInstance().isValid(email) && email.length() <= 50;
    }

    public boolean isPasswordValid(String password) {
        return password != null && !password.isEmpty() && password.matches("^[a-zA-Z0-9!@#$%^&*()]*$");
    }

    public boolean isProjectsNonNull(List<Project> projects) {
        return projects != null;
    }
}
