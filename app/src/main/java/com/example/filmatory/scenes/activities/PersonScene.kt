package com.example.filmatory.scenes.activities

import android.os.Bundle
import com.example.filmatory.R
import com.example.filmatory.controllers.sceneControllers.PersonController
import com.example.filmatory.scenes.SuperScene

/**
 * PersonScene contains all the components for creating a person page
 *
 */
class PersonScene : SuperScene() {
    private lateinit var personController : PersonController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_scene)
        personController = PersonController(this)
    }
}