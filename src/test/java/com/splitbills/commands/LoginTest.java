package com.splitbills.commands;

import com.splitbills.account.HashingException;
import com.splitbills.account.Password;
import com.splitbills.database.GroupRepository;
import com.splitbills.database.UserRepository;
import com.splitbills.database.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginTest {

    @Mock
    private UserRepository userRepository;
    private GroupRepository groupRepository;

    @Test
    public void loginWithNotExistingUser() {
        Login login = new Login(userRepository, groupRepository);
        String username = "NotExisting";
        String password = "password";
        when(userRepository.get(username)).thenReturn(null);
        List<String> arguments = new ArrayList<>();
        arguments.add(username);
        arguments.add(password);
        String token = null;
        Result result = login.execute(arguments, token);
        assertEquals(Status.NOT_REGISTERED, result.getStatus());
    }

    @Test
    public void loginWithExistingUser() throws HashingException {
        Login login = new Login(userRepository, groupRepository);
        String username = "existing";
        String password = "password";
        byte[] salt = Password.generateSalt();
        byte[] hashedPassword = Password.getHash(password.toCharArray(), salt);
        User existingUser = new User(username, hashedPassword, salt);
        when(userRepository.get(username)).thenReturn(existingUser);
        List<String> arguments = new ArrayList<>();
        arguments.add(username);
        arguments.add(password);
        String token = null;
        Result result = login.execute(arguments, token);
        assertEquals(Status.OK, result.getStatus());
        assertNotNull(result.getArguments().get(0));
    }
}
