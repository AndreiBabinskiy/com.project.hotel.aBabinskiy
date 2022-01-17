package com.project.aBabinskiy.server.command;

import com.project.aBabinskiy.server.command.implementaion.*;
import com.project.aBabinskiy.server.command.implementaion.citizenship.GetAllCitizenshipsCommand;
import com.project.aBabinskiy.server.command.implementaion.citizenship.SaveCitizenshipCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static final Map<String, ServerCommand> allCommands;

    static {
        allCommands = new HashMap<>();

        allCommands.put("get-all-accounts", new GetAllAccountsCommand());
        allCommands.put("save-account", new SaveAccountCommand());
        allCommands.put("edit-account", new EditAccountCommand());
        allCommands.put("delete-account", new DeleteAccountCommand());

        allCommands.put("get-all-citizenships", new GetAllCitizenshipsCommand());
        allCommands.put("save-citizenship", new SaveCitizenshipCommand());
    }

    // save-citizenship
    public static ServerCommand getCommand(String commandName) {
        ServerCommand serverCommand = allCommands.get(commandName);
        if (serverCommand == null) {
            return new NotFoundCommand();
        }

        return serverCommand;
    }
}
