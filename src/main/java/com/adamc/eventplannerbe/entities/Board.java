package com.adamc.eventplannerbe.entities;

import com.adamc.eventplannerbe.models.TaskItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Board extends TaskItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    public Board() {
        super("", "");
        this.tasks = new ArrayList<>();
    }

    public Board(String name, String details) {
        super(name, details);
        this.tasks = new ArrayList<>();
    }
    private Board(Builder builder) {
        super(builder.name, builder.details);
        this.tasks = builder.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public static class Builder {
        private Long id;
        private String name;
        private String details;
        private List<Task> tasks;

        public Builder() {
            this.tasks = new ArrayList<>();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder details(String details) {
            this.details = details;
            return this;
        }

        public Builder tasks(List<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public Board build() {
            Board board = new Board(this);
            return board;
        }
    }
}
