package com.example.jack_week4.utils

import com.kakao.sdk.common.KakaoSdk
import android.app.Application

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "f88d5d61a7d93882ab0d6edd5902a93b")
    }
}
