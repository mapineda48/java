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
      MONGO_URI: "mongodb://app:example@db:27017/ocho?authSource=admin"
    volumes:
      - "$PWD:/home/app"
    ports:
      - "3000:8080"
    depends_on:
      - db
  db:
    image: mongo:4.4.11-rc0-focal
    environment:
      MONGO_INITDB_ROOT_USERNAME: app
      MONGO_INITDB_ROOT_PASSWORD: example
      MONGO_INITDB_DATABASE: ocho

```
now open a terminal and navigate to the directory where you created the file and run:

```sh
docker-compose up
```
Wait a few minutes while the dependencies are downloaded, the services are configured and when you see that the services are ready go to the browser and go to http://localhost:3000

## docker

If you prefer you can also use the container without docker-compose:

```sh
docker run \
    --name demo \
    -p 3000:8080 \
    -e "MONGO_URI=<here your uri connectio to mongodb>" \
    -d \
    mapineda48/ocho
```

# External Docs

### SpringBoot

- [Spring Boot and Spring Security with JWT including Access and Refresh Tokens 🔑](https://www.youtube.com/watch?v=VVn9OG9nfH0)

- [Spring Boot, MongoDB: JWT Authentication with Spring Security](https://www.bezkoder.com/spring-boot-jwt-auth-mongodb/)

- [Introducción a Spring Security](https://www.adictosaltrabajo.com/2020/05/21/introduccion-a-spring-security/)

- [PathPattern](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/pattern/PathPattern.html)

- [Expression-Based Access Control](https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html)

- [Retrieve User Information in Spring Security](https://www.baeldung.com/get-user-in-spring-security)

- [Spring ResponseStatusException](https://www.baeldung.com/spring-response-status-exception)

- [JWT Authentication Tutorial](https://www.svlada.com/jwt-token-authentication-with-spring-boot/)

- [Springboot integra la versión completa de minio, con muchos pits](https://www.jianshu.com/p/403eaf7d401c)

- [Multipart Request Handling in Spring](https://www.baeldung.com/sprint-boot-multipart-requests)

- [Spring Boot File upload example with Multipart File - BezKoder](https://www.bezkoder.com/spring-boot-file-upload/)

- [Spring Boot y Spring Security Custom Login - Arquitectura Java](https://www.arquitecturajava.com/spring-boot-y-spring-security-custom-login/)

### Minio

- [MinIO | The MinIO Quickstart Guide](https://docs.min.io/docs/minio-quickstart-guide.html)

- [MinIO | Upload files from browser using pre-signed URLs - Cookbook/Recipe](https://docs.min.io/docs/upload-files-from-browser-using-pre-signed-urls.html)

- [MinIO | Learn more about MinIO's Docker Implementation](https://docs.min.io/docs/minio-docker-quickstart-guide.html)

- [docker-compose.yml](https://raw.githubusercontent.com/minio/minio/master/docs/orchestration/docker-compose/docker-compose.yaml)

- [Policy Management — MinIO Baremetal Documentation](https://docs.min.io/minio/baremetal/security/minio-identity-management/policy-based-access-control.html)

### Mongodb

- [Introduction To Cloud Computing | MongoDB](https://www.mongodb.com/cloud-database/cloud-computing)

### Other

- [auth0/java-jwt](https://github.com/auth0/java-jwt)

- [Java Program to Convert Milliseconds to Minutes and Seconds](https://www.programiz.com/java-programming/examples/milliseconds-minutes-seconds)

- [Java String format() Method With Examples - GeeksforGeeks](https://www.geeksforgeeks.org/java-string-format-method-with-examples/)

- [Spring Boot Actuator con Prometheus y Grafana - Refactorizando](https://refactorizando.com/spring-boot-actuator-prometheus-grafana/)

- [Convertir HTML a PDF en JavaScript | Delft Stack](https://www.delftstack.com/es/howto/javascript/javascript-convert-html-to-pdf/#:~:text=Utilice%20la%20biblioteca%20jsPDF%20para%20convertir%20HTML%20a%20PDF,-En%20este%20m%C3%A9todo&text=Verifique%20el%20c%C3%B3digo%20a%20continuaci%C3%B3n.&text=Copy%20var%20source%20%3D%20window.,landscape'%20%7D\)%3B%20doc.)

- [Word to HTML - Fácil de usar y conversiones instantáneas](https://wordtohtml.net/)

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
