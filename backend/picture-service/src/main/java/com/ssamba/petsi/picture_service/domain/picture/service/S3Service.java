package com.ssamba.petsi.picture_service.domain.picture.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URLDecoder;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;
    private final String region;

    public String upload(MultipartFile multipartFile, String s3FileName) throws IOException {


        // PutObjectRequest 생성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(s3FileName)
                .contentType(multipartFile.getContentType())
                .contentDisposition("inline")
                .build();

        // S3로 파일 업로드
        s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(
                multipartFile.getInputStream(), multipartFile.getSize()));

        // URL 생성
        return URLDecoder.decode(String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucket, region, s3FileName), "utf-8");
    }

    public void delete(String keyName) {
        try {
            // DeleteObjectRequest 생성 후 삭제
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(keyName)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}

