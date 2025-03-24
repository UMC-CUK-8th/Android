package com.example.bazzi // 이 파일(MainActivity.kt)이 포함된 패키지 이름

import android.content.Intent  // 다른 화면(Activity)으로 이동할 때 사용하는 인텐트(Intent) 클래스
import android.os.Bundle  // 액티비티가 생성될 때 필요한 데이터 번들을 위한 클래스
import android.view.View     // 버튼 클릭 함수에 필요한 View 타입 (onClick 함수에 꼭 필요)
import androidx.appcompat.app.AppCompatActivity  // AppCompatActivity는 일반적인 액티비티의 기본 클래스 (우리가 주로 사용하는 화면 클래스)
import com.example.week1.YellowActivity

class MainActivity : AppCompatActivity() { // MainActivity는 앱을 실행했을 때 처음 보여지는 기본 화면(Activity)이다
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)  // 부모 클래스의 onCreate 함수를 호출해서 기본 초기화 작업을 해줌
        setContentView(R.layout.activity_main)  // 이 액티비티를 activity_main.xml로 설정함
    }

    // 💡 버튼 클릭 시 호출될 함수
    fun goToYellow(view: View) {  // YellowActivity로 이동할 인텐트를 생성
        val intent = Intent(this, YellowActivity::class.java)  // 생성한 인텐트를 실행해서 YellowActivity로 화면 전환
        startActivity(intent)
    }

    fun goToBlue(view: View) {  // BlueActivity로 이동할 인텐트를 생성
        val intent = Intent(this, BlueActivity::class.java)  // 생성한 인텐트를 실행해서 YellowActivity로 화면 전환
        startActivity(intent)
    }

}