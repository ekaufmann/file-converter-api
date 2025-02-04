package com.ekaufmann.file_converter_api.service;

import com.ekaufmann.file_converter_api.dto.OrderDTO;
import com.ekaufmann.file_converter_api.dto.ProductDTO;
import com.ekaufmann.file_converter_api.dto.UserDTO;
import com.ekaufmann.file_converter_api.util.FileUtil;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

@Service
public class FileConverterService {

    private final Logger logger = LoggerFactory.getLogger(FileConverterService.class);

    private final int REGISTRY_MAX_SIZE = 95;

    public Collection<UserDTO> convertFile(
            MultipartFile file, LocalDate startDate, LocalDate endDate, Integer searchedOrder) throws BadRequestException {

        logger.info("Start reading contents of received file:");
        var fileContent = FileUtil.read(file);

        logger.info("The file has {} lines to convert", fileContent.size());

        if (fileContent.stream().anyMatch(registry -> registry.length() != REGISTRY_MAX_SIZE)) {
            throw new BadRequestException("All the file's content should follow the standard format!");
        }

        var users = new HashSet<UserDTO>();

        logger.info("Starting file content conversion:");

        for (String line : fileContent) {

            var userData = line.substring(0, 55);
            var userId = Integer.parseInt(userData.substring(0, 10));
            var userName = userData.substring(10, 55).trim();

            var orderData = line.substring(55, 65) + line.substring(87, 95);
            var orderId = Integer.parseInt(orderData.substring(0, 10));

            if (searchedOrder != null && searchedOrder != orderId) {
                continue;
            }

            var orderDate = orderData.substring(10, 18)
                    .replaceAll("(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");

            if (startDate != null && endDate != null && verifyDateNotInRange(orderDate, startDate, endDate)) {
                continue;
            }

            var productData = line.substring(65, 87);
            var productId = Integer.parseInt(productData.substring(0, 10));
            var productValue = productData.substring(10, 22).trim();

            var product = new ProductDTO(productId, productValue);
            var products = new TreeSet<>(Comparator.comparing(ProductDTO::id));

            products.add(product);

            var orders = new TreeSet<>(Comparator.comparing(OrderDTO::id));
            var order = new OrderDTO(orderId, product.value(), orderDate, products);

            orders.add(order);

            users.stream()
                    .filter(oldUser -> oldUser.id() == userId)
                    .findFirst()
                    .ifPresentOrElse(
                            user -> {
                                user.orders().stream()
                                        .filter(oldOrder -> oldOrder.id() == orderId)
                                        .findFirst()
                                        .ifPresentOrElse(
                                                oldOrder -> {
                                                    updateOldOrder(oldOrder, user, product);
                                                },
                                                () -> user.orders().add(order)
                                        );
                            },
                            () -> users.add(new UserDTO(userId, userName, orders))
                    );
        }

        logger.info("The file was converted successfully!");

        return users;
    }

    private boolean verifyDateNotInRange(String orderDate, LocalDate startDate, LocalDate endDate) {

        var date = LocalDate.parse(orderDate);

        return date.isBefore(startDate) || date.isAfter(endDate);
    }

    private void updateOldOrder(OrderDTO oldOrder, UserDTO user, ProductDTO newProduct) {

        var oldProducts = oldOrder.products();
        oldProducts.add(newProduct);

        var total = oldProducts.stream()
                .map(product -> new BigDecimal(product.value()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .toString()
                .formatted(".2f");

        var newOrder = new OrderDTO(oldOrder.id(), total, oldOrder.date(), oldProducts);

        user.orders().remove(oldOrder);
        user.orders().add(newOrder);
    }
}
