package com.barcodescanner.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val barcode: String,
    val name: String,
    val description: String = "",
    val price: Double = 0.0,
    val quantity: Int = 1,
    val timestamp: Date = Date(),
    val category: String = "",
    val notes: String = ""
)
