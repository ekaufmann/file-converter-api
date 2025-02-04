package com.ekaufmann.file_converter_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductDTO(

        @JsonProperty("product_id")
        Integer id,

        String value) {
}
