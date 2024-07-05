package com.honka.cryptoTracker.dto

class UserDto(
    val id: Long?,
    val username: String,
    var portfolio: PortfolioDto?,
    val userHistory: ArrayList<UserHistoryDto>?
)