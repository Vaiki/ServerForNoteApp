package com.example.plugins

import com.example.auth.JwtService
import com.example.auth.hash
import com.example.data.model.RegisterRequest
import com.example.data.model.SimpleResponse
import com.example.data.model.UserInfo
import com.example.repository.repo
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.resources.*

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.resources.Resources

fun Application.configureRouting() {
    install(Resources)
    val db = repo()
    val jwtService = JwtService()
    val hashFunction = { s: String -> hash(s) }

    routing {
        get("/hello/world") {
            call.respondText("Hello World!")
        }
        get("/token") {
            val email = call.request.queryParameters["email"]!!
            val pass = call.request.queryParameters["pass"]!!
            val userName = call.request.queryParameters["name"]!!

            val user = UserInfo(email, hash(pass), userName)
            call.respond(jwtService.generateToken(user))
       }
     UserRoutes(db, jwtService, hashFunction)

        }


}

