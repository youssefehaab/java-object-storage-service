package com.chesterford.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import com.chesterford.storage.service.StorageService;

@RestController
@RequiredArgsConstructor
public class StorageController {

  private static final String ATTACHMENT_FILENAME = "attachment;filename=";

  private final StorageService storageService;

  @PostMapping(value = "/upload", consumes = { "multipart/form-data" })
  public ResponseEntity<Void> upload(@RequestParam(value = "object", required = false) MultipartFile[] multipartFiles) {
    if (multipartFiles != null) {
      List<MultipartFile> contentList = Arrays.asList(multipartFiles);
      contentList.forEach(storageService::uploadContent);
    }

    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/{filename}")
  public ResponseEntity<Resource> getContent(@PathVariable("filename") String filename) {
    return ResponseEntity.ok()
             .header(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT_FILENAME + filename)
             .header(HttpHeaders.CONTENT_LENGTH, storageService.getFileSize(filename))
             .body(new InputStreamResource(storageService.getContent(filename)));
  }

}
