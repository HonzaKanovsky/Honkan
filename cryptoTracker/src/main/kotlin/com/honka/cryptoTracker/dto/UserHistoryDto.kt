package com.honka.cryptoTracker.dto

import java.math.BigDecimal
import java.time.Instant

class UserHistoryDto(
    val id : Long,
    val portfolioValue: BigDecimal,
    val historizationDate: Instant
)