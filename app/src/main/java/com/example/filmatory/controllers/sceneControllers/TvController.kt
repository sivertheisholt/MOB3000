package com.example.filmatory.controllers.sceneControllers

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmatory.R
import com.example.filmatory.api.data.tv.Tv
import com.example.filmatory.api.data.tv.TvReviews
import com.example.filmatory.api.data.user.Favorites
import com.example.filmatory.api.data.user.UserLists
import com.example.filmatory.api.data.user.Watchlist
import com.example.filmatory.controllers.MainController
import com.example.filmatory.guis.TvGui
import com.example.filmatory.scenes.activities.CreateReviewScene
import com.example.filmatory.scenes.activities.TvScene
import com.example.filmatory.systems.ApiSystem.RequestBaseOptions
import com.example.filmatory.systems.FavoriteSystem
import com.example.filmatory.systems.TvSystem
import com.example.filmatory.systems.WatchlistSystem
import com.example.filmatory.utils.items.PersonItem
import com.example.filmatory.utils.adapters.PersonRecyclerViewAdapter
import com.example.filmatory.utils.adapters.ReviewAdapter
import com.example.filmatory.utils.items.ListItem
import com.example.filmatory.utils.items.ReviewItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * TvController controls everything related to the tv page
 *
 * @param tvScene The TvScene to use
 */
class TvController(private val tvScene: TvScene) : MainController(tvScene) {
    private var intent: Intent = tvScene.intent
    private val tvSystem = TvSystem(apiSystem, snackbarSystem, tvScene)
    private val favoriteSystem = FavoriteSystem(tvScene, null, tvSystem)
    private val watchlistSystem = WatchlistSystem(tvScene, null, tvSystem)
    private val tvGui = TvGui(tvScene, this)

    private val tvId = intent.getIntExtra("tvId", 0)
    private val personsArrayList: MutableList<PersonItem> = ArrayList()
    private val personsAdapter = PersonRecyclerViewAdapter(personsArrayList, tvScene)
    private var listNameArrayList = arrayOf<String>()
    private var listArrayList : MutableList<ListItem> = ArrayList()

    private val reviewArrayList: MutableList<ReviewItem> = ArrayList()
    private val reviewAdapter = ReviewAdapter(reviewArrayList, tvScene)

    var tvIsWatched : Boolean = false
        private set
    var tvIsFavorited : Boolean = false
        private set

    init {
        apiSystem.requestTV(RequestBaseOptions(tvId.toString(), null, ::getTv, ::onFailure), languageCode)
        apiSystem.requestTvReviews(RequestBaseOptions(tvId.toString(), null, ::getReviews, ::onFailure), languageCode)

        if(isLoggedIn){
            apiSystem.requestUserFavorites(RequestBaseOptions(null, uid, ::checkIfFavorited, ::onFailure))
            apiSystem.requestUserWatchlist(RequestBaseOptions(null, uid, ::checkIfWatchlist, ::onFailure))
            apiSystem.requestUserLists(RequestBaseOptions(null, uid, ::getUserLists, ::onFailure), languageCode)
        }
        tvGui.personsRecyclerView.layoutManager = LinearLayoutManager(tvScene, LinearLayoutManager.HORIZONTAL, false)
        tvGui.personsRecyclerView.adapter = personsAdapter

        tvGui.reviewRecyclerView.layoutManager = LinearLayoutManager(tvScene, LinearLayoutManager.VERTICAL, false)
        tvGui.reviewRecyclerView.adapter = reviewAdapter
    }

    /**
     * Update the gui with data from API
     *
     * @param tv The response from API
     */
    private fun getTv(tv : Tv){
        tvGui.setTvInfo(tv)
        tv.personer.cast.take(10).forEach { item ->
            personsArrayList.add(
                PersonItem(
                    item.name,
                    item.character,
                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + item.profile_path,
                    item.id
                )
            )
        }
        tvScene.runOnUiThread{
            personsAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Update the gui with the reviews that belongs to the tv-show
     *
     * @param tvReviews : API response with the reviews
     */
    private fun getReviews(tvReviews: TvReviews){
        tvScene.runOnUiThread {
            if(tvReviews.size != 0) tvGui.reviewHeading.visibility = View.VISIBLE


            tvReviews.forEach {
                    item -> reviewArrayList.add(ReviewItem(item.author, item.avatar, item.date, item.text, item.stars, item.userId, item._id))
            }
            reviewAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Gets the current users lists, and stores them in an arraylist
     *
     * @param userLists : Users lists
     */
    private fun getUserLists(userLists: UserLists){
        if(userLists.size != 0){
            for(item in userLists){
                listNameArrayList += arrayOf(item.listname)
                listArrayList.add(ListItem(item.listname, item.listUserId, "", "", "", item.listId))
            }
        } else {
            println("User does not have any lists")
        }
    }

    /**
     * Checks if the tv-show is favorited
     * If favorited, set the correct icon
     *
     * @param favorites : Users favorites
     */
    private fun checkIfFavorited(favorites: Favorites){
        tvIsFavorited = favoriteSystem.checkIfTvFavorited(favorites, tvId)
        if(!tvIsFavorited) {
            tvGui.setFavoriteBtnBackground(R.drawable.favorite_icon_border)
        } else {
            tvGui.setFavoriteBtnBackground(R.drawable.favorite_icon_filled)
        }
    }

    /**
     * Checks if the tv-show is in watchlist
     *
     * @param watchlist : Users watchlist
     */
    private fun checkIfWatchlist(watchlist: Watchlist){
        tvIsFavorited = watchlistSystem.checkIfTvWatchlist(watchlist, tvId)
        if(!tvIsFavorited) {
            tvGui.setWatchedBtnBackground(R.drawable.watchlist_icon_border)
        } else {
            tvGui.setWatchedBtnBackground(R.drawable.watchlist_icon_filled)
        }
    }

    /**
     * Runs a method which adds the tv-show to favorites
     * Then updates icon
     */
    fun addToFavorites(){
        tvIsFavorited = favoriteSystem.addTvToFavorites(tvId.toString())
        tvGui.setFavoriteBtnBackground(R.drawable.favorite_icon_filled)
    }

    /**
     * Runs a method which removes the tv-show from favorites
     * Then updates icon
     */
    fun removeFromFavorites(){
        tvIsFavorited = favoriteSystem.removeTvFromFavorites(tvId.toString())
        tvGui.setFavoriteBtnBackground(R.drawable.favorite_icon_border)
    }

    /**
     * Runs a method which adds the tv-show to watchlist
     * Then updates icon
     */
    fun addToWatchlist(){
        tvIsFavorited = watchlistSystem.addTvToWatchlist(tvId.toString())
        tvGui.setWatchedBtnBackground(R.drawable.watchlist_icon_filled)
    }

    /**
     * Runs a method which removes the tv-show from watchlist
     * Then updates icon
     */
    fun removeFromWatchlist(){
        tvIsFavorited = watchlistSystem.removeMovieFromWatchlist(tvId.toString())
        tvGui.setWatchedBtnBackground(R.drawable.watchlist_icon_border)
    }

    /**
     * Opens a confirm dialog and fills it with the users lists
     * When positive button is pressed, run a method which adds the tv-show to selected list.
     *
     */
    fun addToUserList(){
        var chosenList : Int = -1
        MaterialAlertDialogBuilder(tvScene)
            .setTitle(tvScene.resources.getString(R.string.mylists))
            .setNeutralButton(tvScene.resources.getString(R.string.close_btn)) { _, which -> }
            .setPositiveButton(tvScene.resources.getString(R.string.confirm_btn)) { dialog, which ->
                if(chosenList != -1){
                    tvSystem.addTvToList(listArrayList[chosenList].list_id, tvId.toString())
                } else {
                    snackbarSystem.showSnackbarWarning("No list was selected")
                }
            }
            .setSingleChoiceItems(listNameArrayList, chosenList) { dialog, which ->
                chosenList = which
            }
        .show()
    }

    /**
     * Methods which opens the review activity
     *
     */
    fun newReviewActivity(){
        val intent = Intent(tvScene, CreateReviewScene::class.java)
        intent.putExtra("mediaId", tvId)
        intent.putExtra("mediaType","tv")
        tvScene.finish()
        tvScene.startActivity(intent)
    }

    /**
     * Message for the user if they are not logged in
     *
     */
    fun notLoggedin() {
        snackbarSystem.showSnackbarWarning("You need to log in to use this function!")
    }

    /**
     * Gets the system that contains methods for favorites
     *
     * @return favoriteSystem
     */
    override fun getFavoriteSystem(): FavoriteSystem {
        return favoriteSystem
    }

    /**
     * Gets the system that contains methods for watchlists
     *
     * @return watchlistSystem
     */
    override fun getWatchlistSystem(): WatchlistSystem {
        return watchlistSystem
    }
}