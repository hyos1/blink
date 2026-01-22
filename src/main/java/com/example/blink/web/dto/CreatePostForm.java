package com.example.blink.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreatePostForm {

    private String content;
    private List<MultipartFile> imageFiles = new ArrayList<>();
}