package com.honka.cryptoTracker.controller

import com.honka.cryptoTracker.dto.UserDto
import com.honka.cryptoTracker.model.User
import com.honka.cryptoTracker.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserService) {

    @PutMapping("register")
    fun registerUser(@RequestBody user: UserDto): String {
        return userService.registerUser(user);
    }

    @PostMapping("login")
    fun loginUser(@RequestBody user: UserDto): String {
        return userService.loginUser(user)
    }
}