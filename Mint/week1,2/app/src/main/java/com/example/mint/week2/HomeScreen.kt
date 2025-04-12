package com.example.mint.week2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.mint.R


@Composable
fun HomeScreen() {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F6F6)),

            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
//                    .verticalScroll(rememberScrollState()),
            ) {
                Spacer(modifier = Modifier.height(130.dp))
                VerticalSegmentedControl()

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Box(
                        modifier = Modifier
                            .padding(top = 40.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_speech_buddle),
                            contentDescription = "말풍선"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 30.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_toyou_logo),
                            contentDescription = "우체통",
                            modifier = Modifier
                                .zIndex(1f)
                        )
                    }

                    //
                    Box(
                        modifier = Modifier

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_mailbox),
                            contentDescription = "우체통 기둥",
                            modifier = Modifier
                                .zIndex(0f)
                                .offset(y = (-10).dp)
//                                .size(width = 60.dp, height = 230.dp) // <- 원하는 크기로 강제
                        )

                    }
                }




//                Spacer(modifier = Modifier.height(20.dp))
//                Text(
//                    text = "따뜻따뜻한 친구들의 편지카드를 확인해보세요!",
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        color = Color.Gray
//                    ),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(horizontal = 16.dp)
//                )
            }
        }
}

