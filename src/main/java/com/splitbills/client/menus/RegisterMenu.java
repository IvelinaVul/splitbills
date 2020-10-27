package com.splitbills.client.menus;

import com.splitbills.client.*;
import com.splitbills.client.io.Reader;
import com.splitbills.client.io.Writer;
import com.splitbills.logging.Level;
import com.splitbills.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterMenu extends Menu {

    private final static Logger LOGGER = Logger.getLogger(RegisterMenu.class.getName());

    public RegisterMenu(SplitbillsClient splitbillsClient, UserLoginInfo storage, Reader reader, Writer writer) {
        super(splitbillsClient, storage, reader, writer);
    }

    @Override
    public void run() {
        try {
            List<String> arguments = readCommandArguments();
            Command register = new Command(CommandName.REGISTER.toString(), arguments);
            Response response = splitbillsClient.sendCommand(register);
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
        writer.writeWithNewLine("Please enter your username");
        String username = reader.readNext();
        writer.writeWithNewLine("Please enter your password");
        String password = reader.readNext();
        List<String> arguments = new ArrayList<>();
        arguments.add(username);
        arguments.add(password);
        return arguments;
    }

    private void writeResponse(Response response) {
        switch (response.getStatus()) {
            case OK:
                writer.writeWithNewLine("Successful registration");
                break;
            case INVALID_ARGUMENTS:
                writer.writeWithNewLine("The provided arguments are not valid. Please try again");
                break;
            case ALREADY_EXISTS:
                writer.writeWithNewLine("The username is already taken. Please try again" +
                        " with another username");
                break;
            default:
                writer.writeWithNewLine("A problem occurred. Please try again later");
                break;
        }
        writer.writeNewLine();
    }
}
