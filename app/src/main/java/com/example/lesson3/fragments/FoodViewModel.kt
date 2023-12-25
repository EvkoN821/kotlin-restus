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
    fun update_info(typeSort: Int){
        if (typeSort == 1)
            AppRepository.getInstance().listOfFood.observeForever{
                foodList.postValue(AppRepository.getInstance().getCourseFoods(this.course!!.id))
            }
        if (typeSort == 2)
            AppRepository.getInstance().listOfFood.observeForever{
                foodList.postValue(AppRepository.getInstance().getCourseFoodsByPrice(course!!.id))
            }
        if (typeSort == 3)
            AppRepository.getInstance().listOfFood.observeForever{
                foodList.postValue(AppRepository.getInstance().getCourseFoodsByWeight(course!!.id))
            }
        AppRepository.getInstance().food.observeForever{
            _food=it
        }
    }
    fun set_Group(course: Course) {
        this.course = course
        AppRepository.getInstance().listOfFood.observeForever {
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
    fun update_info(){
        AppRepository.getInstance().fetchFoods()
    }

    fun appendStudent(name:String, weight:Int, price:Int, calories:Int, info:String, comp:String, prep:Int){
        val food = Food()
        food.name = name
        food.weight = weight
        food.price = price
        food.calories = calories
        food.info = info
        food.courseID = course!!.id
        food.comp = comp
        food.prep = prep
        AppRepository.getInstance().addFood(food)
    }

    fun updateStudent(name:String, weight:Int, price:Int, calories:Int, info:String, comp:String, prep:Int){
        if (_food!=null){
            _food!!.name = name
            _food!!.weight = weight
            _food!!.price = price
            _food!!.calories = calories
            _food!!.info = info
            _food!!.comp = comp
            _food!!.prep = prep
            AppRepository.getInstance().updateFood(_food!!)
        }
    }

    fun setCurrentStudent(food: Food){
        AppRepository.getInstance().setCurrentFood(food)
    }

}