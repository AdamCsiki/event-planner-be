package com.adamc.eventplannerbe.entities;

import com.adamc.eventplannerbe.models.TaskItem;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Task extends TaskItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean finished;

    public Task() {
        super("", "");
        this.finished = false;
    }

    public Task(String name, String details) {
        super(name, details);
        this.finished = false;
    }

    public Task(Builder builder) {
        super(builder.name, builder.details);
        this.finished = builder.finished;
    }

    public void finish() {
        this.finished = true;
    }

    public static class Builder {
        private String name;
        private String details;
        private Boolean finished;

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

        public Builder finished(Boolean finished) {
            this.finished = finished;
            return this;
        }

        public Task build() {
            Task task = new Task(this);
            return task;
        }
    }
}
