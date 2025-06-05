package com.example.week7.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.week7.data.entities.User
import com.example.week7.data.remote.AuthService
import com.example.week7.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 카카오 사용자 정보 영역 처음엔 숨기기
        binding.kakaoUserInfoLayout.visibility = View.GONE

        // 기존 이메일 로그인
        binding.loginSignUpTv.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
        }

        // ✅ 카카오 로그인 버튼 클릭
        binding.loginKakakoLoginIv.setOnClickListener {
            handleKakaoLogin()
        }

        // ✅ 카카오 로그아웃 버튼 클릭
        binding.btnKakaoLogout.setOnClickListener {
            logoutFromKakao()
        }
    }

    private fun login() {
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val email = binding.loginIdEt.text.toString().trim() + "@" +
                binding.loginDirectInputEt.text.toString().trim()
        val pwd: String = binding.loginPasswordEt.text.toString()

        val authService = AuthService()
        authService.setLoginView(this)
        authService.login(User(email, pwd, ""))
    }

    private fun handleKakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카카오톡 앱 로그인 강제
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                handleLoginResult(token, error)
            }
        } else {
            Toast.makeText(this, "카카오톡 앱이 설치되어 있어야 로그인할 수 있습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleLoginResult(token: OAuthToken?, error: Throwable?) {
        Log.d("KakaoLogin", "콜백 호출됨")
        if (error != null) {
            Log.e("KakaoLogin", "로그인 실패", error)
            Toast.makeText(this, "카카오 로그인 실패: ${error.message}", Toast.LENGTH_SHORT).show()
        } else if (token != null) {
            Log.d("KakaoLogin", "로그인 성공: ${token.accessToken}")
            getUserInfo()
        }
    }



    private fun getUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("KakaoUser", "사용자 정보 요청 실패", error)
                Toast.makeText(this, "카카오 사용자 정보를 가져오지 못했습니다", Toast.LENGTH_SHORT).show()
            } else if (user != null) {
                val email = user.kakaoAccount?.email ?: ""
                val nickname = user.kakaoAccount?.profile?.nickname ?: ""
                val profileImage = user.kakaoAccount?.profile?.profileImageUrl ?: ""

                Log.d("KakaoUser", "email: $email, nickname: $nickname")

                // 사용자 정보 바인딩
                binding.tvKakaoNickname.text = nickname
                binding.tvKakaoEmail.text = ""
                Glide.with(this)
                    .load(profileImage)
                    .into(binding.ivKakaoProfile)

                // 사용자 정보 표시
                binding.kakaoUserInfoLayout.visibility = View.VISIBLE
                binding.btnKakaoLogout.visibility = View.VISIBLE

            }
        }
    }

    private fun logoutFromKakao() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("KakaoLogout", "로그아웃 실패", error)
                Toast.makeText(this, "로그아웃 실패", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("KakaoLogout", "로그아웃 성공")
                Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

                // UI 초기화
                binding.tvKakaoNickname.text = "닉네임"
                binding.tvKakaoEmail.text = "이메일"
                binding.ivKakaoProfile.setImageResource(R.drawable.ic_launcher_foreground)
                binding.kakaoUserInfoLayout.visibility = View.GONE
            }
        }
    }

    private fun saveJwt(jwt: Int) {
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putInt("jwt", jwt)
        editor.apply()
    }

    private fun saveJwtFromServer(jwt: String) {
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString("jwt", jwt)
        editor.apply()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginSuccess(code: String, result: LoginResult) {
        saveJwtFromServer(result.accessToken)
        startMainActivity()
    }

    override fun onLoginFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}