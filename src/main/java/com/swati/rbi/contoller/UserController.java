package com.swati.rbi.contoller;

import com.swati.rbi.entities.User;
import com.swati.rbi.repository.UserDto;
import com.swati.rbi.service.UserService;
import com.swati.rbi.utils.ValidatorUtil;
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
        final String userName = user.getName();
        final String emailAddr = user.getEmail();
        log.debug("Request to addNewUser received: {}, {}", userName, emailAddr);

        if (ValidatorUtil.isNotValidParam(userName) || ValidatorUtil.isNotValidParam(emailAddr)) {
            log.warn("{} or {} is invalid parameter.", userName, emailAddr);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            try {
                Optional<UserDto> foundByNameAndEmail = userService.findByNameAndEmail(userName, emailAddr);
                if (foundByNameAndEmail.isPresent()) {
                    log.warn("{} already exists.", userName);
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                } else {
                    UserDto userDto = userService.addUser(user);
                    log.warn("{} was added to database.", userDto.getName());
                    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
                }
            } catch (Exception ex) {
                log.error(ex.getLocalizedMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

}
