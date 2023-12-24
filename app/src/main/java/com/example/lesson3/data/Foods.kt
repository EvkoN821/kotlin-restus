package com.example.lesson3.data

import com.google.gson.annotations.SerializedName

class Foods {
    @SerializedName("item") lateinit var items: List<Food>
}