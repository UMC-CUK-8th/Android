package com.example.week7.ui.signup

interface SignUpView {
    fun onSignUpSuccess()
    fun onSignUpFailure(message : String)
}