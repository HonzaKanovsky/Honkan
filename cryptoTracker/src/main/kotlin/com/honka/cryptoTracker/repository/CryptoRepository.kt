package com.honka.cryptoTracker.repository

import com.honka.cryptoTracker.model.Crypto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CryptoRepository : JpaRepository<Crypto, Long> {
    fun getCryptoById(id: Long?): Optional<Crypto>
}