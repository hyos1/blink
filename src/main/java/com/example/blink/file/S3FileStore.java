package com.example.blink.file;

import com.example.blink.file.request.UploadFile;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class S3FileStore implements FileStore {

    private final S3Operations s3Operations;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) {

        List<UploadFile> uploadFiles = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFiles.isEmpty()) {
                uploadFiles.add(storeFile(multipartFile));
            }
        }

        return uploadFiles;
    }

    @Override
    public UploadFile storeFile(MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            S3Resource s3Resource = s3Operations.upload(bucket, storeFileName, inputStream,
                    ObjectMetadata.builder().contentType(multipartFile.getContentType()).build());
            String imageUrl = s3Resource.getURL().toString();

            return new UploadFile(storeFileName, imageUrl);
        } catch (IOException e) {
            throw new RuntimeException("S3 파일 업로드 실패");
        }
    }

    @Override
    public void deleteFile(String fileName) {
        s3Operations.deleteObject(bucket, fileName);
    }

    //파일 확장자 추출
    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

    private String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
}