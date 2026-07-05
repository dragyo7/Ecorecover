package com.ecorecover.app.data.model

data class SearchResultItem(
    val id: Int,
    val name: String
)

data class SearchResponse(
    val success: Boolean,
    val query: String,
    val count: Int,
    val results: List<SearchResultItem>
)