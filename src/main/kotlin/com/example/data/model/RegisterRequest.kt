package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(

    val email: String,
    val name: String,
    val password: String

)
