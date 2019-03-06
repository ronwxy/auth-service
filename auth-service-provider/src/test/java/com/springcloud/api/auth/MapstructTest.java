package com.springcloud.api.auth;

import com.springcloud.service.auth.domain.User;

public class MapstructTest {


    public static void main(String[] args) {
        User user = new User();
        user.setUsername("abc");
        System.out.println(user);

    }
}
