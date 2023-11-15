package com.emyr78.theproj.models

data class RepoApiModel(
    val id: Long,
    val name: String,
    val description: String,
    val owner: UserApiModel,
    val forksCount: Int,
    val stargazersCount: Int,
    val contributorsUrl: String,
    val createdDate: String,
    val updatedDate: String
)