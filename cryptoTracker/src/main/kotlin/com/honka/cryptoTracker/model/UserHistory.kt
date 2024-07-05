package com.honka.cryptoTracker.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.honka.cryptoTracker.dto.UserHistoryDto
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "user_history", schema = "crypto_tracker")
class UserHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    @Column(name = "portfolio_value", nullable = false, precision = 19, scale = 4)
    var portfolioValue: BigDecimal = BigDecimal.ZERO,

    @Column(name = "date")
    var historizationDate: Instant? = null
){

    fun toDto(): UserHistoryDto {
        return UserHistoryDto(id = this.id!!, portfolioValue=portfolioValue, historizationDate = historizationDate!!)
    }
}
