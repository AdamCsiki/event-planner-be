package com.adamc.eventplannerbe.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBoardRequest {
    private Long projectId;
    private String boardName;
}
