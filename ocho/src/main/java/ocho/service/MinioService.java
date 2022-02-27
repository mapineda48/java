package ocho.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.extern.slf4j.Slf4j;
import ocho.util.ResourceUtil;

/**
 * https://docs.min.io/docs/java-client-api-reference.html
 */

@Slf4j
@Service
public class MinioService extends ResourceUtil {
  private MinioClient minioClient;
  private String myBucketName;
  private String publicUrl;

  @Autowired
  MinioService(@Value("${app.s3.password}") String password, @Value("${app.s3.user}") String username,
      @Value("${app.s3.endpoint}") String endpointS3, OchoConfigService ochoConfigService,
      @Value("${app.s3.docker:}") String endpointDocker)
      throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException,
      InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException,
      IOException {

    this.myBucketName = ochoConfigService.getBucketName();

    var endpoint = endpointS3;

    if (StringUtils.hasText(endpointDocker)) {
      endpoint = endpointDocker;
    }

    minioClient = MinioClient.builder()
        .endpoint(endpoint)
        .credentials(username, password)
        .build();

    publicUrl = endpointS3 + "/" + myBucketName + "/";

    var existsBucket = minioClient.bucketExists(BucketExistsArgs.builder().bucket(myBucketName).build());

    if (existsBucket) {
      log.info("bucket {} exists", myBucketName);
      return;
    }

    log.info("creating bucket {}", myBucketName);

    minioClient.makeBucket(
        MakeBucketArgs.builder()
            .bucket(myBucketName)
            .build());

    log.info("setting policy bucket {}", myBucketName);

    var policyJson = String.format(loadResource("bucketPolicy.json"), myBucketName);

    minioClient.setBucketPolicy(
        SetBucketPolicyArgs.builder().bucket(myBucketName).config(policyJson).build());
  }

  public String uploadFile(MultipartFile file) {

    var uuid = UUID.randomUUID().toString();
    var size = file.getSize();
    var contentType = file.getContentType();

    try {
      var inputStream = file.getInputStream();

      minioClient.putObject(
          PutObjectArgs.builder().bucket(myBucketName).object(uuid).stream(
              inputStream, size, -1)
              .contentType(contentType)
              .build());

      return publicUrl + uuid;
    } catch (Exception e) {
      log.error("fail upload", e);

      return null;
    }
  }

  public void removeFile(String url) {
    if (url == null) {
      return;
    }

    var objectname = url.replace(publicUrl, "");

    try {
      minioClient.removeObject(
          RemoveObjectArgs.builder().bucket(myBucketName).object(objectname).build());
    } catch (Exception e) {
      log.error("can't remove file", e);
    }
  }
}