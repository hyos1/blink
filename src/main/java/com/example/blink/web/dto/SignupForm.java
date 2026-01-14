package com.example.blink.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignupForm {

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 2, message = "최소 2글자 이상 입력해주세요")
    private String name;
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}