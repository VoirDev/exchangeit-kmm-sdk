package dev.voir.exchangeit.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class ListDto<T>(
    val meta: ListMetaDto,
    val data: List<T>
)
