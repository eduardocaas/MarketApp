package com.efc.service;

import com.efc.dto.UserDTO;
import com.efc.entity.User;
import com.efc.exception.NotFoundException;
import com.efc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return getUserDTO(user);
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(user -> getUserDTO(user)).collect(Collectors.toList());
    }

    public UserDTO save(User user) {
        User obj = userRepository.save(user);
        return getUserDTO(obj);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setIsEnabled((user.getIsEnabled()));
        return userDTO;
    }
}
