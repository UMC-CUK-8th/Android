package com.example.week7

interface LoginView {
    fun onLoginSuccess(code : Int, result : Result)
    fun onLoginFailure(message : String)
}