package com.honka.cryptoTracker.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Data
import java.math.BigDecimal
import java.time.Instant

@Data
@Entity
@Table(name = "crypto")
data class Crypto(
    @Id
    val id: Long?,
    val name: String,
    val symbol: String,
    @Column(precision = 19, scale = 4)
    val price: BigDecimal = BigDecimal.ZERO,
    @Column(name = "date_updated")
    val dateUpdated: Instant
)
