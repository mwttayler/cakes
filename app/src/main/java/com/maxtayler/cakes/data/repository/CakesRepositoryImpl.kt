package com.maxtayler.cakes.data.repository

import com.maxtayler.cakes.data.api.CakesApi
import com.maxtayler.cakes.data.model.Cake
import com.maxtayler.cakes.data.model.mapper.CakeMapper
import javax.inject.Inject

class CakesRepositoryImpl @Inject constructor(
    private val api: CakesApi,
    private val mapper: CakeMapper
) : CakesRepository {

    override suspend fun getCakes(): List<Cake> {
        return api.getCakes().map { mapper.mapFrom(it) }
            .distinctBy { it.title.trim().lowercase() }
            .sortedBy { it.title.trim().lowercase() }
    }
}