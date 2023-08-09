package com.basic.backend.user.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users")
    public List<String> getUsers() {
        return Arrays.asList("user1", "user2", "user3");
    }

}
