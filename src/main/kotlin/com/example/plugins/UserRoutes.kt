package com.example.plugins

import com.example.auth.JwtService
import com.example.data.model.LoginRequest
import com.example.data.model.RegisterRequest
import com.example.data.model.SimpleResponse
import com.example.data.model.UserInfo
import com.example.repository.Repo
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val API_VERSION ="/v1"
const val USERS = "$API_VERSION/users"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"


@Resource(REGISTER_REQUEST)
class UserRegisterRoute
@Resource(LOGIN_REQUEST)
class UserLoginRoute


// регистрация пользователя. Добавление в бд и передача токена пользователю
fun Route.UserRoutes(
    db: Repo,
    jwtService: JwtService,
    hashFunction: (String)->String
    ){
    post("/v1/users/register"){
        val registerRequest = try {
            call.receive<RegisterRequest>()
        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest,SimpleResponse(false,"Missing Some Fields"))
            return@post
        }
        try {
            val user = UserInfo(registerRequest.email,hashFunction(registerRequest.password),registerRequest.name)
            db.addUser(user)
            call.respond(HttpStatusCode.OK,SimpleResponse(true,jwtService.generateToken(user)))
        }catch (e:Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse(false,e.message?:"Some Problem Occurred"))
        }

    }

    //вход пользователя
    post("v1/users/login") {
        val loginRequest = try {
            call.receive<LoginRequest>()
        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest,SimpleResponse(false,"Missing Some Fields"))
            return@post
        }
        try {
            val user = db.findUserByEmail(loginRequest.email)
            if(user==null){
                call.respond(HttpStatusCode.BadRequest,SimpleResponse(false,"Wrong Email ID"))
            }else{
                if (user.password == hashFunction(loginRequest.password)){
                    call.respond(HttpStatusCode.OK,SimpleResponse(true,jwtService.generateToken(user)))
                }else{
                    call.respond(HttpStatusCode.BadRequest,SimpleResponse(false,"Password Incorrect!"))
                }
            }
        }catch (e:Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse(false, e.message?:"Some Problem Occurred"))
        }
    }


}
