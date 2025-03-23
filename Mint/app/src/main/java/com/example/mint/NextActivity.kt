package com.example.mint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

// 필요한 import 추가
class NextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        // XML에 정의된 Toolbar를 찾아서 액션바로 설정
        val toolbar = findViewById<Toolbar>(R.id.toolbar_emotion)
        setSupportActionBar(toolbar)

        // 액션바에 뒤로가기 버튼을 표시하고, 커스텀 아이콘을 설정
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_custom_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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
