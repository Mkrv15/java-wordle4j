package ru.yandex.practicum;

import org.w3c.dom.ls.LSOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private static final int MAX_STEPS = 6;
    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private List<String> attemptsWithResults;
    private StatusGame statusGame;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void startGame(Scanner scanner) {
        answer = dictionary.getRandomWord();
        statusGame = StatusGame.IN_PROGRESS;
        steps = 0;
        System.out.println(answer);

        while (statusGame == StatusGame.IN_PROGRESS) {
            System.out.println("Введите слово: ");
            String userAnswer = scanner.nextLine();
            steps++;
            if (!dictionary.contains(userAnswer)) {
                //throw new RuntimeException("такого слова нет в словаре");
            } else if (userAnswer.equals(answer)) {
                statusGame = StatusGame.WON;
                System.out.println("win");
            } else if(steps<MAX_STEPS){
                System.out.println(getHint(userAnswer));
                System.out.printf("осталось %s попыток\n",MAX_STEPS-steps);
            }else{
                System.out.println("lost");
                statusGame = StatusGame.LOST;
                System.out.println(answer);
            }
        }
    }

    private String getHint(String userAnswer) {
        char[] answerChars = answer.toCharArray();
        char[] result = new char[5];
        for (int i = 0; i < userAnswer.length(); i++) {
            if (userAnswer.charAt(i) == answerChars[i]) {
                result[i] = '+';
                answerChars[i] = '_';
            }
        }
        for (int i = 0; i < userAnswer.length(); i++) {
            if(result[i]=='+'){
                continue;
            }
            boolean found = false;
            for (int j = 0; j < userAnswer.length(); j++){
                if(answerChars[j]!='_' && userAnswer.charAt(i)==answerChars[j]){
                    result[i] = '^';
                    answerChars[j] = '_';
                    found = true;
                    break;
                }
            }
            if (!found){
                result[i] = '-';
            }
        }
        return new String(result);
    }

}
