package com.example.lesson3.data

import com.google.gson.annotations.SerializedName

class Restaurants {
    @SerializedName("item") lateinit var items: List<Restaurant>
}