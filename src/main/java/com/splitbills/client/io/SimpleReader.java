package com.splitbills.client.io;

import java.util.Scanner;

public class SimpleReader implements Reader {

    private Scanner scanner;
    private String delimiter;

    public SimpleReader() {
        delimiter = " ";
        scanner = new Scanner(System.in);
    }
    public String readNextLine() {
        return scanner.nextLine().trim();
    }

    public String[] readAllTillNextLine() {
        String line = scanner.nextLine();
        return line.split(delimiter);
    }
}
