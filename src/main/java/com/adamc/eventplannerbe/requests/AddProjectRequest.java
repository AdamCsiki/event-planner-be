package com.adamc.eventplannerbe.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProjectRequest {
    private String name;
    private Date deadLine;
}
