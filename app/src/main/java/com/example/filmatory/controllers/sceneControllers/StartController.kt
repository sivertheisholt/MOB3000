package com.example.filmatory.controllers.sceneControllers

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmatory.MainActivity
import com.example.filmatory.api.data.movie.MovieFrontpage
import com.example.filmatory.api.data.tv.TvFrontpage
import com.example.filmatory.controllers.MainController
import com.example.filmatory.guis.StartGui
import com.example.filmatory.scenes.activities.StartScene
import com.example.filmatory.systems.ApiSystem.RequestBaseOptions
import com.example.filmatory.utils.adapters.DataAdapter
import com.example.filmatory.utils.items.MediaModel

/**
 * StartController controls everything related to the start page
 *
 * @param startScene The StartScene to use
 */
class StartController(private val startScene: StartScene) : MainController(startScene) {
    private val startGui = StartGui(startScene, this)

    init {
        discoverMoviesData(discoverMovieFrontpage)
        discoverTvData(discoverTvFrontpage)
        recMovieData(recMovieFrontpage)
        recTvData(recTvFrontPage)
    }

    /**
     * Update the gui with data from API
     *
     * @param movieFrontpage The response from API
     */
    private fun discoverMoviesData(movieFrontpage: MovieFrontpage){
        val discoverMoviesArraylist: ArrayList<MediaModel> = ArrayList()
        val discoverMoviesAdapter = DataAdapter(startScene, this, startScene, discoverMoviesArraylist)

        movieFrontpage.take(10).forEach { item ->
            discoverMoviesArraylist.add(
                MediaModel(
                    DataAdapter.TYPE_MOVIE_SLIDER,
                    item.original_title,
                    item.release_date,
                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + item.poster_path,
                    item.id
                )
            )
        }
        startScene.runOnUiThread {
            startGui.discoverMoviesRecyclerView.layoutManager =
                LinearLayoutManager(startScene, LinearLayoutManager.HORIZONTAL, false)
            startGui.discoverMoviesRecyclerView.adapter = discoverMoviesAdapter
        }
    }

    /**
     * Update the gui with data from API
     *
     * @param tvFrontpage The response from API
     */
    private fun discoverTvData(tvFrontpage: TvFrontpage){
        val discoverTvsArrayList: ArrayList<MediaModel> = ArrayList()
        val discoverTvsAdapter = DataAdapter(startScene,this, startScene, discoverTvsArrayList)

        tvFrontpage.take(10).forEach { item ->
            discoverTvsArrayList.add(
                MediaModel(
                    DataAdapter.TYPE_TV_SLIDER,
                    item.name,
                    item.first_air_date,
                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + item.poster_path,
                    item.id
                )
            )
        }
        startScene.runOnUiThread {
            startGui.discoverTvsRecyclerView.layoutManager =
                LinearLayoutManager(startScene, LinearLayoutManager.HORIZONTAL, false)
            startGui.discoverTvsRecyclerView.adapter = discoverTvsAdapter
        }
    }

    /**
     * Update the gui with data from API
     *
     * @param movieFrontpage The response from API
     */
    private fun recMovieData(movieFrontpage: MovieFrontpage){
        val recMoviesArrayList: ArrayList<MediaModel> = ArrayList()
        val redMoviesAdapter = DataAdapter(startScene,this, startScene, recMoviesArrayList)

        movieFrontpage.forEach { item ->
            recMoviesArrayList.add(
                MediaModel(
                    DataAdapter.TYPE_MOVIE_SLIDER,
                    item.original_title,
                    item.release_date,
                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + item.poster_path,
                    item.id
                )
            )
        }
        startScene.runOnUiThread {
            startGui.recMoviesRecyclerView.layoutManager =
                LinearLayoutManager(startScene, LinearLayoutManager.HORIZONTAL, false)
            startGui.recMoviesRecyclerView.adapter = redMoviesAdapter
        }
    }

    /**
     * Update the gui with data from API
     *
     * @param tvFrontpage The response from API
     */
    private fun recTvData(tvFrontpage: TvFrontpage){
        val recTvsArrayList: ArrayList<MediaModel> = ArrayList()
        val recTvsAdapter = DataAdapter(startScene,this, startScene, recTvsArrayList)

        tvFrontpage.forEach { item ->
            recTvsArrayList.add(
                MediaModel(
                    DataAdapter.TYPE_TV_SLIDER,
                    item.name,
                    item.first_air_date,
                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + item.poster_path,
                    item.id
                )
            )
        }
        startScene.runOnUiThread {
            startGui.recTvsRecyclerView.layoutManager =
                LinearLayoutManager(startScene, LinearLayoutManager.HORIZONTAL, false)
            startGui.recTvsRecyclerView.adapter = recTvsAdapter
        }
    }
}