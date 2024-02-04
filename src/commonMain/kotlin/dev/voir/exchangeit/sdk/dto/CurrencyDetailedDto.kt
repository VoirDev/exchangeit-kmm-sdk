package dev.voir.exchangeit.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDetailedDto(
    val code: String,
    val title: String,
    val description: String? = null,
    val localizedTitle: Map<String, String>,
    val localizedDescription: Map<String, String>,
    val crypto: Boolean,
    val symbol: String? = null,
    val year: String? = null,
    val url: String? = null,
)
