package com.example.mint.week2

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mint.R


@Composable
fun VerticalSegmentedControl() {
    val gangwonFontFamily = FontFamily(Font(R.font.gangwon_edu_hyeonok_t))

    // 각 항목의 높이 및 전체 높이 (두 항목)
    val segmentHeight = 35.dp
    val totalHeight = segmentHeight * 2

    // 선택된 항목 (0: "날짜", 1: "멘트")
    var selectedIndex by remember { mutableStateOf(0) }
    // 드래그에 따른 임시 오프셋 (픽셀 단위)
    var dragOffset by remember { mutableStateOf(0f) }

    val density = LocalDensity.current
    // 선택된 항목의 타겟 오프셋 (픽셀 단위)
    val targetOffsetPx = with(density) { selectedIndex * segmentHeight.toPx() }
    // 드래그 값과 타겟 오프셋을 합쳐서 애니메이션 처리 (드래그 후 자동 정렬)
    val animatedOffsetPx by animateFloatAsState(targetValue = targetOffsetPx + dragOffset)

    // 드래그 상태. 드래그 중에는 dragOffset가 누적되고,
    // 드래그가 끝나면 targetOffset을 결정한 후 dragOffset를 0으로 리셋.
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(totalHeight)
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    dragOffset += delta
                },
                onDragStopped = {
                    // 현재 인디케이터의 최종 위치(픽셀)
                    val currentPos = targetOffsetPx + dragOffset
                    // 중간값(세그먼트 높이의 절반, 픽셀 단위)
                    val threshold = with(density) { segmentHeight.toPx() / 2 }
                    // 선택을 결정: 만약 현재 인디케이터가 첫 번째 항목에서 threshold 이상 아래로 내려갔다면 1, 아니면 0
                    selectedIndex = if (currentPos >= threshold) 1 else 0
                    dragOffset = 0f
                }
            ),
        contentAlignment = Alignment.TopStart
    ) {
        // 회색 선택 인디케이터 (배경)
        Box(
            modifier = Modifier
                .offset { IntOffset(x = 0, y = animatedOffsetPx.toInt()) }
                .fillMaxWidth()
                .height(segmentHeight)
                .background(Color.LightGray)
        )
        // 항목들을 배치하는 Column
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(segmentHeight),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "날짜", fontSize = 30.sp, color = Color.Black, fontFamily = gangwonFontFamily)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(segmentHeight),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "멘트", fontSize = 30.sp, color = Color.Black, fontFamily = gangwonFontFamily)
            }
        }
    }
}

