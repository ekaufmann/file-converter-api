package com.ekaufmann.file_converter_api.factory;

import com.ekaufmann.file_converter_api.dto.OrderDTO;
import com.ekaufmann.file_converter_api.dto.ProductDTO;
import com.ekaufmann.file_converter_api.dto.UserDTO;

import java.util.Set;

public class DTOFactory {

    public static UserDTO createUserDTO(
            Integer id, String name, Set<OrderDTO> orders
    ) {
        return new UserDTO(id, name, orders);
    }

    public static OrderDTO createOrderDTO(
            Integer id, String total, String date, Set<ProductDTO> products
    ) {
        return new OrderDTO(id, total, date, products);
    }

    public static ProductDTO createProductDTO(
            Integer id, String total
    ) {
        return new ProductDTO(id, total);
    }
}
