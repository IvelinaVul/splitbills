package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserAlreadyExistsException;
import com.splitbills.database.UserRepository;
import com.splitbills.database.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class RegisterTest {

    @Mock
    private UserRepository userRepository;
    private GroupRepository groupRepository;

    @Test
    public void executeWithNoArguments() {
        List<String> arguments = new ArrayList<>();
        Register register = new Register();
        Status response = register.execute(arguments);
        assertEquals(Status.INVALID_ARGUMENTS, response);
    }

    @Test
    public void registerWithValidArguments() throws UserAlreadyExistsException {
        String name = "notExistingUsername";
        String password = "password";
        List<String> arguments = new ArrayList<>();
        arguments.add(name);
        arguments.add(password);

        when(userRepository.add(argThat((User user) -> user.getUsername().equals(name))))
                .thenReturn(name);

        Register register = new Register(userRepository, groupRepository);
        Status response = register.execute(arguments);
        assertEquals(Status.OK, response);
    }

    @Test
    public void registerWithAlreadyTakenName() throws UserAlreadyExistsException {
        String takenName = "taken";
        String password = "some pass";
        List<String> arguments = new ArrayList<>();
        arguments.add(takenName);
        arguments.add(password);
        when(userRepository.add(argThat((User user) -> user.getUsername().equals(takenName))))
                .thenThrow(UserAlreadyExistsException.class);

        Register register = new Register(userRepository, groupRepository);
        Status response = register.execute(arguments);
        assertEquals(Status.ALREADY_EXISTS, response);

    }

}
