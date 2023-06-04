package com.example.quanlypet.common.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class CryptoUtils {

    public static class MD5 {
        public static String encode(String text){
            MessageDigest m = null;
            try {
                m = MessageDigest.getInstance("MD5");
                m.reset();
                m.update(text.getBytes());
                byte[] digest = m.digest();
                BigInteger bigInt = new BigInteger(1,digest);
                String hashtext = bigInt.toString(16);
// Now we need to zero pad it if you actually want the full 32 chars.
                while(hashtext.length() < 32 ){
                    hashtext = "0"+hashtext;
                }
                return hashtext;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static class AES {
        static byte[] CIPHER_KEY = "0123456789abcdef0123456789abcdef".getBytes(StandardCharsets.UTF_8);
        static byte[] IV = "1234567890ABCDEF".getBytes(StandardCharsets.UTF_8);
        static char PADDING_CHAR = '\034';

        public static String encrypt(String text) throws Exception {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec key = new SecretKeySpec(CIPHER_KEY, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));
            int paddingSize = 16 - text.length() % 16;
            String padding = String.format("%0" + paddingSize + "d", 0).replace('0', PADDING_CHAR);
            String padded = text + padding;
            byte[] encrypted = cipher.doFinal(padded.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        }

        public static String decrypt(String data) throws Exception {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec key = new SecretKeySpec(CIPHER_KEY, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));
            byte[] encrypted = Base64.getDecoder().decode(data);
            String padded = new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
            return padded.replaceAll(PADDING_CHAR + "+$", "");
        }
    }
}
