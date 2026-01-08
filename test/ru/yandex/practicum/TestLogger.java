package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;

public class TestLogger extends WordleLogger {
    private final List<String> logs = new ArrayList<>();

    public TestLogger() {
        super("test.log");
    }

    @Override
    public void log(String message) {
    }
}
