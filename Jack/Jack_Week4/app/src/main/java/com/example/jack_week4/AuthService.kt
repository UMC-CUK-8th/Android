package com.example.jack_week4

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun signUp(user: User) {

        val signUpService = getRetrofit().create(AuthRetrofitInterface::class.java)

        signUpService.signUp(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()
                if (resp != null && resp.isSuccess) {
                    signUpView.onSignUpSuccess()
                } else {
                    Log.d("SIGNUP-FAILURE", "response failed: ${resp?.code} / ${resp?.message}")
                    signUpView.onSignUpFailure(resp?.code ?: "UNKNOWN", resp?.message ?: "알 수 없는 오류")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP-FAILURE", "통신 오류: ${t.message}")
                signUpView.onSignUpFailure("NETWORK_ERROR", t.message ?: "서버와 연결할 수 없습니다.")
            }

        })
    }


    fun login(user: User) {
        val loginService = getRetrofit().create(AuthRetrofitInterface::class.java)


        loginService.login(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("LOGIN-RESPONSE", response.toString())
                if (response.isSuccessful && response.code() == 200) {
                    val loginResponse: AuthResponse = response.body()!!
                    Log.d("LOGIN-SUCCESS", loginResponse.toString())

                    when (val code = loginResponse.code) {
                        "200" -> loginView.onLoginSuccess(code, loginResponse.result!!)
                        else -> loginView.onLoginFailure()
                    }
                } else {
                    Log.d("LOGIN-FAILURE", "response failed: ${response.code()} / ${response.errorBody()?.string()}")
                    loginView.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("LOGIN-NETWORK-FAILURE", t.message ?: "Unknown error")
                loginView.onLoginFailure()
            }

        })
    }
}