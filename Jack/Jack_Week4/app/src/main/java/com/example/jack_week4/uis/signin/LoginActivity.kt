package com.example.jack_week4.uis.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jack_week4.uis.main.MainActivity
import com.example.jack_week4.uis.signup.SignUpActivity
import com.example.jack_week4.data.entities.User
import com.example.jack_week4.data.remote.AuthService
import com.example.jack_week4.data.remote.Result
import com.example.jack_week4.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient


class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
        }
        binding.loginKakakoLoginIv.setOnClickListener {
            kakaoLogin()
        }
    }

    private fun login() {
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val user = getUser()
        Log.d("LOGIN-REQUEST", "email: ${user.email}, password: ${user.password}")

        val authService = AuthService()
        authService.setLoginView(this)
        authService.login(user)
    }

    private fun getUser(): User {
        val email = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val password = binding.loginPasswordEt.text.toString()

        Log.d("LOGIN-EMAIL", "Email: $email, Password: $password")
        return User(email = email, password = password, name = "")
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun saveJwt2(jwt: String) {
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString("jwt", jwt)
        editor.apply()
    }

    override fun onLoginSuccess(code: String, result: Result) {
        when (code) {
            "COMMON200" -> {
                saveJwt2(result.accessToken)
                Log.d("LOGIN-ACT/JWT", result.accessToken)
                startMainActivity()
            }
        }
    }

    override fun onLoginFailure() {
        Log.d("LOGIN-FAILURE", "로그인 실패")
        Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                handleKakaoLoginResult(token, error)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                handleKakaoLoginResult(token, error)
            }
        }
    }

    private fun handleKakaoLoginResult(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            Toast.makeText(this, "카카오 로그인 실패: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            Log.e("KAKAO_LOGIN", "로그인 실패", error)
        } else if (token != null) {
            Log.d("KAKAO_LOGIN", "로그인 성공! 토큰: ${token.accessToken}")

            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Toast.makeText(this, "사용자 정보 요청 실패", Toast.LENGTH_SHORT).show()
                    Log.e("KAKAO_LOGIN", "사용자 정보 요청 실패", error)
                } else if (user != null) {
                    Log.d("KAKAO_LOGIN", "사용자 정보: id=${user.id}, email=${user.kakaoAccount?.email}")

                    saveKakaoUser(token, user)

                    startMainActivity()
                    finish()
                }
            }
        }
    }
    private fun saveKakaoUser(token: OAuthToken, kakaoUser: com.kakao.sdk.user.model.User) {
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        with(spf.edit()) {
            putString("token", token.accessToken)
            putString("email", kakaoUser.kakaoAccount?.email)
            apply()
        }
    }


}
