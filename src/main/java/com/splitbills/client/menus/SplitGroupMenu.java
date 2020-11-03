package com.splitbills.client.menus;

import com.splitbills.client.*;
import com.splitbills.client.io.Reader;
import com.splitbills.client.io.Writer;
import com.splitbills.logging.Level;
import com.splitbills.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplitGroupMenu extends Menu {

    private final static Logger LOGGER = Logger.getLogger(SplitGroupMenu.class.getName());

    public SplitGroupMenu(SplitbillsClient splitbillsClient, UserLoginInfo userLoginInfo, Reader reader,
                          Writer writer) {
        super(splitbillsClient, userLoginInfo, reader, writer);
    }

    @Override
    public void run() {
        try {
            List<String> arguments = readCommandArguments();
            Command splitGroup = new Command(CommandName.SPLIT_GROUP.toString(), arguments);
            splitGroup.setAuthenticationToken(userLoginInfo.getAuthenticationToken());
            Response response = splitbillsClient.sendCommand(splitGroup);
            writeResponse(response);
        } catch (DisconnectedException disconnectedException) {
            writer.writeWithNewLine("A problem occurred. Please try again later");
            LOGGER.log(Level.ERROR, "Disconnected from server while sending request",
                    disconnectedException);
        } catch (IOException ioException) {
            writer.writeWithNewLine("A problem occurred. Please try again later");
            LOGGER.log(Level.ERROR, "Error while sending request", ioException);
        }
    }

    private List<String> readCommandArguments() {
        writer.writeWithNewLine("Please enter the group name");
        String groupName = reader.readNextLine();
        writer.writeWithNewLine("Please enter the amount to be split");
        String amount = reader.readNextLine();
        writer.writeWithNewLine("Please enter the reason:");
        String reason = reader.readNextLine();
        List<String> arguments = new ArrayList<>();
        arguments.add(userLoginInfo.getUsername());
        arguments.add(groupName);
        arguments.add(amount);
        arguments.add(reason);
        return arguments;
    }

    private void writeResponse(Response response) {
        switch (response.getStatus()) {
            case OK:
                writer.writeWithNewLine("Operation successful");
                break;
            case NOT_EXISTING:
                writer.writeWithNewLine("No such group exists.");
                break;
            case INVALID_ARGUMENTS:
                writer.writeWithNewLine("The provided arguments are not valid");
                break;
            default:
                writer.writeWithNewLine("A problem occurred. Please try again later");
                break;
        }

    }
}
