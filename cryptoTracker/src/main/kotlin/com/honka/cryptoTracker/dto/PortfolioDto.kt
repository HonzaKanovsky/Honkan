package com.honka.cryptoTracker.dto

import java.math.BigDecimal

class PortfolioDto (
    val portfolioValue : BigDecimal,
    val positions : ArrayList<PositionDto>
)