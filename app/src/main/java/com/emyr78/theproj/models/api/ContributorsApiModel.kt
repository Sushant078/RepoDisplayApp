package com.emyr78.theproj.models.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContributorsApiModel(
    val id: Long,
    val login: String,
    @Json(name = "avatar_url") val avatarUrl: String
)