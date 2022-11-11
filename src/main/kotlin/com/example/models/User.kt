package com.example.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object User : Table() {
    val uuid: Column<UUID> = uuid("userid")
    val username: Column<String> = varchar("username", 64)
    val password: Column<String> = varchar("password", 64)


}