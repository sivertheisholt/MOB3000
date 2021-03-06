package com.example.filmatory.utils.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmatory.R
import com.example.filmatory.controllers.MainController
import com.example.filmatory.scenes.SuperScene
import com.example.filmatory.scenes.activities.MovieScene
import com.example.filmatory.scenes.activities.TvScene
import com.example.filmatory.utils.items.MediaModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Custom adapter for recyclerview that contains movies or tv-shows
 *
 * @property scene
 * @property controller
 * @property context
 * @property arrayList
 */
class DataAdapter(private val scene: SuperScene, private val controller : MainController, private var context : Context, var arrayList: ArrayList<MediaModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var uid = scene.auth.uid

    /**
     * Companion object for which ViewHolder to use
     */
    companion object {
        const val TYPE_MOVIE = 1
        const val TYPE_TV = 2
        const val TYPE_MOVIE_SLIDER = 3
        const val TYPE_TV_SLIDER = 4
        const val TYPE_FAVORITES_MOVIE = 5
        const val TYPE_FAVORITES_TV = 6
        const val TYPE_WATCHLIST_MOVIE = 7
        const val TYPE_WATCHLIST_TV = 8
        const val TYPE_SEARCH_MOVIE = 9
        const val TYPE_SEARCH_TV = 10
    }

    /**
     * Binds data for movies to view and creates a direct reference
     *
     * @constructor
     *
     * @param itemView : The view to use
     */
    private inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.media_image)
        val itemTitle: TextView = itemView.findViewById(R.id.media_title)
        val itemDate: TextView = itemView.findViewById(R.id.media_date)
        var movieId: Int? = null
        fun bind(position: Int) {
            val viewmodel = arrayList[position]
            itemTitle.text = viewmodel.itemTitle
            itemDate.text = viewmodel.itemDate
            Glide.with(context)
                .load(viewmodel.itemImage)
                .error(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(itemImage)
            itemView.setOnClickListener {
                movieId = viewmodel.itemId
                val intent = Intent(context, MovieScene::class.java)
                intent.putExtra("movieId", movieId)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                scene.finish()
                context.startActivity(intent)
            }
        }
    }

    /**
     * Binds data for tv-shows to view and creates a direct reference
     *
     * @constructor
     *
     * @param itemView : The view to use
     */
    private inner class TvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.media_image)
        val itemTitle: TextView = itemView.findViewById(R.id.media_title)
        val itemDate: TextView = itemView.findViewById(R.id.media_date)
        var tvId: Int? = null
        fun bind(position: Int) {
            val viewmodel = arrayList[position]
            itemTitle.text = viewmodel.itemTitle
            itemDate.text = viewmodel.itemDate
            Glide.with(context)
                .load(viewmodel.itemImage)
                .error(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(itemImage)
            itemView.setOnClickListener {
                tvId = viewmodel.itemId
                val intent = Intent(context, TvScene::class.java)
                intent.putExtra("tvId", tvId)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                scene.finish()
                context.startActivity(intent)
            }
        }
    }

    /**
     * Binds data for tv-shows in slider to view and creates a direct reference
     *
     * @constructor
     *
     * @param itemView : The view to use
     */
    private inner class TvSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.slider_image)
        val itemTitle: TextView = itemView.findViewById(R.id.slider_title)
        val itemDate: TextView = itemView.findViewById(R.id.slider_date)
        var tvId: Int? = null
        fun bind(position: Int) {
            val viewmodel = arrayList[position]
            itemTitle.text = viewmodel.itemTitle
            itemDate.text = viewmodel.itemDate
            Glide.with(context)
                .load(viewmodel.itemImage)
                .error(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(itemImage)
            itemView.setOnClickListener {
                tvId = viewmodel.itemId
                val intent = Intent(context, TvScene::class.java)
                intent.putExtra("tvId", tvId)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                scene.finish()
                context.startActivity(intent)
            }
        }
    }

    /**
     * Binds data for movies in slider to view and creates a direct reference
     *
     * @constructor
     *
     * @param itemView : The view to use
     */
    private inner class MovieSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.slider_image)
        val itemTitle: TextView = itemView.findViewById(R.id.slider_title)
        val itemDate: TextView = itemView.findViewById(R.id.slider_date)
        var movieId: Int? = null
        fun bind(position: Int) {
            val viewmodel = arrayList[position]
            itemTitle.text = viewmodel.itemTitle
            itemDate.text = viewmodel.itemDate
            Glide.with(context)
                .load(viewmodel.itemImage)
                .error(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(itemImage)
            itemView.setOnClickListener {
                movieId = viewmodel.itemId
                val intent = Intent(context, MovieScene::class.java)
                intent.putExtra("movieId", movieId)
                scene.finish()
                context.startActivity(intent)
            }
        }
    }

    /**
     * Binds data for movies in favorites to view and creates a direct reference
     * Makes clickable so redirect to movies page, and hold it to open window to remove from favorites
     *
     * @constructor
     *
     * @param itemView : The view to use
     */
    private inner class MovieFavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.media_image)
        val itemTitle: TextView = itemView.findViewById(R.id.media_title)
        val itemDate: TextView = itemView.findViewById(R.id.media_date)
        var movieId: Int? = null
        fun bind(position: Int) {
            val viewmodel = arrayList[position]
            itemTitle.text = viewmodel.itemTitle
            itemDate.text = viewmodel.itemDate
            movieId = viewmodel.itemId
            Glide.with(context)
                .load(viewmodel.itemImage)
                .error(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(itemImage)
            itemView.setOnClickListener {
                val intent = Intent(context, MovieScene::class.java)
                intent.putExtra("movieId", movieId)
                scene.finish()
                context.startActivity(intent)
            }
            itemView.setOnLongClickListener {
                val menuItems = arrayOf("Delete")
                val chosenList = -1
                MaterialAlertDialogBuilder(context)
                    .setTitle(context.resources.getString(R.string.accinfo_mediamenu))
                    .setNeutralButton(context.resources.getString(R.string.cancel_btn)) { dialog, which ->

                    }
                    .setPositiveButton(context.resources.getString(R.string.confirm_btn)) { dialog, which ->
                        val favoriteSystem = controller.getFavoriteSystem()
                        favoriteSystem?.removeMovieFromFavorites(movieId.toString())
                        controller.notifyMovieFavoriteAdapter(position)
                    }
                    .setSingleChoiceItems(menuItems, chosenList) { dialog, which ->
                        println(movieId)
                    }
                    .show()
                return@setOnLongClickListener true
            }
        }
    }

    /**
     * Binds data for tv-shows in favorites to view and creates a direct reference
     * Makes clickable so redirect to tv-show page, and hold it to open window to remove from favorites
     *
     * @constructor
     *
     * @param itemView : The view to use
     */
    private inner class TvFavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.media_image)
        val itemTitle: TextView = itemView.findViewById(R.id.media_title)
        val itemDate: TextView = itemView.findViewById(R.id.media_date)
        var tvId: Int? = null
        fun bind(position: Int) {
            val viewmodel = arrayList[position]
            itemTitle.text = viewmodel.itemTitle
            itemDate.text = viewmodel.itemDate
            tvId = viewmodel.itemId
            Glide.with(context)
                .load(viewmodel.itemImage)
                .error(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(itemImage)
            itemView.setOnClickListener {
                val intent = Intent(context, TvScene::class.java)
                intent.putExtra("tvId", tvId)
                scene.finish()
                context.startActivity(intent)
            }
            itemView.setOnLongClickListener {
                val menuItems = arrayOf("Delete")
                val chosenList = -1
                MaterialAlertDialogBuilder(context)
                    .setTitle(context.resources.getString(R.string.accinfo_mediamenu))
                    .setNeutralButton(context.resources.getString(R.string.cancel_btn)) { dialog, which ->

                    }
                    .setPositiveButton(context.resources.getString(R.string.confirm_btn)) { dialog, which ->
                        val favoriteSystem = controller.getFavoriteSystem()
                        favoriteSystem?.removeTvFromFavorites(tvId.toString())
                        controller.notifyTvFavoriteAdapter(position)
                    }
                    .setSingleChoiceItems(menuItems, chosenList) { dialog, which ->
                        println(tvId)
                    }
                    .show()
                return@setOnLongClickListener true
            }
        }
    }

    /**
     * Binds data for movies in watchlist to view and creates a direct reference
     * Makes clickable so redirect to movies page, and hold it to open window to remove from favorites
     *
     * @constructor
     *
     * @param itemView : The view to use
     */
    private inner class MovieWatchlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.media_image)
        val itemTitle: TextView = itemView.findViewById(R.id.media_title)
        val itemDate: TextView = itemView.findViewById(R.id.media_date)
        var movieId: Int? = null
        fun bind(position: Int) {
            val viewmodel = arrayList[position]
            itemTitle.text = viewmodel.itemTitle
            itemDate.text = viewmodel.itemDate
            movieId = viewmodel.itemId
            Glide.with(context)
                .load(viewmodel.itemImage)
                .error(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(itemImage)
            itemView.setOnClickListener {
                val intent = Intent(context, MovieScene::class.java)
                intent.putExtra("movieId", movieId)
                scene.finish()
                context.startActivity(intent)
            }
            itemView.setOnLongClickListener {
                val menuItems = arrayOf("Delete")
                val chosenList = -1
                MaterialAlertDialogBuilder(context)
                    .setTitle(context.resources.getString(R.string.accinfo_mediamenu))
                    .setNeutralButton(context.resources.getString(R.string.cancel_btn)) { dialog, which ->

                    }
                    .setPositiveButton(context.resources.getString(R.string.confirm_btn)) { dialog, which ->
                        val watchlistSystem = controller.getWatchlistSystem()
                        watchlistSystem?.removeMovieFromWatchlist(movieId.toString())
                        controller.notifyMovieWatchlistAdapter(position)
                    }
                    .setSingleChoiceItems(menuItems, chosenList) { dialog, which ->
                        println(movieId)
                    }
                    .show()
                return@setOnLongClickListener true
            }
        }
    }

    /**
     * Binds data for tv-shows in watchlist to view and creates a direct reference
     * Makes clickable so redirect to tv-show page, and hold it to open window to remove from favorites
     *
     * @constructor
     *
     * @param itemView : The view to use
     */
    private inner class TvWatchlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.media_image)
        val itemTitle: TextView = itemView.findViewById(R.id.media_title)
        val itemDate: TextView = itemView.findViewById(R.id.media_date)
        var tvId: Int? = null
        fun bind(position: Int) {
            val viewmodel = arrayList[position]
            itemTitle.text = viewmodel.itemTitle
            itemDate.text = viewmodel.itemDate
            tvId = viewmodel.itemId
            Glide.with(context)
                .load(viewmodel.itemImage)
                .error(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(itemImage)
            itemView.setOnClickListener {
                val intent = Intent(context, TvScene::class.java)
                intent.putExtra("tvId", tvId)
                scene.finish()
                context.startActivity(intent)
            }
            itemView.setOnLongClickListener {
                val menuItems = arrayOf("Delete")
                val chosenList = -1
                MaterialAlertDialogBuilder(context)
                    .setTitle(context.resources.getString(R.string.accinfo_mediamenu))
                    .setNeutralButton(context.resources.getString(R.string.cancel_btn)) { dialog, which ->

                    }
                    .setPositiveButton(context.resources.getString(R.string.confirm_btn)) { dialog, which ->
                        val watchSystem = controller.getWatchlistSystem()
                        watchSystem?.removeTvFromWatchlist(tvId.toString())
                        controller.notifyTvWatchlistAdapter(position)
                    }
                    .setSingleChoiceItems(menuItems, chosenList) { dialog, which ->
                        println(tvId)
                    }
                    .show()
                return@setOnLongClickListener true
            }
        }
    }

    /**
     * Binds data for movies from searchresult to view and creates a direct reference
     * Makes clickable so redirect to movies
     *
     * @constructor
     *
     * @param itemView : The view to use
     */
    private inner class MovieSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.search_image)
        val itemTitle: TextView = itemView.findViewById(R.id.search_title)
        val itemOverview: TextView = itemView.findViewById(R.id.search_overview)
        var movieId: Int? = null
        fun bind(position: Int) {
            val viewmodel = arrayList[position]
            itemTitle.text = viewmodel.itemTitle
            itemOverview.text = viewmodel.itemDate
            movieId = viewmodel.itemId
            Glide.with(context)
                .load(viewmodel.itemImage)
                .error(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(itemImage)
            itemView.setOnClickListener {
                val intent = Intent(context, MovieScene::class.java)
                intent.putExtra("movieId", movieId)
                scene.finish()
                context.startActivity(intent)
            }
        }
    }

    /**
     * Binds data for movies from searchresult to view and creates a direct reference
     * Makes clickable so redirect to tv-shows
     *
     * @constructor
     *
     * @param itemView : The view to use
     */
    private inner class TvSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.search_image)
        val itemTitle: TextView = itemView.findViewById(R.id.search_title)
        val itemOverview: TextView = itemView.findViewById(R.id.search_overview)
        var tvId: Int? = null
        fun bind(position: Int) {
            val viewmodel = arrayList[position]
            itemTitle.text = viewmodel.itemTitle
            itemOverview.text = viewmodel.itemDate
            tvId = viewmodel.itemId
            Glide.with(context)
                .load(viewmodel.itemImage)
                .error(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(itemImage)
            itemView.setOnClickListener {
                val intent = Intent(context, TvScene::class.java)
                intent.putExtra("tvId", tvId)
                scene.finish()
                context.startActivity(intent)
            }
        }
    }

    /**
     * Switch to decide which ViewHolder to inflate, creates the ViewHolder required
     *
     * @param parent
     * @param viewType : Required ViewHolder
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_MOVIE -> MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_media_item, parent, false))
            TYPE_TV -> TvViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_media_item, parent, false))
            TYPE_MOVIE_SLIDER -> MovieSliderViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_slider_item, parent, false))
            TYPE_TV_SLIDER -> TvSliderViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_slider_item, parent, false))
            TYPE_FAVORITES_MOVIE -> MovieFavoritesViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_media_item, parent, false))
            TYPE_FAVORITES_TV -> TvFavoritesViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_media_item, parent, false))
            TYPE_WATCHLIST_MOVIE -> MovieWatchlistViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_media_item, parent, false))
            TYPE_WATCHLIST_TV -> TvWatchlistViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_media_item, parent, false))
            TYPE_SEARCH_MOVIE -> MovieSearchViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_search_item, parent, false))
            TYPE_SEARCH_TV -> TvSearchViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_search_item, parent, false))
            else -> {
                TvViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_media_item, parent, false))
            }
        }
    }

    /**
     * Sets the number of items the adapter will display
     *
     * @return Size of array
     */
    override fun getItemCount(): Int {
        return arrayList.size
    }

    /**
     * Updates the contents of the ItemView
     *
     * @param holder : ViewHolder
     * @param position
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(arrayList[position].viewType){
            TYPE_MOVIE -> (holder as MovieViewHolder).bind(position)
            TYPE_TV -> (holder as TvViewHolder).bind(position)
            TYPE_MOVIE_SLIDER -> (holder as MovieSliderViewHolder).bind(position)
            TYPE_TV_SLIDER -> (holder as TvSliderViewHolder).bind(position)
            TYPE_FAVORITES_MOVIE -> (holder as MovieFavoritesViewHolder).bind(position)
            TYPE_FAVORITES_TV -> (holder as TvFavoritesViewHolder).bind(position)
            TYPE_WATCHLIST_MOVIE -> (holder as MovieWatchlistViewHolder).bind(position)
            TYPE_WATCHLIST_TV -> (holder as TvWatchlistViewHolder).bind(position)
            TYPE_SEARCH_MOVIE -> (holder as MovieSearchViewHolder).bind(position)
            TYPE_SEARCH_TV -> (holder as TvSearchViewHolder).bind(position)
        }
    }

    /**
     * Gets the itemview type
     *
     * @param position
     * @return ItemView Type
     */
    override fun getItemViewType(position: Int): Int {
        return arrayList[position].viewType
    }
}
