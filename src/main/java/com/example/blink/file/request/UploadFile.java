package com.example.blink.file.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadFile {

    // 사진 이름이 같은 경우 덮어씌워질 수 있으므로 다른 이름으로 저장
    private String storeFileName; // 저장될 파일명(UUID)
    private String imageUrl;
}