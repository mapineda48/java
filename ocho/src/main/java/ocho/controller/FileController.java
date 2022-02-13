package ocho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import ocho.service.MinioService;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public String uploadFile(@RequestPart MultipartFile file) {
        var url = minioService.uploadFile(file);

        if (url != null) {
            return url;
        }

        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");

    }
}
