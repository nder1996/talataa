package Talataa.E_Commerce.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;


public class PasswordEncoder {

    // Opción 2: Usando Base64 con salt fijo
    private static final String SALT = "tuSaltFijo123";

    public String encode(String password) {
        String saltedPassword = password + SALT;
        return Base64.getEncoder().encodeToString(saltedPassword.getBytes());
    }
}
