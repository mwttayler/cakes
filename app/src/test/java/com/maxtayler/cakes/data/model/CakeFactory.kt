package com.maxtayler.cakes.data.model

object CakeFactory {

    fun create(
        title: String = "Carrot cake",
        description: String = "$title description",
        imageUrl: String = "https://example.com/cake.jpg"
    ) = Cake(
        title = title,
        description = description,
        imageUrl = imageUrl
    )
}
