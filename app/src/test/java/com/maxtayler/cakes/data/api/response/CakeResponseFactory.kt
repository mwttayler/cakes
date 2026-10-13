package com.maxtayler.cakes.data.api.response

object CakeResponseFactory {

    fun create(
        title: String = "Carrot cake",
        desc: String = "$title description",
        image: String = "https://example.com/cake.jpg"
    ) = CakeResponse(
        title = title,
        desc = desc,
        image = image
    )
}
