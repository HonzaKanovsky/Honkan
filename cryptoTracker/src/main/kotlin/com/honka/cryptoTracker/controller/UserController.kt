package com.honka.cryptoTracker.controller

import com.honka.cryptoTracker.dto.UserDto
import com.honka.cryptoTracker.dto.UserRequestDto
import com.honka.cryptoTracker.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(val userService: UserService) {

    @PostMapping("register")
    fun registerUser(@RequestBody user: UserRequestDto): UserDto {
        return userService.registerUser(user);
    }

    @PostMapping("login")
    fun loginUser(@RequestBody user: UserRequestDto): UserDto {
        return userService.loginUser(user)
    }
}