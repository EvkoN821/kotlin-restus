package com.example.lesson3.API

import com.example.lesson3.data.Restaurants
import com.example.lesson3.data.Restaurant
import com.example.lesson3.data.Course
import com.example.lesson3.data.Courses
import com.example.lesson3.data.Food
import com.example.lesson3.data.Foods
import com.example.lesson3.data.Spasibo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

interface ListAPI{
    @GET("restaurant")
    fun getRestaurants(): Call<Restaurants>

    @Headers("Content-Type: application/json")
    @POST("restaurant")
    fun updateRestaurant(@Body faculty: Restaurant): Call<PostResult>

    @Headers("Content-Type: application/json")
    @POST("restaurant/delete")
    fun deleteRestaurant(@Body id: PostId): Call<PostResult>

    @Headers("Content-Type: application/json")
    @PUT("restaurant")
    fun insertRestaurant(@Body faculty: Restaurant): Call<PostResult>

    @GET("course")
    fun getCourses(): Call<Courses>
    @Headers("Content-Type: application/json")
    @POST("course")
    fun updateCourse(@Body course: Course): Call<PostResult>

    @Headers("Content-Type: application/json")
    @POST("course/delete")
    fun deleteCourse(@Body id: PostId): Call<PostResult>

    @Headers("Content-Type: application/json")
    @PUT("course")
    fun insertCourse(@Body course: Course): Call<PostResult>

    @GET("food")
    fun getFoods(): Call<Foods>

    @Headers("Content-Type: application/json")
    @POST("food")
    fun updateFood(@Body food: Food): Call<PostResult>

    @Headers("Content-Type: application/json")
    @POST("food/delete")
    fun deleteFood(@Body id: PostId): Call<PostResult>

    @Headers("Content-Type: application/json")
    @PUT("food")
    fun insertFood(@Body food: Food): Call<PostResult>

    @Headers("Content-Type: application/json")
    @POST("user")
    fun login(@Body user: PostUser): Call<Spasibo>

}