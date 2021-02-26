package com.swati.rbi.service;

import com.swati.rbi.entities.User;
import com.swati.rbi.repository.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto addUser(User user);

    List<UserDto> getAllUser();

    Optional<UserDto> findByNameAndEmail(String name, String email);
}
