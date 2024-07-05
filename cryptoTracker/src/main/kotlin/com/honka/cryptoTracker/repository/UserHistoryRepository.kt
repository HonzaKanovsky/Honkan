package com.honka.cryptoTracker.repository

import com.honka.cryptoTracker.model.User
import com.honka.cryptoTracker.model.UserHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserHistoryRepository : JpaRepository<UserHistory, Long> {
    fun getFirstByUserIdOrderByHistorizationDateDesc(id: Long) : UserHistory?
}