package com.adamc.eventplannerbe.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditBoardRequest {
    private Long projectId;
    private Long boardId;
    private String boardName;
}
