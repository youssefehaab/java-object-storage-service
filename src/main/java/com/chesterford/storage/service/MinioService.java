package com.chesterford.storage.service;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.chesterford.storage.configuration.MinioConfigurationProperties;
import com.chesterford.storage.exception.MinioException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService {

  private final MinioClient minioClient;
  private final MinioConfigurationProperties minioProperties;

  @PostConstruct
  private void postConstruct() {
    try {
      var bucket = minioProperties.getBucket();
      if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
      }
    } catch (ServerException | InsufficientDataException | ErrorResponseException
             | IOException | NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException
             | XmlParserException | InternalException exception) {
      throw new MinioException(String.format("Error making bucket: %s", exception.getMessage()));
    }
  }

  public void upload(final String objectName, final MultipartFile file) {
    try {
      minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(objectName)
                              .stream(file.getInputStream(), file.getSize(), -1).build());
    } catch (Exception exception) {
      throw new MinioException(String.format("Error uploading object: %s", exception.getMessage()));
    }
  }

  @Nullable
  public InputStream download(final String objectName) {
    try {
      return minioClient.getObject(GetObjectArgs.builder().bucket(minioProperties.getBucket()).object(objectName).build());
    } catch (Exception e) {
      throw new MinioException(String.format("Error downloading object: %s/%s", minioProperties.getBucket(), objectName), e);
    }
  }

  @Nullable
  public Long getFileSize(String objectName) {
    try {
      return minioClient.statObject(
        StatObjectArgs.builder().bucket(minioProperties.getBucket()).object(objectName).build()).size();
    } catch (Exception e) {
      throw new MinioException(String.format("Error getting file size: %s/%s", minioProperties.getBucket(), objectName), e);
    }
  }

}
