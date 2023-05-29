package com.efc.service;

import com.efc.dto.UserDTO;
import com.efc.entity.Product;
import com.efc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DBService {

    private final UserService userService;
    private final ProductRepository productRepository;

    @Autowired
    public DBService(UserService userService, ProductRepository productRepository) {
        this.userService = userService;
        this.productRepository = productRepository;
    }

    public void loadDataDB() {

        UserDTO u1 = new UserDTO(null, "Fulano", "fulano123", "abc123",  true);
        UserDTO u2 = new UserDTO(null, "Beltrano", "beeltraano", "ajKty7g", true);
        UserDTO u3 = new UserDTO(null, "Ciclano", "cicla", "cicla2000", true);

        userService.save(u1);
        userService.save(u2);
        userService.save(u3);

        Product p1 = new Product(null, "Feijão", new BigDecimal("7.99"), 50);
        Product p2 = new Product(null, "Arroz", new BigDecimal("5.99"), 30);

        productRepository.save(p1);
        productRepository.save(p2);

    }

}
