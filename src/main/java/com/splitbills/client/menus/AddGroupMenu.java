package com.splitbills.client.menus;

import com.splitbills.client.*;
import com.splitbills.client.io.Reader;
import com.splitbills.client.io.Writer;
import com.splitbills.logging.Level;
import com.splitbills.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddGroupMenu extends Menu {

    private final static Logger LOGGER = Logger.getLogger(AddGroupMenu.class.getName());

    public AddGroupMenu(SplitbillsClient splitbillsClient, UserLoginInfo userLoginInfo, Reader reader, Writer writer) {
        super(splitbillsClient, userLoginInfo, reader, writer);
    }

    @Override
    public void run() {
        try {
            List<String> arguments = readCommandArguments();
            Command addGroup = new Command(CommandName.ADD_GROUP.toString(), arguments);
            addGroup.setAuthenticationToken(userLoginInfo.getAuthenticationToken());
            Response response = splitbillsClient.sendCommand(addGroup);
            handleResponse(response);
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
        List<String> arguments = new ArrayList<>();
        writer.writeWithNewLine("Please enter the group name");
        String groupName = reader.readNextLine();
        arguments.add(groupName);
        arguments.add(userLoginInfo.getUsername());
        String member;
        String answer;
        final String continueToEnter = "yes";
        writer.writeWithNewLine("Enter the other group members usernames");
        do {
            writer.writeWithNewLine("next username:");
            member = reader.readNextLine();
            arguments.add(member);
            writer.writeWithNewLine("Do you wish to add more members(yes/no)");
            answer = reader.readNextLine();
        } while (answer != null && answer.equalsIgnoreCase(continueToEnter));
        return arguments;
    }

    private void handleResponse(Response response) {
        switch (response.getStatus()) {
            case OK:
                writer.writeWithNewLine("The adding of the group was successful");
                break;
            case INVALID_ARGUMENTS:
                writer.writeWithNewLine("The provided arguments are not valid");
                break;
            default:
                writer.writeWithNewLine("A problem occurred. Please try again later");
                break;
        }
        writer.writeNewLine();
    }
}
