package com.example

import com.example.models.Task
import com.example.models.Tasks
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import org.jetbrains.exposed.sql.CurrentDateTime
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import org.json.simple.JSONArray

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
                var arr = getTasks(username)
                call.respond(arr)
            }
            post("/add") {
                val params = call.receiveParameters()
                val username = params["username"].toString()
                val title = params["title"].toString()
                val description = params["description"].toString()
                val date = DateTime(CurrentDateTime())
                if(addTask(username, title, description, date)){
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NotAcceptable)
                }
            }
            post("/remove") {
                val params = call.receiveParameters()
                val username = params["username"].toString()
                val title = params["title"].toString()
                val description = params["description"].toString()
                val date = DateTime(CurrentDateTime())
                if(removeTask(username, title, description, date)){
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NotAcceptable)
                }
            }
        }
    }
}

suspend fun addTask(username: String, title: String, description: String, date: DateTime): Boolean{
    return try {
        DatabaseFactory.dbQuery {
            Tasks.insert{
                it[this.username] = username
                it[this.title] = title
                it[this.description] = description
                it[this.date] = date.toString()
            }
        }
        true
    } catch (e: Exception) {
        false
    }
}
suspend fun removeTask(username: String, title: String, description: String, date: DateTime): Boolean{
    return try {
        DatabaseFactory.dbQuery {
            Tasks.deleteWhere {
                Tasks.username eq username
                Tasks.title eq title
                Tasks.description eq description
                Tasks.date eq date.toString()
            }
        }
        true
    } catch (e: Exception) {
        false
    }
}