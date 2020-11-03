package com.splitbills.client.menus;

import com.splitbills.client.CommandName;
import com.splitbills.client.SplitbillsClient;
import com.splitbills.client.UserLoginInfo;
import com.splitbills.client.io.Reader;
import com.splitbills.client.io.Writer;

public class HomeMenu extends Menu {

    private boolean isRunning = true;

    public HomeMenu(SplitbillsClient splitbillsClient, UserLoginInfo userLoginInfo, Reader reader, Writer writer) {
        super(splitbillsClient, userLoginInfo, reader, writer);
    }

    @Override
    public void run() {
        while (isRunning) {
            if (userLoginInfo.isLoggedIn()) {
                new AfterLogInHomeMenu(splitbillsClient, userLoginInfo, reader, writer).run();
            } else {
                CommandName commandName = getCommandName();
                getNextMenu(commandName).run();
            }
        }
    }

    private CommandName getCommandName() {
        writer.writeWithNewLine("Welcome");
        writer.writeWithNewLine("Please choose a command from the listed below: ");
        writer.writeWithNewLine("Login");
        writer.writeWithNewLine("Register");
        writer.writeWithNewLine("Exit");
        writer.write(": ");
        String input = reader.readNextLine();
        return CommandName.getCommandName(input);
    }

    private Menu getNextMenu(CommandName commandName) {
        Menu menu;
        switch (commandName) {
            case LOGIN:
                menu = new LoginMenu(splitbillsClient, userLoginInfo, reader, writer);
                break;
            case REGISTER:
                menu = new RegisterMenu(splitbillsClient, userLoginInfo, reader, writer);
                break;
            case EXIT:
                isRunning = false;
                menu = new ExitMenu(splitbillsClient, userLoginInfo, reader, writer);
                break;
            default:
                menu = new InvalidCommandMenu(splitbillsClient, userLoginInfo, reader, writer);
        }
        return menu;
    }
}
