package com.splitbills.client.io;

public class SimpleWriter implements Writer {

    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void writeWithNewLine(String message) {
        System.out.println(message);
    }

    @Override
    public void writeNewLine() {
        System.out.println();
    }
}
