package com.swati.rbi.service;

import com.swati.rbi.entities.User;
import com.swati.rbi.repository.UserDto;
import com.swati.rbi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserService implements UserService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private UserRepository userRepository;

    public MyUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto addUser(User user) {
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);

    }

    @Override
    public List<UserDto> getAllUser() {
        List<UserDto> userDtosToReturn = Collections.synchronizedList(new ArrayList<>());

        List<User> allUsersList = userRepository.findAll();

        for (User aUser : allUsersList) {
            userDtosToReturn.add(modelMapper.map(aUser, UserDto.class));
        }

        return userDtosToReturn;
    }

    @Override
    public Optional<UserDto> findByNameAndEmail(String name, String email) {
        User byNameAndEmail = userRepository.findByNameAndEmail(name, email);
        UserDto userDto2return = null;
        if (byNameAndEmail != null) {
            userDto2return = modelMapper.map(byNameAndEmail, UserDto.class);
        }
        return Optional.ofNullable(userDto2return);
    }
}
