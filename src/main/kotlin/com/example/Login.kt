package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
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
                call.respond(HttpStatusCode.OK)
            }else {
                call.respond(HttpStatusCode.NotAcceptable)
            }
        }
    }
    route("/register"){
        post {
            print("register")
            val params = call.receiveParameters()
            val username = params["username"].toString();
            val password = params["password"].toString()
            print("username: $username, password: $password")
            register(username, password)
            call.respond(HttpStatusCode.OK)
        }
    }

}