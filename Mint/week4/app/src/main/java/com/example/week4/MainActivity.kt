package com.example.week4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week4.ui.theme.Week4Theme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Week4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StopwatchScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

/**
 * 스톱워치 화면 (Jetpack Compose)
 */
@Composable
fun StopwatchScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    // 누적 시간(ms)
    var timeInMillis by remember { mutableStateOf(0L) }
    // 실행 여부
    var isRunning by remember { mutableStateOf(false) }
    // 시간을 갱신하는 코루틴 Job
    var job by remember { mutableStateOf<Job?>(null) }
    // "실행 중"일 때 시간 계산을 위한 기준 시각
    var startTime by remember { mutableStateOf(0L) }

    Box(modifier = modifier.fillMaxSize()) {

        // 1) 시간 표시 (화면 중앙)
        Text(
            text = formatTime(timeInMillis),
            style = MaterialTheme.typography.displayMedium.copy(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.Center)
        )

        // 2) 버튼 영역 (화면 아래)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Start/Pause 통합 버튼
            val startPauseLabel = if (isRunning) "Pause" else "Start"

            Button(
                onClick = {
                    if (isRunning) {
                        // 실행 중 → Pause
                        job?.cancel()
                        isRunning = false
                    } else {
                        // 정지 중 → Start
                        isRunning = true
                        // startTime 재설정 (지금 시각 - 이미 지난 시간)
                        startTime = System.currentTimeMillis() - timeInMillis
                        job = scope.launch {
                            while (isActive) {
                                timeInMillis = System.currentTimeMillis() - startTime
                                delay(10)
                            }
                        }
                    }
                },
                modifier = Modifier
                    .height(60.dp)
                    .width(120.dp)
            ) {
                Text(
                    text = startPauseLabel,
                    fontSize = 20.sp
                )
            }

            // Clear 버튼
            Button(
                onClick = {
                    if (!isRunning) {
                        // (A) 정지 상태에서 Clear => 0초로 초기화
                        timeInMillis = 0L
                    } else {
                        // (B) 실행 중일 때 Clear => 정지 없이 시간만 0초로 리셋
                        startTime = System.currentTimeMillis() // 현재 시점으로 다시 기준 잡기
                        timeInMillis = 0L
                    }
                },
                modifier = Modifier
                    .height(60.dp)
                    .width(120.dp)
            ) {
                Text(
                    text = "Clear",
                    fontSize = 20.sp
                )
            }
        }
    }
}

/**
 * 밀리초 -> `분:초.밀리초(두 자리)` 변환
 *  예) 0:00.00, 4:05.37 등
 */
fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    val hundredths = (ms % 1000) / 10  // 0..99

    return String.format("%d:%02d.%02d", minutes, seconds, hundredths)
}

@Preview(showBackground = true)
@Composable
fun PreviewStopwatchScreen() {
    Week4Theme {
        StopwatchScreen()
    }
}
