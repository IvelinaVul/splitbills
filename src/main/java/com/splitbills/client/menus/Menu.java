package com.splitbills.client.menus;

import com.splitbills.client.SplitbillsClient;
import com.splitbills.client.UserStorage;
import com.splitbills.client.io.Reader;
import com.splitbills.client.io.Writer;

public abstract class Menu {

    protected SplitbillsClient splitbillsClient;
    protected UserStorage userStorage;
    protected Reader reader;
    protected Writer writer;

    public Menu(SplitbillsClient splitbillsClient, UserStorage userStorage, Reader reader, Writer writer) {
        this.splitbillsClient = splitbillsClient;
        this.userStorage = userStorage;
        this.reader = reader;
        this.writer = writer;
    }

    public abstract void run();
}
