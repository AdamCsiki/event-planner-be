package com.adamc.eventplannerbe.controllers;

import com.adamc.eventplannerbe.entities.Board;
import com.adamc.eventplannerbe.entities.Project;
import com.adamc.eventplannerbe.requests.AddTaskRequest;
import com.adamc.eventplannerbe.requests.EditBoardRequest;
import com.adamc.eventplannerbe.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("projects/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * PUT REQUESTS
     */
    @PutMapping("/{id}/add_task")
    public Board addTask(@RequestBody AddTaskRequest addTaskRequest, @PathVariable Long id) {
        Board board = boardService.getOneById(id);

        boardService.addNewTask(board, addTaskRequest.getTaskName(), addTaskRequest.getTaskDetails());
        boardService.putBoard(board);

        return boardService.getOneById(id);
    }

    @PutMapping("/{id}/edit_board")
    public Board editBoard(@RequestBody EditBoardRequest editBoardRequest, @PathVariable Long id) {
        Board board = boardService.getOneById(editBoardRequest.getBoardId());

        boardService.editBoard(board, editBoardRequest.getBoardName());
        boardService.putBoard(board);

        return boardService.getOneById(editBoardRequest.getProjectId());
    }
}
