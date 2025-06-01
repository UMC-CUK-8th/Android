package com.example.jack_week4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jack_week4.databinding.ActivityLoginBinding

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
}
