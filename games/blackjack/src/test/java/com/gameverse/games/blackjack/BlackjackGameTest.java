package com.gameverse.games.blackjack;

import com.gameverse.core.GameResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {

    @Test
    void startsWithTwoCards() {
        BlackjackGame game = new BlackjackGame();
        game.start();

        assertEquals(2, game.getPlayerHand().size());
        assertEquals(2, game.getDealerHand().size());
        assertTrue(game.isPlayerTurn());
    }

    @Test
    void hitAddsCardToPlayerHand() {
        BlackjackGame game = new BlackjackGame();
        game.start();

        int before = game.getPlayerHand().size();
        assertTrue(game.hit());
        assertEquals(before + 1, game.getPlayerHand().size());
    }

    @Test
    void standEndsTheRound() {
        BlackjackGame game = new BlackjackGame();
        game.start();

        assertTrue(game.stand());
        assertTrue(game.isGameOver());
        assertNotNull(game.getResult());
    }

    @Test
    void handValueHandlesAceLikeOneOrEleven() {
        BlackjackGame game = new BlackjackGame();
        game.start();

        assertTrue(game.getPlayerScore() >= 2 && game.getPlayerScore() <= 21);
        assertTrue(game.getDealerScore() >= 2 && game.getDealerScore() <= 21);
    }
}
