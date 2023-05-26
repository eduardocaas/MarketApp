package com.efc.service;

import com.efc.dto.UserDTO;
import com.efc.entity.User;
import com.efc.exception.NotFoundException;
import com.efc.repository.UserRepository;
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

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return getUserDTO(user);
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(user -> getUserDTO(user)).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO userDTO) {
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
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setIsEnabled((user.getIsEnabled()));
        return userDTO;
    }
}
