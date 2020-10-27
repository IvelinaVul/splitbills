package com.splitbills.client.menus;

import com.splitbills.client.SplitbillsClient;
import com.splitbills.client.UserLoginInfo;
import com.splitbills.client.io.Reader;
import com.splitbills.client.io.Writer;

public abstract class Menu {

    protected SplitbillsClient splitbillsClient;
    protected UserLoginInfo userLoginInfo;
    protected Reader reader;
    protected Writer writer;

    public Menu(SplitbillsClient splitbillsClient, UserLoginInfo userLoginInfo, Reader reader, Writer writer) {
        this.splitbillsClient = splitbillsClient;
        this.userLoginInfo = userLoginInfo;
        this.reader = reader;
        this.writer = writer;
    }

    public abstract void run();
}
