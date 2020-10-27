package com.splitbills.client.io;

public interface Writer {

    void write(String message);

    void writeWithNewLine(String message);

    void writeNewLine();
}
