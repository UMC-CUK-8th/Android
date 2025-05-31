package com.example.week7

interface LoginView {
    fun onLoginSuccess(code : String, result : LoginResult)
    fun onLoginFailure(message : String)
}