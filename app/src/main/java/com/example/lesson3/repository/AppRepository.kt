package com.example.lesson3.repository

import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.lesson3.API.ListAPI
import com.example.lesson3.API.ListConnection
import com.example.lesson3.API.PostId
import com.example.lesson3.API.PostResult
import com.example.lesson3.API.PostUser
import com.example.lesson3.MainActivity
import com.example.lesson3.MyApplication
import com.example.lesson3.data.Restaurants
import com.example.lesson3.data.Restaurant
import com.example.lesson3.data.Course
import com.example.lesson3.data.Courses
import com.example.lesson3.data.Food
import com.example.lesson3.data.Foods
import com.example.lesson3.data.Spasibo
import com.example.lesson3.database.ListDatabase
import com.example.lesson3.myConsts.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository {
    companion object{
        private var INSTANCE: AppRepository?=null

        fun getInstance(): AppRepository {
            if (INSTANCE==null){
                INSTANCE= AppRepository()
            }
            return INSTANCE?:
            throw IllegalStateException("реп не иниц")
        }
    }

    var restaurant: MutableLiveData<Restaurant> = MutableLiveData()
    var course: MutableLiveData<Course> = MutableLiveData()
    var food: MutableLiveData<Food> = MutableLiveData()


    fun getRestaurantPosition(restaurant: Restaurant): Int=listOfRestaurant.value?.indexOfFirst {
        it.id==restaurant.id } ?:-1

    fun getRestaurantPosition()=getRestaurantPosition(restaurant.value?: Restaurant())

    fun setCurrentRestaurant(position:Int){
        if (position<0 || (listOfRestaurant.value?.size!! <= position))
            return setCurrentRestaurant(listOfRestaurant.value!![position])
    }

    fun setCurrentRestaurant(_restaurant: Restaurant){
        restaurant.postValue(_restaurant)
    }

    fun saveData(){

    }

    fun loadData(){
        fetchRestaurants()
    }

    fun getCoursePosition(course: Course): Int=listOfCourse.value?.indexOfFirst {
        it.id==course.id } ?:-1

    fun getCoursePosition()=getCoursePosition(course.value?: Course())

    fun setCurrentCourse(position:Int){
        if (listOfCourse.value==null || position<0 ||
            (listOfCourse.value?.size!! <=position))
            return setCurrentCourse(listOfCourse.value!![position])
    }

    fun setCurrentCourse(_course: Course){
        course.postValue(_course)
    }

    val restaurantCourses
        get()= listOfCourse.value?.filter { it.restaurantID == (restaurant.value?.id ?: 0) }?.sortedBy { it.name }?: listOf()

    fun getFoodPosition(food: Food): Int=listOfFood.value?.indexOfFirst {
        it.id==food.id } ?:-1

    fun getFoodPosition()=getFoodPosition(food.value?: Food())

    fun setCurrentFood(position:Int){
        if (listOfFood.value==null || position<0 ||
            (listOfFood.value?.size!! <=position))
            return setCurrentFood(listOfFood.value!![position])
    }

    fun setCurrentFood(_food: Food){
        food.postValue(_food)
    }

    fun getCourseFoods(courseID: Int) =
        (listOfFood.value?.filter { it.courseID == courseID }?.sortedBy { it.shortName }?: listOf())
    fun getCourseFoodsByPrice(courseID: Int) =
        (listOfFood.value?.filter { it.courseID == courseID }?.sortedBy { it.getPrice }?: listOf())
    fun getCourseFoodsByWeight(courseID: Int) =
        (listOfFood.value?.filter { it.courseID == courseID }?.sortedBy { it.getWeight }?: listOf())
    private val listDB by lazy {OfflineDBRepository(ListDatabase.getDatabase(MyApplication.context).listDAO())}

    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)

    fun onDestroy(){
        myCoroutineScope.cancel()
    }

    val listOfRestaurant: LiveData<List<Restaurant>> = listDB.getFaculty().asLiveData()
    val listOfCourse: LiveData<List<Course>> = listDB.getAllGroups().asLiveData()
    val listOfFood: LiveData<List<Food>> = listDB.getAllStudents().asLiveData()


    private var listAPI = ListConnection.getClient().create(ListAPI::class.java)

    fun fetchRestaurants(){
        listAPI.getRestaurants().enqueue(object: Callback<Restaurants> {
            override fun onFailure(call: Call<Restaurants>, t :Throwable){
                Log.d(TAG,"Ошибка получения списка факультетов", t)
            }
            override fun onResponse(
                call : Call<Restaurants>,
                response: Response<Restaurants>
            ){
                if (response.code()==200){
                    val restaurants = response.body()
                    val items = restaurants?.items?:emptyList()
                    Log.d(TAG,"Получен список факультетов $items")
                    for (f in items){
                        println(f.id::class.java.typeName)
                        println(f.name::class.java.typeName)
                    }
                    myCoroutineScope.launch{
                        listDB.deleteAllFaculty()
                        for (f in items){
                            listDB.insertFaculty(f)
                        }
                    }
                    fetchCourses()
                }
            }
        })
    }

    fun addRestaurant(restaurant: Restaurant){
        listAPI.insertRestaurant(restaurant)
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response: Response<PostResult>){
                    if (response.code()==200) fetchRestaurants()
                }
                override fun onFailure(call:Call<PostResult>,t: Throwable){
                    Log.d(TAG,"Ошибка добавления факультета",t)
                }
            })
    }

    fun updateRestaurant(restaurant: Restaurant){
        listAPI.updateRestaurant(restaurant)
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response: Response<PostResult>){
                    if (response.code()==200) fetchRestaurants()
                }
                override fun onFailure(call:Call<PostResult>,t: Throwable){
                    Log.d(TAG,"Ошибка обновления факультета",t)
                }
            })
    }

    fun deleteRestaurant(restaurant: Restaurant){
        listAPI.deleteRestaurant(PostId(restaurant.id))
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response: Response<PostResult>){
                    if (response.code()==200) fetchRestaurants()
                }
                override fun onFailure(call:Call<PostResult>,t: Throwable){
                    Log.d(TAG,"Ошибка удаления факультета",t)
                }
            })
    }

    fun fetchCourses(){
        listAPI.getCourses().enqueue(object: Callback<Courses> {
            override fun onFailure(call: Call<Courses>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка групп", t)
            }

            override fun onResponse(
                call: Call<Courses>,
                response: Response<Courses>
            ) {
                if (response.code() == 200) {
                    val courses = response.body()
                    val items = courses?.items ?: emptyList()
                    Log.d(TAG, "Получен список групп $items")
                    myCoroutineScope.launch {
                        listDB.deleteAllGroups()
                        for (g in items) {
                            listDB.insertGroup(g)
                        }
                    }
                    fetchFoods()
                }
            }
        })
    }

    fun addCourse(course: Course){
        listAPI.insertCourse(course)
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response:Response<PostResult>){
                    if (response.code()==200) fetchCourses()
                }
                override fun onFailure(call:Call<PostResult>,t:Throwable){
                    Log.d(TAG,"Ошибка обновления группы", t)
                }
            })
    }

    fun updateCourse(course: Course){
        listAPI.updateCourse(course)
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response:Response<PostResult>){
                    if (response.code()==200) fetchCourses()
                }
                override fun onFailure(call:Call<PostResult>,t:Throwable){
                    Log.d(TAG,"Ошибка записи группы", t)
                }
            })
    }

    fun deleteCourse(course: Course){
        listAPI.deleteCourse(PostId(course.id))
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response:Response<PostResult>){
                    if (response.code()==200) fetchCourses()
                }
                override fun onFailure(call:Call<PostResult>,t:Throwable){
                    Log.d(TAG,"Ошибка удаления группы", t)
                }
            })
    }

    fun fetchFoods(){
        listAPI.getFoods().enqueue(object : Callback<Foods>{
            override fun onFailure(call:Call<Foods>,t : Throwable){
                Log.d(TAG,"Ошибка получения списка студентов",t)
            }
            override fun onResponse(
                call:Call<Foods>,
                response: Response<Foods>
            ){
                if(response.code()==200){
                    val foods = response.body()
                    val items = foods?.items?: emptyList()
                    Log.d(TAG,"Получен список студентов $items")
                    myCoroutineScope.launch {
                        listDB.deleteAllStudents()
                        for (s in items){
                            listDB.insertStudent(s)
                        }
                    }
                }
            }
        })
    }

    fun addFood(food: Food){
        listAPI.insertFood(food)
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response:Response<PostResult>){
                    if (response.code()==200) fetchFoods()
                }
                override fun onFailure(call:Call<PostResult>,t:Throwable){
                    Log.d(TAG,"Ошибка записи студента", t.fillInStackTrace())
                }
            })
    }

    fun updateFood(food: Food){
        listAPI.updateFood(food)
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response:Response<PostResult>){
                    if (response.code()==200) fetchFoods()
                }
                override fun onFailure(call:Call<PostResult>,t:Throwable){
                    Log.d(TAG,"Ошибка обновления студента", t.fillInStackTrace())
                }
            })
    }

    fun deleteFood(food: Food){
        listAPI.deleteFood(PostId(food.id))
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response:Response<PostResult>){
                    if (response.code()==200) fetchFoods()
                }
                override fun onFailure(call:Call<PostResult>,t:Throwable){
                    Log.d(TAG,"Ошибка удаления студента", t.fillInStackTrace())
                }
            })
    }

    fun login(userName: String, pwd : String, dickandballs : TextView){
        listAPI.login(PostUser(userName,pwd)).enqueue(object: Callback<Spasibo> {
            override fun onFailure(call: Call<Spasibo>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка групп", t)
            }

            override fun onResponse(
                call: Call<Spasibo>,
                response: Response<Spasibo>
            ) {
                if (response.code() == 200) {
                    val resp = response.body()
                    dickandballs.text = resp?.items.toString()
                    dickandballs.callOnClick()
                }
            }
        })
    }

}





















