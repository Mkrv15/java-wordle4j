package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;

public class WordleGameRound {
    public static final int MAX_STEPS = 6;
    private StatusGame statusGame;
    private int steps;
    private List<String> attempts;
    private List<String> hints;

    public WordleGameRound() {
        statusGame = StatusGame.IN_PROGRESS;
        steps = 0;
        attempts = new ArrayList<>();
        hints = new ArrayList<>();
    }

    public StatusGame getStatusGame() {
        return statusGame;
    }

    public int getSteps() {
        return steps;
    }

    public int getStepsLeft() {
        int stepsLeft = MAX_STEPS - steps;
        return Math.max(stepsLeft, 0);
    }

    public List<String> getAttempts() {
        return new ArrayList<>(attempts);
    }

    public List<String> getHints() {
        return new ArrayList<>(hints);
    }

    public void addAttempt(String word, String hint) {
        if (statusGame != StatusGame.IN_PROGRESS) {
            throw new IllegalArgumentException("Игра закончена");
        }

        if (steps >= MAX_STEPS) {
            throw new IllegalArgumentException("Превышено максимальное количество попыток");
        }
        attempts.add(word);
        hints.add(hint);
        steps++;
    }

    public void markAsWon() {
        if (statusGame != StatusGame.IN_PROGRESS) {
            throw new IllegalStateException("Игра уже закончена");
        }
        statusGame = StatusGame.WON;
    }

    public void markAsLost() {
        if (statusGame != StatusGame.IN_PROGRESS) {
            throw new IllegalStateException("Игра уже закончена");
        }
        statusGame = StatusGame.LOST;
    }

    public boolean canContinue() {
        return statusGame == StatusGame.IN_PROGRESS && steps < MAX_STEPS;
    }
}
