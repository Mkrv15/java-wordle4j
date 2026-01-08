package ru.yandex.practicum;

public class InvalidGuessException extends RuntimeException {
    public InvalidGuessException(String message) {
        super(message);
    }
}
