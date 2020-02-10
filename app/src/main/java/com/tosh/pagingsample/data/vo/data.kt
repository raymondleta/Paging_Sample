package com.tosh.pagingsample.data.vo

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class Movie(
    val id: Int,
    val poster_path: String,
    val release_date: String,
    val title: String
)