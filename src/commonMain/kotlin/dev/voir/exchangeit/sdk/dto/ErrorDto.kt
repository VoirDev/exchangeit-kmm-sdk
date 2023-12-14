package dev.voir.exchangeit.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    val statusCode: Int,
    val message: String,
    val error: String?
)
