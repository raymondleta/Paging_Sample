package com.tosh.pagingsample.data.vo

data class MovieResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

data class Result(
    val id: Int,
    val poster_path: String,
    val release_date: String,
    val title: String
)