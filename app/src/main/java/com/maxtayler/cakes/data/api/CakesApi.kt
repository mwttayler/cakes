package com.maxtayler.cakes.data.api

import com.maxtayler.cakes.data.api.response.CakeResponse

interface CakesApi {

    suspend fun getCakes(): List<CakeResponse>
}