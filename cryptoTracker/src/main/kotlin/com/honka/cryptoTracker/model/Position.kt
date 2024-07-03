package com.honka.cryptoTracker.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "position", schema = "crypto_tracker")
data class Position(
    @Id
    @Column(name = "id", nullable = false)
    var id: Long,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crypto_id", nullable = false)
    var crypto: Crypto? = null,

    @Column(name = "amount", nullable = false, precision = 18, scale = 8)
    var amount: BigDecimal? = null
)