package com.efc.service;

import com.efc.entity.User;
import com.efc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    private final UserRepository userRepository;

    @Autowired
    public DBService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void loadDataDB() {

        User u1 = new User(null, "Fulano", true, null);
        User u2 = new User(null, "Beltrano", true, null);
        User u3 = new User(null, "Ciclano", true, null);

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);

    }

}
