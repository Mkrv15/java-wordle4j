package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class WordleGameRoundTest {
    private WordleGameRound gameRound;


    @BeforeEach
    void setUp() {
        gameRound = new WordleGameRound();
    }

    @Test
    void testInitialState(){
        Assertions.assertEquals(StatusGame.IN_PROGRESS,gameRound.getStatusGame());
        Assertions.assertEquals(0,gameRound.getSteps());
        Assertions.assertEquals(6,gameRound.getStepsLeft());
        Assertions.assertTrue(gameRound.canContinue());
    }

    @Test
    void testAddAttemptIncreasesSteps(){
        gameRound.addAttempt("слово","+----");

        Assertions.assertEquals(1,gameRound.getSteps());
        Assertions.assertEquals(5,gameRound.getStepsLeft());
    }

    @Test
    void testAddAttemptThrowsExceptionWhenMaxStepsReached(){
        for (int i = 0; i<6; i++){
            gameRound.addAttempt("слов"+i,"-----");
        }
        Assertions.assertEquals(6,gameRound.getSteps());
        Assertions.assertEquals(0,gameRound.getStepsLeft());

    }

    @Test
    void testAddAttemptSavesWordAndHint(){
        gameRound.addAttempt("слово","+----");

        List<String> attempts = gameRound.getAttempts();
        List<String> hints = gameRound.getHints();

        Assertions.assertEquals(1,attempts.size());
        Assertions.assertEquals(1,hints.size());
        Assertions.assertEquals("слово",attempts.getFirst());
        Assertions.assertEquals("+----",hints.getFirst());
    }

    @Test
    void testAddAttemptMultipleTimes(){
        gameRound.addAttempt("слов1","+----");
        gameRound.addAttempt("слов2","-+---");
        gameRound.addAttempt("слов3","--+--");

        Assertions.assertEquals(3,gameRound.getSteps());
        Assertions.assertEquals(3,gameRound.getStepsLeft());

        List<String> attempts = gameRound.getAttempts();
        Assertions.assertEquals(3, attempts.size());
        Assertions.assertEquals("слов1", attempts.get(0));
        Assertions.assertEquals("слов2", attempts.get(1));
        Assertions.assertEquals("слов3", attempts.get(2));
    }

    @Test
    void testMarkAsWonChangesStatus(){
        gameRound.markAsWon();

        Assertions.assertEquals(StatusGame.WON,gameRound.getStatusGame());
        Assertions.assertFalse(gameRound.canContinue());
    }

    @Test
    void testMarkAsLostChangesStatus(){
        gameRound.markAsLost();

        Assertions.assertEquals(StatusGame.LOST,gameRound.getStatusGame());
        Assertions.assertFalse(gameRound.canContinue());
    }

    @Test
    void testCanContinueReturnsFalseWhenMaxStepsReached(){
        for (int i = 0; i < 6 ;i++){
            gameRound.addAttempt("слов"+i,"-----");
        }
        Assertions.assertFalse(gameRound.canContinue());
    }

    @Test
    void testGetAttemptsReturnsCopy(){
        gameRound.addAttempt("слово","+----");

        List<String> attempts = gameRound.getAttempts();
        attempts.add("новое");
        List<String> attemptsAgain = gameRound.getAttempts();

        Assertions.assertEquals(1,attemptsAgain.size());
        Assertions.assertEquals("слово",attemptsAgain.getFirst());
    }
    @Test
    void testGetHintsReturnsCopy(){
        gameRound.addAttempt("слово","+----");

        List<String> hints = gameRound.getHints();
        hints.add("новое");
        List<String> hintsAgain = gameRound.getHints();

        Assertions.assertEquals(1,hintsAgain.size());
        Assertions.assertEquals("+----",hintsAgain.getFirst());
    }


}
