package com.example.blink.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreatePostForm {

    @NotBlank
    @Size(min = 1, max = 1000)
    private String content;
    @NotEmpty
    @Size(max = 3)
    private List<MultipartFile> imageFiles = new ArrayList<>();
}