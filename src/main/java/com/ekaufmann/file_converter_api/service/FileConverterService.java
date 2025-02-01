package com.ekaufmann.file_converter_api.service;

import com.ekaufmann.file_converter_api.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Service
public class FileConverterService {

    public Collection<String> convertFile(MultipartFile file) {
        return FileUtil.read(file);
    }
}
