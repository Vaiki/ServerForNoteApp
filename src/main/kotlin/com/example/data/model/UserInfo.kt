package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val email: String,
    val password: String,
    val name: String
)
