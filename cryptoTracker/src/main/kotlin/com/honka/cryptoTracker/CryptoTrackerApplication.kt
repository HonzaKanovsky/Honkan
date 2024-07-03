package com.honka.cryptoTracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CryptoTrackerApplication

fun main(args: Array<String>) {
	runApplication<CryptoTrackerApplication>(*args)
}
