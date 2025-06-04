package com.example.jack_week4.uis.signin

import com.example.jack_week4.data.remote.Result

interface LoginView {
    fun onLoginSuccess(code : String, result: Result)
    fun onLoginFailure()
}