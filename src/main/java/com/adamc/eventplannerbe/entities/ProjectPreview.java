package com.adamc.eventplannerbe.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class ProjectPreview {
    private Long id;
    private String name;
    private Date deadLine;

    public ProjectPreview id(Long id) {
        this.id = id;

        return this;
    }

    public ProjectPreview name(String name) {
        this.name = name;

        return this;
    }

    public ProjectPreview deadLine(Date deadLine) {
        this.deadLine = deadLine;

        return this;
    }
}
