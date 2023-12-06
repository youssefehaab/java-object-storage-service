package com.chesterford.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StorageService {

  private static final String VIDEO_CONTENT_DIRECTORY = "chesterford";

  private final MinioService minioService;

  public void uploadContent(MultipartFile content) {
    minioService.upload(VIDEO_CONTENT_DIRECTORY + '/' + content.getOriginalFilename(), content);
  }

  public InputStream getContent(String fileName) {
    return minioService.download(VIDEO_CONTENT_DIRECTORY + '/' + fileName);
  }

  public String getFileSize(String fileName) {
    return Objects.requireNonNull(minioService.getFileSize(VIDEO_CONTENT_DIRECTORY + '/' + fileName)).toString();
  }
}
