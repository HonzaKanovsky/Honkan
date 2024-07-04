package com.honka.cryptoTracker.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduledTasks(private val cryptoService: CryptoService) {
    @Scheduled(cron = "0 0 0 * * ?", zone = "Europe/Prague")
    fun updatePrices() {
        cryptoService.getUpdatedPrices()
    }
}