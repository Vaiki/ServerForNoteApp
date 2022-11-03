package com.example.plugins

import com.example.auth.JwtService
import com.example.auth.hash
import com.example.data.model.UserInfo
import com.example.repository.Repo
import io.ktor.server.routing.*

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.resources.Resources

fun Application.configureRouting() {

    routing {
        get("/hello/world") {
            call.respondText("Hello World!")
        }
    }

}

