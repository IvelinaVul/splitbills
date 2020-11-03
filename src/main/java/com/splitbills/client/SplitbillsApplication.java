package com.splitbills.client;

import com.splitbills.client.io.Reader;
import com.splitbills.client.io.SimpleReader;
import com.splitbills.client.io.SimpleWriter;
import com.splitbills.client.io.Writer;
import com.splitbills.client.menus.HomeMenu;
import com.splitbills.logging.Logger;

import java.io.IOException;

public class SplitbillsApplication {

    private final static Logger LOGGER = Logger.getLogger(SplitbillsApplication.class.getName());
    private final SplitbillsClient splitbillsClient;
    private final Reader reader;
    private final Writer writer;
    private final UserLoginInfo storage;
    private boolean isRunning = true;

    public SplitbillsApplication() {
        splitbillsClient = new SplitbillsClient();
        storage = new UserLoginInfo();
        writer = new SimpleWriter();
        reader = new SimpleReader();
    }

    public void run() {
        HomeMenu homeMenu = new HomeMenu(splitbillsClient, storage, reader, writer);
        homeMenu.run();
        try {
            closeApplication();
        } catch (IOException ioException) {
            writer.writeWithNewLine("Could not close the application correctly");
        }
    }

    private void closeApplication() throws IOException {
        isRunning = false;
        splitbillsClient.close();
    }

    public static void main(String[] args) {
        SplitbillsApplication application = new SplitbillsApplication();
        application.run();
    }
}