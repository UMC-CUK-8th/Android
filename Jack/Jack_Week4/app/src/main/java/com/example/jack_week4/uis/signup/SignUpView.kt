package com.example.jack_week4.uis.signup

interface SignUpView {
    fun onSignUpSuccess()
    fun onSignUpFailure(code: String, message: String)
}
