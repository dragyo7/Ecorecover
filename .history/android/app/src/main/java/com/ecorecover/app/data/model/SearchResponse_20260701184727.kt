package com.ecorecover.app.data.model

data class SearchResponse(
    val success: Boolean,
    val query: String,
    val count: Int,
    val results: List<String>
)