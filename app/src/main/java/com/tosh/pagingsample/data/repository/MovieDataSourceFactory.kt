package com.tosh.pagingsample.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.tosh.pagingsample.data.api.MovieApiInterface
import com.tosh.pagingsample.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService: MovieApiInterface, private val disposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, disposable)

        movieLiveDataSource.value = movieDataSource

        return movieDataSource
    }
}