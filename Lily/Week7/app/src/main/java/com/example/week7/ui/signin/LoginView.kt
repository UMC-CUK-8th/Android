package com.example.week7.ui.signin

interface LoginView {
    fun onLoginSuccess(code : String, result : LoginResult)
    fun onLoginFailure(message : String)
}