package com.example.movie.model

data class RequestTokenResponse(
    val success: Boolean,
    val request_token: String
)

data class SessionResponse(
    val success: Boolean,
    val session_id: String
)
data class RequestTokenRequest(
    val username: String,
    val password: String,
    val request_token: String
)
data class RequestTokenBody(val request_token: String)
