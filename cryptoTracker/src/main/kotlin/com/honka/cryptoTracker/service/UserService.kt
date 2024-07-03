package com.honka.cryptoTracker.service

import com.honka.cryptoTracker.dto.UserDto
import com.honka.cryptoTracker.model.User
import com.honka.cryptoTracker.repository.UserRepository
import io.netty.util.internal.StringUtil
import org.springframework.security.crypto.password.PasswordEncoder

import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val passwordEncoder: PasswordEncoder) {
    fun registerUser(user: UserDto): String {
        val result = "User: " + user.username + " is registered successfully."
        if(isUserValid(user)){
            val databaseUser = userRepository.findByUsername(user.username).orElse(null)
            if(databaseUser == null){
                val newUser = User(username = user.username,
                                hashedPassword = passwordEncoder.encode(user.password))
                userRepository.save(newUser)
                return result
            }
            return "Name is taken."
        }
        return "Name or password doesn't meet criteria."
    }
    fun loginUser(user: UserDto): String {
        if(validateUser(user)){
            return "user Logged in"
        }
        return "user not logged in"
    }
    
    private fun validateUser(user: UserDto): Boolean {
        val databaseUser = userRepository.findByUsername(user.username).orElse(null) ?: return false
        return passwordEncoder.matches(user.password, databaseUser.hashedPassword)
    }

    private fun isUserValid(user: UserDto): Boolean {
        if (!user.equals(null) && user.username.isNotEmpty() && user.password.isNotEmpty()) {
            return isUserNameCorrect(user.username) && isPasswordCorrect(user.password)
        }
        return false;
    }

    private fun isPasswordCorrect(password: String): Boolean {
        return password.length > 5 && containsNumeric(password) && containsSpecialCharacter(password)
    }

    private fun isUserNameCorrect(username: String) : Boolean{
        return username.length > 2
    }

    private fun containsNumeric(password: String): Boolean {
        val regex = Regex("\\d")
        return regex.containsMatchIn(password)
    }

    private fun containsSpecialCharacter(password: String): Boolean {
        val regex = Regex("[^a-zA-Z0-9]")
        return regex.containsMatchIn(password)
    }

}