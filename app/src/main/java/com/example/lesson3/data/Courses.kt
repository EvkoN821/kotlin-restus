package com.example.lesson3.data

import com.google.gson.annotations.SerializedName

class Courses {
    @SerializedName("item") lateinit var items: List<Course>
}