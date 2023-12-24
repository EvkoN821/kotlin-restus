package com.example.lesson3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "courses",
        indices = [Index("id"),Index("restaurant_id")],
        foreignKeys = [
            ForeignKey(
                entity = Restaurant::class,
                parentColumns = ["id"],
                childColumns = ["restaurant_id"],
                onDelete = ForeignKey.CASCADE
            )
        ]
)

data class Course(
    @SerializedName("id") @PrimaryKey var id: Int=0, //UUID = UUID.randomUUID(),
    @SerializedName("name") @ColumnInfo(name= "name") var name: String="",
    @SerializedName("restaurant_id") @ColumnInfo(name= "restaurant_id") var restaurantID: Int=0 //UUID?=null
)
