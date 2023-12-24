package com.example.lesson3.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lesson3.data.Restaurant
import com.example.lesson3.myConsts.TAG
import com.example.lesson3.repository.AppRepository

class RestaurantViewModel : ViewModel() {

    var restaurantList: LiveData<List<Restaurant>> = AppRepository.getInstance().listOfRestaurant
    private var _restaurant : Restaurant = Restaurant()
    val faculty
        get()=_restaurant

    init{
        AppRepository.getInstance().restaurant.observeForever{
            _restaurant=it
            Log.d(TAG, "получен student v studlistviewmodel")
        }
    }

    fun deleteFaculty(){
        if (faculty!=null)
            AppRepository.getInstance().deleteRestaurant(faculty!!)
    }

    fun appendFaculty(facultyName: String){
        val restaurant=Restaurant()
        restaurant.name=facultyName
        AppRepository.getInstance().addRestaurant(restaurant)
    }

    fun updateFaculty(facultyName: String){
        if (_restaurant!=null){
            _restaurant!!.name=facultyName
            AppRepository.getInstance().updateRestaurant(_restaurant!!)
        }
    }

    fun setFaculty(restaurant: Restaurant){
        AppRepository.getInstance().setCurrentRestaurant(restaurant)
    }
}






