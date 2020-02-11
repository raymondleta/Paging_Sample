package com.tosh.pagingsample.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tosh.pagingsample.data.api.MovieApiInterface
import com.tosh.pagingsample.data.api.POSTS_PER_PAGE
import com.tosh.pagingsample.data.repository.MovieDataSource
import com.tosh.pagingsample.data.repository.MovieDataSourceFactory
import com.tosh.pagingsample.data.repository.NetworkState
import com.tosh.pagingsample.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: MovieApiInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePageList(disposible: CompositeDisposable): LiveData<PagedList<Movie>>{
        movieDataSourceFactory = MovieDataSourceFactory(apiService, disposible)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POSTS_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
        )
    }

}