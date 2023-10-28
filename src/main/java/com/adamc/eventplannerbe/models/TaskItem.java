package com.adamc.eventplannerbe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class TaskItem {
    @Column(length = 50)
    private String name;
    @Lob
    @Column
    private String details;
    @Column
    private final Date createdAt = new Date();

    public TaskItem(String name, String details) {
        this.name = name;
        this.details = details;
    }
}
