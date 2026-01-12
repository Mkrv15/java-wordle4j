package ru.yandex.practicum;

import java.util.*;

public class WordleGame {

    private final String answer;
    private final WordleDictionary dictionary;
    private final WordleGameRound gameRound;
    private final WordleLogger logger;
    public static final int WORD_LENGTH = 5;

    public WordleGame(WordleDictionary dictionary, WordleLogger logger) {
        this.dictionary = dictionary;
        this.gameRound = new WordleGameRound();
        this.answer = dictionary.getRandomWord();
        this.logger = logger;
        logger.log("Игра создана, загаданное слово " + answer);
    }

    public void startGame(Scanner scanner) {
        logger.log("Старт игрового цикла");
        System.out.println("Игра началась! Угадайте слово из 5 букв.");
        System.out.println("'+' - буква на правильном месте, '^' - есть в слове, '-' - нет в слове");
        while (gameRound.canContinue()) {
            System.out.println("Введите слово (Enter - подсказка): ");
            String userAnswer = scanner.nextLine().trim();

            if (userAnswer.isEmpty()) {
                handleSuggestion(scanner);
                continue;
            }

            handleGuess(userAnswer);
            showProgress();
        }
        showGameResult();
    }

    private void handleGuess(String userInout) {
        logger.log("Попытка игрока: " + userInout);
        try {
            String userAnswer = userInout.toLowerCase().replace("ё", "е");
            validateGuess(userAnswer);

            String hint = getHint(userInout);
            gameRound.addAttempt(userAnswer, hint);

            logger.log("Результат попытки: " + hint + " для слова: " + userAnswer);
            System.out.println("Результат: " + hint);

            if (userAnswer.equals(answer)) {
                gameRound.markAsWon();
                logger.log("Игрок угадал слово");
            }
        } catch (InvalidGuessException | WordNotFoundInDictionary e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Ошибка игры: " + e.getMessage());
        }
    }

    private void showProgress() {
        System.out.println("Попыток использовано: " + gameRound.getSteps());
        System.out.println("Попыток осталось: " + gameRound.getStepsLeft());
    }

    private void showGameResult() {
        if (gameRound.getStatusGame() == StatusGame.WON) {
            System.out.println("Вы угадали слово!");
        } else {
            if (gameRound.getStatusGame() == StatusGame.IN_PROGRESS) {
                gameRound.markAsLost();
            }
            System.out.println("Игра закончена. Загаданное слово " + answer + '.');
        }
    }

    private String getSuggestion() {

        if (gameRound.getAttempts().isEmpty()) {
            return dictionary.getRandomWord();
        }
        WordleGameConstraints constraints = new WordleGameConstraints();
        List<String> attempts = gameRound.getAttempts();
        List<String> hints = gameRound.getHints();

        for (int i = 0; i < attempts.size(); i++) {
            constraints.addAttempt(attempts.get(i), hints.get(i));
        }

        Set<String> triedWords = new HashSet<>(gameRound.getAttempts());
        String suggestion = dictionary.findWord(constraints, triedWords);

        if (suggestion != null) {
            System.out.println(suggestion);
            return suggestion;
        }
        String randomUntriedWord = getRandomUntriedWord(triedWords);
        return randomUntriedWord;
    }

    public void handleSuggestion(Scanner scanner) {
        logger.log("Запрос подсказки");
        String suggestion = getSuggestion();

        if (suggestion == null) {
            logger.log("Не удалось предложить подсказку");
            System.out.println("Не могу предложить подсказку");
            return;
        }

        handleGuess(suggestion);

    }

    private String getRandomUntriedWord(Set<String> triedWords) {
        List<String> allWords = dictionary.getAllWords();
        List<String> untriedWords = new ArrayList<>();

        for (String word : allWords) {
            if (!triedWords.contains(word)) {
                untriedWords.add(word);
            }
        }
        if (untriedWords.isEmpty()) {
            return dictionary.getRandomWord();
        }
        Random random = new Random();
        return untriedWords.get(random.nextInt(untriedWords.size()));
    }

    private void validateGuess(String userAnswer) {
        if (userAnswer.length() != WORD_LENGTH) {
            throw new InvalidGuessException("Слово должно содержать 5 букв");
        } else {
            for (int i = 0; i < WORD_LENGTH; i++) {
                if (Character.isLetter(userAnswer.charAt(i))) {
                    throw new InvalidGuessException("Слово должно состоять из букв букв");
                }
            }
        }
        if (!dictionary.contains(userAnswer)) {
            throw new WordNotFoundInDictionary("Слова нет в словаре");
        }
    }

    private String getHint(String userAnswer) {
        char[] answerChars = answer.toCharArray();
        char[] result = new char[WORD_LENGTH];
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (userAnswer.charAt(i) == answerChars[i]) {
                result[i] = '+';
                answerChars[i] = '_';
            }
        }
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (result[i] == '+') {
                continue;
            }
            boolean found = false;
            for (int j = 0; j < WORD_LENGTH; j++) {
                if (answerChars[j] != '_' && userAnswer.charAt(i) == answerChars[j]) {
                    result[i] = '^';
                    answerChars[j] = '_';
                    found = true;
                    break;
                }
            }
            if (!found) {
                result[i] = '-';
            }
        }
        return new String(result);
    }
}
