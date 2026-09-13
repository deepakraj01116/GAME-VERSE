package com.gameverse.games.blackjack;

import com.gameverse.core.Difficulty;
import com.gameverse.core.GameResult;
import com.gameverse.games.core.BaseGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A streamlined Blackjack game for the GameVerse platform.
 */
public class BlackjackGame extends BaseGame {
    private static final int TARGET = 21;
    private static final int DEALER_STAND = 17;

    private final Random random = new Random();
    private final List<Integer> playerHand = new ArrayList<>();
    private final List<Integer> dealerHand = new ArrayList<>();
    private boolean playerTurn = true;
    private boolean gameOver = false;

    public BlackjackGame() {
        super("Blackjack");
        initialize();
    }

    @Override
    public void initialize() {
        playerHand.clear();
        dealerHand.clear();
        playerTurn = true;
        gameOver = false;
        result = null;
        score = 0;
        dealInitialCards();
    }

    @Override
    public void start() {
        super.start();
        initialize();
    }

    @Override
    public void restart() {
        super.restart();
        initialize();
    }

    @Override
    public void update(float deltaTime) {
        if (gameOver) {
            return;
        }
        if (!playerTurn) {
            dealerTurn();
        }
    }

    public boolean hit() {
        if (!isRunning() || gameOver || !playerTurn) {
            return false;
        }

        playerHand.add(drawCard());
        if (handValue(playerHand) > TARGET) {
            finishRound(GameResult.Status.LOST, 0);
            return true;
        }
        if (handValue(playerHand) == TARGET) {
            stand();
        }
        return true;
    }

    public boolean stand() {
        if (!isRunning() || gameOver || !playerTurn) {
            return false;
        }
        playerTurn = false;
        dealerTurn();
        return true;
    }

    public List<Integer> getPlayerHand() {
        return new ArrayList<>(playerHand);
    }

    public List<Integer> getDealerHand() {
        return new ArrayList<>(dealerHand);
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getPlayerScore() {
        return handValue(playerHand);
    }

    public int getDealerScore() {
        return handValue(dealerHand);
    }

    private void dealInitialCards() {
        playerHand.add(drawCard());
        dealerHand.add(drawCard());
        playerHand.add(drawCard());
        dealerHand.add(drawCard());

        if (handValue(playerHand) == TARGET) {
            finishRound(GameResult.Status.WON, 200);
        }
    }

    private void dealerTurn() {
        while (handValue(dealerHand) < DEALER_STAND) {
            dealerHand.add(drawCard());
        }

        int playerValue = handValue(playerHand);
        int dealerValue = handValue(dealerHand);
        if (dealerValue > TARGET) {
            finishRound(GameResult.Status.WON, 200);
        } else if (playerValue > dealerValue) {
            finishRound(GameResult.Status.WON, 200);
        } else if (playerValue == dealerValue) {
            finishRound(GameResult.Status.DRAWN, 100);
        } else {
            finishRound(GameResult.Status.LOST, 0);
        }
    }

    private void finishRound(GameResult.Status status, int amount) {
        gameOver = true;
        isRunning = false;
        score = amount;
        result = new GameResult(getName(), status, score, 0L);
        playerTurn = false;
    }

    private int drawCard() {
        return random.nextInt(10) + 1;
    }

    private int handValue(List<Integer> hand) {
        int total = 0;
        int aces = 0;
        for (int value : hand) {
            total += value;
            if (value == 11) {
                aces++;
            }
        }
        while (total > TARGET && aces > 0) {
            total -= 10;
            aces--;
        }
        return total;
    }
}
