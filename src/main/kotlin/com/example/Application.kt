package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.http.content.*
import io.ktor.server.locations.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt(), host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureSerialization()
    configureRouting()
}
fun Application.configureRouting() {
    routing {
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
}