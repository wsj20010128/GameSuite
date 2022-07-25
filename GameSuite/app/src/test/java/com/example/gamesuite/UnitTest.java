package com.example.gamesuite;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Random;

/**
 * Unit test
 *
 * @author Zixiang Xu
 * @version 1.0
 */
public class UnitTest {
    private static final int TIMEOUT = 200;
    private static final int STR_LENGTH = 16;
    private static final int MAX_STR_LIST_LENGTH = 30;
    private static final Random rand = new Random();
    private static DataSecurity security;

    /**
     * Initializer
     *     -- Initialize the instances required for the unit test
     */
    @Before
    public void init() {

        // Initialize security
        try {
            security = new DataSecurity();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Test case 01
     *     --Target: DataSecurity.java
     *     --Goal:   test the process of encryption
     */
    @Test(timeout = TIMEOUT)
    public void testEncryption() {

        // Initialize a decrypted string array for the encryption
        String[] encryptStrList = new String[] {
                "Zixiang Xu", "Kaixiang Cui", "Guanfeng Chen", "Jason An", "Shijie Wang"
        };

        // Initialize a encrypted string array for the comparison
        String[] encryptedStrList = new String[] {
                "bcd82f625bf9ebaf46cd46a176decbff",
                "77956a3e5686abd4fc024eaa3c01fd11",
                "1eb4bdb0c5696e1eefd349ae111639ae",
                "d44e31aaa13f7a8774e9458fafab2215",
                "cf6d980b4f840e9d5a6f4ab66de05660"
        };

        // Encrypt all strings in the encryption source
        for (int i = 0; i < encryptStrList.length; i++) {
            try {
                encryptStrList[i] = security.encrypt(encryptStrList[i]);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        // Check if all strings matched after the encryption process
        for (int i = 0; i < encryptStrList.length; i++) {
            assertEquals(encryptedStrList[i], encryptStrList[i]);
        }
    }

    /**
     * Test case 02
     *     --Target: DataSecurity.java
     *     --Goal:   test the process of decryption
     */
    @Test(timeout = TIMEOUT)
    public void testDecryption() {

        // Initialize a encrypted string array for the decryption
        String[] decryptStrList = new String[] {
                "bcd82f625bf9ebaf46cd46a176decbff",
                "77956a3e5686abd4fc024eaa3c01fd11",
                "1eb4bdb0c5696e1eefd349ae111639ae",
                "d44e31aaa13f7a8774e9458fafab2215",
                "cf6d980b4f840e9d5a6f4ab66de05660"
        };

        // Initialize a decrypted string array for the comparison
        String[] decryptedStrList = new String[] {
                "Zixiang Xu", "Kaixiang Cui", "Guanfeng Chen", "Jason An", "Shijie Wang"
        };

        // Decrypt all strings in the decryption source
        for (int i = 0; i < decryptStrList.length; i++) {
            try {
                decryptStrList[i] = security.decrypt(decryptStrList[i]);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        // Check if all strings matched after the decryption process
        for (int i = 0; i < decryptStrList.length; i++) {
            assertEquals(decryptedStrList[i], decryptStrList[i]);
        }
    }

    /**
     * Test case 03
     *     --Target: DataSecurity.java
     *     --Goal:   test reversible process between the encryption & decryption
     */
    @Test(timeout = TIMEOUT)
    public void testEnDecryption() {

        // Initialize a random-length string array as an encryption source
        String[] encryptStrList = new String[rand.nextInt(MAX_STR_LIST_LENGTH) + 1];
        for (int i = 0; i < encryptStrList.length; i++) {
            encryptStrList[i] = strRandom();
        }

        // Make a copy of string array as a decryption source
        String[] decryptStrList = new String[encryptStrList.length];
        System.arraycopy(encryptStrList, 0,
                decryptStrList, 0, encryptStrList.length);

        try {

            // Encrypt all strings in the encryption source
            for (int i = 0; i < encryptStrList.length; i++) {
                encryptStrList[i] = security.encrypt(encryptStrList[i]);
            }

            // Decrypt the encrypted source string array
            for (int i = 0; i < encryptStrList.length; i++) {
                encryptStrList[i] = security.decrypt(encryptStrList[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Check if all strings matched after both the encryption and decryption process
        for (int i = 0; i < encryptStrList.length; i++) {
            assertEquals(encryptStrList[i], decryptStrList[i]);
        }
    }

    /**
     * Form a string randomly
     *     mini length: 15
     *     ascii range: 32-126
     *
     * @return the randomly formed string
     */
    private static String strRandom() {

        // Randomize the length of a string
        // min length: 15
        int randIndex = rand.nextInt(STR_LENGTH) + 15;

        // Initialize a character list
        char[] charList = new char[randIndex];

        // Create each character for a string
        for (int i = 0; i < charList.length; i++) {

            // Form a character by ASCII table
            // ASCII: 32-126
            charList[i] = (char) (rand.nextInt(95) + 32);
        }

        // Return the formed string
        return String.valueOf(charList);
    }
}