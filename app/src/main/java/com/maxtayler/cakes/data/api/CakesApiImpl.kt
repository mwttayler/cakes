package com.maxtayler.cakes.data.api

import com.maxtayler.cakes.data.api.response.CakeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class CakesApiImpl @Inject constructor(
    private val client: HttpClient
) : CakesApi {

    override suspend fun getCakes(): List<CakeResponse> {
        return client.get(
            urlString = "https://raw.githubusercontent.com/Waracle/mobile-coding-test-api/" +
                    "refs/heads/main/cakes"
        ).body()
    }
}