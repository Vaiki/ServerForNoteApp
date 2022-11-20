package com.example.data.model

import io.ktor.server.auth.*


data class UserInfo(
    val email: String,
    val password: String,
    val name: String
):Principal
