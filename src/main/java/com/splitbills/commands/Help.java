package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserRepository;
import com.splitbills.database.models.Group;

import java.util.ArrayList;
import java.util.List;

public class Help extends Command {

    public Help(UserRepository userRepository, GroupRepository groupRepository) {
        super(userRepository, groupRepository);
    }

    @Override
    public Result execute(List<String> arguments, String token) {
        List<String> commands = new ArrayList<>();

        for (CommandName commandName : CommandName.values()) {
            commands.add(commandName.toString());
        }
        return new Result(Status.OK, commands);
    }
}
