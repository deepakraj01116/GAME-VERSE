package com.gameverse.games.connectfour;

import com.gameverse.core.GameResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectFourGameTest {

    @Test
    void dropsDiscInLowestAvailableRow() {
        ConnectFourGame game = new ConnectFourGame();
        game.start();

        assertTrue(game.makeMove(0));
        char[][] board = game.getBoard();
        assertEquals('R', board[5][0]);
        assertEquals('Y', game.getCurrentPlayer());
    }

    @Test
    void detectsHorizontalWin() {
        ConnectFourGame game = new ConnectFourGame();
        game.start();

        game.makeMove(0);
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(1);
        game.makeMove(2);
        game.makeMove(2);
        game.makeMove(3);

        assertTrue(game.isGameOver());
        assertEquals(GameResult.Status.WON, game.getResult().getStatus());
    }

    @Test
    void detectsVerticalWin() {
        ConnectFourGame game = new ConnectFourGame();
        game.start();

        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);

        assertTrue(game.isGameOver());
        assertEquals(GameResult.Status.WON, game.getResult().getStatus());
    }

    @Test
    void rejectsInvalidColumnAndFullColumn() {
        ConnectFourGame game = new ConnectFourGame();
        game.start();

        assertFalse(game.makeMove(-1));
        assertFalse(game.makeMove(8));

        for (int i = 0; i < 6; i++) {
            game.makeMove(0);
        }
        assertFalse(game.makeMove(0));
    }
}
