package com.ekaufmann.file_converter_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FileConverterServiceTest {

    @InjectMocks
    private FileConverterService service;

    private String content;

    private MockMultipartFile file;

    @BeforeEach
    public void setup() {
        content = """
                0000000070                                   Joao Teste00000007530000000003     1836.7420210308
                0000000075                                  Maria Teste00000007980000000002     1578.5720211116
                0000000049                               Fernanda Teste00000005230000000003      586.7420210903
                0000000014                                Alcides Teste00000001460000000001      673.4920211125
                0000000057                                 Elidia Teste00000006200000000000     1417.2520210919
                0000000080                                 Tabita Teste00000008770000000003      817.1320210612
                """;

        file = new MockMultipartFile("file", content.getBytes());
    }

    @Test
    public void shouldConvertFileAndReturnWithSuccess() {

        var result = service.convertFile(file);

        assertEquals(content.split("\n").length, result.size());
    }
}
