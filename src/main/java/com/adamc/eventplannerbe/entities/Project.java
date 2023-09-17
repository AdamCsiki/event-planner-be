package com.adamc.eventplannerbe.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 255)
    private String details;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public void addBoard(Board board) {
        this.boards.add(board);
    }
}
