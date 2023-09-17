package com.adamc.eventplannerbe.service;

import com.adamc.eventplannerbe.entities.Board;
import com.adamc.eventplannerbe.entities.Task;
import com.adamc.eventplannerbe.repos.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;


    /**
     * GET REQUESTS
     */
    public Board getOneById(Long id) {
        return boardRepository.findOneById(id);
    }

    /**
     * PUT REQUESTS
     */
    public Board putBoard(Board board) {
        boardRepository.save(board);

        return boardRepository.findOneById(board.getId());
    }

    public Board editBoard(Board board, String name) {
        board.setName(name);

        return board;
    }

    public Board addNewTask(Board board, String name, String details) {
        Task newTask = new Task(name, details);

        board.addTask(newTask);

        return board;
    }
}
