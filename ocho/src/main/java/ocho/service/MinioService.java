package ocho.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.extern.slf4j.Slf4j;
import ocho.util.ResourceUtil;

@Slf4j
@Service
public class MinioService extends ResourceUtil {
  private MinioClient minioClient;
  private String myBucketName = "ocho";
  private String publicUrl;

  MinioService(@Value("${app.s3.password}") String password, @Value("${app.s3.user}") String username,
      @Value("${app.s3.endpoint}") String endpointS3, @Value("${app.s3.docker}") String endpointDocker)
      throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException,
      InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException,
      IOException {

    var endpoint = endpointS3;

    if (endpointDocker != null) {
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

    var json = loadResource("bucketPolicy.json");

    var policyJson = String.format(json, myBucketName);

    minioClient.setBucketPolicy(
        SetBucketPolicyArgs.builder().bucket(myBucketName).config(policyJson).build());
  }

  public String uploadFile(MultipartFile file) throws IOException, InvalidKeyException, ErrorResponseException,
      InsufficientDataException, InternalException, InvalidResponseException,
      NoSuchAlgorithmException,
      ServerException, XmlParserException, IllegalArgumentException {

    var uuid = UUID.randomUUID().toString();
    var size = file.getSize();
    var contentType = file.getContentType();
    var inputStream = file.getInputStream();

    minioClient.putObject(
        PutObjectArgs.builder().bucket(myBucketName).object(uuid).stream(
            inputStream, size, -1)
            .contentType(contentType)
            .build());

    return publicUrl + uuid;
  }
}
