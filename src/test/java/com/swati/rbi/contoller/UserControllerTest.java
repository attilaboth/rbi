package com.swati.rbi.contoller;

import com.swati.rbi.entities.User;
import com.swati.rbi.repository.UserDto;
import com.swati.rbi.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddNewUser() {
        Optional<UserDto> userDto = Optional.of(new UserDto("Attila Both", "email@gmail.com"));
        User newUser = new User();
        newUser.setName("Attila Both");
        newUser.setEmail("email@gmail.com");

        when(userService.addUser(any())).thenReturn(userDto.get());
        when(userService.findByNameAndEmail(anyString(), anyString())).thenReturn(userDto);

        ResponseEntity<UserDto> result = userController.addNewUser(newUser);
        Assertions.assertNotNull(result);
    }
}
