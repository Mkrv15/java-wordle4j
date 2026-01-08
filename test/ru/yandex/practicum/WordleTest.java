package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    @Test
    void testDictionaryFileExists() {
        File file = new File("words_ru.txt");
        assertTrue(file.exists());
    }
}
