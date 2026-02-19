package com.example.blink.file;

import com.example.blink.file.request.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStore {
    List<UploadFile> storeFiles(List<MultipartFile> multipartFiles);
    UploadFile storeFile(MultipartFile multipartFile);

    void deleteFile(String fileName);
}