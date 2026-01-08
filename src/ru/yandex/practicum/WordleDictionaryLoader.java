package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final WordleLogger logger;
    public WordleDictionaryLoader(WordleLogger logger) {
    this.logger=logger;
    }

    WordleDictionary loadWordleDictionary(String fileName) {
        logger.log("начало загрузки словаря из файла: "+ fileName);
        List<String> words = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))){
            int lineCount = 0;
            while (br.ready()){
                String word = br.readLine();
                lineCount++;
                if(!(word.isBlank())){
                    words.add(word);
                }
            }
            logger.log("Непустых слов: "+ words.size());
        } catch (FileNotFoundException e) {
            logger.log("ОШИБКА: Файл не найден - " + fileName);
            throw new RuntimeException("Файл "+fileName+" не найден");
        } catch (IOException e) {
            logger.log("ОШИБКА при чтении файла: " + e.getMessage());
            throw new RuntimeException(e);
        }
        WordleDictionary dictionary = new WordleDictionary(words,logger);
        logger.log("Словарь создан");
        return dictionary;
    }
}