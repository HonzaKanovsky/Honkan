package com.honka.cryptoTracker.model

import com.honka.cryptoTracker.dto.UserDto
import com.honka.cryptoTracker.dto.UserHistoryDto
import jakarta.persistence.*
import lombok.Data

@Entity
@Table(name = "user", schema = "crypto_tracker")
@Data
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "username", nullable = false)
    val username: String,

    @Column(name = "hashed_password", nullable = false)
    val hashedPassword: String,

    @OneToMany(mappedBy = "user")
    val positions: MutableSet<Position> = mutableSetOf(),

    @OneToMany(mappedBy = "user")
    val userHistory: MutableSet<UserHistory> = mutableSetOf()
){
    fun toDto() : UserDto {
        return UserDto(
            id = id,
            username = username,
            null,
            userHistory = convertUserHistoryToDto(userHistory)
        )
    }
    private fun convertUserHistoryToDto(userHistory: MutableSet<UserHistory>): ArrayList<UserHistoryDto> {
        return userHistory
            .sortedBy { record -> record.historizationDate }
            .map { record -> record.toDto() }
            .toCollection(ArrayList())
    }

}
