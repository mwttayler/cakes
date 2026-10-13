package com.maxtayler.cakes.data.repository

import com.maxtayler.cakes.data.model.Cake

interface CakesRepository {
    suspend fun getCakes(): List<Cake>
}