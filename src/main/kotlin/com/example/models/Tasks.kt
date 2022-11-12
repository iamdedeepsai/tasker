package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

object Tasks : Table(){
    val username: Column<String> = varchar("username", 64)
    val title: Column<String> = varchar("title", 128)
    val description: Column<String> = varchar("description", 2048)
    val date: Column<String> = varchar("date", 64)
}

@Serializable
data class Task(val username: String, val title: String, val description: String, val date: String)