package com.example.pouchy.data.dao

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pouchy.data.entity.Transaction
import com.example.pouchy.data.entity.User

data class UserWithTransactions(
    @Embedded val user: User,
    @Relation(
        parentColumn = "uid",
        entityColumn = "id_user"
    )
    val transactions: List<Transaction>
)
