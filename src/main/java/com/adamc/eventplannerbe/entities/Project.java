package com.adamc.eventplannerbe.entities;

import com.adamc.eventplannerbe.models.TaskItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Project extends TaskItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date deadLine;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards;

    public Project() {
        super("", "");
        this.deadLine = null;
        this.boards = new ArrayList<>();
    }

    public Project(Builder builder) {
        super(builder.name, builder.details);
        this.deadLine = builder.deadLine;
        this.boards = builder.boards;
    }

    public Project(String name, String details) {
        super(name, details);
        this.deadLine = null;
        this.boards = new ArrayList<>();
    }

    public Project(String name, String details, Date deadLine) {
        super(name, details);
        this.deadLine = deadLine;
        this.boards = new ArrayList<>();
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public void addBoard(Board board) {
        this.boards.add(board);
    }

    public void removeBoard(Board board) {
        this.boards.remove(board);
    }

    public static class Builder {
        private String name;
        private String details;
        private Date deadLine;
        private List<Board> boards = new ArrayList<>();

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder details(String details) {
            this.details = details;
            return this;
        }

        public Builder deadLine(Date deadLine) {
            this.deadLine = deadLine;
            return this;
        }

        public Builder boards(List<Board> boards) {
            this.boards = boards;
            return this;
        }

        public Project build() {
            Project project = new Project(this);

            return project;
        }
    }
}
