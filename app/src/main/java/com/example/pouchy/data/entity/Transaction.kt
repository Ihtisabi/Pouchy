package com.example.pouchy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.example.pouchy.data.DateConverter

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["uid"],
            childColumns = ["id_user"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id_user"])]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "Tanggal")@TypeConverters(DateConverter::class) val tanggal: String,
    @ColumnInfo(name = "Type") val type: String,
    @ColumnInfo(name = "Kategori") val kategori: String,
    @ColumnInfo(name = "Deskripsi") val deskripsi: String,
    @ColumnInfo(name = "Jumlah") val jumlah: Double,
    @ColumnInfo(name = "id_user") val id_user: Int
)
