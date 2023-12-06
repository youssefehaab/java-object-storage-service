package com.chesterford.storage.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties(prefix = "minio")
public class MinioConfigurationProperties {

  @NotBlank
  private String url;

  @NotBlank
  private String accessKeyId;

  @NotBlank
  private String secretAccessKey;

  @NotBlank
  private String bucket;

}
