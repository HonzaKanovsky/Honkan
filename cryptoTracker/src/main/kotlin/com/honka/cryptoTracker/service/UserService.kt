package com.honka.cryptoTracker.service

import com.honka.cryptoTracker.dto.PortfolioDto
import com.honka.cryptoTracker.dto.UserDto
import com.honka.cryptoTracker.dto.UserRequestDto
import com.honka.cryptoTracker.model.User
import com.honka.cryptoTracker.model.UserHistory
import com.honka.cryptoTracker.repository.UserHistoryRepository
import com.honka.cryptoTracker.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder

import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant

@Service
class UserService(private val userRepository: UserRepository,
                  private val passwordEncoder: PasswordEncoder,
                  private val userHistoryRepository: UserHistoryRepository,
                  private val portfolioService: PortfolioService) {
    fun registerUser(user: UserRequestDto): UserDto? {
        if(isUserValid(user)){
            val databaseUser = userRepository.findByUsername(user.username).orElse(null)
            if(databaseUser == null){
                val newUser = User(username = user.username,
                                hashedPassword = passwordEncoder.encode(user.password))

                val userHistory = UserHistory(user =newUser, portfolioValue = BigDecimal.ZERO, historizationDate = Instant.now())
                userRepository.save(newUser)
                userHistoryRepository.save(userHistory)
                return newUser.toDto()
            }
        }
        return null

    }
    fun loginUser(user: UserRequestDto): UserDto? {
        if(validateUser(user)) {
            val databaseUser = userRepository.findByUsername(user.username).orElse(null)
            if(databaseUser != null){
                val result = databaseUser.toDto()
                result.portfolio = PortfolioDto(portfolioService.getPortfolioValueByUserID(databaseUser.id!!),portfolioService.getPositionsByUserID(databaseUser.id))
                return result
            }
        }
        return null
    }
    
    private fun validateUser(user: UserRequestDto): Boolean {
        val databaseUser = userRepository.findByUsername(user.username).orElse(null) ?: return false
        return passwordEncoder.matches(user.password, databaseUser.hashedPassword)
    }

    private fun isUserValid(user: UserRequestDto): Boolean {
        if (!user.equals(null) && user.username.isNotEmpty() && user.password.isNotEmpty()) {
            return isUserNameCorrect(user.username) && isPasswordCorrect(user.password)
        }
        return false
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