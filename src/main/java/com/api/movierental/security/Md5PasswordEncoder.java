package com.api.movierental.security;

import org.springframework.core.codec.EncodingException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(rawPassword.toString().getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);

            StringBuilder sb = new StringBuilder(hashtext);
            while (sb.length() < 32) {
                sb.insert(0, "0");
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new EncodingException("Error encoding password", e);
        }
    }


    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }

}
