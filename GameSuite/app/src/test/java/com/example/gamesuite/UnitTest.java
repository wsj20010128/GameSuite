package com.example.gamesuite;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Unit test
 *
 * @author Zixiang Xu
 * @version 3.0
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
    @BeforeEach
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
     * Test case 06
     *     --Target: DataSecurity.java
     *     --Goal:   test encryption & decryption with non-default constructors of DataSecurity
     */
    @Test(timeout = TIMEOUT)
    public void testNonDefaultSecurity() {
        try {

            // Initialize 2 DataSecurity with a given key and a random key
            DataSecurity securityGivenKey = new DataSecurity("DWDJ1IQx1~@E/35 \t");
            DataSecurity securityRandomKey = new DataSecurity(strRandom());

            // Initialize the encrypted & decrypted strings
            String[] sourceStrDecrypted = new String[]{
                    "ASDcSDF%3$#@&fDASDqwr@\t\n#fder&*i&&hgres.SAD/[[=12",
                    "12EDFc#@$r$#tv54VT_$\t%#tp_34PRTC-21",
                    "W{dw_+ D;1P23LDXQW WQF-= #`Q~WQD21D:DAS'",
                    "\"qwd12ededsdC@!:+E1!@E21;dC \"\n12ED@!c ",
                    ""
            };
            String[] sourceStrEncryptedStrGivenKey = new String[]{
                    "23ba1ed3efa7a88c871440a1368eb4b1e6ba159dd1ac3f1d396738ded0785d50a0c56a09f2c607"
                            + "d9fad2a71740b47c02fefc96a9369e73f4",
                    "a83560f101c3d657a2143907dff0ac25b305c025dce36488219d569c2ced5094e4ae6b511151ad"
                            + "43",
                    "717172dd29c343185a30a802c8af4e2bda2ba769c681b1fb66380ac248516df43e782b5a3756b8"
                            + "787051a853e9e74d0e",
                    "1e7efbbbb9d9ebd0a507a59254f81e99c509474baa1f3bc3d2b04c5bf6ec5bbc27d7f767346d17"
                            + "10",
                    "7051a853e9e74d0e"
            };
            byte[][] sourceByteEncryptedGivenKey = new byte[][] {
                    {35, -70, 30, -45, -17, -89, -88, -116, -121, 20, 64, -95, 54, -114, -76, -79,
                            -26, -70, 21, -99, -47, -84, 63, 29, 57, 103, 56, -34, -48, 120, 93,
                            80, -96, -59, 106, 9, -14, -58, 7, -39, -6, -46, -89, 23, 64, -76, 124,
                            2, -2, -4, -106, -87, 54, -98, 115, -12},
                    {-88, 53, 96, -15, 1, -61, -42, 87, -94, 20, 57, 7, -33, -16, -84, 37, -77, 5,
                            -64, 37, -36, -29, 100, -120, 33, -99, 86, -100, 44, -19, 80, -108, -28,
                            -82, 107, 81, 17, 81, -83, 67},
                    {113, 113, 114, -35, 41, -61, 67, 24, 90, 48, -88, 2, -56, -81, 78, 43, -38, 43,
                            -89, 105, -58, -127, -79, -5, 102, 56, 10, -62, 72, 81, 109, -12,
                            62, 120, 43, 90, 55, 86, -72, 120, 112, 81, -88, 83, -23, -25, 77, 14},
                    {30, 126, -5, -69, -71, -39, -21, -48, -91, 7, -91, -110, 84, -8, 30, -103, -59,
                            9, 71, 75, -86, 31, 59, -61, -46, -80, 76, 91, -10, -20, 91, -68, 39,
                            -41, -9, 103, 52, 109, 23, 16},
                    {112, 81, -88, 83, -23, -25, 77, 14}
            };

            // Test Part 01: functionality of DataSecurity under a given key
            for (int i = 0; i < sourceStrDecrypted.length; i++) {
                assertEquals(securityGivenKey.encrypt(sourceStrDecrypted[i]),
                        sourceStrEncryptedStrGivenKey[i]);
            }
            for (int i = 0; i < sourceStrDecrypted.length; i++) {
                byte[] result = securityGivenKey.encrypt(sourceStrDecrypted[i].getBytes());
                for (int j = 0; j < result.length; j++)
                assertEquals(result[j], sourceByteEncryptedGivenKey[i][j]);
            }
            for (int i = 0; i < sourceStrEncryptedStrGivenKey.length; i++) {
                assertEquals(securityGivenKey.decrypt(sourceStrEncryptedStrGivenKey[i]),
                        sourceStrDecrypted[i]);
            }
            for (int i = 0; i < sourceByteEncryptedGivenKey.length; i++) {
                byte[] result = securityGivenKey.decrypt(sourceByteEncryptedGivenKey[i]);
                assertEquals(new String(result, StandardCharsets.UTF_8),
                             sourceStrDecrypted[i]);
            }

            // Test Part 02: functionality of DataSecurity under a random key
            for (String str : sourceStrDecrypted) {
                assertEquals(securityRandomKey.decrypt(securityRandomKey.encrypt(str)), str);
            }
            for (String str : sourceStrDecrypted) {
                assertEquals(new String(securityRandomKey.decrypt(
                        securityRandomKey.encrypt(str.getBytes())), StandardCharsets.UTF_8), str);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
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