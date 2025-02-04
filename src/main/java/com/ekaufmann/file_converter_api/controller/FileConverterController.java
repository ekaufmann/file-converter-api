package com.ekaufmann.file_converter_api.controller;

import com.ekaufmann.file_converter_api.dto.UserDTO;
import com.ekaufmann.file_converter_api.service.FileConverterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/files")
public class FileConverterController {

    private final FileConverterService service;

    @Autowired
    public FileConverterController(FileConverterService service) {
        this.service = service;
    }

    @Operation(summary = "Upload a file to convert to json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File converted",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid file supplied")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Collection<UserDTO>> convertFile(
            @Parameter(name = "file", required = true)
            @RequestPart("file") MultipartFile file
    ) throws BadRequestException {

        return ResponseEntity.ok(service.convertFile(file));
    }
}
