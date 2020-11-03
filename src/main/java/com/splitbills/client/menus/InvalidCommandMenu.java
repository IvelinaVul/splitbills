package com.splitbills.client.menus;

import com.splitbills.client.SplitbillsClient;
import com.splitbills.client.UserLoginInfo;
import com.splitbills.client.io.Reader;
import com.splitbills.client.io.Writer;

public class InvalidCommandMenu extends Menu {
    public InvalidCommandMenu(SplitbillsClient splitbillsClient, UserLoginInfo storage, Reader reader, Writer writer) {
        super(splitbillsClient, storage, reader, writer);
    }
    @Override
    public void run() {
        writer.writeWithNewLine("No such command");
    }
}
