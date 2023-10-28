package com.adamc.eventplannerbe;

import com.adamc.eventplannerbe.entities.Board;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTests {

    @Test
    void boardBuilderTest() {
        Date testDate = new Date();

        Board board = new Board("Board", "Details");

        Board boardBuilt = new Board
                .Builder()
                .name("Board")
                .details("Details")
                .build();

        assertThat(board)
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(boardBuilt);
    }
}
