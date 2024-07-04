package com.honka.cryptoTracker.repository

import com.honka.cryptoTracker.model.Crypto
import com.honka.cryptoTracker.model.Position
import com.honka.cryptoTracker.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PositionRepository : JpaRepository<Position,Long> {
    fun getPositionByUserAndCrypto(user: User, crypto: Crypto): Optional<Position>

    fun getAllByUser(user: User): List<Position>

    fun getAllByUserId(userId: Long): List<Position>


}