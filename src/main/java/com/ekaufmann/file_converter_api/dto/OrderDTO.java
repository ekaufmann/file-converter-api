package com.ekaufmann.file_converter_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderDTO(

        @JsonProperty("order_id")
        Integer id,

        String total,

        String date,

        Set<ProductDTO> products

) {
}
