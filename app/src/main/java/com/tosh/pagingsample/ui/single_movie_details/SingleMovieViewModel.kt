package com.tosh.pagingsample.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tosh.pagingsample.data.repository.NetworkState
import com.tosh.pagingsample.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieRepository: MovieDetailsRepository, movieId: Int): ViewModel() {

    private val disposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovie(disposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.fetchMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}