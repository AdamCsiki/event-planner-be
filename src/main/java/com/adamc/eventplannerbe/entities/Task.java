package com.adamc.eventplannerbe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 255)
    private String details;

    @Column
    private Boolean taken;

    public Task(String name, String details) {
        this.name = name;
        this.details = details;
        this.taken = false;
    }
}
