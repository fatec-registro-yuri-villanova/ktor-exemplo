package com.fatec.ktor.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Feriado(
    val date: String,
    val name:String,
    val type:String,
)
