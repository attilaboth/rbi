package com.swati.rbi.contoller;

import com.swati.rbi.entities.User;
import com.swati.rbi.repository.UserDto;
import com.swati.rbi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/addNewUser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> addNewUser(@RequestBody User user) {
        log.debug("Request to addNewUser recieved: {}", user);

        try{
            //check user
            Optional<UserDto> foundByNameAndEmail = userService.findByNameAndEmail(user.getName(), user.getEmail());
            if (foundByNameAndEmail.isPresent()) {
                log.warn("Already exists");
                //FIXME
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                // if not existing persisit
                UserDto userDto = userService.addUser(user);
                // handle unhappy path
                return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
            }
        } catch (Exception ex){
            log.error(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
