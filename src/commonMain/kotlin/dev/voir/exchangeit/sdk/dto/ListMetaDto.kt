package dev.voir.exchangeit.sdk.dto

import kotlinx.serialization.Serializable

@Serializable
data class ListMetaDto(
    val total: Int,
    val perPage: Int? = null,
    val page: Int,
    val nextPage: Int? = null,
    val totalPages: Int? = null
)
