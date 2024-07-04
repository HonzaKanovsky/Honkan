package com.honka.cryptoTracker.dto

import java.math.BigDecimal

class PositionDto (
    val cryptoID : Long?,
    val symbol: String?,
    val amount : BigDecimal = BigDecimal.ZERO
    )