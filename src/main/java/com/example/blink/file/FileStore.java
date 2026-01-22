package com.example.blink.file;

import com.example.blink.file.request.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir; //저장할 파일 디렉토리

    // 이미 저장된 사진의 풀 경로
    public String getFullPath(String storeFileName) {
        return fileDir + storeFileName;
    }

    // 여러 파일 저장
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) {
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                uploadFiles.add(storeFile(multipartFile));
            }
        }
        return uploadFiles;
    }

    // 단일 파일 저장
    public UploadFile storeFile(MultipartFile multipartFile){
        if (multipartFile.isEmpty()) {
            return null; // null로 하는 게 맞을까? 예외?
        }

        String originalFilename = multipartFile.getOriginalFilename();
        //저장할 파일명: UUID.확장자
        String storeFileName = createStoreFileName(originalFilename);

        // 파일 저장
        try {
            // 이미지 저장할 폴더 자체가 없으면 생성
            File dir = new File(fileDir);
            if (!dir.exists()) {
                dir.mkdirs(); //상위 폴더까지 없으면 생성
            }

            multipartFile.transferTo(new File(fileDir + storeFileName));
        } catch (IOException e) {
            log.error("파일 저장 실패", e);
            throw new RuntimeException("파일 저장 실패", e);
        }
        // ImageUrl 생성
        String imageUrl = "/postImages/" + storeFileName;

        return new UploadFile(storeFileName, imageUrl);
    }

    // 저장할 파일명 생성
    public String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //파일 확장자 추출
    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }
}