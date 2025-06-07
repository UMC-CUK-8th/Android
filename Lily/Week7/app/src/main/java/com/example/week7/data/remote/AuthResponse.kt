package com.example.week7.data.remote

data class AuthResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ResultData
)

data class ResultData(
    val memberId: Int,
    val createdAt: String,
    val updatedAt: String
)

