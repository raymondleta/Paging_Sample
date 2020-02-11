package com.tosh.pagingsample.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.tosh.pagingsample.data.api.FIRST_PAGE
import com.tosh.pagingsample.data.api.MovieApiInterface
import com.tosh.pagingsample.data.vo.Movie
import com.tosh.pagingsample.data.vo.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val apiService: MovieApiInterface, private val disposable: CompositeDisposable):
    PageKeyedDataSource<Int, Movie>() {

    private val page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        networkState.value = NetworkState.LOADING

        disposable.add(
        apiService.getPopularMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    callback.onResult(it.results, null, page+1)
                    networkState.value = NetworkState.LOADED
                },
                {
                    networkState.value = NetworkState.FAILED
                    Log.e("MovieDataSource", "")
                }
            )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        disposable.add(
            apiService.getPopularMovies(params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.total_pages >= params.key){
                            callback.onResult(it.results, params.key+1)
                            networkState.value = NetworkState.LOADED
                        }else{
                            networkState.value = NetworkState.ENDOFLIST
                        }
                    },
                    {
                        networkState.value = NetworkState.FAILED
                        Log.e("MovieDataSource", "")
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }


}