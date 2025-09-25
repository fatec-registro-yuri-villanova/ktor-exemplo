package com.fatec.ktor.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val name: String? = null,
    val message: String? = null,
    val type: String? = null
)
