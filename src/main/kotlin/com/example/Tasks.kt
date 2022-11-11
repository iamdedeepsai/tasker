package com.example

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.homeRoute(){
    static("/home"){
        staticBasePackage = "files"
        resource("home.html")
        defaultResource("home.html")
        static("css"){
            resources("css")
        }
        static("js"){
            resources("js")
        }
        route("/tasks"){
            get {
                val params = call.receiveParameters()
                val username = params["username"].toString()
            }
        }
    }
}