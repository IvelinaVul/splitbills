package com.splitbills.account;

import com.splitbills.logging.Level;
import com.splitbills.logging.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

public class Password {

    private final static int SALT_BYTES = 16;
    private final static int KEY_LENGTH = 256;
    private final static int ITERATIONS = 20000;
    private final static String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";
    private final static Random RANDOM = new SecureRandom();
    private final static Logger LOGGER = Logger.getLogger(Password.class.getName());

    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);
        return salt;
    }

    private static void clearPassword(char[] password) {
        Arrays.fill(password, Character.MIN_VALUE);
    }

    public static byte[] getHash(char[] password, byte[] salt) throws HashingException {
        if (password == null) {
            throw new IllegalArgumentException("Parameter password cannot be null");
        }
        if (salt == null) {
            throw new IllegalArgumentException("Parameter salt cannot be null");
        }

        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        clearPassword(password);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
            return secretKeyFactory.generateSecret(spec).getEncoded();

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            LOGGER.log(Level.ERROR, "Provider does not provide implementation for " + HASH_ALGORITHM,
                    noSuchAlgorithmException);
            throw new IllegalStateException("Provider does not provide implementation for " + HASH_ALGORITHM,
                    noSuchAlgorithmException);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            LOGGER.log(Level.ERROR, "Error occurred when generating secret for hashing password",
                    invalidKeySpecException);
            throw new HashingException("Error occurred when generating secret for hashing password",
                    invalidKeySpecException);
        } finally {
            spec.clearPassword();
        }
    }

    public static boolean isMatching(char[] password, byte[] salt, byte[] expectedHash) throws HashingException {
        if (password == null) {
            throw new IllegalArgumentException("Parameter password cannot be null");
        }
        if (salt == null) {
            throw new IllegalArgumentException("Parameter salt cannot be null");
        }
        if (expectedHash == null) {
            throw new IllegalArgumentException("Parameter expectedHash cannot be null");
        }
        byte[] actualHash = getHash(password, salt);
        clearPassword(password);
        return Arrays.equals(actualHash, expectedHash);
    }



}
