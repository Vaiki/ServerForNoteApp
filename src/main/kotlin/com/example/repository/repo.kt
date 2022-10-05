package com.example.repository

import com.example.data.model.UserInfo
import com.example.data.table.UserTable
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class repo {

    suspend fun addUser(user:UserInfo){
      dbQuery{
          UserTable.insert {ut->
              ut[UserTable.email] = user.email
              ut[UserTable.password] = user.password
              ut[UserTable.name] = user.name
          }
      }
    }

    suspend fun findUserByEmail(email:String) = dbQuery {
        UserTable.select { UserTable.email.eq(email) }
            .map {
                rowToUser(it) }
            .singleOrNull()
    }


    private fun rowToUser(row:ResultRow?):UserInfo?{
        return if (row == null){
            null
        } else UserInfo(
            email = row[UserTable.email],
            password = row[UserTable.password],
            name = row[UserTable.name]
        )
    }



}