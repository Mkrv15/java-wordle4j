package ru.yandex.practicum;

import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        WordleLogger logger = new WordleLogger("wordle.log");
        logger.log("Игра запущена");

        try{
            WordleDictionaryLoader loader = new WordleDictionaryLoader(logger);
            WordleDictionary dictionary = loader.loadWordleDictionary("words_ru.txt");

            WordleGame game = new WordleGame(dictionary,logger);
            try(Scanner scanner = new Scanner(System.in)){
                game.startGame(scanner);
            }
            logger.log("Игра завершена успешно");
        }catch (Exception e){
            logger.log("ОШИБКА: " + e.getMessage());
            System.out.println("ОШИБКА: " + e.getMessage());
        }
    }

}
