package com.example

import com.example.auth.JwtService
import com.example.auth.hash
import io.ktor.server.application.*
import com.example.plugins.*
import com.example.repository.DatabaseFactory
import com.example.repository.repo
import io.ktor.server.resources.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    DatabaseFactory.init()
    configureSecurity()
    configureSerialization()
    configureRouting()

}

