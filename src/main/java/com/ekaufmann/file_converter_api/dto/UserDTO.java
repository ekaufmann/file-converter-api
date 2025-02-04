package com.ekaufmann.file_converter_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDTO(

        @JsonProperty("user_id")
        Integer id,

        String name,

        Set<OrderDTO> orders) {
}
