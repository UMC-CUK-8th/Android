package com.example.jack_week4

interface SignUpView {
    fun onSignUpSuccess()
    fun onSignUpFailure(code: String, message: String) // 실패 코드와 메시지 전달 받게 변경
}
