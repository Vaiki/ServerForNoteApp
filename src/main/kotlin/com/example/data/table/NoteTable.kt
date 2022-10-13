package com.example.data.table

import org.jetbrains.exposed.sql.Table

object NoteTable : Table() {
    val id = varchar("id", 512)

    //внешний ключ
    val userEmail = varchar("userEmail", 512).references(UserTable.email)

    val noteTitle = text("noteTitle")
    val description = text("description")
    val date = long("date")
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}