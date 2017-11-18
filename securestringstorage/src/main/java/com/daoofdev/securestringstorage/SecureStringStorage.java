package com.daoofdev.securestringstorage;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2017 Lilli Szafranski - Dao of Development, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * SecureStringStorage
 *
 * Created by Lilli Szafranski on 9/20/17.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.security.auth.x500.X500Principal;

public class SecureStringStorage {
    private final static String TAG = "SecureStringStorage";

    private final static String SECURE_STRING_STORAGE_DIRECTORY = "com.dod.sss";

    private static String getEncryptedDataFilePath(@NotNull Context context, @NotNull String alias) throws Exception {
        String filesDirectory = context.getFilesDir().getAbsolutePath();
        File   sssDirectory   = new File(filesDirectory + File.separator + SECURE_STRING_STORAGE_DIRECTORY);

        boolean success = true;
        if (!sssDirectory.exists()) {
            success = sssDirectory.mkdir();
        }

        if (!success) {
            throw new Exception("Error creating secure string storage directory.");
        }

        return filesDirectory + File.separator + SECURE_STRING_STORAGE_DIRECTORY + File.separator + alias;
    }

    private static void checkArgs(Context context, String alias, String string) throws Exception {
        if (context == null) {
           throw new IllegalArgumentException("Method parameter 'context' cannot be null.");
         }

        if (alias == null) {
           throw new IllegalArgumentException("Method parameter 'alias' cannot be null.");
         }

        if (string == null) {
           throw new IllegalArgumentException("Method parameter 'string' cannot be null.");
         }
    }

    private static void createKeyPairIfNeeded(@NotNull Context context, @NotNull String alias) throws Exception {

        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if (!keyStore.containsAlias(alias)) {
            // TODO (API-LEVEL): Test
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                                 .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                                 .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                                 .build();

                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                generator.initialize(spec);

                KeyPair keyPair = generator.generateKeyPair();

            } else {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 1); // TODO: Test key expiration; figure out appropriate/desired length of time for key expiration

                @SuppressWarnings("deprecation")
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                        .setAlias(alias)
                        .setSubject(new X500Principal("CN=Sample Name, O=Android Authority"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build(); /* As setKeySize() and setKeyType() aren't available for API 18, defaults to 2048 bit RSA */

                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                generator.initialize(spec);

                KeyPair keyPair = generator.generateKeyPair();
            }
        }
    }

    /**
     * Deletes the encrypted string from the file system and removes the encryption key from the keystore.
     * @param context The application context.
     * @param alias The alias under which the key is stored and the filename under which the encrypted string is stored.
     *              Try and make this string unique so as to not step on other files/keys in your app's file system/keystore.
     * @return `true` if deletion is successful, `false` if deletion failed or if the key/file does not exist.
     * @throws Exception
     */
    public static boolean deleteStringFromSecureStorage(@NotNull Context context, @NotNull String alias) throws Exception {
        checkArgs(context, alias, "");

        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if (!keyStore.containsAlias(alias) || !new File(getEncryptedDataFilePath(context, alias)).exists()) {
            return false;
        }

        keyStore.deleteEntry(alias);

        File file = new File(getEncryptedDataFilePath(context, alias));
        return file.delete();
    }

    // TODO: Doesn't work w RSA. Can pre-23 do AES, though? https://stackoverflow.com/a/10007285/955856

    /**
     * Generates a keypair, and uses it to encrypt the string. Saves the encrypted string to the file system.
     * @param context The application context.
     * @param alias The alias under which the key is stored and the filename under which the encrypted string is stored.
     *              Try and make this string unique so as to not step on other files/keys in your app's file system/keystore.
     * @param string The string to be encrypted. Because library supports pre-API-23, encryption keys use RSA, which limits
     *               the size of the string to, like, 245 characters or something. Long strings won't work.
     * @throws Exception
     */
    public static void saveStringToSecureStorage(@NotNull Context context, @NotNull String alias, @NotNull String string) throws Exception {
        checkArgs(context, alias, string);

        createKeyPairIfNeeded(context, alias);

        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
        RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();

        Cipher input = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
        input.init(Cipher.ENCRYPT_MODE, publicKey);

        CipherOutputStream cipherOutputStream =
                new CipherOutputStream(new FileOutputStream(getEncryptedDataFilePath(context, alias)), input);

        cipherOutputStream.write(string.getBytes("UTF-8"));
        cipherOutputStream.close();
    }

    /**
     * Loads and decrypts the encrypted string from the file system.
     * @param context The application context.
     * @param alias The alias under which the key is stored and the filename under which the encrypted string is stored.
     *              Try and make this string unique so as to not step on other files/keys in your app's file system/keystore.
     * @return The decrypted string. If the key/file doesn't exist, returns `null`.
     * @throws Exception
     */
    public static String loadStringFromSecureStorage(@NotNull Context context, @NotNull String alias) throws Exception {
        checkArgs(context, alias, "");

        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if (!keyStore.containsAlias(alias) || !new File(getEncryptedDataFilePath(context, alias)).exists()) {
            return null;
        }

        // TODO: Marshmallow needed code... does it work pre-Marshmallow?
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        @SuppressLint("GetInstance") /* https://stackoverflow.com/questions/36016288/cipher-with-ecb-mode-should-not-be-used */
        Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        output.init(Cipher.DECRYPT_MODE, privateKey);

        //KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
        //RSAPrivateKey privateKey = (RSAPrivateKey) privateKeyEntry.getPrivateKey();
        //
        //Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
        //output.init(Cipher.DECRYPT_MODE, privateKey);

        CipherInputStream cipherInputStream =
                new CipherInputStream(new FileInputStream(getEncryptedDataFilePath(context, alias)), output);

        StringBuilder stringBuffer = new StringBuilder();
        int nextByte;
        while ((nextByte = cipherInputStream.read()) != -1) {
            stringBuffer.append((char)nextByte);
        }

        return stringBuffer.toString();
    }
}
