package dev.voir.anyexchange.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    val statusCode: Int,
    val message: String,
    val error: String?
)
