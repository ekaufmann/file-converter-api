package com.ekaufmann.file_converter_api.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class FileUtil {

    public static Collection<String> read(MultipartFile file) {
        try(var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            return reader.lines().toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
