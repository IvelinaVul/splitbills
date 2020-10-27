package com.splitbills.client.io;

public interface Reader {

    String readNext();

    String[] readAllTillNextLine();
}
