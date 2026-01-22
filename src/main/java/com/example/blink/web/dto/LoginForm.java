package com.example.blink.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginForm {

    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;
}