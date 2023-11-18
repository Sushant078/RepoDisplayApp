package com.emyr78.theproj.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserApiModel(
    val id: Long,
    val login: String
)