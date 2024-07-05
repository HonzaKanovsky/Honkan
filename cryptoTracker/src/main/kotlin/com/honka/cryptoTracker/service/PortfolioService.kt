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
        val userHistory = UserHistory(user=user, portfolioValue = portfolioValue, historizationDate = Instant.now())
        userHistoryRepository.save(userHistory)
    }

    fun getPortfolioForUser(portfolioRequestDto: PortfolioRequestDto): PortfolioDto {
        return PortfolioDto(
            getPortfolioValueByUserID(portfolioRequestDto.userID),
            getPositionsByUserID(portfolioRequestDto.userID)
        )
    }

    fun getPortfolioValueByUserID(userID: Long) : BigDecimal{
        return userHistoryRepository.getFirstByUserIdOrderByHistorizationDateDesc(userID)?.portfolioValue ?: 0.toBigDecimal()
    }
    fun getPositionsByUserID(userID: Long) : ArrayList<PositionDto>{
        val positions = positionRepository.getAllByUserId(userID)

        val result : ArrayList<PositionDto> = ArrayList()

        for (position in positions){
            result.add(position.toDto())
        }

        return result
    }
}