package com.efc.service;

import com.efc.entity.Product;
import com.efc.entity.User;
import com.efc.repository.ProductRepository;
import com.efc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DBService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DBService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public void loadDataDB() {

        User u1 = new User(null, "Fulano", true, null);
        User u2 = new User(null, "Beltrano", true, null);
        User u3 = new User(null, "Ciclano", true, null);

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);

        Product p1 = new Product(null, "Feijão", new BigDecimal("7.99"), 50);
        Product p2 = new Product(null, "Arroz", new BigDecimal("5.99"), 30);

        productRepository.save(p1);
        productRepository.save(p2);

    }

}
