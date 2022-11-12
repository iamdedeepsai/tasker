package com.example

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.json.simple.JSONArray
import java.io.File
import java.util.*

fun Route.indexRoute(){
    static("/"){
        staticBasePackage = "files"
        resource("index.html")
        defaultResource("index.html")
        static("css"){
            resources("css")
        }
        static("js"){
            resources("js")
        }
    }
    loginRoute()
}

fun Route.loginRoute(){
    route("/login"){
        post {
            print("login")
            val params = call.receiveParameters()
            val username = params["username"].toString();
            val password = params["password"].toString()
            val verified = params["verified"].toBoolean()
            print("username: $username, password: $password, verified: $verified")
            val logged: Boolean = login(username, password, verified)
            if(logged){
                clockin(username)
                call.respond(HttpStatusCode.OK, "Valid")
            }else {
                call.respond(HttpStatusCode.OK, "Invalid")
            }
        }
    }
    route("/register"){
        post {
            val params = call.receiveParameters()
            val username = params["username"].toString();
            val password = params["password"].toString()
            print("username: $username, password: $password")
            if(register(username, password)){
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotAcceptable)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
    route("/logout"){
        post {
            val params = call.receiveParameters()
            val username = params["username"].toString();
            clockout(username)
            call.respond(HttpStatusCode.OK)
        }
    }
}