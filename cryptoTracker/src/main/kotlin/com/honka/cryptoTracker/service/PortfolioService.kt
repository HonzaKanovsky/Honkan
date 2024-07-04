package com.honka.cryptoTracker.service

import com.honka.cryptoTracker.dto.PortfolioDto
import com.honka.cryptoTracker.dto.PortfolioRequestDto
import com.honka.cryptoTracker.dto.PositionDto
import com.honka.cryptoTracker.model.Position
import com.honka.cryptoTracker.model.User
import com.honka.cryptoTracker.model.UserHistory
import com.honka.cryptoTracker.repository.CryptoRepository
import com.honka.cryptoTracker.repository.PositionRepository
import com.honka.cryptoTracker.repository.UserHistoryRepository
import com.honka.cryptoTracker.repository.UserRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant

@Service
class PortfolioService(val userRepository: UserRepository,
                       val cryptoRepository: CryptoRepository,
                       val positionRepository: PositionRepository,
                       val userHistoryRepository: UserHistoryRepository) {
    fun updatePortfolioForUser(portfolioRequestDto: PortfolioRequestDto) {
        val user = userRepository.findUserById(portfolioRequestDto.userID)

        if(user != null) {
            val crypto = cryptoRepository.getCryptoById(portfolioRequestDto.positionDto?.cryptoID).orElse(null)
            if(crypto != null) {
                var position = positionRepository.getPositionByUserAndCrypto(user, crypto)
                    .orElse(null)

                if(position == null){
                    position = Position(user = user, crypto = crypto)
                }
                position.amount = portfolioRequestDto.positionDto?.amount ?: BigDecimal.ZERO
                positionRepository.save(position)
                updatePortfolioValue(user)
            }
            else{
                println("unknown cryptoID")
            }
        }
    }

    private fun updatePortfolioValue(user: User){
        val positions = positionRepository.getAllByUser(user)
        var portfolioValue  = BigDecimal.ZERO
        for (position in positions){
            portfolioValue += ((position.crypto?.price ?: BigDecimal.ZERO) * position.amount)
        }
        val userHistory = UserHistory(user=user, portfolioValue = portfolioValue, date = Instant.now())
        userHistoryRepository.save(userHistory)
    }

    fun getPositionsForUser(portfolioRequestDto: PortfolioRequestDto): PortfolioDto {
        var latestPortfolioValue : BigDecimal = BigDecimal.ZERO
        latestPortfolioValue =
            userHistoryRepository.getFirstByUserIdOrderByDateDesc(portfolioRequestDto.userID)?.portfolioValue ?: 0.toBigDecimal()
        val positions = positionRepository.getAllByUserId(portfolioRequestDto.userID)

        val resultList : ArrayList<PositionDto> = ArrayList()

        for (position in positions){
            resultList.add(position.toDto())
        }

        return PortfolioDto(latestPortfolioValue, resultList)
    }
}