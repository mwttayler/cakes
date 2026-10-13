package com.maxtayler.cakes.data.api

import com.maxtayler.cakes.data.api.response.CakeResponse

class FakeCakesApi(
    private val cakes: List<CakeResponse> = emptyList(),
    private val error: Exception? = null
) : CakesApi {

    override suspend fun getCakes(): List<CakeResponse> {
        error?.let { throw it }
        return cakes
    }
}
