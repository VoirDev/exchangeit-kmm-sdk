package dev.voir.anyexchange.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class DataDto<T>(
    val data: T
)
