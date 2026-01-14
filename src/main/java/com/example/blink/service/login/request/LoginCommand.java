package com.example.blink.service.login.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginCommand {

    private String email;
    private String password;
}