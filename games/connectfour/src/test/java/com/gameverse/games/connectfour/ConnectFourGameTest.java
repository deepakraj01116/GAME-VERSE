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
    void detectsDiagonalWinDownRight() {
        ConnectFourGame game = new ConnectFourGame();
        game.start();

        // Builds a "\" diagonal: (5,0), (4,1), (3,2), (2,3) all Red.
        // Yellow fills the support cells underneath each diagonal target.
        int[] columns = {0, 1, 1, 2, 2, 3, 3, 3, 2, 6, 3};
        for (int col : columns) {
            game.makeMove(col);
        }
        // Board after the sequence:
        //   R: (5,0) (4,1) (4,2) (4,3) (3,2) (2,3)
        //   Y: (5,1) (5,2) (5,3) (3,3) (5,6)
        // The final R at (2,3) completes (5,0)-(4,1)-(3,2)-(2,3).

        assertTrue(game.isGameOver(), "down-right diagonal win should end the game");
        assertEquals(GameResult.Status.WON, game.getResult().getStatus());
    }

    @Test
    void detectsDiagonalWinUpRight() {
        ConnectFourGame game = new ConnectFourGame();
        game.start();

        // Builds a "/" diagonal: (5,3), (4,2), (3,1), (2,0) all Red.
        int[] columns = {3, 2, 2, 1, 1, 0, 0, 0, 1, 6, 0};
        for (int col : columns) {
            game.makeMove(col);
        }
        // Board after the sequence:
        //   R: (5,3) (4,2) (4,1) (4,0) (3,1) (2,0)
        //   Y: (5,2) (5,1) (5,0) (3,0) (5,6)
        // The final R at (2,0) completes (5,3)-(4,2)-(3,1)-(2,0).

        assertTrue(game.isGameOver(), "up-right diagonal win should end the game");
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
