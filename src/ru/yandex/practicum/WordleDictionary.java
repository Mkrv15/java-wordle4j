package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    private final Random random = new Random();
    private final List<String> words;

    public WordleDictionary(List<String> wordsInput)  {
        words = selectWordsForGame(wordsInput);
    }

    private static List<String> selectWordsForGame(List<String> inputWords)   {
        if(inputWords == null|| inputWords.isEmpty()){
         throw new IllegalArgumentException ("В WordleDictionary пришел пустой список слов из WordleDictionaryLoader");
        }
        List<String> newWords = new ArrayList<>();
        for (String word : inputWords) {
            if (word.length() == 5) {
                newWords.add(word.toLowerCase().replace('ё', 'е'));
            }
        }
        if(newWords.isEmpty()){
            throw new IllegalArgumentException ("после фильтрации из пришедшего списка В WordleDictionary список пуст");
        }
        return newWords;
    }

    public String getRandomWord(){
        if(words.isEmpty()){
            throw new IndexOutOfBoundsException("словарь пуст");
        }
        return words.get(random.nextInt(words.size()));
    }
    public boolean contains(String word){
        return words.contains(word.toLowerCase().replace('ё','е'));
    }
    public int size(){
        return words.size();
    }
}






















