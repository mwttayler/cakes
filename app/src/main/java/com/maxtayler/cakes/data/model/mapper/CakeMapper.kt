package com.maxtayler.cakes.data.model.mapper

import com.maxtayler.cakes.data.api.response.CakeResponse
import com.maxtayler.cakes.data.model.Cake
import javax.inject.Inject

class CakeMapper @Inject constructor() {

    fun mapFrom(response: CakeResponse): Cake {
        return Cake(
            title = response.title,
            description = response.desc,
            imageUrl = response.image
        )
    }
}