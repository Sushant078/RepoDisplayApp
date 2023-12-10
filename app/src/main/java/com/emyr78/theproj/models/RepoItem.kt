package com.emyr78.theproj.models

data class RepoItem(
    val owner: String,
    val name: String,
    val description: String,
    val starsCount: Int,
    val forksCount: Int
)