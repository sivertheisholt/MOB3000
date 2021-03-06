package com.example.filmatory.scenes.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmatory.R
import com.example.filmatory.api.data.user.UserLists
import com.example.filmatory.controllers.MainController
import com.example.filmatory.errors.BaseError
import com.example.filmatory.scenes.SuperScene
import com.example.filmatory.scenes.activities.StartScene
import com.example.filmatory.systems.UserInfoSystem
import com.example.filmatory.utils.items.ListItem
import com.example.filmatory.utils.adapters.ListsAdapter

/**
 * This fragment is a component to show the user's lists
 *
 * @property scene The scene to use
 * @constructor
 * Extends Fragment
 *
 * @param controller the controller to use
 */
class ListFragment(private val scene: SuperScene, private val controller: MainController) : Fragment(R.layout.fragment_list) {
    private val listsArrayList: MutableList<ListItem> = ArrayList()
    private lateinit var listsAdapter : ListsAdapter
    private var userInfoSystem = UserInfoSystem(controller.apiSystem)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val createListBtn : Button = view.findViewById(R.id.newListBtn)
        val recyclerView : RecyclerView = view.findViewById(R.id.userlists_rv)
        listsAdapter = ListsAdapter(listsArrayList, requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = listsAdapter
        createListBtn.setOnClickListener {
            val textView = view.findViewById<TextView>(R.id.accinfoNewListTextField)
            val listName : String = textView.text.toString()
            if(listName.length<= 3) return@setOnClickListener controller.snackbarSystem.showSnackbarWarning(requireActivity().resources.getString(R.string.list_name_to_short))
            userInfoSystem.createList(scene.auth.currentUser!!.uid, listName, ::addList, ::onFailure)
            textView.text = ""
        }
    }

    /**
     * Runs when API failed
     *
     * @param baseError Error response
     */
    fun onFailure(baseError: BaseError) {
        controller.snackbarSystem.showSnackbarFailure(baseError.message, ::redirectHome, scene.resources.getString(R.string.nav_home))
    }

    /**
     * Redirects the user back to the start page
     *
     */
    fun redirectHome() {
        val intent = Intent(scene, StartScene::class.java)
        scene.finish()
        scene.startActivity(intent)
    }

    /**
     * Adds list and notifies adapter of change
     *
     * @param id The id of the list
     * @param name The name of the list
     */
    fun addList(id: String, name: String) {
        val newId : String = id.replace("\"", "" )
        listsArrayList.add(
            ListItem(
                name,
                "",
                "https://picsum.photos/124/189",
                "0",
                "0",
                newId
            )
        )
        scene.runOnUiThread {
            listsAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Creates necessary data for showing the lists
     * And notifies adapter of data set change
     *
     * @param userLists
     */
    fun showUserLists(userLists: UserLists){
        for (item in userLists) {
            listsArrayList.add(
                ListItem(
                    item.listname,
                    "",
                    "https://picsum.photos/124/189",
                    item.tvs.size.toString(),
                    item.movies.size.toString(),
                    item.listId
                )
            )
        }
        scene.runOnUiThread {
            listsAdapter.notifyDataSetChanged()
        }
    }
}