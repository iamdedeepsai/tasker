package com.example

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.indexRoute(){
    static("/"){
        staticBasePackage = "files"
        resource("index.html")
        defaultResource("index.html")
        static("assets"){
            resources("css")
            resources("js")
        }
    }
}

fun Route.loginRoute(){
    get("/login") {

    }
    post("/login") {
        val params = call.receiveParameters()
        val username = params["username"].toString();
        val password = params["password"].toString()
        db.login(username, password)
    }
}