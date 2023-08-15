package dev.voir.anyexchange.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class DataArrayDto<T>(
    val total: Int,
    val data: List<T>
)
