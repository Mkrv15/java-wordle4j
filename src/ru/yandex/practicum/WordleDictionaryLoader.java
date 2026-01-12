package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {
    private final WordleLogger logger;

    public WordleDictionaryLoader(WordleLogger logger) {
        this.logger = logger;
    }

    WordleDictionary loadWordleDictionary(String fileName) {
        logger.log("начало загрузки словаря из файла: " + fileName);
        List<String> words = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            while ((line = br.readLine()) != null) {
                if (!(line.isBlank())) {
                    words.add(line);
                }
            }
            logger.log("Непустых слов: " + words.size());
        } catch (FileNotFoundException e) {
            logger.log("ОШИБКА: Файл не найден - " + fileName);
            throw new RuntimeException("Файл " + fileName + " не найден");
        } catch (IOException e) {
            logger.log("ОШИБКА при чтении файла: " + e.getMessage());
            throw new RuntimeException(e);
        }
        WordleDictionary dictionary = new WordleDictionary(words, logger);
        logger.log("Словарь создан");
        return dictionary;
    }
}