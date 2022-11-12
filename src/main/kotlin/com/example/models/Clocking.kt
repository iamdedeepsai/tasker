package com.example.models

import org.jetbrains.exposed.sql.Table

object Clocking : Table() {
    val username = varchar("username", 64)
    val clockin = datetime("clockin")
    val clockout = datetime("clockout")
}