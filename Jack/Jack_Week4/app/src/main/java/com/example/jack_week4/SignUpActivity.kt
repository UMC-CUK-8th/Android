package com.example.jack_week4

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jack_week4.databinding.ActivitySignupBinding
import com.example.jack_week4.databinding.ActivitySongBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity(),SignUpView {

    lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
        }
    }

    private fun getUser(): User {
        val email = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd = binding.signUpPasswordEt.text.toString()
        val name = binding.signUpNameEt.text.toString()

        Log.d("SIGNUP-EMAIL", "Email: $email, Password: $pwd")
        return User(email, pwd, name)
    }

private fun signUp() {
    if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
        Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
        return
    }

    if (binding.signUpNameEt.text.toString().isEmpty()) {
        Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
        return
    }

    if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
        Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
        return
    }


    val authService= AuthService()
    authService.setSignUpView(this)

    authService.signUp(getUser())

    Log.d("SIGNUP-ACT/ASYNC", "Hello, FLO")
    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure(code: String, message: String) {
        Toast.makeText(this, "회원가입 실패: $message", Toast.LENGTH_SHORT).show()
        Log.d("SIGNUP-FAILURE", "code: $code / message: $message")
    }



}