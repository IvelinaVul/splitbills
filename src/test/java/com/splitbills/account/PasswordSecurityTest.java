package com.splitbills.account;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordSecurityTest {

    @Test
    public void isMatching() throws HashingException {
        byte[] salt = {12, -28, 75, 118, 108, 41, 45, -50, -36, 56, 13, 44, -120, 75, 23, 31};
        String password = "sh12ASD";
        byte[] hash = PasswordSecurity.getHash(password.toCharArray(), salt);
        assertTrue(PasswordSecurity.isMatching(password.toCharArray(), salt, hash));
    }

    @Test
    public void isMatchingClearsPassword() throws HashingException {
        byte[] salt = {12, -28, 75, 118, 108, 41, 45, -50, -36, 56, 13, 44, -120, 75, 23, 31};
        byte[] hash = "someHash".getBytes();
        String password = "somePasword";
        char[] actual = password.toCharArray();
        PasswordSecurity.isMatching(actual, salt, hash);
        assertNotEquals(Arrays.toString(actual), password);
    }
    @Test
    public void getHashClearsPassword() throws HashingException {
        byte[] salt = {12, -28, 75, 118, 108, 41, 45, -50, -36, 56, 13, 44, -120, 75, 23, 31};
        String password = "somePasword";
        char[] actual = password.toCharArray();
        PasswordSecurity.getHash(actual,salt);
        assertNotEquals(Arrays.toString(actual), password);
    }
}