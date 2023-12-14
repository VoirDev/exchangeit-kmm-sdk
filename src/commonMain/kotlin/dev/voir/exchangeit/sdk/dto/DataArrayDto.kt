package dev.voir.exchangeit.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class DataArrayDto<T>(
    val meta: ListMetaDto,
    val data: List<T>
)
