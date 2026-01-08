package ru.yandex.practicum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordleGameConstraints {
    private final Character[] knownPositions = new Character[5];
    private final Set<Character> presentLetters = new HashSet<>();
    private final Set<Character> absentLetters = new HashSet<>();
    private final Map<Character, Set<Integer>> forbiddenPositions = new HashMap<>();

    public void addAttempt(String word, String hint) {

        for (int i = 0; i < 5; i++) {
            char letter = word.charAt(i);
            char status = hint.charAt(i);

            switch (status) {
                case '+':
                    knownPositions[i] = letter;
                    presentLetters.add(letter);
                    break;
                case '^':
                    presentLetters.add(letter);
                    addForbiddenPosition(letter, i);
                    break;
                case '-':
                    if (!presentLetters.contains(letter)) {
                        absentLetters.add(letter);
                    }
                    break;
            }
        }
    }

    private void addForbiddenPosition(char letter, int position) {
        Set<Integer> positions = forbiddenPositions.get(letter);
        if (positions == null) {
            positions = new HashSet<>();
            forbiddenPositions.put(letter, positions);
        }
        positions.add(position);
    }

    public boolean matches(String word) {
        for (int i = 0; i < 5; i++) {
            if (knownPositions[i] != null) {
                if (word.charAt(i) != knownPositions[i]) {
                    return false;
                }
            }
        }
        for (Character letter : presentLetters) {
            boolean found = false;
            for (int i = 0; i < 5; i++) {
                if (word.charAt(i) == letter) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        for (Character letter : absentLetters) {
            for (int i = 0; i < 5; i++) {
                if (word.charAt(i) == letter) {
                    return false;
                }
            }
        }
        for (Map.Entry<Character, Set<Integer>> entry : forbiddenPositions.entrySet()) {
            char letter = entry.getKey();
            ;
            Set<Integer> forbidden = entry.getValue();

            for (int i = 0; i < 5; i++) {
                if (word.charAt(i) == letter && forbidden.contains(i)) {
                    return false;
                }
            }
        }
        return true;
    }
}
