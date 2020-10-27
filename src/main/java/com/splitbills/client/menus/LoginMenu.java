package com.splitbills.client.menus;

import com.splitbills.client.*;
import com.splitbills.client.io.Reader;
import com.splitbills.client.io.Writer;
import com.splitbills.logging.Level;
import com.splitbills.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginMenu extends Menu {

    private final static Logger LOGGER = Logger.getLogger(LoginMenu.class.getName());

    public LoginMenu(SplitbillsClient splitbillsClient, UserLoginInfo storage, Reader reader, Writer writer) {
        super(splitbillsClient, storage, reader, writer);
    }

    @Override
    public void run() {
        try {
            List<String> arguments = readCommandArguments();
            Command command = new Command(CommandName.LOGIN.toString(), arguments);
            Response response = splitbillsClient.sendCommand(command);
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
        writer.writeWithNewLine("Please enter your username");
        String username = reader.readNextLine();
        writer.writeWithNewLine("Please enter your password");
        String password = reader.readNextLine();
        userLoginInfo.setUsername(username);
        List<String> arguments = new ArrayList<>();
        arguments.add(username);
        arguments.add(password);
        return arguments;
    }

    private void handleResponse( Response response) {
        switch (response.getStatus()) {
            case OK:
                writer.writeWithNewLine("Successful login");
                updateUserLoginInfo(response.getArguments());
                break;
            case NOT_MATCHING_ARGUMENTS:
                writer.writeWithNewLine("The username or password does not match. Please try again");
                break;
            case NOT_REGISTERED:
                writer.writeWithNewLine("No user with the provided username exists");
                break;
            default:
                writer.writeWithNewLine("A problem occurred. Please try again later");
                break;
        }
        writer.writeNewLine();
    }

    private void updateUserLoginInfo(List<String> arguments) {
        int first = 0;
        String token = arguments.get(first);
        userLoginInfo.setLoggedIn(true);
        userLoginInfo.setAuthenticationToken(token);
    }

}
