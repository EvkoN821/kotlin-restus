package com.example.lesson3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date

@Entity(tableName = "foods",
        indices = [Index("id"), Index("course_id", "id")],
        foreignKeys = [
            ForeignKey(
                entity = Course::class,
                parentColumns = ["id"],
                childColumns = ["course_id"],
                onDelete = ForeignKey.CASCADE
            )
        ]
)

data class Food(
    @SerializedName("id") @PrimaryKey val id: Int=0, //UUID = UUID.randomUUID(),
    @SerializedName("name") var name: String="",
    @SerializedName("weight") var weight: Int=0,
    @SerializedName("price") var price: Int=0,
    @SerializedName("calories") var calories: Int=0,
    @SerializedName("course_id") @ColumnInfo(name="course_id") var courseID: Int=0, //UUID?=null,
    @SerializedName("info") var info: String=""
){
    val shortName
        get()=name
}
