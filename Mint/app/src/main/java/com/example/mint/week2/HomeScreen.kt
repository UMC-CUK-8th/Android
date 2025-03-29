package com.example.mint.week2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mint.R


@Composable
fun HomeScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDFDFD)),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(150.dp))
                Text(
                    text = "날짜",
                    style = TextStyle(
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "멘트",
                    style = TextStyle(
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFDFDFD)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // (1) 말풍선 - 윗부분에 배치
                    Box(
                        modifier = Modifier
                            .padding(top = 50.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_speech_buddle),
                            contentDescription = "말풍선"
                        )
                    }

                    // (2) 우체통 - 아랫부분에 배치
                    Box(
                        modifier = Modifier
                            .padding(top = 20.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_red_mailbox),
                            contentDescription = "우체통"
                        )
                    }
                }




                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "따뜻따뜻한 친구들의 편지카드를 확인해보세요!",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
}

