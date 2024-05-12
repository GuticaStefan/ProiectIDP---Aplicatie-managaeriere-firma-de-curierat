package com.proiect.CourierAPP.resource;

import com.proiect.CourierAPP.dtos.AddOrderDto;
import com.proiect.CourierAPP.dtos.PasswordDto;
import com.proiect.CourierAPP.dtos.UserDto;
import com.proiect.CourierAPP.dtos.UserUpdateDto;
import com.proiect.CourierAPP.service.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PatchMapping("/{userName}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto updateUser(@PathVariable String userName, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        return userService.update(userName, userUpdateDto);
    }

    @PatchMapping("/{userName}/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable String userName, @RequestBody @Valid PasswordDto passwordDto) {
        userService.changePassword(userName, passwordDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAll() {
        return userService.findAll();
    }


    @GetMapping("/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<UserDto> findByUserName(@PathVariable("userName") String userName) {
        return userService.findByUserName(userName);
    }

    @DeleteMapping("/{userName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("userName") String userName) {
        userService.deleteByUserName(userName);
    }

    @PostMapping("/{userName}/userorders")
    @ResponseStatus(HttpStatus.OK)
    public UserDto addOrderToUser(@PathVariable String userName, @RequestBody @Valid AddOrderDto addOrderDto) {
        return userService.addOrderToUser(userName, addOrderDto);
    }

}