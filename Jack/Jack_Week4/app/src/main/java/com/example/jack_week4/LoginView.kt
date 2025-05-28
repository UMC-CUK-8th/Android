package com.example.jack_week4

interface LoginView {
    fun onLoginSuccess(code : String, result: Result)
    fun onLoginFailure()
}