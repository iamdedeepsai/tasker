package com.example.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.CurrentDateTime
import org.jetbrains.exposed.sql.Date
import org.jetbrains.exposed.sql.Table

object Tasks : Table(){
    val username: Column<String> = varchar("username", 64)
    val title: Column<String> = varchar("title", 128)
    val description: Column<String> = varchar("description", 2048)
    val date = date("date").defaultExpression(CurrentDateTime())
}