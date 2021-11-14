package com.example.filmatory.controllers.sceneControllers

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmatory.R
import com.example.filmatory.api.data.tv.Tvs
import com.example.filmatory.controllers.MainController
import com.example.filmatory.errors.BaseError
import com.example.filmatory.scenes.activities.TvsScene
import com.example.filmatory.systems.ApiSystem.RequestBaseOptions
import com.example.filmatory.utils.adapters.DataAdapter
import com.example.filmatory.utils.items.MediaModel

/**
 * TvsController manipulates the TvsScene gui
 *
 * @param tvsScene The TvsScene to use
 */
class TvsController(private val tvsScene: TvsScene) : MainController(tvsScene) {
    private val tvsArrayList: ArrayList<MediaModel> = ArrayList()
    private var tvsRecyclerView: RecyclerView = tvsScene.findViewById(R.id.recyclerView)
    private val tvsAdapter = DataAdapter(tvsScene, tvsArrayList)
    private val spinner : Spinner = tvsScene.findViewById(R.id.filter_spinner)

    init {
        apiSystem.requestTvs(RequestBaseOptions(null, null, ::tvsData, ::onFailure))
        tvsRecyclerView.layoutManager = GridLayoutManager(tvsScene, 2)
        tvsRecyclerView.adapter = tvsAdapter

        ArrayAdapter.createFromResource(tvsScene, R.array.filter_array, android.R.layout.simple_spinner_dropdown_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.visibility = View.VISIBLE
            spinner.adapter = adapter
        }

        //Pass the scene to the listener that implements OnItemSelectedListener
        spinner.onItemSelectedListener = tvsScene
    }
    fun onFailure(baseError: BaseError) {

    }

    /**
     * Update the gui with data from API
     *
     * @param tvs The response from API
     */
    private fun tvsData(tvs: Tvs){
        tvsScene.runOnUiThread(Runnable {
            tvs.forEach{
                    item -> tvsArrayList.add(MediaModel(DataAdapter.TYPE_TV ,item.title, item.releaseDate,"https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + item.pictureUrl, item.id))
            }
            tvsAdapter.notifyDataSetChanged()
        })
    }

    fun onNewSelected(itemAtPosition: Any) {
        //TODO: MAKE ADAPTER UPDATE
    }
}