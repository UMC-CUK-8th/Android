package com.example.mint

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mint.week2.ToYouAppActivity


class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // (1) 로고를 중앙에 배치하기 위한 Composable
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFAAF0D1)), // 민트색
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_mint_logo),
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp)), // ← 모서리를 둥글게,
                    contentDescription = "Mint Logo"
                )
            }
        }

        // (2) 2초 뒤 메인 액티비티로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() // 스플래시 액티비티를 닫아서 뒤로가기 시 돌아오지 않도록
        }, 2000) // 2초 후
    }
}
