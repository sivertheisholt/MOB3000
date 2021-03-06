package com.example.filmatory.guis;

import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmatory.R
import com.example.filmatory.api.data.movie.Movie
import com.example.filmatory.controllers.sceneControllers.MovieController
import com.example.filmatory.scenes.activities.MovieScene

/**
 * MovieGui contains all the gui elements for the movie page
 *
 * @property movieScene The scene to use
 * @property movieController The controller to use
 */
class MovieGui(private var movieScene: MovieScene, private var movieController : MovieController) {
    var personsRecyclerView: RecyclerView = movieScene.findViewById(R.id.m_person_slider)
    var reviewRecyclerView : RecyclerView = movieScene.findViewById(R.id.review_rv)

    var favoriteBtn : ImageButton = movieScene.findViewById(R.id.movie_favorite_icon)
    var watchlistBtn : ImageButton = movieScene.findViewById(R.id.movie_watchlist_icon)

    var addToListBtn : TextView = movieScene.findViewById(R.id.movie_addtolist_btn)
    var movieTitle: TextView = movieScene.findViewById(R.id.m_title)
    var movieDate: TextView = movieScene.findViewById(R.id.m_date)
    var movieOverview: TextView = movieScene.findViewById(R.id.m_overview)

    var movieImage: ImageView = movieScene.findViewById(R.id.m_img)
    var reviewHeading: TextView = movieScene.findViewById(R.id.review_heading)

    var newReviewBtn: Button = movieScene.findViewById(R.id.new_review_btn)

    init {
        if(movieController.isLoggedIn) {
            favoriteBtn.setOnClickListener {
                if (!movieController.movieIsFavorited) {
                    movieController.addToFavorites()
                } else {
                    movieController.removeFromFavorites()
                }
            }
            watchlistBtn.setOnClickListener {
                if (!movieController.movieIsWatched) {
                    movieController.addToWatchlist()
                } else {
                    movieController.removeFromWatchlist()
                }
            }
            addToListBtn.setOnClickListener {
                movieController.addToUserList()
            }
            newReviewBtn.setOnClickListener {
                movieController.newReviewActivity()
            }
        } else {
            favoriteBtn.setOnClickListener {
                movieController.notLoggedin()
            }

            watchlistBtn.setOnClickListener {
                movieController.notLoggedin()
            }

            addToListBtn.setOnClickListener {
                movieController.notLoggedin()
            }
            newReviewBtn.setOnClickListener {
                movieController.notLoggedin()
            }
        }
    }

    /**
     * Sets the favorite button background icon
     *
     * @param drawable The drawable to set
     */
    fun setFavoriteBtnBackground(drawable: Int) {
        movieScene.runOnUiThread {
            favoriteBtn.setBackgroundResource(drawable)
        }
    }

    /**
     * Sets the watched button background icon
     *
     * @param drawable The drawable to set
     */
    fun setWatchedBtnBackground(drawable: Int) {
        movieScene.runOnUiThread {
            watchlistBtn.setBackgroundResource(drawable)
        }
    }

    /**
     * Sets the movie information
     *
     * @param movie The movie information
     */
    fun setMovieInfo(movie: Movie) {
        movieScene.runOnUiThread {
            movieTitle.text = movie.filminfo.title
            movieDate.text = movie.filminfo.release_date

            Glide.with(movieScene)
                .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + movie.filminfo.poster_path)
                .placeholder(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .centerCrop()
                .into(movieImage)
            movieOverview.text = movie.filminfo.overview
        }
    }
}
