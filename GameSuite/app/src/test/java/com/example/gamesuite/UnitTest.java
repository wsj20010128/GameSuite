package com.example.gamesuite;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Unit test
 *
 * @author Zixiang Xu
 * @version 2.0
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
     *     --Goal:   test the byteArr2HexStr()
     */
    @Test(timeout = TIMEOUT)
    public void testByteArr2HexStr() {

        // Initialize the test source string array
        String[] testSource = new String[]{
            "ASDcSDF%3$#@&fDASDqwr@\t\n#fder&*i&&hgres.SAD/[[=12",
            "12EDFc#@$r$#tv54VT_$\t%#tp_34PRTC-21",
            "W{dw_+ D;1P23LDXQW WQF-= #`Q~WQD21D:DAS'",
            "\"qwd12ededsdC@!:+E1!@E21;dC \"\n12ED@!c "
        };

        // Initialize the test result string array
        String[] testResult = new String[]{
            "41534463534446253324234026664441534471777240090a2366646572262a69262668677265732e53"
                + "41442f5b5b3d3132",
            "3132454446632340247224237476353456545f2409252374705f3334505254432d3231",
            "577b64775f2b20443b315032334c44585157205751462d3d202360517e5751443231443a44415327",
            "2271776431326564656473644340213a2b453121404532313b644320220a3132454440216320"
        };

        // Compare all the results from byteArr2HexStr()
        for (int i = 0; i < testSource.length; i++){
            assertEquals(DataSecurity.byteArr2HexStr(testSource[i].getBytes()), testResult[i]);
        }
    }

    /**
     * Test case 02
     *     --Target: DataSecurity.java
     *     --Goal:   test the hexStr2ByteArr()
     */
    @Test(timeout = TIMEOUT)
    public void testHexStr2ByteArr() {

        // Initialize the test source string array
        String[] testSource = new String[]{
            "41534463534446253324234026664441534471777240090a2366646572262a69262668677265732e53"
                + "41442f5b5b3d3132",
            "3132454446632340247224237476353456545f2409252374705f3334505254432d3231",
            "577b64775f2b20443b315032334c44585157205751462d3d202360517e5751443231443a44415327",
            "2271776431326564656473644340213a2b453121404532313b644320220a3132454440216320"
        };

        // Initialize the test result string array
        String[] testResult = new String[]{
            "ASDcSDF%3$#@&fDASDqwr@\t\n#fder&*i&&hgres.SAD/[[=12",
            "12EDFc#@$r$#tv54VT_$\t%#tp_34PRTC-21",
            "W{dw_+ D;1P23LDXQW WQF-= #`Q~WQD21D:DAS'",
            "\"qwd12ededsdC@!:+E1!@E21;dC \"\n12ED@!c "
        };

        // Compare all the results from hexStr2ByteArr()
        for (int i = 0; i < testSource.length; i++){
            assertEquals(new String(DataSecurity.hexStr2ByteArr(testSource[i]),
                StandardCharsets.UTF_8), testResult[i]);
        }
    }

    /**
     * Test case 03
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
     * Test case 04
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
     * Test case 05
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