package com.splitbills.commands;

import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class RegisterTest {

    @Mock
    private UserRepository userRepository;
    private GroupRepository groupRepository;

    @Test
    public void executeWithNoArguments() {
        Map<String, String> loggedInUsers = new HashMap<>();
        List<String> arguments = new ArrayList<>();
        Register register = new Register(userRepository, groupRepository, loggedInUsers);
        String token = null;
        Result result = register.execute(arguments, token);
        assertEquals(Status.INVALID_ARGUMENTS, result.getStatus());
    }

    @Test
    public void registerWithValidArguments() {
        Map<String, String> loggedInUsers = new HashMap<>();
        String name = "notExistingUsername";
        String password = "password";
        List<String> arguments = new ArrayList<>();
        arguments.add(name);
        arguments.add(password);
        when(userRepository.contains(name)).thenReturn(false);
        Register register = new Register(userRepository, groupRepository, loggedInUsers);
        String token = null;
        Result result = register.execute(arguments, token);
        assertEquals(Status.OK, result.getStatus());
    }

    @Test
    public void registerWithAlreadyTakenName() {
        Map<String, String> loggedInUsers = new HashMap<>();
        String takenName = "taken";
        String password = "some pass";
        List<String> arguments = new ArrayList<>();
        arguments.add(takenName);
        arguments.add(password);
        when(userRepository.contains(takenName)).thenReturn(true);
        Register register = new Register(userRepository, groupRepository, loggedInUsers);
        String token = null;
        Result result = register.execute(arguments, token);
        assertEquals(Status.ALREADY_EXISTS, result.getStatus());

    }

}
