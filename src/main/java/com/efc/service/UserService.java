package com.efc.service;

import com.efc.dto.UserDTO;
import com.efc.dto.UserResponseDTO;
import com.efc.entity.User;
import com.efc.exception.NotFoundException;
import com.efc.repository.UserRepository;
import com.efc.security.SecurityConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private ModelMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return getUserResponseDTO(user);
    }

    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream().map(user -> getUserResponseDTO(user)).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO userDTO) {
        userDTO.setPassword(SecurityConfig.passwordEncoder().encode(userDTO.getPassword()));
        User user = mapper.map(userDTO, User.class);
        User obj = userRepository.save(user);
        return getUserDTO(obj);
    }

    public UserDTO update(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        Optional<User> obj = userRepository.findById(user.getId());
        if(obj.isEmpty()){
            throw new NotFoundException("User not found");
        }
        userRepository.save(user);
        return getUserDTO(user);
    }

    public void deleteById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        if (obj.isEmpty()){
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    public UserDTO getUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .isEnabled(user.getIsEnabled())
                .build();
    }

    public UserResponseDTO getUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .isEnabled(user.getIsEnabled())
                .build();
    }
}
