package ru.otus.hw.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class StreamsIOService implements IOService {
    private final PrintStream printStream;

    private final Scanner input;

    public StreamsIOService(PrintStream printStream, InputStream inputStream) {
        this.printStream = printStream;
        this.input = new Scanner(inputStream);
    }

    @Override
    public void printLine(String s) {
        printStream.println(s);
    }

    @Override
    public void printFormattedLine(String s, Object... args) {
        printStream.printf(s + "%n", args);
    }

    @Override
    public int readInt() {
        return input.nextInt();
    }
}
