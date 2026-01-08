package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class WordleDictionaryTest {
    private TestLogger logger = new TestLogger();
    private WordleDictionary dictionary;

    @Test
    void testConstructorFiltersFiveLetterWords(){
        List<String> inputWords = Arrays.asList("копье","дождь","ствол","крот","кот","дом","дрова","балет");
        dictionary = new WordleDictionary(inputWords,logger);
        Assertions.assertEquals(5,dictionary.size());
     }

     @Test
    void testContainsNormalizesInput(){
         List<String> inputWords = Arrays.asList("копье","дождь","ствол");
         dictionary = new WordleDictionary(inputWords,logger);

         Assertions.assertTrue(dictionary.contains("копье"));
         Assertions.assertTrue(dictionary.contains("копьё"));
         Assertions.assertTrue(dictionary.contains("КОпье"));
         Assertions.assertFalse(dictionary.contains("капье"));
         Assertions.assertFalse(dictionary.contains("копь"));
         Assertions.assertFalse(dictionary.contains("копьее"));
     }

     @Test
    void TestGetRandomWord(){
         List<String> inputWords = Arrays.asList("копье","дождь","ствол");
         dictionary = new WordleDictionary(inputWords,logger);

         String word = dictionary.getRandomWord();

         Assertions.assertNotNull(word);
         Assertions.assertTrue(dictionary.contains(word));
         Assertions.assertEquals(5,word.length());
     }

     @Test
    void testGetAllWordsReturnsCopy(){
         List<String> inputWords = Arrays.asList("копье","дождь","ствол");
         dictionary = new WordleDictionary(inputWords,logger);

         List<String> words = dictionary.getAllWords();
         words.add("новое");

         Assertions.assertEquals(3,dictionary.size());
         Assertions.assertFalse(dictionary.contains("новое"));
     }

     @Test
    void testFindWordWithConstrains(){
         List<String> inputWords = Arrays.asList("копье","дождь","ствол","сдача");
         dictionary = new WordleDictionary(inputWords,logger);

         WordleGameConstraints constraints = new WordleGameConstraints();
         constraints.addAttempt("ствол","+----");

         HashSet<String> excludeWords = new HashSet<>();
         excludeWords.add("ствол");

         String found = dictionary.findWord(constraints,excludeWords);

         Assertions.assertNotNull(found);
         Assertions.assertEquals(5,found.length());
         Assertions.assertTrue(found.startsWith("с"));
         Assertions.assertNotEquals("ствол",found);
         Assertions.assertTrue(dictionary.contains(found));
     }

     @Test
    void testFindWordReturnsNullWhenNoMatches(){
         List<String> inputWords = Arrays.asList("копье","дождь","ствол");
         dictionary = new WordleDictionary(inputWords,logger);

         WordleGameConstraints constraints = new WordleGameConstraints();
         constraints.addAttempt("ххххх","+----");

         HashSet<String> excludeWords = new HashSet<>();

        String found = dictionary.findWord(constraints,excludeWords);

        Assertions.assertNull(found);
     }

     @Test
    void testGetRandomWordMultipleTimes(){
         List<String> inputWords = Arrays.asList("копье","дождь","ствол");
         dictionary = new WordleDictionary(inputWords,logger);

         boolean gotSpear = false;
         boolean gotRain = false;
         boolean gotTrunk = false;

         for (int i = 0; i<100 ; i++){
             String word = dictionary.getRandomWord();

             if (word.equals("копье")) gotSpear = true;
             if (word.equals("дождь")) gotRain = true;
             if (word.equals("ствол")) gotTrunk = true;

            Assertions.assertTrue(dictionary.contains(word));
         }
         Assertions.assertTrue(gotSpear);
         Assertions.assertTrue(gotRain);
         Assertions.assertTrue(gotTrunk);
     }
}
