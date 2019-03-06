package com.springcloud.service.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pwd = passwordEncoder.encode("passw0rd");
        System.out.println(pwd);
        System.out.println(passwordEncoder.matches("passw0rd", pwd));
    }
}
