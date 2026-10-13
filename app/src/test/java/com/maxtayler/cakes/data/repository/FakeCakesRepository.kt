package com.maxtayler.cakes.data.repository

import com.maxtayler.cakes.data.model.Cake

class FakeCakesRepository(
    var cakes: List<Cake> = emptyList(),
    var error: Exception? = null
) : CakesRepository {

    override suspend fun getCakes(): List<Cake> {
        error?.let { throw it }
        return cakes
    }
}
