package com.example.lesson3.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson3.data.Course
import com.example.lesson3.data.Food
import com.example.lesson3.repository.AppRepository
import java.util.Date

class FoodViewModel : ViewModel() {
    var foodList: MutableLiveData<List<Food>> = MutableLiveData()

    private var _food: Food?= null
    val student
        get()= _food

    var course: Course? = null

    fun set_Group(course: Course) {
        this.course = course
        AppRepository.getInstance().listOfFood.observeForever{
            foodList.postValue(AppRepository.getInstance().getCourseFoods(course.id))
        }
        AppRepository.getInstance().food.observeForever{
            _food=it
        }
    }

    fun deleteStudent() {
        if(student!=null)
            AppRepository.getInstance().deleteFood(student!!)
    }

    fun appendStudent(name:String, weight:Int, price:Int, calories:Int, info:String){
        val food = Food()
        food.name = name
        food.weight = weight
        food.price = price
        food.calories = calories
        food.info = info
        food.courseID = course!!.id
        AppRepository.getInstance().addFood(food)
    }

    fun updateStudent(name:String, weight:Int, price:Int, calories:Int, info:String){
        if (_food!=null){
            _food!!.name = name
            _food!!.weight = weight
            _food!!.price = price
            _food!!.calories = calories
            _food!!.info = info
            AppRepository.getInstance().updateFood(_food!!)
        }
    }

    fun setCurrentStudent(food: Food){
        AppRepository.getInstance().setCurrentFood(food)
    }

}