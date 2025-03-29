package com.example.mint

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mint.ui.theme.MintTheme
import com.example.mint.week1.EmotionSelectionActivity
import com.example.mint.week2.ToYouAppActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 필요 시 호출
        setContent {
            MintTheme {
                // 앱 전체 배경을 채우는 Surface에 week 선택 화면을 넣습니다.
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    WeekSelectionScreen()  // 수정된 함수 호출
                }
            }
        }
    }
}

@Composable
fun WeekSelectionScreen() {
    // Compose 내에서 context를 가져와서 Intent에 사용
    val context = LocalContext.current
    // 이미지와 텍스트를 화면 중앙에 배치하는 Column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 이미지 추가
        Image(
            painter = painterResource(id = R.drawable.ic_mint_logo), // 원하는 이미지 리소스 ID 사용
            contentDescription = "개발자 민트의 로고",
            modifier = Modifier
                .size(200.dp) // 이미지 크기 설정
                .padding(bottom = 5.dp) // 이미지와 텍스트 간 간격
        )

        // 텍스트 추가
        Text(
            text = "민트의 안드로이드", // 표시할 텍스트
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold, // 글자 두께
                color = MaterialTheme.colorScheme.primary // 텍스트 색상
            )
        )

        // 버튼
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {context.startActivity(Intent(context, EmotionSelectionActivity::class.java)) }) {
            Text("Go to Week 1")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            context.startActivity(Intent(context, ToYouAppActivity::class.java))  // HomeScreenActivity로 이동
        }) {
            Text("Go to Week 2")
        }


    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MintTheme {
        WeekSelectionScreen()
    }
}
