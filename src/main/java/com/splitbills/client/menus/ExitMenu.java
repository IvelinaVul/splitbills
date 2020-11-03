package com.splitbills.client.menus;

import com.splitbills.client.SplitbillsClient;
import com.splitbills.client.UserLoginInfo;
import com.splitbills.client.io.Reader;
import com.splitbills.client.io.Writer;

public class ExitMenu extends Menu {
    public ExitMenu(SplitbillsClient splitbillsClient, UserLoginInfo storage, Reader reader, Writer writer) {
        super(splitbillsClient, storage, reader, writer);
    }
    @Override
    public void run() {
        writer.write("Closing application");
    }
}
