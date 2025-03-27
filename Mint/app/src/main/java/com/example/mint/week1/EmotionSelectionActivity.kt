package com.example.mint.week1

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import com.example.mint.week1.NextActivity
import com.example.mint.R


class EmotionSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion_selection)

        // XML에 정의된 Toolbar를 찾아서 액션바로 설정
        val toolbar = findViewById<Toolbar>(R.id.toolbar_emotion)
        setSupportActionBar(toolbar)

        // 액션바에 뒤로가기 버튼을 표시하고, 커스텀 아이콘을 설정
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_custom_back)

        // 1) "행복" 아이콘/텍스트 클릭 시
        val btnHappy = findViewById<LinearLayout>(R.id.btn_emotion_happy)
        btnHappy.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            // 필요하다면 intent.putExtra("emotion", "happy")
            startActivity(intent)
        }

        // 2) "들뜸" 아이콘/텍스트 클릭 시
        val btnExcited = findViewById<LinearLayout>(R.id.btn_emotion_excited)
        btnExcited.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            // intent.putExtra("emotion", "excited")
            startActivity(intent)
        }

        // 3) "평범" 아이콘/텍스트 클릭 시
        val btnNormal = findViewById<LinearLayout>(R.id.btn_emotion_normal)
        btnNormal.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            // intent.putExtra("emotion", "normal")
            startActivity(intent)
        }

        // 4) "불안" 아이콘/텍스트 클릭 시
        val btnAnxious = findViewById<LinearLayout>(R.id.btn_emotion_anxious)
        btnAnxious.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            // intent.putExtra("emotion", "anxious")
            startActivity(intent)
        }

        // 5) "화남" 아이콘/텍스트 클릭 시
        val btnAngry = findViewById<LinearLayout>(R.id.btn_emotion_angry)
        btnAngry.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            // intent.putExtra("emotion", "angry")
            startActivity(intent)
        }
    }

    // 업 버튼 클릭 시 동작 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()  // 뒤로가기: 현재 Activity 종료
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
