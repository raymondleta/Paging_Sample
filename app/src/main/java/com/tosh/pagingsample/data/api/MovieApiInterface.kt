package com.tosh.pagingsample.data.api

import com.tosh.pagingsample.data.vo.MovieDetails
import com.tosh.pagingsample.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiInterface {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page")page: Int): Single<MovieResponse>
}