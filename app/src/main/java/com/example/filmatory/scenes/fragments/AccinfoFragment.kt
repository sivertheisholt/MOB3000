package com.example.filmatory.scenes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.filmatory.R
import com.example.filmatory.controllers.MainController
import com.example.filmatory.scenes.SuperScene
import com.example.filmatory.systems.AuthSystem
import com.example.filmatory.systems.UserInfoSystem
import com.google.android.material.textfield.TextInputEditText

/**
 * This fragment is a component to show the account info
 *
 * @property controller
 * @constructor
 * Extends Fragment
 *
 * @param scene The scene to use
 */
class AccinfoFragment(scene: SuperScene, private val controller: MainController) : Fragment() {
    lateinit var changeUsernameBtn : Button
    lateinit var changePwBtn : Button
    var userInfoSystem = UserInfoSystem(controller.apiSystem)
    val authSystem = AuthSystem(controller.apiSystem, scene.auth, scene, controller.snackbarSystem)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater.inflate(R.layout.fragment_accinfo, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeUsernameBtn = view.findViewById(R.id.accinfo_username_btn)
        changePwBtn = view.findViewById(R.id.accinfo_password_btn)
        changeUsernameBtn.setOnClickListener {
            val textInputField = view.findViewById<TextInputEditText>(R.id.accinfoUsernameTextField)
            val name : String = textInputField.text.toString()
            if(name.length <= 3) return@setOnClickListener controller.snackbarSystem.showSnackbarWarning(requireActivity().resources.getString(R.string.username_too_short))
            userInfoSystem.updateUsername(controller.uid!!, name, ::showMessage)
            textInputField.text?.clear()
        }

        changePwBtn.setOnClickListener {
            val passwordFieldOne : String = view.findViewById<TextInputEditText>(R.id.accinfoPassEditTextField).text.toString()
            val passwordFieldTwo : String = view.findViewById<TextInputEditText>(R.id.accinfoRepeatPassEditTextField).text.toString()
            if(passwordFieldOne == passwordFieldTwo){
                authSystem.updatePassword(passwordFieldOne)
            } else {
                controller.snackbarSystem.showSnackbarWarning("Fields dont match!")
            }
        }
    }
    private fun showMessage(message: String) {
        controller.snackbarSystem.showSnackbarSuccess(message)
    }
}