package com.splitbills.client.menus;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.splitbills.client.*;
import com.splitbills.client.io.Reader;
import com.splitbills.client.io.Writer;
import com.splitbills.client.models.Debt;
import com.splitbills.commands.GetStatus;
import com.splitbills.logging.Level;
import com.splitbills.logging.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.splitbills.client.CommandName.GET_STATUS;

public class GetStatusMenu extends Menu {

    private final static Logger LOGGER = Logger.getLogger(GetStatus.class.getName());
    private Gson gson;

    public GetStatusMenu(SplitbillsClient splitbillsClient, UserLoginInfo userLoginInfo, Reader reader, Writer writer) {
        super(splitbillsClient, userLoginInfo, reader, writer);
        gson = new Gson();
    }

    @Override
    public void run() {
        try {
            Response response = sendCommand();
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

    private Response sendCommand() throws IOException {
        List<String> arguments = new ArrayList<>();
        arguments.add(userLoginInfo.getUsername());
        Command command = new Command(GET_STATUS.toString(), arguments);
        command.setAuthenticationToken(userLoginInfo.getAuthenticationToken());
        return splitbillsClient.sendCommand(command);
    }

    private void handleResponse(Response response) {
        switch (response.getStatus()) {
            case OK:
                List<Debt> debts = getDebts(response.getArguments());
                writeAccountStatus(debts);
                break;
            default:
                writer.writeWithNewLine("A problem occurred. Please try again later");
                break;
        }
        writer.writeNewLine();
    }

    private List<Debt> getDebts(List<String> arguments) {
        int debtsIndex = 0;
        String debtsJson = arguments.get(debtsIndex);
        Type debtListType = new TypeToken<ArrayList<Debt>>() {
        }.getType();
        return gson.fromJson(debtsJson, debtListType);
    }

    private void writeAccountStatus(List<Debt> debts) {
        for (Debt debt : debts) {
            writer.writeWithNewLine(debt.getDebtor() + " owes " + debt.getLender() + " amount: " + debt.getAmount());

        }
    }
}
