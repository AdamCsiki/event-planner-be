package com.adamc.eventplannerbe.validator;

import com.adamc.eventplannerbe.entities.Board;
import com.adamc.eventplannerbe.entities.Project;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class ProjectValidator {

    public boolean isProjectValid(Project project) {
        return project != null &&
                isNameValid(project.getName()) &&
                isDetailsValid(project.getDetails()) &&
                isDeadLineValid(project.getDeadLine()) &&
                isBoardsNonNull(project.getBoards());
    }

    public boolean isNameValid(String name) {
        return name != null && !name.isEmpty() && name.matches("^(?!\\s+$)[A-Za-z0-9!@#$%^&*() ]*$") && name.length() <= 50;
    }

    public boolean isDetailsValid(String details) {
        return details != null && details.matches("^[A-Za-z0-9!@#$%^&*()-_=+;:'\",<.>/?|\t\n\r\s]*$") && details.length() <= 255;
    }

    public boolean isDeadLineValid(Date deadLine) {
        return deadLine != null && deadLine.before(new Date());
    }

    public boolean isBoardsNonNull(List<Board> boards) {
        return boards != null;
    }
}
