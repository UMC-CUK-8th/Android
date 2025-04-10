package com.flow.data

// Activity와 Auth 서비스를 연결시켜주기 위한 것
interface SignUpView {
    fun onSignUpSuccess()
    fun onSignUpFailure(message: String)
}