package com.ekaufmann.file_converter_api.service;

import com.ekaufmann.file_converter_api.dto.UserDTO;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.ekaufmann.file_converter_api.factory.DTOFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FileConverterServiceTest {

    @InjectMocks
    private FileConverterService service;

    private String content;

    private MockMultipartFile file;

    private List<UserDTO> convertedData;

    @BeforeEach
    public void setup() {
       content = """
                0000000070                                   Joao Teste00000007530000000003     1836.7420210308
                0000000075                                  Maria Teste00000007980000000002     1578.5720211116
                0000000070                                   Joao Teste00000007530000000002     1578.5720210308
                0000000075                                  Maria Teste00000001460000000001      673.4920211125
                0000000070                                   Joao Teste00000001470000000001      673.4920210612
                """;

        file = new MockMultipartFile("file", content.getBytes());

        var firstUserOrders = Set.of(
                createOrderDTO(753, "3415.31", "2021-03-08", Set.of(
                        createProductDTO(2, "1578.57"),
                        createProductDTO(3, "1836.74")
                )),
                createOrderDTO(147, "673.49", "2021-06-12", Set.of(
                        createProductDTO(1, "673.49")
                ))
        );

        var secondUserOrders = Set.of(
                createOrderDTO(798, "1578.57", "2021-11-16", Set.of(
                        createProductDTO(2, "1578.57")
                )),
                createOrderDTO(146, "673.49", "2021-11-25", Set.of(
                        createProductDTO(1, "673.49")
                ))
        );

        convertedData = List.of(
                createUserDTO(70, "Joao Teste", firstUserOrders),
                createUserDTO(75, "Maria Teste", secondUserOrders)
        );
    }

    @Test
    public void shouldConvertFileAndReturnWithSuccess() throws BadRequestException {

        var result = service.convertFile(file, null, null, null);

        assertEquals(2, result.size());

        var firstUser = convertedData.getFirst();
        var lastUser = convertedData.getLast();

        assertTrue(result.stream().anyMatch(user -> user.equals(firstUser)));
        assertTrue(result.stream().anyMatch(user -> user.equals(lastUser)));
    }

    @Test
    public void shouldConvertFileAndReturnWithSuccessWithDateRange() throws BadRequestException {

        var result = service.convertFile(
                file,
                LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 7, 1),
                null
        );

        assertEquals(1, result.size());

        var firstUser = convertedData.getFirst();

        assertTrue(result.stream().anyMatch(user -> user.equals(firstUser)));
    }

    @Test
    public void shouldConvertFileAndReturnWithSuccessWhenSearchForASpecificOrder() throws BadRequestException {

        var result = service.convertFile(file, null, null, 753);

        assertEquals(1, result.size());

        var firstUser = convertedData.getFirst();

        assertTrue(result.stream().anyMatch(user -> Objects.equals(user.id(), firstUser.id())));
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenFileNotFollowTheStandard() {

        content += "0000000075             Maria Teste00000001460000000001      673.492021112";

        file = new MockMultipartFile("file", content.getBytes());

        assertThrows(BadRequestException.class, () -> {
            service.convertFile(file, null, null, null);
        });
    }
}
