package com.example.week7.data.entities

import com.google.gson.annotations.SerializedName

// 제네릭 타입 T로 수정
data class BaseResponse<T>(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: T?
)

// 로그인 결과 전용 데이터 클래스
data class LoginResult(
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("accessToken") val accessToken: String
)

// 회원가입 결과 전용 데이터 클래스
data class SignUpResult(
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
