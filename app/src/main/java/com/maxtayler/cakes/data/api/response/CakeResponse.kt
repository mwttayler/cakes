package com.maxtayler.cakes.data.api.response

import kotlinx.serialization.Serializable

@Serializable
data class CakeResponse(
    val title: String,
    val desc: String,
    val image: String
)