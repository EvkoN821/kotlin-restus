package com.example.lesson3.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson3.data.Course
import com.example.lesson3.myConsts.TAG
import com.example.lesson3.repository.AppRepository

class CourseViewModel : ViewModel() {

    var courseList: MutableLiveData<List<Course>> = MutableLiveData()
    private var _course : Course? = null
    val group
        get()=_course

    init {
        AppRepository.getInstance().listOfCourse.observeForever{
            courseList.postValue(AppRepository.getInstance().restaurantCourses)
        }

        AppRepository.getInstance().course.observeForever{
            _course=it
            Log.d(TAG, "GroupViewModel текущая группа $it")
        }

        AppRepository.getInstance().restaurant.observeForever{
            courseList.postValue(AppRepository.getInstance().restaurantCourses)
        }
    }

    fun deleteGroup(){
        if(group!=null)
            AppRepository.getInstance().deleteCourse(group!!)
    }

    fun appendGroup(groupName: String){
        val course=Course()
        course.name=groupName
        course.restaurantID=faculty!!.id
        AppRepository.getInstance().addCourse(course)
    }

    fun updateGroup(groupName: String){
        if (_course!=null){
            _course!!.name=groupName
            AppRepository.getInstance().updateCourse(_course!!)
        }
    }

    fun setCurrentGroup(position: Int){
        if ((courseList.value?.size ?: 0)>position)
            courseList.value?.let{ AppRepository.getInstance().setCurrentCourse(it.get(position))}
    }

    fun setCurrentGroup(course: Course){
        AppRepository.getInstance().setCurrentCourse(course)
    }

    val getGroupListPosition
        get()= courseList.value?.indexOfFirst { it.id==group?.id } ?: -1

    val faculty
        get()=AppRepository.getInstance().restaurant.value
}