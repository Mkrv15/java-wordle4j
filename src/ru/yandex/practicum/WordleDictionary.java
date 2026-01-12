package ru.yandex.practicum;

import java.util.*;

public class WordleDictionary {
    private final Random random = new Random();
    private final List<String> words;
    private final WordleLogger logger;


    public WordleDictionary(List<String> wordsInput, WordleLogger logger) {
        this.logger = logger;
        words = selectWordsForGame(wordsInput);
        logger.log("-------------Словарь отфильтрован-----------");
    }

    private static List<String> selectWordsForGame(List<String> inputWords) {
        if (inputWords == null || inputWords.isEmpty()) {
            throw new IllegalArgumentException("В WordleDictionary пришел пустой список слов из WordleDictionaryLoader");
        }
        List<String> newWords = new ArrayList<>();
        for (String word : inputWords) {
            if (word.length() == WordleGame.WORD_LENGTH) {
                newWords.add(word.toLowerCase().replace('ё', 'е'));
            }
        }
        if (newWords.isEmpty()) {
            throw new IllegalArgumentException("после фильтрации из пришедшего списка В WordleDictionary список пуст");
        }
        return newWords;
    }

    public String getRandomWord() {
        String randomWord = words.get(random.nextInt(words.size()));
        logger.log("Выбрано случайное слово: " + randomWord);
        return randomWord;
    }

    public boolean contains(String word) {
        boolean result = words.contains(word.toLowerCase().replace('ё', 'е'));
        logger.log("Проверка слова " + word + " в словаре:" + result);
        return result;
    }

    public int size() {
        return words.size();
    }

    public List<String> getAllWords() {
        return new ArrayList<>(words);
    }

    public String findWord(WordleGameConstraints constraints, Set<String> excludeWords) {
        logger.log("Поиск слова по ограничениям, исключено слов:" + excludeWords.size());
        List<String> matchingWords = new ArrayList<>();

        for (String word : words) {
            if (constraints.matches(word) && !excludeWords.contains(word)) {
                matchingWords.add(word);
            }
        }
        logger.log("Найдено подходящих слов:" + matchingWords.size());
        if (matchingWords.isEmpty()) {
            return null;
        }
        String result = matchingWords.get(random.nextInt(matchingWords.size()));
        logger.log("Выбрано слово:" + result);
        return result;
    }
}






















