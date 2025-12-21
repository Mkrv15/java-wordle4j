package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    WordleDictionary loadWordleDictionary( String fileName) {
        List<String> words = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))){
            while (br.ready()){
                String word = br.readLine();
                if(!(word.isBlank())){
                    words.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл "+fileName+" не найден");
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return new WordleDictionary(words);
    }
}