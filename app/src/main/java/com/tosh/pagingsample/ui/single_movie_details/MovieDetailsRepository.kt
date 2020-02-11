package com.tosh.pagingsample.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.tosh.pagingsample.data.api.MovieApiInterface
import com.tosh.pagingsample.data.repository.MovieDetailsDataSource
import com.tosh.pagingsample.data.repository.NetworkState
import com.tosh.pagingsample.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: MovieApiInterface) {
    lateinit var movieDetailsDs: MovieDetailsDataSource

    fun fetchSingleMovie(disposible: CompositeDisposable, movieId: Int): LiveData<MovieDetails>{
        movieDetailsDs = MovieDetailsDataSource(apiService, disposible)
        movieDetailsDs.fetchMovieDetails(movieId)

        return movieDetailsDs.movieDetailsResponse
    }

    fun fetchMovieDetailNetworkState(): LiveData<NetworkState>{
        return movieDetailsDs.networkState
    }
}