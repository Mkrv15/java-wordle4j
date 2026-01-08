package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WordleGameConstraintsTest {
    private WordleGameConstraints constraints;

    @BeforeEach
    void setUp(){
        constraints = new WordleGameConstraints();
    }

    @Test
    void testAddAttemptWithAllCorrect(){
        constraints.addAttempt("столы", "+++++");

        Assertions.assertTrue(constraints.matches("столы"));
        Assertions.assertFalse(constraints.matches("дрова"));
        Assertions.assertFalse(constraints.matches("ствол"));
        Assertions.assertFalse(constraints.matches("слоны"));
    }
    @Test
    void testAddAttemptWithAllAbsent(){
        constraints.addAttempt("столы", "-----");

        Assertions.assertFalse(constraints.matches("столы"));
        Assertions.assertFalse(constraints.matches("дрова"));
        Assertions.assertFalse(constraints.matches("ствол"));
        Assertions.assertFalse(constraints.matches("слоны"));
    }
    @Test
    void testAddAttemptWithFirstLetterCorrect(){
        constraints.addAttempt("столы", "^----");

        Assertions.assertFalse(constraints.matches("столы"));
        Assertions.assertFalse(constraints.matches("дрова"));
        Assertions.assertFalse(constraints.matches("ствол"));
        Assertions.assertFalse(constraints.matches("слоны"));
    }
}
