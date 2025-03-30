package com.example.mint.week2

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.mint.R


class ToYouAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToYouApp()
        }
    }
}

sealed class NavScreen(val route: String) {
    object Home : NavScreen("home")
    object Pencil : NavScreen("pencil")
    object Profile : NavScreen("profile")
    object Calendar : NavScreen("calendar") // Calendar 추가
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ToYouApp() {
    val gangwonFontFamily = FontFamily(Font(R.font.gangwon_edu_hyeonok_t))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
    ) {

    val navController = rememberNavController()
        val currentRoute = remember { mutableStateOf(NavScreen.Home.route) }

        // BottomNav에 표시할 아이템 목록
        val bottomNavItems = listOf(
            NavScreen.Home,
            NavScreen.Pencil,
            NavScreen.Profile,
            NavScreen.Calendar
        )

        // Scaffold로 전체 레이아웃 구성
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.height(100.dp),  // 앱바 자체 높이
                    backgroundColor = Color(0xFFF6F6F6),
                    title = {
                        Text(
                            text = "투유",
                            fontFamily = gangwonFontFamily,
                            fontSize = 30.sp,                 // 글자 크게
                            modifier = Modifier.offset(x = 20.dp)
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = { /* 알림 처리 */ },
                            modifier = Modifier.size(48.dp)  // IconButton 자체도 크게
                                .offset(x = -20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "알림",
                                modifier = Modifier.size(50.dp) // 아이콘 크기
                            )
                        }
                    },
                    contentColor = Color.Black,

                    elevation = 0.dp
                )
            },
            bottomBar = {
                BottomNavigation(
                    contentColor = Color.Black,
                    backgroundColor = Color(0xFFF6F6F6),
                    modifier = Modifier.height(90.dp)
                ) {
                    bottomNavItems.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                when (screen) {
                                    is NavScreen.Home -> Icon(
                                        painter = painterResource(id = R.drawable.ic_home), // VectorDrawable 사용
                                        contentDescription = "홈", modifier = Modifier.size(40.dp)
                                    )

                                    is NavScreen.Pencil -> Icon(
                                        painter = painterResource(id = R.drawable.ic_pencil), // VectorDrawable 사용
                                        contentDescription = "연필", modifier = Modifier.size(40.dp)
                                    )

                                    is NavScreen.Profile -> Icon(
                                        painter = painterResource(id = R.drawable.ic_profile), // VectorDrawable 사용
                                        contentDescription = "프로필", modifier = Modifier.size(40.dp)
                                    )

                                    is NavScreen.Calendar -> Icon(
                                        painter = painterResource(id = R.drawable.ic_calendar), // VectorDrawable 사용
                                        contentDescription = "캘린더", modifier = Modifier.size(40.dp)
                                    )
                                }
                            },
                            selected = currentRoute.value == screen.route,
                            onClick = {
                                currentRoute.value = screen.route
                                navController.navigate(screen.route) {
                                    // 뒤로가기 스택 관리
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            selectedContentColor = Color.Red,      // 진한색 (예: 빨간색)
                            unselectedContentColor = Color.Gray    // 회색
                        )
                    }
                }
            }
        ) { innerPadding ->
            // 하단/상단 바 제외한 화면 영역
            Box(modifier = Modifier.padding(innerPadding)) {
                // 화면 전환 시 애니메이션 적용
                AnimatedNavHost(
                    navController = navController,
                    startDestination = NavScreen.Home.route,
                    currentRoute = currentRoute.value,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedNavHost(
    navController: NavHostController,
    startDestination: String,
    currentRoute: String,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = currentRoute,
        transitionSpec = {
            when (targetState) {
                // 슬라이드 애니메이션을 적용할 라우트
                NavScreen.Pencil.route, NavScreen.Calendar.route -> {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(durationMillis = 300)
                    ) with slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(durationMillis = 300)
                    )
                }
                // 그 외에는 페이드 애니메이션 적용
                else -> {
                    fadeIn(animationSpec = tween(durationMillis = 300)) with
                            fadeOut(animationSpec = tween(durationMillis = 300))
                }
            }
        }
    ) { targetRoute ->
        NavHost(
            navController = navController,
            startDestination = targetRoute,
            modifier = modifier
        ) {
            composable(NavScreen.Home.route) {
                HomeScreen()
            }
            composable(NavScreen.Pencil.route) {
                PencilScreen()
            }
            composable(NavScreen.Profile.route) {
                ProfileScreen()
            }
            composable(NavScreen.Calendar.route) {
                CalendarScreen()
            }
        }
    }
}

