package com.honka.cryptoTracker.model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "user_history", schema = "crypto_tracker")
open class UserHistory {
    @Id
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @Column(name = "portfolio_value", nullable = false, precision = 18, scale = 8)
    open var portfolioValue: BigDecimal? = null

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date")
    open var date: Instant? = null
}