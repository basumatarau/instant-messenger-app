package by.vironit.training.basumatarau.messengerService.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordEncoder {

    private PasswordEncoder(){}

    public static String getPwdHash(String pwd, byte[] salt){
        return get_SHA_512_encryptedPassword(pwd, salt);
    }

    private static String get_SHA_512_encryptedPassword(String passwordToHash, byte[] salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[20];
        new SecureRandom().nextBytes(salt);
        return salt;
    }
}
