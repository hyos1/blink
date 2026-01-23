package com.example.blink.web.controller;

import com.example.blink.file.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class PostImageController {

    private final FileStore fileStore;

    @Value("${file.dir}")
    private String fileDir;

    @ResponseBody
    @GetMapping("/postImages/{imageUrl}")
    public Resource thumbnailImage(@PathVariable String imageUrl) throws MalformedURLException {
        return new UrlResource("file:" + fileDir + imageUrl);
    }
}