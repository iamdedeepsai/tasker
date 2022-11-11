package com.example

import com.example.models.Tasks
import com.example.models.User
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object DatabaseFactory {
    private val jdbcUrl = System.getenv("JDBC_DATABASE_URL")
    private val username = System.getenv("JDBC_DATABASE_USERNAME")
    private val password = System.getenv("JDBC_DATABASE_PASSWORD")
    init {
        Database.connect(hikari())
        transaction {
            SchemaUtils.createMissingTablesAndColumns(User, Tasks)
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
suspend fun login(username: String, password: String): UUID?{
    DatabaseFactory.dbQuery {
        User.select {
            User.username eq username
        }
    }.forEach{
        if(Password.verifyUserPassword(password, it[User.password])){
            return it[User.uuid]
        }
    }
    return null
}
suspend fun register(uuid: UUID, username: String, password: String){
    DatabaseFactory.dbQuery {
        User.insert {
            it[this.uuid] = uuid
            it[this.username] = username
            it[this.password] = Password.generate(password)
        }
    }
}