package com.honka.cryptoTracker.repository

import com.honka.cryptoTracker.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(userName: String) : Optional<User>
    fun findUserById(id: Long) : User?
}