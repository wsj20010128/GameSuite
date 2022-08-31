package com.example.gamesuite;

import android.annotation.SuppressLint;
import java.security.Key;
import javax.crypto.Cipher;

/**
 * Encryption & decryption of user data
 *
 * @author Zixiang Xu
 * @version 1.0
 */
public class DataSecurity {

    // Default key value for encryption/decryption
    private static final String defaultKey = "GeorgiaTech2022CipherSecurity@@!1";
    // Encryption Cipher tool
    private final Cipher encryptCipher;
    // Decryption Cipher tool
    private final Cipher decryptCipher;

    /**
     * Default constructor: use default key
     */
    public DataSecurity() throws Exception {
        this(defaultKey);
    }

    /**
     * Constructor 01: use given key
     *
     * @param strKey the given key
     * @throws Exception any exception will be throw
     */
    @SuppressLint("GetInstance")
    public DataSecurity(String strKey) throws Exception {

        // Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());
        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * Convert a byte array to a string representing a hexadecimal value
     * Reversible conversion process with hexStr2ByteArr()
     *
     * @param arrByte byte array that needs to be converted
     * @return converted string
     */
    public static String byteArr2HexStr(byte[] arrByte) {
        int arrLength = arrByte.length;

        // Each byte can be represented by 2 characters
        // so the length of the string is twice the length of the array
        StringBuilder strBuilder = new StringBuilder(arrLength * 2);
        for (int byteElement : arrByte) {
            int intTmp = byteElement;

            // Convert negative numbers to the positive ones
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }

            // Numbers less than 0F need to be prepended with 0
            if (intTmp < 16) {
                strBuilder.append("0");
            }
            strBuilder.append(Integer.toString(intTmp, 16));
        }
        return strBuilder.toString();
    }

    /**
     * Convert a string representing a hexadecimal value to a byte array
     * Reversible conversion process with byteArr2HexStr()
     *
     * @param strIn string that needs to be converted
     * @return converted byte array
     */
    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrByte = strIn.getBytes();
        int arrLength = arrByte.length;

        // Two characters represent one byte
        // so the byte array length is the string length divided by 2
        byte[] arrOut = new byte[arrLength / 2];
        for (int i = 0; i < arrLength; i = i + 2) {
            String strTmp = new String(arrByte, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * Encrypted byte array
     *
     * @param arrByte byte array to be encrypted
     * @return encrypted byte array
     */
    public byte[] encrypt(byte[] arrByte) throws Exception {
        return encryptCipher.doFinal(arrByte);
    }

    /**
     * Encrypted string
     * @param strIn string to be encrypted
     * @return encrypted string
     */
    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    /**
     * Decrypt byte array
     * @param arrByte byte array to decrypt
     * @return decrypted byte array
     */
    public byte[] decrypt(byte[] arrByte) throws Exception {
        return decryptCipher.doFinal(arrByte);
    }

    /**
     * Decrypt string
     * @param strIn string to decrypt
     * @return decrypted string
     */
    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    /**
     * Generate key from specified string
     * Length of the byte array required for the key = 8 bits
     *     If < 8 digits: it will be followed by 0
     *     If > 8 digits: only the first 8 digits are taken
     *
     * @param arrBTmp the byte array that makes up the string
     * @return generated key
     */
    private Key getKey(byte[] arrBTmp) {

        // Create an empty 8-bit byte array (default is 0)
        byte[] arrB = new byte[8];

        // Convert raw byte array to 8 bits
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        // Generate key
        return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }
}
