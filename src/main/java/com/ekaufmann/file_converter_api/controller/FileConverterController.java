package com.ekaufmann.file_converter_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileConverterController {

    @PostMapping
    public ResponseEntity<String> convertFile(@RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(file.getName());
    }
}
