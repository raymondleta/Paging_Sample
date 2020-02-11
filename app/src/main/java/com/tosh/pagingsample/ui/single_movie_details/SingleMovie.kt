package com.tosh.pagingsample.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.tosh.pagingsample.R
import com.tosh.pagingsample.data.api.MovieApiInterface
import com.tosh.pagingsample.data.api.MovieDbClient
import com.tosh.pagingsample.data.api.POSTER_URL
import com.tosh.pagingsample.data.repository.NetworkState
import com.tosh.pagingsample.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId = intent.getIntExtra("id", 1)
        val apiService : MovieApiInterface = MovieDbClient.getClient()
        movieRepository =
            MovieDetailsRepository(
                apiService
            )

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUi(it)
        })

        viewModel.networkState.observe(this, Observer {
            progressBar.visibility = if (it == NetworkState.LOADING) VISIBLE else GONE
            txtError.visibility = if (it == NetworkState.FAILED) VISIBLE else GONE
        })

    }

    private fun bindUi(it: MovieDetails?) {
        movie_title.text = it!!.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.release_date
        movie_rating.text = it.vote_average.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val movieposterUrl = POSTER_URL + it.poster_path
        Glide.with(this)
            .load(movieposterUrl)
            .into(iv_movie_poster)
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(
                    movieRepository,
                    movieId
                ) as T
            }

        })[SingleMovieViewModel::class.java]
    }
}
