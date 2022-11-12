package com.example

import com.example.models.Task
import com.example.models.Tasks
import com.example.models.User
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    private val jdbcUrl = System.getenv("JDBC_DATABASE_URL")
    private val username = System.getenv("JDBC_DATABASE_USERNAME")
    private val password = System.getenv("JDBC_DATABASE_PASSWORD")
    init {
        Database.connect(hikari())
        transaction {
            SchemaUtils.drop(User, Tasks)
            SchemaUtils.create(User, Tasks)
        }
    }
    private fun hikari(): HikariDataSource{
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = jdbcUrl
        config.username = username
        config.password = password
        config.validate()
        return HikariDataSource(config)
    }
    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}
suspend fun login(username: String, password: String, verified: Boolean): Boolean{
    DatabaseFactory.dbQuery {
        User.select {
            User.username eq username
        }
    }.forEach {
        if(verified || Password.verifyUserPassword(password, it[User.password])){
            print("verified: $verified")
            return true
        }
    }
    return false
}
suspend fun register(username: String, password: String): Boolean{
    return try {
        DatabaseFactory.dbQuery {
            User.insert {
                it[this.username] = username
                it[this.password] = Password.generate(password)
            }
        }
        true
    } catch (e: Exception) {
        false
    }
}
suspend fun getTasks(username: String): MutableList<Task> {
    val arr = mutableListOf<Task>()
    DatabaseFactory.dbQuery {
        Tasks.select {
            Tasks.username eq username
            Tasks.date eq CurrentDateTime().toString()
        }
    }.forEach {
        print(it[Tasks.username] + "" + it[Tasks.title] + "" + it[Tasks.description] +  it[Tasks.date] + "" + CurrentDateTime().toString())
        arr.add(Task(it[Tasks.username], it[Tasks.title], it[Tasks.description], it[Tasks.date]))
    }
    return arr
}