package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WordleLogger {
    private final String fileName;

    public WordleLogger(String fileName) {
        this.fileName = fileName;
    }

    public void log(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(message);
        } catch (IOException e) {
            System.out.println("Ошибка записи в лог" + e.getMessage());
        }
    }
}
