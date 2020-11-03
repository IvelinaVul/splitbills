package com.splitbills.client.menus;

import com.splitbills.client.CommandName;
import com.splitbills.client.SplitbillsClient;
import com.splitbills.client.UserLoginInfo;
import com.splitbills.client.io.Reader;
import com.splitbills.client.io.Writer;

public class AfterLogInHomeMenu extends Menu {

    private boolean isRunning = true;

    public AfterLogInHomeMenu(SplitbillsClient splitbillsClient, UserLoginInfo storage, Reader reader, Writer writer) {
        super(splitbillsClient, storage, reader, writer);
    }

    @Override
    public void run() {
        while (isRunning) {
            CommandName commandName = getCommandName();
            getNextMenu(commandName).run();
        }
    }

    private CommandName getCommandName() {
        writer.writeWithNewLine("Welcome");
        writer.writeWithNewLine("Please choose a command from the listed below: ");
        writer.writeWithNewLine("Add-group");
        writer.writeWithNewLine("Split-group");
        writer.writeWithNewLine("Back");
        writer.write(": ");
        String input = reader.readNextLine();
        return CommandName.getCommandName(input);
    }

    private Menu getNextMenu(CommandName commandName) {
        Menu menu = null;
        switch (commandName) {
            case ADD_GROUP:
                menu = new AddGroupMenu(splitbillsClient, userLoginInfo, reader, writer);
                break;
            case SPLIT_GROUP:
                menu = new SplitGroupMenu(splitbillsClient, userLoginInfo, reader, writer);
                break;
            case BACK:
                isRunning = false;
                menu = new BackMenu(splitbillsClient, userLoginInfo, reader, writer);
                break;
            default:
                menu = new InvalidCommandMenu(splitbillsClient, userLoginInfo, reader, writer);
        }
        return menu;
    }
}
