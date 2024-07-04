package com.honka.cryptoTracker.model

import com.honka.cryptoTracker.dto.PositionDto
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "position", schema = "crypto_tracker")
data class Position(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crypto_id", nullable = false)
    var crypto: Crypto? = null,

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    var amount: BigDecimal = BigDecimal.ZERO
){

    fun toDto(): PositionDto {
        return PositionDto(this.crypto?.id, this.crypto?.symbol,this.amount)
    }
}
