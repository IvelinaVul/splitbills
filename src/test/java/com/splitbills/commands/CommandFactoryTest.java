package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CommandFactoryTest {

    @Mock
    UserRepository userRepository;
    @Mock
    GroupRepository groupRepository;

    @Test
    public void getCommandWithInvalidName() {
        CommandFactory commandFactory = new CommandFactory(userRepository, groupRepository);
        String commandName = "noSuchCommand";
        assertThrows(InvalidCommandException.class, () -> commandFactory.getCommand(commandName));
    }

    @Test
    public void getCommandAddGroup() throws InvalidCommandException {
        CommandFactory commandFactory = new CommandFactory(userRepository, groupRepository);
        String validName = "add-group";
        Command command = commandFactory.getCommand(validName);
        assertTrue(AddGroup.class.isInstance(command));
    }

    @Test
    public void getCommandIsCaseInsensitive() throws InvalidCommandException {
        CommandFactory commandFactory = new CommandFactory(userRepository, groupRepository);
        String validName = "aDd-grouP";
        Command command = commandFactory.getCommand(validName);
        assertTrue(command instanceof AddGroup);
    }

}
