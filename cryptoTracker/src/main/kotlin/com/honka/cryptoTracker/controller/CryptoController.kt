package com.honka.cryptoTracker.controller

import com.honka.cryptoTracker.model.Crypto
import com.honka.cryptoTracker.service.CryptoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/crypto")
class CryptoController(private val cryptoService: CryptoService) {
    @GetMapping("/price")
    fun updatePrices(){
        return cryptoService.getUpdatedPrices()
    }
}