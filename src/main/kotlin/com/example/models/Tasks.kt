package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

object Tasks : Table(){
    val username: Column<String> = varchar("username", 64)
    val description: Column<String> = varchar("description", 256)
    val date = varchar("date", 11)
}

@Serializable
data class Task(val username: String, val description: String, val date: String)