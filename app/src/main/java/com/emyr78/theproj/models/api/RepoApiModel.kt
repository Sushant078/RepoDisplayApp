package com.emyr78.theproj.models.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoApiModel(
    val id: Long,
    val name: String,
    val description: String,
    val owner: UserApiModel,
    @Json(name="forks_count")val forksCount: Int,
    @Json(name="stargazers_count")val stargazersCount: Int = 0,
    @Json(name="contributors_url")val contributorsUrl: String,
    @Json(name="created_at")val createdDate: String,
    @Json(name="updated_at")val updatedDate: String
)