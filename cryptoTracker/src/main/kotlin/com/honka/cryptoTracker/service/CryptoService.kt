package com.honka.cryptoTracker.service

import com.honka.cryptoTracker.model.Crypto
import com.honka.cryptoTracker.repository.CryptoRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.stereotype.Service
import java.util.ArrayList
import java.time.Instant
import org.json.JSONObject


@Service
class CryptoService(private val cryptoRepository: CryptoRepository) {

    fun getUpdatedPrices() {
        val result = ArrayList<Crypto>()

        // Replace with your actual CoinMarketCap API key
        val apiKey = System.getenv("CMC_API_KEY") ?: throw IllegalStateException("API key not found")

        // URL to fetch 1000 currencies
        val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=1&limit=5000&convert=USD"

        // Set up logging interceptor (optional)
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        // Set up OkHttp client
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // Create the request
        val request = Request.Builder()
            .url(url)
            .addHeader("Accepts", "application/json")
            .addHeader("X-CMC_PRO_API_KEY", apiKey)
            .build()

        // Execute the request and get the response
        val response: Response = client.newCall(request).execute()
        val responseData = response.body?.string()

        // Parse the JSON response
        if (response.isSuccessful && responseData != null) {
            val jsonResponse = JSONObject(responseData)
            val data = jsonResponse.getJSONArray("data")

            for (i in 0 until data.length()) {
                val currency = data.getJSONObject(i)
                val id = currency.getLong("id")
                val name = currency.getString("name")
                val symbol = currency.getString("symbol")
                val price = currency.getJSONObject("quote").getJSONObject("USD").getBigDecimal("price")

                val crypto = Crypto(id=id,name=name,symbol=symbol,price=price, dateUpdated = Instant.now())
                cryptoRepository.save(crypto)
                result.add(crypto)
            }
        } else {
            println("Failed to fetch data: ${response.message}")
        }
    }
}