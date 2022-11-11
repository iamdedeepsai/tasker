package com.example

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
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
}

fun Route.loginRoute(){
    route("/login"){
        post {
            val params = call.receiveParameters()
            val username = params["username"].toString();
            val password = params["password"].toString()
            login(username, password)
        }
    }
    route("/register"){
        post {
            val params = call.receiveParameters()
            val uuid = UUID.fromString(params["uuid"].toString())
            val username = params["username"].toString();
            val password = params["password"].toString()
            register(uuid, username, password)
        }
    }

}