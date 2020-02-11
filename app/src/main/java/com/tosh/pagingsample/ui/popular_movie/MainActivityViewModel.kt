package com.tosh.pagingsample.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.tosh.pagingsample.data.repository.NetworkState
import com.tosh.pagingsample.data.vo.Movie
import com.tosh.pagingsample.ui.single_movie_details.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepository: MoviePagedListRepository): ViewModel() {
    private val disposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePageList(disposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty() : Boolean{
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}