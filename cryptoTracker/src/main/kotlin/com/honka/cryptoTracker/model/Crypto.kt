package com.honka.cryptoTracker.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Data
import java.time.Instant

@Data
@Entity
@Table(name = "crypto")
data class Crypto(
    @Id
    val id: Long?,
    val name: String,
    val symbol: String,
    val price: Double,
    @Column(name = "date_updated")
    val dateUpdated: Instant
)