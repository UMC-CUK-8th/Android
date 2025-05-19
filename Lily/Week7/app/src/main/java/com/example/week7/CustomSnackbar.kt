package com.example.week7

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar

class CustomSnackbar(view: View, private val message: String) {

    companion object {

        fun make(view: View, message: String) = CustomSnackbar(view, message)
    }

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", 5000)


    private val inflater = LayoutInflater.from(context)




    fun show() {
        snackbar.show()
    }
}