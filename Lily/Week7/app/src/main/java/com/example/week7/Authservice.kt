package com.example.week7

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
        RetrofitInstance.authApi.signUp(user)
            .enqueue(object : Callback<BaseResponse<SignUpResult>> {
                override fun onResponse(
                    call: Call<BaseResponse<SignUpResult>>,
                    response: Response<BaseResponse<SignUpResult>>
                ) {
                    Log.d("SignUp-StatusCode", response.code().toString())

                    val body = response.body()

                    if (body == null) {
                        Log.e("SignUp", "응답 body가 null입니다.")
                        signUpView.onSignUpFailure("서버 응답이 잘못되었습니다.")
                        return
                    }

                    Log.d("SignUp-isSuccess", body.isSuccess.toString())
                    Log.d("SignUp-code", body.code)
                    Log.d("SignUp-message", body.message)

                    if (body.isSuccess && body.code == "COMMON200") {
                        signUpView.onSignUpSuccess()
                    } else {
                        signUpView.onSignUpFailure("회원가입 실패: ${body.message}")
                    }
                }

                override fun onFailure(call: Call<BaseResponse<SignUpResult>>, t: Throwable) {
                    Log.d("SignUp-Failure", t.message.toString())
                    signUpView.onSignUpFailure("네트워크 오류: ${t.message}")
                }
            })

        Log.d("SignUpActivity", "All Finished")
    }

    fun login(user: User) {
        val loginRequest = LoginRequest(user.email, user.password)
        Log.d("LoginRequest", "email=${loginRequest.email}, password=${loginRequest.password}")
        RetrofitInstance.authApi.login(loginRequest).enqueue(object : Callback<BaseResponse<LoginResult>> {
            override fun onResponse(
                call: Call<BaseResponse<LoginResult>>,
                response: Response<BaseResponse<LoginResult>>
            ) {
                Log.d("LoginRequest", "email: ${user.email}, password: ${user.password}")
                Log.d("Login-StatusCode", response.code().toString())
                Log.d("Login-Success", response.toString())

                val body = response.body()  // ✅ 여기에 body를 선언해야 함

                if (body != null) {
                    when (body.code) {
                        "COMMON200" -> loginView.onLoginSuccess(body.code, body.result!!)
                        else -> loginView.onLoginFailure(body.message)
                    }
                } else {
                    val errorMsg = response.errorBody()?.string()
                    Log.e("Login-ErrorBody", errorMsg ?: "errorBody is null")
                    loginView.onLoginFailure("서버 응답이 없습니다. (errorBody: $errorMsg)")
                }
            }

            override fun onFailure(call: Call<BaseResponse<LoginResult>>, t: Throwable) {
                Log.d("Login-Failure", t.message.toString())
                loginView.onLoginFailure("네트워크 오류: ${t.message}")
            }
        })

        Log.d("LoginActivity", "All Finished")
    }
}