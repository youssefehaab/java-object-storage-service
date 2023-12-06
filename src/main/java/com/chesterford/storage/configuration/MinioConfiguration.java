package com.chesterford.storage.configuration;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MinioConfigurationProperties.class)
public class MinioConfiguration {

  private final MinioConfigurationProperties minioProperties;

  @Bean
  public MinioClient generateMinioClient() {
    return MinioClient.builder().endpoint(minioProperties.getUrl())
        .credentials(minioProperties.getAccessKeyId(), minioProperties.getSecretAccessKey())
        .build();
  }

}
