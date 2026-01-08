package ru.yandex.practicum;

import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        WordleLogger logger = new WordleLogger("wordle.log");
        logger.log("Игра запущена");

        try {
            WordleDictionaryLoader loader = new WordleDictionaryLoader(logger);
            WordleDictionary dictionary = loader.loadWordleDictionary("words_ru.txt");

            WordleGame game = new WordleGame(dictionary, logger);
            try (Scanner scanner = new Scanner(System.in)) {
                game.startGame(scanner);
            }
            logger.log("Игра завершена успешно");
        } catch (Exception e) {
            logger.log("ОШИБКА: " + e.getMessage());
            System.out.println("ОШИБКА: " + e.getMessage());
        }
    }
}
