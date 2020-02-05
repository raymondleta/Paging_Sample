package com.tosh.pagingsample.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tosh.pagingsample.data.api.MovieApiInterface
import com.tosh.pagingsample.data.vo.MovieDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsDataSource(private val apiService: MovieApiInterface, private val disposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()

    val networkState: LiveData<NetworkState>
            get() = _networkState

    private val _movieDetailsResponse = MutableLiveData<MovieDetails>()
    val movieDetailsResponse: LiveData<MovieDetails>
    get() = _movieDetailsResponse

    fun fetchMovieDetails(movieId: Int){
        _networkState.value = NetworkState.LOADING

        try {
            disposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            _movieDetailsResponse.value = it
                            _networkState.value = NetworkState.LOADED
                        },
                        {
                            _networkState.value = NetworkState.FAILED
                            Log.e("MovieDataSource ", it.localizedMessage!!)
                        }
                    )
            )
        }catch (e: Exception){
            Log.e("Error", e.localizedMessage)
        }
    }
}