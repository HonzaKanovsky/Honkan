package com.honka.cryptoTracker.model

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
    val userHistories: MutableSet<UserHistory> = mutableSetOf()
)