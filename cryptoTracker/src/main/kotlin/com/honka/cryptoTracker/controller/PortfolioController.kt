package com.honka.cryptoTracker.controller

import com.honka.cryptoTracker.dto.PortfolioDto
import com.honka.cryptoTracker.dto.PortfolioRequestDto
import com.honka.cryptoTracker.service.PortfolioService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/portfolio")
class PortfolioController(val portfolioService: PortfolioService) {

    @PostMapping("/update")
    fun updatePortfolio(@RequestBody portfolioRequestDto: PortfolioRequestDto){
        portfolioService.updatePortfolioForUser(portfolioRequestDto)
    }

    @GetMapping("/positions")
    fun getPositions(@RequestBody portfolioRequestDto : PortfolioRequestDto): PortfolioDto{
        return portfolioService.getPositionsForUser(portfolioRequestDto)
    }
}