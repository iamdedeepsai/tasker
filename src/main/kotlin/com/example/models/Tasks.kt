package com.example.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Tasks : Table(){
    val uuid: Column<Int> = integer("uuid")
    val title: Column<String> = varchar("title", 128)
    val description: Column<String> = varchar("description", 2048)

}