package com.emyr78.theproj.models

import com.emyr78.theproj.models.api.RepoApiModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopRepoSearchResults(val items: List<RepoApiModel>)