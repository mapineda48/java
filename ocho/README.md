# Ocho

This project was carried out as the final activity of cycle 4 in training as a [software programmer at the Sergio Arboleda University](https://www.usergioarboleda.edu.co/noticias/la-sergio-hara-parte-de-mision-tic-2022-el-programa-para-capacitar-a-mas-de-50-mil-colombianos-en-habilidades-y-competencias-4-0/).

# Usage

## docker-compose

Do not complicate configuring an environment to run the application, this demo has a [container](https://hub.docker.com/r/mapineda48/ocho) available in [dockerhub](https://hub.docker.com/), the only thing you should have installed on your pc is [docker-compose](https://docs.docker.com/compose/), create a directory, in this create a file called `docker-compose.yml` and add the following:

```yml
version: "3.8"
services: 
  web:
    image: "mapineda48/ocho"
    environment:
      APP_MONGO: "mongodb://app:example@db:27017/ocho?authSource=admin"
      APP_EMAIL: "admin@mapineda48.com"
      APP_PASSWORD: "12345"
      APP_JWT_KEY: "myCoolSecretKey"
      APP_JWT_EXP_TOKEN: 2
      APP_JWT_EXP_REFRESH: 8
      APP_S3_USER: "minio"
      APP_S3_PASSWORD: "minio123"
      APP_S3_ENDPOINT: "http://localhost:9000"
      APP_S3_DOCKER: "http://s3:9000"
    ports:
      - "3000:8080"
    depends_on:
      - db
      - s3
  db:
    image: mongo:4.4.11-rc0-focal
    environment:
      MONGO_INITDB_ROOT_USERNAME: app
      MONGO_INITDB_ROOT_PASSWORD: example
      MONGO_INITDB_DATABASE: ocho
  s3:
    image: minio/minio:RELEASE.2022-02-07T08-17-33Z
    ports:
      - "9000:9000"
      - "9001:9001"
    environment:
      MINIO_ROOT_USER: "minio"
      MINIO_ROOT_PASSWORD: "minio123"
      MINIO_SERVER_URL: "http://localhost:9000"
    command: server --console-address ":9001" /data
```
now open a terminal and navigate to the directory where you created the file and run:

```sh
docker-compose up
```
Wait a few minutes while the dependencies are downloaded, the services are configured and when you see that the services are ready go to the browser and go to http://localhost:3000

## docker

The most similar to a production would be to try with **mongo atlas** and **amazon s3**, for this I can simply use the following command, wait for the service to load, enter http://localhost:3000, the App login credentials are **admin@ocho** and password **12345**:

```sh
docker run \
    --name ocho \
    -p 3000:8080 \
    -e "APP_MONGO=<here your uri connection to mongodb>" \
    -e "APP_EMAIL=admin@ocho" \
    -e "APP_PASSWORD=12345" \
    -e "APP_JWT_KEY=myCoolSecretKey" \
    -e "APP_JWT_EXP_TOKEN=2" \
    -e "APP_JWT_EXP_REFRESH=8" \
    -e "APP_S3_USER=<amanzon s3 user>" \
    -e "APP_S3_PASSWORD=<amanzon s3 password>" \
    -e "APP_S3_ENDPOINT=<amanzon s3 endpoint>" \
    -d \
    mapineda48/ocho
```

# External Docs

### SpringBoot

- [Spring Boot and Spring Security with JWT including Access and Refresh Tokens ????](https://www.youtube.com/watch?v=VVn9OG9nfH0)
- [Spring Boot, MongoDB: JWT Authentication with Spring Security](https://www.bezkoder.com/spring-boot-jwt-auth-mongodb/)
- [Introducci??n a Spring Security](https://www.adictosaltrabajo.com/2020/05/21/introduccion-a-spring-security/)
- [PathPattern](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/pattern/PathPattern.html)
- [Expression-Based Access Control](https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html)
- [Retrieve User Information in Spring Security](https://www.baeldung.com/get-user-in-spring-security)
- [Spring ResponseStatusException](https://www.baeldung.com/spring-response-status-exception)
- [JWT Authentication Tutorial](https://www.svlada.com/jwt-token-authentication-with-spring-boot/)
- [Springboot integra la versi??n completa de minio, con muchos pits](https://www.jianshu.com/p/403eaf7d401c)
- [Multipart Request Handling in Spring](https://www.baeldung.com/sprint-boot-multipart-requests)
- [Spring Boot File upload example with Multipart File - BezKoder](https://www.bezkoder.com/spring-boot-file-upload/)
- [Spring Boot y Spring Security Custom Login - Arquitectura Java](https://www.arquitecturajava.com/spring-boot-y-spring-security-custom-login/)
- [Using Spring @Value With Defaults](https://www.baeldung.com/spring-value-defaults)
- [Guide to Spring @Autowired](https://www.baeldung.com/spring-autowire#:~:text=Starting%20with%20Spring%202.5%2C%20the,collaborating%20beans%20into%20our%20bean.)

### Minio

- [MinIO | The MinIO Quickstart Guide](https://docs.min.io/docs/minio-quickstart-guide.html)
- [MinIO | Upload files from browser using pre-signed URLs - Cookbook/Recipe](https://docs.min.io/docs/upload-files-from-browser-using-pre-signed-urls.html)
- [MinIO | Learn more about MinIO's Docker Implementation](https://docs.min.io/docs/minio-docker-quickstart-guide.html)
- [docker-compose.yml](https://raw.githubusercontent.com/minio/minio/master/docs/orchestration/docker-compose/docker-compose.yaml)
- [Policy Management ??? MinIO Baremetal Documentation](https://docs.min.io/minio/baremetal/security/minio-identity-management/policy-based-access-control.html)

### Mongodb

- [Introduction To Cloud Computing | MongoDB](https://www.mongodb.com/cloud-database/cloud-computing)

### Amazon S3

- [How To Get Amazon S3 Access Keys](https://objectivefs.com/howto/how-to-get-amazon-s3-keys)
- [Methods for accessing a bucket](https://docs.aws.amazon.com/AmazonS3/latest/userguide/access-bucket-intro.html)
- [Amazon S3 Website vs REST API Endpoints](https://cloudconfusing.com/2017/12/24/amazon-s3-website-vs-rest-api-endpoint/)

### Gradle Scrips

- [Build Script Basics](https://docs.gradle.org/current/userguide/tutorial_using_tasks.html#sec:task_dependencies)
- [DSL Reference Home](https://docs.gradle.org/current/dsl/index.html)
- [Execute task.dependsOn only on a Condition](https://stackoverflow.com/questions/52667846/execute-task-dependson-only-on-a-condition-in-gradle)

### Other

- [auth0/java-jwt](https://github.com/auth0/java-jwt)
- [Java Program to Convert Milliseconds to Minutes and Seconds](https://www.programiz.com/java-programming/examples/milliseconds-minutes-seconds)
- [Java String format() Method With Examples - GeeksforGeeks](https://www.geeksforgeeks.org/java-string-format-method-with-examples/)
- [Spring Boot Actuator con Prometheus y Grafana - Refactorizando](https://refactorizando.com/spring-boot-actuator-prometheus-grafana/)
- [Convertir HTML a PDF en JavaScript | Delft Stack](https://www.delftstack.com/es/howto/javascript/javascript-convert-html-to-pdf/#:~:text=Utilice%20la%20biblioteca%20jsPDF%20para%20convertir%20HTML%20a%20PDF,-En%20este%20m%C3%A9todo&text=Verifique%20el%20c%C3%B3digo%20a%20continuaci%C3%B3n.&text=Copy%20var%20source%20%3D%20window.,landscape'%20%7D\)%3B%20doc.)
- [Word to HTML - F??cil de usar y conversiones instant??neas](https://wordtohtml.net/)
- [Intro to the Jackson ObjectMapper | Baeldung](https://www.baeldung.com/jackson-object-mapper-tutorial)
- [How to Read a File in Java](https://www.baeldung.com/reading-file-in-java)
- [Tipos MIME](https://developer.mozilla.org/es/docs/Web/HTTP/Basics_of_HTTP/MIME_types)

# VSCode

- [Lombok](https://projectlombok.org/setup/vscode)

# License

MIT

**Free Software, Hell Yeah!**

## Disclaimer

- This project was bootstrapped with [Spring Initializr](https://start.spring.io/).
