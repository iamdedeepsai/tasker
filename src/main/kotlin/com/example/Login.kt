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
                call.respond(HttpStatusCode.OK)
            }else {
                call.respond(HttpStatusCode.NotAcceptable)
            }
        }
    }
    route("/register"){
        post {
            val multipartData = call.receiveMultipart()
            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        var fileName = part.originalFileName as String
                        var fileBytes = part.streamProvider().readBytes()
                        File("uploads/$fileName").writeBytes(fileBytes)
                    }
                    else -> {}
                }
                part.dispose()
            }
        }
//            val params = call.receiveParameters()
//            val username = params["username"].toString();
//            val password = params["password"].toString()
//            val images = params["images"]
//            print("username: $username, password: $password")
//            register(username, password)
//            call.respond(HttpStatusCode.OK)
    }

}