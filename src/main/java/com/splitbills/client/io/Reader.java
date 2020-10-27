package com.splitbills.client.io;

public interface Reader {

    String readNextLine();

    String[] readAllTillNextLine();
}
