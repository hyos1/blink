package com.example.blink.web.controller.api;

import com.example.blink.file.LocalFileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@Profile("local")
@RequiredArgsConstructor
public class PostImageApiController {

    private final LocalFileStore localFileStore;

    @GetMapping("/postImages/{imageUrl}")
    public Resource thumbnailImage(@PathVariable String imageUrl) throws MalformedURLException {
        return new UrlResource("file:" + localFileStore.getFullPath(imageUrl));
    }
}